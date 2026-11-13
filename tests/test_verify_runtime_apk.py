"""Launch preflight checks against actual DEX definitions and APK payloads."""
from pathlib import Path
import hashlib
import json
import os
import struct
import sys
import tempfile
import unittest
from unittest import mock
import zipfile
import zlib

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
import verify_runtime_apk as verify


def seal(data):
    data = bytearray(data)
    data[12:32] = hashlib.sha1(data[32:]).digest()
    struct.pack_into("<I", data, 8, zlib.adler32(data[12:]) & 0xffffffff)
    return bytes(data)


def dex(definitions, literals=()):
    """Small real class-def tables; string references alone define no classes."""
    names = sorted(set(definitions) | set(definitions.values()) - {None})
    strings = names + list(literals)
    strings_off = 112
    types_off = strings_off + 4 * len(strings)
    classes_off = types_off + 4 * len(names)
    data_off = classes_off + 32 * len(definitions)
    data = bytearray(data_off)
    for index, value in enumerate(strings):
        encoded = value.encode()
        assert len(encoded) < 128
        struct.pack_into("<I", data, strings_off + 4 * index, len(data))
        data.extend(bytes([len(value)]) + encoded + b"\0")
    data.extend(bytes(-len(data) % 4))
    map_off = len(data)
    entries = [(0, 1, 0), (1, len(strings), strings_off), (2, len(names), types_off),
               (6, len(definitions), classes_off), (0x2002, len(strings), data_off),
               (0x1000, 1, map_off)]
    entries = [entry for entry in entries if entry[1]]
    data.extend(struct.pack("<I", len(entries)))
    for kind, size, offset in entries:
        data.extend(struct.pack("<HHII", kind, 0, size, offset))
    data[:8] = b"dex\n035\0"
    struct.pack_into("<20I", data, 32, len(data), 112, 0x12345678, 0, 0, map_off,
                     len(strings), strings_off if strings else 0,
                     len(names), types_off if names else 0, 0, 0, 0, 0,
                     0, 0, len(definitions), classes_off if definitions else 0,
                     len(data) - data_off, data_off)
    for index, name in enumerate(names):
        struct.pack_into("<I", data, types_off + 4 * index, strings.index(name))
    for index, (name, superclass) in enumerate(sorted(definitions.items())):
        struct.pack_into("<8I", data, classes_off + 32 * index, names.index(name), 1,
                         names.index(superclass) if superclass else 0xffffffff,
                         0, 0xffffffff, 0, 0, 0)
    return seal(data)


class RuntimeApkTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory(prefix="runtime-apk-")
        self.addCleanup(self.temp.cleanup)
        self.directory = Path(self.temp.name)
        self.original = self.directory / "original.apk"
        self.apk = self.directory / "app.apk"
        self.classes = {name: "Ljava/lang/Object;" for name in verify.REQUIRED}
        self.write(self.original)
        self.write(self.apk)
        patch = mock.patch.object(verify, "manifest", return_value=(
            "com.gameloft.android.ANMP.GloftGF2F", "javax.microedition.midlet.App",
            "com.gameloft.android.ANMP.GloftGF2F.Start"))
        patch.start()
        self.addCleanup(patch.stop)

    def write(self, path, definitions=None, extra=None, asset=b"farm", compression=zipfile.ZIP_DEFLATED,
              dexname="classes.dex"):
        with zipfile.ZipFile(path, "w") as archive:
            archive.writestr("AndroidManifest.xml", b"binary manifest fixture")
            archive.writestr(dexname, dex(self.classes if definitions is None else definitions))
            archive.writestr("assets/farm.bin", asset, compress_type=compression)
            archive.writestr("lib/x86/libfarm.so", b"native", compress_type=zipfile.ZIP_STORED)
            for name, contents in (extra or {}).items():
                archive.writestr(name, contents)

    def check(self, **kwargs):
        return verify.verify_apk(self.apk, self.original, self.directory, **kwargs)

    def test_complete_definitions_pass(self):
        self.assertEqual(verify.dex_classes(dex(self.classes)), self.classes)
        self.check()

    def test_class_name_literal_does_not_define_application(self):
        missing = "Ljavax/microedition/midlet/App;"
        definitions = {name: value for name, value in self.classes.items() if name != missing}
        self.write(self.apk, definitions, {"classes2.dex": dex({}, [missing])})
        with self.assertRaisesRegex(ValueError, "App"):
            self.check()

    def test_duplicate_definitions_across_dex_rejected(self):
        self.write(self.apk, extra={"classes2.dex": dex(self.classes)})
        with self.assertRaisesRegex(ValueError, "[Dd]uplicate"):
            self.check()

    def test_runtime_classes_can_be_split_across_dex(self):
        names = list(self.classes)
        first = {name: self.classes[name] for name in names[:2]}
        second = {name: self.classes[name] for name in names[2:]}
        self.write(self.apk, first, {"classes2.dex": dex(second)})
        self.check()

    def test_primary_dex_required_even_if_secondary_defines_runtime_classes(self):
        self.write(self.apk, dexname="classes2.dex")
        with self.assertRaisesRegex(ValueError, "[Pp]rimary|classes.dex"):
            self.check()

    def test_missing_packaged_superclass_rejected(self):
        self.classes[next(iter(self.classes))] = "Lmissing/Base;"
        self.write(self.apk)
        with self.assertRaisesRegex(ValueError, "[Ss]uperclass|missing/Base"):
            self.check()

    def test_android_support_superclass_is_packaged_not_a_boot_class(self):
        self.classes[next(iter(self.classes))] = "Landroid/support/v4/app/FragmentActivity;"
        self.write(self.apk)
        with self.assertRaisesRegex(ValueError, "[Ss]uperclass"):
            self.check()

    def test_malformed_dex_headers_and_references_rejected(self):
        valid = dex(self.classes)
        cases = [valid[:80], valid[:-1], b"dex\n099\0" + valid[8:]]
        for offset, value in [(36, 111), (40, 0), (60, len(valid) + 4),
                              (80, 1), (100, len(valid) - 4)]:
            broken = bytearray(valid)
            struct.pack_into("<I", broken, offset, value)
            cases.append(seal(broken))
        broken = bytearray(valid)
        struct.pack_into("<I", broken, struct.unpack_from("<I", broken, 68)[0], 0xffffffff)
        cases.append(seal(broken))
        for data in cases:
            with self.subTest(header=data[:44]), self.assertRaises(ValueError):
                verify.dex_classes(data)

    def test_changed_original_payload_or_compression_rejected(self):
        for changes in ({"asset": b"changed"}, {"compression": zipfile.ZIP_STORED}):
            self.write(self.apk, **changes)
            with self.subTest(changes=changes), self.assertRaises(ValueError):
                self.check()

    def test_metadata_must_reference_this_apk(self):
        metadata = self.directory / "output-metadata.json"
        values = {"applicationId": "com.gameloft.android.ANMP.GloftGF2F",
                  "elements": [{"outputFile": "other.apk"}]}
        metadata.write_text(json.dumps(values))
        with self.assertRaisesRegex(ValueError, "[Mm]etadata|outputFile"):
            self.check(metadata=metadata)
        values["elements"][0]["outputFile"] = self.apk.name
        metadata.write_text(json.dumps(values))
        self.check(metadata=metadata)
        values["applicationId"] = "com.example.wrong"
        metadata.write_text(json.dumps(values))
        with self.assertRaisesRegex(ValueError, "[Mm]etadata"):
            self.check(metadata=metadata)

    def test_packaged_manifest_must_match_original(self):
        original = verify.manifest(self.original, self.directory)
        with mock.patch.object(verify, "manifest", side_effect=[("com.example.wrong", *original[1:]), original]):
            with self.assertRaisesRegex(ValueError, "manifest"):
                self.check()


