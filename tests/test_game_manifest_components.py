import sys
import unittest
from pathlib import Path
from xml.etree import ElementTree

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'tools'))
import verify_runtime_apk as verify

ANDROID = '{http://schemas.android.com/apk/res/android}'
MAIN = Path(__file__).resolve().parents[1] / 'app/src/main'


class GameManifestComponentsTest(unittest.TestCase):
    def tree(self, name, authority=None, tag='provider'):
        attributes = {'name': name}
        if authority is not None:
            attributes['authorities'] = authority
        return {'children': [{'tag': 'application', 'children': [
            {'tag': tag, 'attributes': attributes}]}]}

    def test_bridge_component_uses_new_class_and_old_app_authority(self):
        name = verify.APPLICATION_PACKAGE + '.KeyProvider'
        tree = self.tree(name, verify.LEGACY_PACKAGE + '.KeyProvider')
        definitions = {'L' + name.replace('.', '/') + ';'}
        self.assertEqual([name], verify.verify_game_components(tree, definitions, verify.LEGACY_PACKAGE))
        with self.assertRaisesRegex(ValueError, 'authority'):
            verify.verify_game_components(tree, definitions, verify.APPLICATION_PACKAGE)
        with self.assertRaisesRegex(ValueError, 'Missing game component'):
            verify.verify_game_components(self.tree(verify.LEGACY_PACKAGE + '.KeyProvider'), definitions, verify.LEGACY_PACKAGE)

    def test_exact_original_dangling_activity_is_reported_but_new_omission_fails(self):
        original = verify.LEGACY_PACKAGE + '.SoftKeyBoardActivity'
        target = verify.APPLICATION_PACKAGE + '.SoftKeyBoardActivity'
        baseline = verify.missing_original_components(self.tree(original, tag='activity'), set(), verify.APPLICATION_PACKAGE)
        self.assertEqual([target], baseline)
        self.assertEqual([], verify.verify_game_components(self.tree(target, tag='activity'), set(), verify.APPLICATION_PACKAGE, baseline))
        with self.assertRaisesRegex(ValueError, 'Missing game component'):
            verify.verify_game_components(self.tree(verify.APPLICATION_PACKAGE + '.migration.SaveTransferActivity', tag='activity'), set(), verify.APPLICATION_PACKAGE, baseline)

    def test_intent_filters_declare_export_behavior_for_android_12(self):
        root = ElementTree.parse(MAIN / 'AndroidManifest.xml').getroot()
        application = root.find('application')
        components = {
            element.get(ANDROID + 'name'): element.get(ANDROID + 'exported')
            for element in application if element.find('intent-filter') is not None
        }
        self.assertTrue(components)
        self.assertTrue(all(exported in {'true', 'false'} for exported in components.values()), components)
        self.assertEqual(components[verify.APPLICATION_PACKAGE + '.Start'], 'true')
        self.assertEqual(components[verify.APPLICATION_PACKAGE + '.S800x480.SocialAuthCallbackActivity'], 'true')
        self.assertEqual(components[verify.APPLICATION_PACKAGE + '.S800x480.GloftGF2F'], 'false')
        permission_name = '${applicationId}.permission.INTERNAL_BROADCAST'
        definition = next(item for item in root.findall('permission') if item.get(ANDROID + 'name') == permission_name)
        self.assertEqual(definition.get(ANDROID + 'protectionLevel'), 'signature')
        self.assertIn(permission_name, {item.get(ANDROID + 'name') for item in root.findall('uses-permission')})

    def test_cloud_backup_and_device_transfer_exclude_only_the_keystore_bound_preferences(self):
        application = ElementTree.parse(MAIN / 'AndroidManifest.xml').getroot().find('application')
        def read_rules(attribute):
            spec = application.get(ANDROID + attribute)
            self.assertTrue(spec.startswith('@xml/'))
            return ElementTree.parse(MAIN / 'res' / (spec.removeprefix('@') + '.xml')).getroot()

        legacy = read_rules('fullBackupContent')
        modern = read_rules('dataExtractionRules')
        expected = {'domain': 'sharedpref', 'path': 'greenfarm_online_auth_v1.xml'}
        self.assertEqual([(rule.tag, rule.attrib) for rule in legacy], [('exclude', expected)])
        self.assertEqual([rule.tag for rule in modern], ['cloud-backup', 'device-transfer'])
        for scope in modern:
            self.assertEqual([(rule.tag, rule.attrib) for rule in scope], [('exclude', expected)])


if __name__ == '__main__':
    unittest.main()
