package com.quanhoang.greenfarm3.S800x480

/** Account error copy for the language IDs used by the game's shipped text packs. */
internal object SocialAccountText {
    private val text = mapOf(
        0 to "Sign in to continue.|Checking your account…|Account HTTPS configuration needs attention.|Invalid sign-in response from the server.|Sign-in address does not match the game's HTTPS settings.|Cannot reach the account server.|The sign-in request timed out.|Could not verify the HTTPS certificate.|Could not read or save the session.|Could not complete sign-in in your browser.|Invalid sign-in return from the browser.|Sign-in has expired. Sign in again.|Sign-in failed. Please try again.",
        1 to "Melde dich an, um fortzufahren.|Konto wird überprüft…|Überprüfe die HTTPS-Einstellungen des Kontos.|Ungültige Antwort vom Anmeldeserver.|Die Anmeldeadresse stimmt nicht mit den HTTPS-Einstellungen überein.|Der Kontoserver ist nicht erreichbar.|Die Anmeldung hat zu lange gedauert.|Das HTTPS-Zertifikat konnte nicht geprüft werden.|Die Sitzung konnte nicht gelesen oder gespeichert werden.|Die Anmeldung im Browser wurde nicht abgeschlossen.|Ungültige Rückmeldung vom Browser.|Die Anmeldung ist abgelaufen. Melde dich erneut an.|Anmeldung fehlgeschlagen. Versuche es erneut.",
        2 to "Connectez-vous pour continuer.|Vérification du compte…|Vérifiez la configuration HTTPS du compte.|Réponse de connexion invalide.|L’adresse de connexion ne correspond pas aux paramètres HTTPS.|Le serveur de compte est inaccessible.|Le délai de connexion est dépassé.|Impossible de vérifier le certificat HTTPS.|Impossible de lire ou enregistrer la session.|La connexion dans le navigateur n’a pas abouti.|Retour de connexion du navigateur invalide.|La connexion a expiré. Reconnectez-vous.|La connexion a échoué. Réessayez.",
        3 to "Accedi per continuare.|Verifica dell’account…|Controlla la configurazione HTTPS dell’account.|Risposta di accesso non valida.|L’indirizzo di accesso non corrisponde alle impostazioni HTTPS.|Impossibile raggiungere il server dell’account.|Tempo per l’accesso scaduto.|Impossibile verificare il certificato HTTPS.|Impossibile leggere o salvare la sessione.|Accesso nel browser non completato.|Risposta del browser non valida.|Accesso scaduto. Accedi di nuovo.|Accesso non riuscito. Riprova.",
        4 to "Inicia sesión para continuar.|Verificando la cuenta…|Comprueba la configuración HTTPS de la cuenta.|Respuesta de inicio de sesión no válida.|La dirección de inicio de sesión no coincide con la configuración HTTPS.|No se pudo conectar con el servidor de cuentas.|Se agotó el tiempo de espera de la conexión.|No se pudo verificar el certificado HTTPS.|No se pudo leer o guardar la sesión.|El inicio de sesión en el navegador no se completó.|Respuesta del navegador no válida.|La sesión ha caducado. Vuelve a iniciar sesión.|No se pudo iniciar sesión. Inténtalo de nuevo.",
        5 to "Entre para continuar.|Verificando a conta…|Verifique a configuração HTTPS da conta.|Resposta de login inválida.|O endereço de login não corresponde às configurações HTTPS.|Não foi possível conectar ao servidor de contas.|O tempo de conexão se esgotou.|Não foi possível verificar o certificado HTTPS.|Não foi possível ler ou salvar a sessão.|O login no navegador não foi concluído.|Resposta de login do navegador inválida.|A sessão expirou. Entre novamente.|Não foi possível entrar. Tente novamente.",
        7 to "続けるにはログインしてください。|アカウントを確認しています。|HTTPSの設定を確認してください。|ログインサーバーの応答が無効です。|ログイン先がゲームのHTTPS設定と一致しません。|アカウントサーバーに接続できません。|ログインがタイムアウトしました。|HTTPS証明書を確認できません。|セッションを読み込み、または保存できません。|ブラウザーでのログインが完了しませんでした。|ブラウザーからのログイン結果が無効です。|ログインの有効期限が切れました。再ログインしてください。|ログインできませんでした。再試行してください。",
        9 to "계속하려면 로그인하세요.|계정을 확인하는 중입니다.|계정 HTTPS 설정을 확인하세요.|로그인 서버의 응답이 잘못되었습니다.|로그인 주소가 게임의 HTTPS 설정과 일치하지 않습니다.|계정 서버에 연결할 수 없습니다.|로그인 요청 시간이 초과되었습니다.|HTTPS 인증서를 확인할 수 없습니다.|세션을 읽거나 저장할 수 없습니다.|브라우저에서 로그인이 완료되지 않았습니다.|브라우저 로그인 결과가 잘못되었습니다.|로그인이 만료되었습니다. 다시 로그인하세요.|로그인에 실패했습니다. 다시 시도하세요.",
        10 to "Войдите, чтобы продолжить.|Проверяем ваш аккаунт…|Проверьте настройки HTTPS для аккаунта.|Неверный ответ сервера при входе.|Адрес входа не совпадает с настройками HTTPS игры.|Сервер аккаунтов недоступен.|Время ожидания входа истекло.|Не удалось проверить сертификат HTTPS.|Не удалось прочитать или сохранить сеанс.|Вход через браузер не завершён.|Неверный результат входа из браузера.|Срок сеанса истёк. Войдите снова.|Не удалось войти. Повторите попытку.",
        11 to "Devam etmek için giriş yap.|Hesap kontrol ediliyor…|Hesabın HTTPS ayarlarını kontrol et.|Giriş sunucusunun yanıtı geçersiz.|Giriş adresi oyunun HTTPS ayarlarıyla eşleşmiyor.|Hesap sunucusuna ulaşılamıyor.|Giriş isteği zaman aşımına uğradı.|HTTPS sertifikası doğrulanamadı.|Oturum okunamadı veya kaydedilemedi.|Tarayıcıda giriş tamamlanmadı.|Tarayıcıdan gelen giriş sonucu geçersiz.|Oturumun süresi doldu. Yeniden giriş yap.|Giriş başarısız. Tekrar dene.",
        12 to "Zaloguj się, aby kontynuować.|Sprawdzanie konta…|Sprawdź ustawienia HTTPS konta.|Nieprawidłowa odpowiedź serwera logowania.|Adres logowania nie zgadza się z ustawieniami HTTPS gry.|Nie można połączyć się z serwerem kont.|Upłynął limit czasu logowania.|Nie można zweryfikować certyfikatu HTTPS.|Nie można odczytać ani zapisać sesji.|Logowanie w przeglądarce nie zostało zakończone.|Nieprawidłowy wynik logowania z przeglądarki.|Sesja wygasła. Zaloguj się ponownie.|Nie udało się zalogować. Spróbuj ponownie.",
        15 to "เข้าสู่ระบบเพื่อดำเนินการต่อ|กำลังตรวจสอบบัญชี|โปรดตรวจสอบการตั้งค่า HTTPS ของบัญชี|การตอบกลับการเข้าสู่ระบบไม่ถูกต้อง|ที่อยู่เข้าสู่ระบบไม่ตรงกับการตั้งค่า HTTPS ของเกม|ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์บัญชี|การเข้าสู่ระบบหมดเวลารอ|ไม่สามารถตรวจสอบใบรับรอง HTTPS|ไม่สามารถอ่านหรือบันทึกเซสชัน|เข้าสู่ระบบในเบราว์เซอร์ไม่สำเร็จ|ผลการเข้าสู่ระบบจากเบราว์เซอร์ไม่ถูกต้อง|เซสชันหมดอายุ โปรดเข้าสู่ระบบใหม่|เข้าสู่ระบบไม่สำเร็จ โปรดลองอีกครั้ง",
        16 to "Vui lòng đăng nhập tài khoản để tiếp tục|Đang xác thực tài khoản…|Hãy kiểm tra cấu hình HTTPS của tài khoản.|Phản hồi đăng nhập từ máy chủ không hợp lệ.|Địa chỉ đăng nhập không khớp cấu hình HTTPS của trò chơi.|Không thể kết nối máy chủ tài khoản.|Yêu cầu đăng nhập quá thời gian chờ.|Không thể xác minh chứng chỉ HTTPS.|Không thể đọc hoặc lưu phiên đăng nhập.|Đăng nhập trong trình duyệt chưa hoàn tất.|Kết quả đăng nhập từ trình duyệt không hợp lệ.|Phiên đăng nhập đã hết hạn. Hãy đăng nhập lại.|Đăng nhập chưa thành công. Vui lòng thử lại."
    ).mapValues { (_, value) -> value.split('|') }

    fun message(language: Int, code: String?, busy: Boolean): String {
        val pack = text[if (language == 20) 4 else language] ?: text.getValue(0)
        if (busy) return pack[1]
        if (code == null) return pack[0]
        if (language == 16) return SocialAccountFlow.messageFor(code)
        return pack[when (code) {
            "configuration_required", "https_required", "auth_not_configured", "oauth_not_configured" -> 2
            "invalid_response" -> 3
            "invalid_authorization_url" -> 4
            "network_error", "database_unavailable" -> 5
            "timeout" -> 6
            "tls_error" -> 7
            "storage_error", "auth_record_unreadable" -> 8
            "browser_unavailable", "browser_closed", "access_denied" -> 9
            "invalid_callback" -> 10
            "attempt_expired", "expired_code", "invalid_code",
                "invalid_session", "session_expired", "invalid_refresh_token" -> 11
            else -> 12
        }]
    }
}