class ActualApkTest(unittest.TestCase):
    def test_incomplete_agp_carrier_rejected_and_complete_app_passes(self):
        sdk = Path(os.environ.get("ANDROID_HOME", "/workspace/tooling/android-sdk"))
        original = ROOT / "green-farm-3.apk"
        carrier = ROOT / "app/build/intermediates/apk/debug/packageDebug/app-debug.apk"
        complete = ROOT / "app/build/outputs/apk/debug/app-debug.apk"
        if not all(path.exists() for path in (sdk, original, carrier, complete)):
            self.skipTest("Build carrier and complete local APK, and supply Android SDK")
        with self.assertRaisesRegex(ValueError, "App"):
            verify.verify_apk(carrier, original, sdk)
        verify.verify_apk(complete, original, sdk)


class NetworkPolicyTest(unittest.TestCase):
    BASE = 'http://192.168.1.20:8787/'

    @staticmethod
    def tree(*, internet=True, debuggable='true', cleartext='true', security_config=None):
        attributes = {'debuggable': debuggable, 'usesCleartextTraffic': cleartext}
        if security_config is not None:
            attributes['networkSecurityConfig'] = security_config
        children = [{'tag': 'application', 'attributes': attributes, 'children': []}]
        if internet:
            children.append({'tag': 'uses-permission', 'attributes': {'name': 'android.permission.INTERNET'}, 'children': []})
        return {'tag': 'manifest', 'attributes': {}, 'children': children}

    def test_packaged_http_debug_manifest_is_checked(self):
        with mock.patch.object(verify, 'manifest_tree', return_value=self.tree()):
            result = verify.network_policy('final.apk', 'sdk', self.BASE)
        self.assertTrue(result['internet_permission'])
        self.assertTrue(result['debuggable'])
        self.assertTrue(result['uses_cleartext_traffic'])

    def test_rejects_missing_permission_release_manifest_http_block_and_unreviewed_override(self):
        for changes in ({'internet': False}, {'debuggable': 'false'}, {'cleartext': 'false'},
                        {'security_config': '@0x7f010001'}):
            with self.subTest(changes=changes), mock.patch.object(verify, 'manifest_tree', return_value=self.tree(**changes)):
                with self.assertRaises(ValueError):
                    verify.network_policy('final.apk', 'sdk', self.BASE)


