import unittest
import xml.etree.ElementTree as ET

from restore_resource_values import restore_id_declarations, restore_style_values


class RestoreResourceValuesTest(unittest.TestCase):
    def test_proven_enum_flag_and_dimension_values(self):
        text = '''<resources><style name="example">
        <item name="android:orientation">1</item>
        <item name="android:gravity">0x11</item>
        <item name="android:layout_gravity">0x51</item>
        <item name="android:layout_width">-2</item>
        <item name="android:typeface">0</item>
        <item name="android:textStyle">0x0</item>
        </style></resources>'''
        restored, count = restore_style_values(text)
        self.assertEqual(count, 6)
        for value in ("vertical", "center", "bottom|center_horizontal", "wrap_content", "normal"):
            self.assertIn(">" + value + "</item>", restored)

    def test_unknown_flag_combination_is_rejected(self):
        with self.assertRaisesRegex(ValueError, "Unsupported decoded android:gravity"):
            restore_style_values('<resources><style name="a"><item name="android:gravity">0x91</item></style></resources>')

    def test_unknown_enum_is_rejected(self):
        with self.assertRaisesRegex(ValueError, "Unsupported decoded android:orientation"):
            restore_style_values('<resources><style name="a"><item name="android:orientation">2</item></style></resources>')

    def test_resources_references_comments_and_non_style_items_stay_unchanged(self):
        text = '''<resources>
        <!-- <style name="comment"><item name="android:gravity">0x11</item></style> -->
        <style name="a"><!-- <item name="android:gravity">0x11</item> -->
        <item name="android:gravity">center</item>
        <item name="android:layout_width">@dimen/width</item>
        <item name="android:layout_height">12dp</item>
        <item name="android:shadowRadius">0.25</item>
        <item name="customValue">1</item></style>
        <item type="integer" name="android:gravity">17</item>
        </resources>'''
        self.assertEqual(restore_style_values(text), (text, 0))

    def test_conversion_is_idempotent_and_preserves_whitespace(self):
        text = '<resources><style name="a"><item name="android:layout_width">\n -2 \n</item></style></resources>'
        restored, count = restore_style_values(text)
        self.assertEqual(count, 1)
        self.assertIn('>\n wrap_content \n</item>', restored)
        self.assertEqual(restore_style_values(restored), (restored, 0))

    def test_public_ids_define_missing_tag_id_without_inventing_values(self):
        public_xml = '''<resources>
        <public type="id" name="item_touch_helper_previous_elevation" id="0x7f0c0000" />
        <public type="string" name="unrelated" id="0x7f070000" />
        <public type="id" name="existing_layout_view" id="0x7f0c0001" />
        </resources>'''
        generated, count = restore_id_declarations(public_xml)
        self.assertEqual(count, 2)
        entries = list(ET.fromstring(generated))
        self.assertEqual([element.attrib for element in entries], [
            {"type": "id", "name": "item_touch_helper_previous_elevation"},
            {"type": "id", "name": "existing_layout_view"}])
        self.assertTrue(all(element.text is None for element in entries))
        self.assertNotIn("0x7f", generated)
        self.assertEqual(restore_id_declarations(public_xml), (generated, count))

    def test_invalid_or_duplicate_public_ids_are_rejected(self):
        for public_xml in (
                '<resources><public type="id" name="bad/name" /></resources>',
                '<resources><public type="id" name="same" /><public type="id" name="same" /></resources>'):
            with self.assertRaises(ValueError):
                restore_id_declarations(public_xml)


if __name__ == "__main__":
    unittest.main()
