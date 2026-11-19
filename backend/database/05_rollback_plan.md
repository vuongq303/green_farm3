# Khôi phục MySQL sau nâng cấp v5

MySQL có thể commit từng câu DDL. Backup và kế hoạch khôi phục cần được kiểm tra trên một database riêng trước khi mở cửa sổ triển khai. Dừng toàn bộ tiến trình ghi dữ liệu cho cả chuyển đổi và khôi phục.

## Nếu chưa mở lại lưu lượng

Giữ database gặp lỗi để chẩn đoán. Nếu lỗi mang tính vận hành và dữ liệu nguồn đã được xác minh, sửa nguyên nhân rồi chạy lại `npm run migrate`. Các bước v5 kiểm tra DDL đã áp dụng và migration chỉ ghi ledger sau khi toàn bộ SQL thành công.

Nếu cần quay về v4 ngay, khôi phục đầy đủ backup trước nâng cấp vào một database khác. Kiểm tra số hàng, khóa ngoại, danh tính, đăng nhập và refresh với backend v4 trước khi chuyển cấu hình kết nối. Quản lý việc đổi database trong cửa sổ bảo trì. Giữ nguyên database lỗi và lịch sử migration để điều tra.

## Nếu v5 đã nhận lưu lượng

Backup trước nâng cấp sẽ thiếu tài khoản và phiên phát sinh sau thời điểm chụp. Dùng bản sao đầy đủ của **database hiện tại**, giữ bản gốc v5 ở chế độ bảo trì, và thử các bước sau trên bản sao trước khi đổi cấu hình kết nối của backend v4.

1. Chạy `03_verify_data.sql` trên bản sao và điều tra mọi sai lệch, bao gồm `archive_membership_mismatches`.
2. Nếu bảng liên kết đã được dọn, tạo lại bằng đúng câu `CREATE TABLE` dưới đây. Nạp các liên kết còn thiếu từ cột có thẩm quyền `gf_auth_sessions.family_id`, bao gồm các phiên đã thu hồi. Không chạy câu backfill trong migration 003 vì nó giả định mỗi phiên có nhóm phiên riêng.
3. Khi phép kiểm tra `rollback_link_mismatches` trả `0`, chuyển `family_id` thành cột cho phép `NULL`. Backend v4 sẽ ghi liên kết qua bảng v3. Giữ nguyên khóa ngoại hiện có, bảng tài khoản, mật khẩu hash, token hash và ledger.
4. Khởi động đúng backend v4 trên bản sao. Kiểm tra tài khoản có trước và sau v5, phiên đã refresh, thu hồi cả nhóm phiên và tính bền vững sau khi restart trước khi chuyển lưu lượng.

```sql
CREATE TABLE IF NOT EXISTS gf_auth_session_links (
    session_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    family_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    PRIMARY KEY (session_id),
    KEY gf_auth_link_family (family_id),
    CONSTRAINT gf_auth_link_session_fk FOREIGN KEY (session_id)
        REFERENCES gf_auth_sessions (id) ON DELETE CASCADE,
    CONSTRAINT gf_auth_link_family_fk FOREIGN KEY (family_id)
        REFERENCES gf_auth_session_families (id) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO gf_auth_session_links (session_id, family_id)
SELECT s.id, s.family_id
FROM gf_auth_sessions s
LEFT JOIN gf_auth_session_links l ON l.session_id = s.id
WHERE l.session_id IS NULL;

SELECT COUNT(*) AS rollback_link_mismatches
FROM gf_auth_sessions s
LEFT JOIN gf_auth_session_links l ON l.session_id = s.id
WHERE l.session_id IS NULL OR NOT (s.family_id <=> l.family_id);
```

Chỉ thực hiện bước DDL dưới đây sau khi đã xác minh kết quả:

```sql
ALTER TABLE gf_auth_sessions
    MODIFY COLUMN family_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NULL;
```

## Nâng lại v5 sau khi quay về backend v4

Dừng mọi tiến trình v4 và sao lưu database đang phục vụ. Vì ledger vẫn giữ mục v5, quản trị viên phải chạy lại trực tiếp SQL **đúng checksum** trong `backend/migrations/005_session_family_membership.sql` để backfill các phiên v4 phát sinh. Sau đó chạy `npm run migrate` để đối chiếu ledger và `03_verify_data.sql` để kiểm tra schema, rồi mới mở lại backend v5. Dùng database hiện tại của v4 để giữ các thay đổi phát sinh từ lần chuyển trước. Không chạy cleanup trong lúc rollback còn cần bảng liên kết.
