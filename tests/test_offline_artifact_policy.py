"""Negative artifact checks: a network permission/provider/SDK must fail packaging."""
from pathlib import Path
import sys,tempfile,unittest,zipfile
from unittest import mock
sys.path.insert(0,str(Path(__file__).resolve().parents[1]/'tools'))
import package_game as package

class OfflineArtifactPolicyTest(unittest.TestCase):
    def test_rejects_online_permission_component_sdk_and_config(self):
        def tree(permission=None,component=None):
            root={'children':[{'tag':'application','attributes':{'allowBackup':'false'},'children':[]}]}
            if permission:root['children'].append({'tag':'uses-permission','attributes':{'name':permission}})
            if component:root['children'][0]['children'].append({'tag':'provider','attributes':{'name':component}})
            return root
        with tempfile.TemporaryDirectory() as d:
            apk=Path(d)/'probe.apk'
            with zipfile.ZipFile(apk,'w') as z:z.writestr('classes.dex',b'fixture')
            for manifest,classes in [(tree('android.permission.INTERNET'),{}),
                    (tree(component='com.google.firebase.provider.FirebaseInitProvider'),{}),
                    (tree(),{'Lcom/google/firebase/FirebaseApp;':'Ljava/lang/Object;'})]:
                with mock.patch.object(package,'manifest_tree',return_value=manifest),mock.patch.object(package,'dex_classes',return_value=classes):
                    with self.assertRaises(ValueError):package.assert_offline(apk,'sdk')
            with mock.patch.object(package,'manifest_tree',return_value=tree()),mock.patch.object(package,'dex_classes',return_value={}):
                package.assert_offline(apk,'sdk')
                for entry,data in [('resources.arsc',b'default_web_client_id'),
                                   ('resources.arsc','google_api_key'.encode('utf-16le')),
                                   ('res/xml/network_security_config.xml',b'fixture')]:
                    with zipfile.ZipFile(apk,'w') as z:
                        z.writestr('classes.dex',b'fixture');z.writestr(entry,data)
                    with self.assertRaises(ValueError):package.assert_offline(apk,'sdk')
                with zipfile.ZipFile(apk,'w') as z:z.writestr('classes.dex',b'fixture')
                with zipfile.ZipFile(apk,'a') as z:z.writestr('res/raw/google_services.json','{}')
                with self.assertRaises(ValueError):package.assert_offline(apk,'sdk')