class DisabledServicesPolicyTest(unittest.TestCase):
    BASE = 'http://192.168.1.20:8787/'

    @staticmethod
    def tree(names=(), label='Green Farm 3', endpoint=None):
        children = [{'tag': 'activity', 'attributes': {'name': name}, 'children': []} for name in names]
        if endpoint is not None:
            children.append({'tag': 'meta-data', 'attributes': {'name': 'greenfarm.backendBaseUrl', 'value': endpoint}, 'children': []})
        return {'tag': 'manifest', 'attributes': {}, 'children': [
            {'tag': 'application', 'attributes': {'label': label}, 'children': children}]}

    def test_only_facebook_and_admob_declarations_are_removed(self):
        retained = ('com.google.firebase.provider.FirebaseInitProvider', 'com.google.android.gms.measurement.AppMeasurementService')
        original = self.tree(retained + ('com.google.android.gms.ads.AdActivity', 'com.facebook.FacebookActivity'))
        final = self.tree(retained, endpoint=self.BASE)
        result = verify.disabled_services_policy(final, original, self.BASE)
        self.assertEqual(result['unrelated_google_components_and_metadata_preserved'], sorted(retained))
        for name in ('com.facebook.FacebookActivity', 'com.facebook.sdk.ApplicationId', 'com.google.android.gms.ads.AdActivity'):
            with self.subTest(name=name), self.assertRaisesRegex(ValueError, 'Disabled Facebook/Google Ads'):
                verify.disabled_services_policy(self.tree(retained + (name,), endpoint=self.BASE), original, self.BASE)
        with self.assertRaisesRegex(ValueError, 'Unrelated Google'):
            verify.disabled_services_policy(self.tree(endpoint=self.BASE), original, self.BASE)

    def test_endpoint_and_test_label_are_checked_in_final_binary_manifest(self):
        original = self.tree()
        for endpoint in (None, 'http://192.168.1.21:8787/'):
            with self.subTest(endpoint=endpoint), self.assertRaisesRegex(ValueError, 'single configured backend origin'):
                verify.disabled_services_policy(self.tree(endpoint=endpoint), original, self.BASE)
        with self.assertRaisesRegex(ValueError, 'Offline test label'):
            verify.disabled_services_policy(self.tree(label='Green Farm 3 Offline Test', endpoint=self.BASE), original, self.BASE)

    def test_current_offline_profile_is_rejected_without_touching_legitimate_player_values(self):
        for namespace in (verify.GAME, 'Lcom/quanhoang/greenfarm3/'):
            for name in ('OfflineTestConfig;', 'OfflineTestConfig$Companion;', 'OfflineSession;', 'OfflineTestBalances;'):
                with self.subTest(name=name), self.assertRaisesRegex(ValueError, 'Offline test helper'):
                    verify.reject_offline_test_classes([namespace + 'S800x480/' + name], verify.APPLICATION_PACKAGE)
        with self.assertRaisesRegex(ValueError, 'Offline test application ID'):
            verify.reject_offline_test_classes([], verify.APPLICATION_PACKAGE + '.offlinetest')
        verify.reject_offline_test_classes(['Lcom/quanhoang/greenfarm3/S800x480/FarmGameEngine;'], verify.APPLICATION_PACKAGE)

    def test_google_presentation_sdk_removed_while_identifier_allowlist_stays_exact(self):
        for descriptor in ('Lcom/google/android/gms/ads/AdView;', 'Lcom/google/android/gms/internal/ads/zzabc;',
                           'Lcom/google/ads/Ad;', 'Lcom/google/android/gms/ads/identifier/Unreviewed;'):
            with self.subTest(descriptor=descriptor), self.assertRaisesRegex(ValueError, 'presentation SDK class remains'):
                verify.verify_google_ads_absent([descriptor])
        self.assertEqual(verify.verify_google_ads_absent(verify.GOOGLE_IDENTIFIER_CLASSES, True),
                         sorted(verify.GOOGLE_IDENTIFIER_CLASSES))
        with self.assertRaisesRegex(ValueError, 'shared Google identifier'):
            verify.verify_google_ads_absent(set(verify.GOOGLE_IDENTIFIER_CLASSES) - {next(iter(verify.GOOGLE_IDENTIFIER_CLASSES))}, True)

    def test_facebook_sdk_is_removed_but_legacy_profile_data_class_can_remain(self):
        for descriptor in ('Lcom/facebook/FacebookSdk;', 'Lcom/facebook/GraphRequest;', 'Lcom/facebook/R;',
                           'Lcom/facebook/ads/AudienceNetworkActivity;', 'Lcom/gameloft/android2d/socialnetwork/l;',
                           'Ljavax/microedition/midlet/v;'):
            with self.subTest(descriptor=descriptor), self.assertRaisesRegex(ValueError, 'Facebook SDK class remains'):
                verify.verify_facebook_absent([descriptor])
        verify.verify_facebook_absent(['Lcom/gameloft/android2d/socialnetwork/i;'])

    def test_binary_xml_dump_parser_retains_boolean_policy_attributes(self):
        dump = '''E: manifest (line=1)
  A: package="com.gameloft.android.ANMP.GloftGF2F"
  E: uses-permission (line=2)
    A: http://schemas.android.com/apk/res/android:name(0x01010003)="android.permission.INTERNET"
  E: application (line=3)
    A: http://schemas.android.com/apk/res/android:debuggable(0x0101000f)=true
    A: http://schemas.android.com/apk/res/android:usesCleartextTraffic(0x010104ec)=true
'''
        with mock.patch.object(verify.subprocess, 'run', return_value=mock.Mock(stdout=dump)):
            result = verify.network_policy('final.apk', 'sdk', self.BASE)
        self.assertTrue(result['uses_cleartext_traffic'])


if __name__ == "__main__":
    unittest.main()
