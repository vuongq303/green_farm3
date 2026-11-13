import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'tools'))
import verify_runtime_apk as verify


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


if __name__ == '__main__':
    unittest.main()
