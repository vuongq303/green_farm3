"""Focused checks for preservation and failure handling in nine-patch recovery."""

import struct
import unittest
import zlib

import restore_ninepatch as recovery


class NinePatchRecoveryTest(unittest.TestCase):
    def fixture(self):
        # Distinct RGBA content including transparent pixels, not a flat image.
        rows = [bytes((x * 20, y * 30, 50, (x + y) * 20))
                for y in range(5) for x in range(6)]
        rows = [b"".join(rows[y * 6:(y + 1) * 6]) for y in range(5)]
        patch = bytes((0, 4, 2, 15)) + bytes(8)
        patch += struct.pack(">4i", 1, 2, 2, 1) + bytes(4)
        patch += struct.pack(">6i", 0, 2, 4, 6, 1, 4) + bytes(15 * 4)
        chunks = recovery.png_chunks(recovery.encode_rgba(6, 5, rows))
        original = recovery.PNG_SIGNATURE + b"".join(
            recovery.png_chunk(k, p) for k, p in chunks[:-1])
        original += recovery.png_chunk(b"npTc", patch)
        original += recovery.png_chunk(b"IEND", b"")
        return original, rows

    def test_interior_alpha_and_pixels_survive_reconstruction(self):
        original, rows = self.fixture()
        width, height, generated = recovery.rgba_pixels(
            recovery.png_chunks(recovery.reconstruct(original)))
        self.assertEqual((width, height), (8, 7))
        self.assertEqual([r[4:-4] for r in generated[1:-1]], rows)

    def test_disjoint_stretch_runs_and_content_padding_are_encoded(self):
        original, _ = self.fixture()
        _, _, rows = recovery.rgba_pixels(recovery.png_chunks(recovery.reconstruct(original)))
        black_x = lambda row: [i for i in range(len(row) // 4)
                               if row[i * 4:i * 4 + 4] == recovery.BLACK]
        self.assertEqual(black_x(rows[0]), [1, 2, 5, 6])
        self.assertEqual(black_x(rows[-1]), [2, 3, 4])
        self.assertEqual([y for y, row in enumerate(rows) if row[:4] == recovery.BLACK], [2, 3, 4])
        self.assertEqual([y for y, row in enumerate(rows) if row[-4:] == recovery.BLACK], [3, 4])
        for row in (rows[0], rows[-1]):
            self.assertEqual(row[:4], recovery.TRANSPARENT)
            self.assertEqual(row[-4:], recovery.TRANSPARENT)

    def test_corrupt_crc_is_rejected(self):
        original, _ = self.fixture()
        damaged = bytearray(original)
        damaged[20] ^= 1
        with self.assertRaisesRegex(ValueError, "CRC mismatch"):
            recovery.reconstruct(damaged)

    def test_unsupported_optical_layout_bounds_fail_closed(self):
        original, _ = self.fixture()
        chunks = recovery.png_chunks(original)
        chunks.insert(-1, (b"npLb", bytes(16)))
        modified = recovery.PNG_SIGNATURE + b"".join(recovery.png_chunk(k, p) for k, p in chunks)
        with self.assertRaisesRegex(ValueError, "npLb"):
            recovery.reconstruct(modified)

    def test_invalid_stretch_coordinates_fail_closed(self):
        original, _ = self.fixture()
        chunks = recovery.png_chunks(original)
        patched = []
        for kind, payload in chunks:
            if kind == b"npTc":
                payload = payload[:32] + struct.pack(">i", -1) + payload[36:]
            patched.append((kind, payload))
        modified = recovery.PNG_SIGNATURE + b"".join(recovery.png_chunk(k, p) for k, p in patched)
        with self.assertRaisesRegex(ValueError, "out of bounds"):
            recovery.reconstruct(modified)

    def test_all_png_row_filters_decode_the_same_pixels(self):
        width, height = 2, 2
        rows = [bytes(range(8)), bytes(range(10, 18))]
        for filter_type in range(5):
            encoded_rows = []
            previous = bytes(width * 4)
            for row in rows:
                filtered = bytearray()
                for x, value in enumerate(row):
                    left = row[x - 4] if x >= 4 else 0
                    above = previous[x]
                    upper_left = previous[x - 4] if x >= 4 else 0
                    if filter_type == 4:
                        p = left + above - upper_left
                        values = (left, above, upper_left)
                        predictor = min(values, key=lambda v: abs(p - v))
                    else:
                        predictor = (0, left, above, (left + above) // 2)[filter_type]
                    filtered.append((value - predictor) & 255)
                encoded_rows.append(bytes((filter_type,)) + filtered)
                previous = row
            chunks = [(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0)),
                      (b"IDAT", zlib.compress(b"".join(encoded_rows))), (b"IEND", b"")]
            self.assertEqual(recovery.rgba_pixels(chunks), (width, height, rows))


if __name__ == "__main__":
    unittest.main()
