package com.gameloft.android.wrapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.gameloft.android.ANMP.GloftGF2F.R;
import java.util.ArrayList;
import javax.microedition.midlet.BaseActivity;

/* JADX INFO: loaded from: classes.dex */
public class GamePermission extends BaseActivity {
    public static Activity bex;
    public static String[] bfw;
    public static boolean bfx = false;
    public static int DG = 0;
    public static String[] bfy = {"EN", "FR", "DE", "IT", "SP", "JP", "KR", "BR", "PT", "RU", "PL", "TR", "AR", "TH", "VI", "SC", "ES_LATAM", "CN"};
    public static int[][] bfz = {new int[]{R.string.GP_SETTING_EN, R.string.GP_CANCEL_EN, R.string.GP_EXIT_EN, R.string.GP_RETRY_EN, R.string.GP_DENIED_EN, R.string.GP_DESCRIPTION_DENY_EN, R.string.GP_PHONE_EN, R.string.GP_STORAGE_EN, R.string.GP_LOCATION_EN, R.string.GP_SMS_EN, R.string.GP_CONTACTS_EN, R.string.GP_DESCRIPTION_EXIT_EN, R.string.GP_DENY_PHONE_EN, R.string.GP_DENY_STORAGE_EN, R.string.GP_DENY_LOCATION_EN, R.string.GP_DENY_SMS_EN, R.string.GP_DENY_CONTACTS_EN, R.string.GP_DESCRIPTION_CONTINUE_EN, R.string.GP_EXPLAIN_PHONE_EN, R.string.GP_EXPLAIN_STORAGE_EN, R.string.GP_EXPLAIN_LOCATION_EN, R.string.GP_EXPLAIN_SMS_EN, R.string.GP_EXPLAIN_CONTACTS_EN, R.string.GP_DESCRIPTION_ALLOW_EN, R.string.GP_CANNOT_GO_BACK_EN, R.string.GP_FIRST_EXPLAIN_PHONE_EN, R.string.GP_FIRST_EXPLAIN_STORAGE_EN, R.string.GP_FIRST_EXPLAIN_LOCATION_EN, R.string.GP_FIRST_EXPLAIN_SMS_EN, R.string.GP_FIRST_EXPLAIN_CONTACTS_EN, R.string.GP_REQUEST_TITLE_EN, R.string.GP_OK_EN, R.string.GP_CAMERA_EN, R.string.GP_DENY_CAMERA_EN, R.string.GP_EXPLAIN_CAMERA_EN, R.string.GP_FIRST_EXPLAIN_CAMERA_EN, R.string.GP_DENY_SMS_AND_PHONE_EN, R.string.GP_EXPLAIN_SMS_AND_PHONE_EN, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_EN, R.string.GP_DENY_PHONE_NEW_EN, R.string.GP_EXPLAIN_PHONE_NEW_EN, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_EN, R.string.GP_DESCRIPTION_CONTINUE_NEW_EN, R.string.GP_DESCRIPTION_ALLOW_NEW_EN}, new int[]{R.string.GP_SETTING_FR, R.string.GP_CANCEL_FR, R.string.GP_EXIT_FR, R.string.GP_RETRY_FR, R.string.GP_DENIED_FR, R.string.GP_DESCRIPTION_DENY_FR, R.string.GP_PHONE_FR, R.string.GP_STORAGE_FR, R.string.GP_LOCATION_FR, R.string.GP_SMS_FR, R.string.GP_CONTACTS_FR, R.string.GP_DESCRIPTION_EXIT_FR, R.string.GP_DENY_PHONE_FR, R.string.GP_DENY_STORAGE_FR, R.string.GP_DENY_LOCATION_FR, R.string.GP_DENY_SMS_FR, R.string.GP_DENY_CONTACTS_FR, R.string.GP_DESCRIPTION_CONTINUE_FR, R.string.GP_EXPLAIN_PHONE_FR, R.string.GP_EXPLAIN_STORAGE_FR, R.string.GP_EXPLAIN_LOCATION_FR, R.string.GP_EXPLAIN_SMS_FR, R.string.GP_EXPLAIN_CONTACTS_FR, R.string.GP_DESCRIPTION_ALLOW_FR, R.string.GP_CANNOT_GO_BACK_FR, R.string.GP_FIRST_EXPLAIN_PHONE_FR, R.string.GP_FIRST_EXPLAIN_STORAGE_FR, R.string.GP_FIRST_EXPLAIN_LOCATION_FR, R.string.GP_FIRST_EXPLAIN_SMS_FR, R.string.GP_FIRST_EXPLAIN_CONTACTS_FR, R.string.GP_REQUEST_TITLE_FR, R.string.GP_OK_FR, R.string.GP_CAMERA_FR, R.string.GP_DENY_CAMERA_FR, R.string.GP_EXPLAIN_CAMERA_FR, R.string.GP_FIRST_EXPLAIN_CAMERA_FR, R.string.GP_DENY_SMS_AND_PHONE_FR, R.string.GP_EXPLAIN_SMS_AND_PHONE_FR, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_FR, R.string.GP_DENY_PHONE_NEW_FR, R.string.GP_EXPLAIN_PHONE_NEW_FR, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_FR, R.string.GP_DESCRIPTION_CONTINUE_NEW_FR, R.string.GP_DESCRIPTION_ALLOW_NEW_FR}, new int[]{R.string.GP_SETTING_DE, R.string.GP_CANCEL_DE, R.string.GP_EXIT_DE, R.string.GP_RETRY_DE, R.string.GP_DENIED_DE, R.string.GP_DESCRIPTION_DENY_DE, R.string.GP_PHONE_DE, R.string.GP_STORAGE_DE, R.string.GP_LOCATION_DE, R.string.GP_SMS_DE, R.string.GP_CONTACTS_DE, R.string.GP_DESCRIPTION_EXIT_DE, R.string.GP_DENY_PHONE_DE, R.string.GP_DENY_STORAGE_DE, R.string.GP_DENY_LOCATION_DE, R.string.GP_DENY_SMS_DE, R.string.GP_DENY_CONTACTS_DE, R.string.GP_DESCRIPTION_CONTINUE_DE, R.string.GP_EXPLAIN_PHONE_DE, R.string.GP_EXPLAIN_STORAGE_DE, R.string.GP_EXPLAIN_LOCATION_DE, R.string.GP_EXPLAIN_SMS_DE, R.string.GP_EXPLAIN_CONTACTS_DE, R.string.GP_DESCRIPTION_ALLOW_DE, R.string.GP_CANNOT_GO_BACK_DE, R.string.GP_FIRST_EXPLAIN_PHONE_DE, R.string.GP_FIRST_EXPLAIN_STORAGE_DE, R.string.GP_FIRST_EXPLAIN_LOCATION_DE, R.string.GP_FIRST_EXPLAIN_SMS_DE, R.string.GP_FIRST_EXPLAIN_CONTACTS_DE, R.string.GP_REQUEST_TITLE_DE, R.string.GP_OK_DE, R.string.GP_CAMERA_DE, R.string.GP_DENY_CAMERA_DE, R.string.GP_EXPLAIN_CAMERA_DE, R.string.GP_FIRST_EXPLAIN_CAMERA_DE, R.string.GP_DENY_SMS_AND_PHONE_DE, R.string.GP_EXPLAIN_SMS_AND_PHONE_DE, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_DE, R.string.GP_DENY_PHONE_NEW_DE, R.string.GP_EXPLAIN_PHONE_NEW_DE, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_DE, R.string.GP_DESCRIPTION_CONTINUE_NEW_DE, R.string.GP_DESCRIPTION_ALLOW_NEW_DE}, new int[]{R.string.GP_SETTING_IT, R.string.GP_CANCEL_IT, R.string.GP_EXIT_IT, R.string.GP_RETRY_IT, R.string.GP_DENIED_IT, R.string.GP_DESCRIPTION_DENY_IT, R.string.GP_PHONE_IT, R.string.GP_STORAGE_IT, R.string.GP_LOCATION_IT, R.string.GP_SMS_IT, R.string.GP_CONTACTS_IT, R.string.GP_DESCRIPTION_EXIT_IT, R.string.GP_DENY_PHONE_IT, R.string.GP_DENY_STORAGE_IT, R.string.GP_DENY_LOCATION_IT, R.string.GP_DENY_SMS_IT, R.string.GP_DENY_CONTACTS_IT, R.string.GP_DESCRIPTION_CONTINUE_IT, R.string.GP_EXPLAIN_PHONE_IT, R.string.GP_EXPLAIN_STORAGE_IT, R.string.GP_EXPLAIN_LOCATION_IT, R.string.GP_EXPLAIN_SMS_IT, R.string.GP_EXPLAIN_CONTACTS_IT, R.string.GP_DESCRIPTION_ALLOW_IT, R.string.GP_CANNOT_GO_BACK_IT, R.string.GP_FIRST_EXPLAIN_PHONE_IT, R.string.GP_FIRST_EXPLAIN_STORAGE_IT, R.string.GP_FIRST_EXPLAIN_LOCATION_IT, R.string.GP_FIRST_EXPLAIN_SMS_IT, R.string.GP_FIRST_EXPLAIN_CONTACTS_IT, R.string.GP_REQUEST_TITLE_IT, R.string.GP_OK_IT, R.string.GP_CAMERA_IT, R.string.GP_DENY_CAMERA_IT, R.string.GP_EXPLAIN_CAMERA_IT, R.string.GP_FIRST_EXPLAIN_CAMERA_IT, R.string.GP_DENY_SMS_AND_PHONE_IT, R.string.GP_EXPLAIN_SMS_AND_PHONE_IT, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_IT, R.string.GP_DENY_PHONE_NEW_IT, R.string.GP_EXPLAIN_PHONE_NEW_IT, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_IT, R.string.GP_DESCRIPTION_CONTINUE_NEW_IT, R.string.GP_DESCRIPTION_ALLOW_NEW_IT}, new int[]{R.string.GP_SETTING_ES, R.string.GP_CANCEL_ES, R.string.GP_EXIT_ES, R.string.GP_RETRY_ES, R.string.GP_DENIED_ES, R.string.GP_DESCRIPTION_DENY_ES, R.string.GP_PHONE_ES, R.string.GP_STORAGE_ES, R.string.GP_LOCATION_ES, R.string.GP_SMS_ES, R.string.GP_CONTACTS_ES, R.string.GP_DESCRIPTION_EXIT_ES, R.string.GP_DENY_PHONE_ES, R.string.GP_DENY_STORAGE_ES, R.string.GP_DENY_LOCATION_ES, R.string.GP_DENY_SMS_ES, R.string.GP_DENY_CONTACTS_ES, R.string.GP_DESCRIPTION_CONTINUE_ES, R.string.GP_EXPLAIN_PHONE_ES, R.string.GP_EXPLAIN_STORAGE_ES, R.string.GP_EXPLAIN_LOCATION_ES, R.string.GP_EXPLAIN_SMS_ES, R.string.GP_EXPLAIN_CONTACTS_ES, R.string.GP_DESCRIPTION_ALLOW_ES, R.string.GP_CANNOT_GO_BACK_ES, R.string.GP_FIRST_EXPLAIN_PHONE_ES, R.string.GP_FIRST_EXPLAIN_STORAGE_ES, R.string.GP_FIRST_EXPLAIN_LOCATION_ES, R.string.GP_FIRST_EXPLAIN_SMS_ES, R.string.GP_FIRST_EXPLAIN_CONTACTS_ES, R.string.GP_REQUEST_TITLE_ES, R.string.GP_OK_ES, R.string.GP_CAMERA_ES, R.string.GP_DENY_CAMERA_ES, R.string.GP_EXPLAIN_CAMERA_ES, R.string.GP_FIRST_EXPLAIN_CAMERA_ES, R.string.GP_DENY_SMS_AND_PHONE_ES, R.string.GP_EXPLAIN_SMS_AND_PHONE_ES, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_ES, R.string.GP_DENY_PHONE_NEW_ES, R.string.GP_EXPLAIN_PHONE_NEW_ES, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_ES, R.string.GP_DESCRIPTION_CONTINUE_NEW_ES, R.string.GP_DESCRIPTION_ALLOW_NEW_ES}, new int[]{R.string.GP_SETTING_JP, R.string.GP_CANCEL_JP, R.string.GP_EXIT_JP, R.string.GP_RETRY_JP, R.string.GP_DENIED_JP, R.string.GP_DESCRIPTION_DENY_JP, R.string.GP_PHONE_JP, R.string.GP_STORAGE_JP, R.string.GP_LOCATION_JP, R.string.GP_SMS_JP, R.string.GP_CONTACTS_JP, R.string.GP_DESCRIPTION_EXIT_JP, R.string.GP_DENY_PHONE_JP, R.string.GP_DENY_STORAGE_JP, R.string.GP_DENY_LOCATION_JP, R.string.GP_DENY_SMS_JP, R.string.GP_DENY_CONTACTS_JP, R.string.GP_DESCRIPTION_CONTINUE_JP, R.string.GP_EXPLAIN_PHONE_JP, R.string.GP_EXPLAIN_STORAGE_JP, R.string.GP_EXPLAIN_LOCATION_JP, R.string.GP_EXPLAIN_SMS_JP, R.string.GP_EXPLAIN_CONTACTS_JP, R.string.GP_DESCRIPTION_ALLOW_JP, R.string.GP_CANNOT_GO_BACK_JP, R.string.GP_FIRST_EXPLAIN_PHONE_JP, R.string.GP_FIRST_EXPLAIN_STORAGE_JP, R.string.GP_FIRST_EXPLAIN_LOCATION_JP, R.string.GP_FIRST_EXPLAIN_SMS_JP, R.string.GP_FIRST_EXPLAIN_CONTACTS_JP, R.string.GP_REQUEST_TITLE_JP, R.string.GP_OK_JP, R.string.GP_CAMERA_JP, R.string.GP_DENY_CAMERA_JP, R.string.GP_EXPLAIN_CAMERA_JP, R.string.GP_FIRST_EXPLAIN_CAMERA_JP, R.string.GP_DENY_SMS_AND_PHONE_JP, R.string.GP_EXPLAIN_SMS_AND_PHONE_JP, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_JP, R.string.GP_DENY_PHONE_NEW_JP, R.string.GP_EXPLAIN_PHONE_NEW_JP, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_JP, R.string.GP_DESCRIPTION_CONTINUE_NEW_JP, R.string.GP_DESCRIPTION_ALLOW_NEW_JP}, new int[]{R.string.GP_SETTING_KR, R.string.GP_CANCEL_KR, R.string.GP_EXIT_KR, R.string.GP_RETRY_KR, R.string.GP_DENIED_KR, R.string.GP_DESCRIPTION_DENY_KR, R.string.GP_PHONE_KR, R.string.GP_STORAGE_KR, R.string.GP_LOCATION_KR, R.string.GP_SMS_KR, R.string.GP_CONTACTS_KR, R.string.GP_DESCRIPTION_EXIT_KR, R.string.GP_DENY_PHONE_KR, R.string.GP_DENY_STORAGE_KR, R.string.GP_DENY_LOCATION_KR, R.string.GP_DENY_SMS_KR, R.string.GP_DENY_CONTACTS_KR, R.string.GP_DESCRIPTION_CONTINUE_KR, R.string.GP_EXPLAIN_PHONE_KR, R.string.GP_EXPLAIN_STORAGE_KR, R.string.GP_EXPLAIN_LOCATION_KR, R.string.GP_EXPLAIN_SMS_KR, R.string.GP_EXPLAIN_CONTACTS_KR, R.string.GP_DESCRIPTION_ALLOW_KR, R.string.GP_CANNOT_GO_BACK_KR, R.string.GP_FIRST_EXPLAIN_PHONE_KR, R.string.GP_FIRST_EXPLAIN_STORAGE_KR, R.string.GP_FIRST_EXPLAIN_LOCATION_KR, R.string.GP_FIRST_EXPLAIN_SMS_KR, R.string.GP_FIRST_EXPLAIN_CONTACTS_KR, R.string.GP_REQUEST_TITLE_KR, R.string.GP_OK_KR, R.string.GP_CAMERA_KR, R.string.GP_DENY_CAMERA_KR, R.string.GP_EXPLAIN_CAMERA_KR, R.string.GP_FIRST_EXPLAIN_CAMERA_KR, R.string.GP_DENY_SMS_AND_PHONE_KR, R.string.GP_EXPLAIN_SMS_AND_PHONE_KR, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_KR, R.string.GP_DENY_PHONE_NEW_KR, R.string.GP_EXPLAIN_PHONE_NEW_KR, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_KR, R.string.GP_DESCRIPTION_CONTINUE_NEW_KR, R.string.GP_DESCRIPTION_ALLOW_NEW_KR}, new int[]{R.string.GP_SETTING_BR, R.string.GP_CANCEL_BR, R.string.GP_EXIT_BR, R.string.GP_RETRY_BR, R.string.GP_DENIED_BR, R.string.GP_DESCRIPTION_DENY_BR, R.string.GP_PHONE_BR, R.string.GP_STORAGE_BR, R.string.GP_LOCATION_BR, R.string.GP_SMS_BR, R.string.GP_CONTACTS_BR, R.string.GP_DESCRIPTION_EXIT_BR, R.string.GP_DENY_PHONE_BR, R.string.GP_DENY_STORAGE_BR, R.string.GP_DENY_LOCATION_BR, R.string.GP_DENY_SMS_BR, R.string.GP_DENY_CONTACTS_BR, R.string.GP_DESCRIPTION_CONTINUE_BR, R.string.GP_EXPLAIN_PHONE_BR, R.string.GP_EXPLAIN_STORAGE_BR, R.string.GP_EXPLAIN_LOCATION_BR, R.string.GP_EXPLAIN_SMS_BR, R.string.GP_EXPLAIN_CONTACTS_BR, R.string.GP_DESCRIPTION_ALLOW_BR, R.string.GP_CANNOT_GO_BACK_BR, R.string.GP_FIRST_EXPLAIN_PHONE_BR, R.string.GP_FIRST_EXPLAIN_STORAGE_BR, R.string.GP_FIRST_EXPLAIN_LOCATION_BR, R.string.GP_FIRST_EXPLAIN_SMS_BR, R.string.GP_FIRST_EXPLAIN_CONTACTS_BR, R.string.GP_REQUEST_TITLE_BR, R.string.GP_OK_BR, R.string.GP_CAMERA_BR, R.string.GP_DENY_CAMERA_BR, R.string.GP_EXPLAIN_CAMERA_BR, R.string.GP_FIRST_EXPLAIN_CAMERA_BR, R.string.GP_DENY_SMS_AND_PHONE_BR, R.string.GP_EXPLAIN_SMS_AND_PHONE_BR, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_BR, R.string.GP_DENY_PHONE_NEW_BR, R.string.GP_EXPLAIN_PHONE_NEW_BR, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_BR, R.string.GP_DESCRIPTION_CONTINUE_NEW_BR, R.string.GP_DESCRIPTION_ALLOW_NEW_BR}, new int[]{R.string.GP_SETTING_PT, R.string.GP_CANCEL_PT, R.string.GP_EXIT_PT, R.string.GP_RETRY_PT, R.string.GP_DENIED_PT, R.string.GP_DESCRIPTION_DENY_PT, R.string.GP_PHONE_PT, R.string.GP_STORAGE_PT, R.string.GP_LOCATION_PT, R.string.GP_SMS_PT, R.string.GP_CONTACTS_PT, R.string.GP_DESCRIPTION_EXIT_PT, R.string.GP_DENY_PHONE_PT, R.string.GP_DENY_STORAGE_PT, R.string.GP_DENY_LOCATION_PT, R.string.GP_DENY_SMS_PT, R.string.GP_DENY_CONTACTS_PT, R.string.GP_DESCRIPTION_CONTINUE_PT, R.string.GP_EXPLAIN_PHONE_PT, R.string.GP_EXPLAIN_STORAGE_PT, R.string.GP_EXPLAIN_LOCATION_PT, R.string.GP_EXPLAIN_SMS_PT, R.string.GP_EXPLAIN_CONTACTS_PT, R.string.GP_DESCRIPTION_ALLOW_PT, R.string.GP_CANNOT_GO_BACK_PT, R.string.GP_FIRST_EXPLAIN_PHONE_PT, R.string.GP_FIRST_EXPLAIN_STORAGE_PT, R.string.GP_FIRST_EXPLAIN_LOCATION_PT, R.string.GP_FIRST_EXPLAIN_SMS_PT, R.string.GP_FIRST_EXPLAIN_CONTACTS_PT, R.string.GP_REQUEST_TITLE_PT, R.string.GP_OK_PT, R.string.GP_CAMERA_PT, R.string.GP_DENY_CAMERA_PT, R.string.GP_EXPLAIN_CAMERA_PT, R.string.GP_FIRST_EXPLAIN_CAMERA_PT, R.string.GP_DENY_SMS_AND_PHONE_PT, R.string.GP_EXPLAIN_SMS_AND_PHONE_PT, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_PT, R.string.GP_DENY_PHONE_NEW_PT, R.string.GP_EXPLAIN_PHONE_NEW_PT, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_PT, R.string.GP_DESCRIPTION_CONTINUE_NEW_PT, R.string.GP_DESCRIPTION_ALLOW_NEW_PT}, new int[]{R.string.GP_SETTING_RU, R.string.GP_CANCEL_RU, R.string.GP_EXIT_RU, R.string.GP_RETRY_RU, R.string.GP_DENIED_RU, R.string.GP_DESCRIPTION_DENY_RU, R.string.GP_PHONE_RU, R.string.GP_STORAGE_RU, R.string.GP_LOCATION_RU, R.string.GP_SMS_RU, R.string.GP_CONTACTS_RU, R.string.GP_DESCRIPTION_EXIT_RU, R.string.GP_DENY_PHONE_RU, R.string.GP_DENY_STORAGE_RU, R.string.GP_DENY_LOCATION_RU, R.string.GP_DENY_SMS_RU, R.string.GP_DENY_CONTACTS_RU, R.string.GP_DESCRIPTION_CONTINUE_RU, R.string.GP_EXPLAIN_PHONE_RU, R.string.GP_EXPLAIN_STORAGE_RU, R.string.GP_EXPLAIN_LOCATION_RU, R.string.GP_EXPLAIN_SMS_RU, R.string.GP_EXPLAIN_CONTACTS_RU, R.string.GP_DESCRIPTION_ALLOW_RU, R.string.GP_CANNOT_GO_BACK_RU, R.string.GP_FIRST_EXPLAIN_PHONE_RU, R.string.GP_FIRST_EXPLAIN_STORAGE_RU, R.string.GP_FIRST_EXPLAIN_LOCATION_RU, R.string.GP_FIRST_EXPLAIN_SMS_RU, R.string.GP_FIRST_EXPLAIN_CONTACTS_RU, R.string.GP_REQUEST_TITLE_RU, R.string.GP_OK_RU, R.string.GP_CAMERA_RU, R.string.GP_DENY_CAMERA_RU, R.string.GP_EXPLAIN_CAMERA_RU, R.string.GP_FIRST_EXPLAIN_CAMERA_RU, R.string.GP_DENY_SMS_AND_PHONE_RU, R.string.GP_EXPLAIN_SMS_AND_PHONE_RU, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_RU, R.string.GP_DENY_PHONE_NEW_RU, R.string.GP_EXPLAIN_PHONE_NEW_RU, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_RU, R.string.GP_DESCRIPTION_CONTINUE_NEW_RU, R.string.GP_DESCRIPTION_ALLOW_NEW_RU}, new int[]{R.string.GP_SETTING_PL, R.string.GP_CANCEL_PL, R.string.GP_EXIT_PL, R.string.GP_RETRY_PL, R.string.GP_DENIED_PL, R.string.GP_DESCRIPTION_DENY_PL, R.string.GP_PHONE_PL, R.string.GP_STORAGE_PL, R.string.GP_LOCATION_PL, R.string.GP_SMS_PL, R.string.GP_CONTACTS_PL, R.string.GP_DESCRIPTION_EXIT_PL, R.string.GP_DENY_PHONE_PL, R.string.GP_DENY_STORAGE_PL, R.string.GP_DENY_LOCATION_PL, R.string.GP_DENY_SMS_PL, R.string.GP_DENY_CONTACTS_PL, R.string.GP_DESCRIPTION_CONTINUE_PL, R.string.GP_EXPLAIN_PHONE_PL, R.string.GP_EXPLAIN_STORAGE_PL, R.string.GP_EXPLAIN_LOCATION_PL, R.string.GP_EXPLAIN_SMS_PL, R.string.GP_EXPLAIN_CONTACTS_PL, R.string.GP_DESCRIPTION_ALLOW_PL, R.string.GP_CANNOT_GO_BACK_PL, R.string.GP_FIRST_EXPLAIN_PHONE_PL, R.string.GP_FIRST_EXPLAIN_STORAGE_PL, R.string.GP_FIRST_EXPLAIN_LOCATION_PL, R.string.GP_FIRST_EXPLAIN_SMS_PL, R.string.GP_FIRST_EXPLAIN_CONTACTS_PL, R.string.GP_REQUEST_TITLE_PL, R.string.GP_OK_PL, R.string.GP_CAMERA_PL, R.string.GP_DENY_CAMERA_PL, R.string.GP_EXPLAIN_CAMERA_PL, R.string.GP_FIRST_EXPLAIN_CAMERA_PL, R.string.GP_DENY_SMS_AND_PHONE_PL, R.string.GP_EXPLAIN_SMS_AND_PHONE_PL, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_PL, R.string.GP_DENY_PHONE_NEW_PL, R.string.GP_EXPLAIN_PHONE_NEW_PL, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_PL, R.string.GP_DESCRIPTION_CONTINUE_NEW_PL, R.string.GP_DESCRIPTION_ALLOW_NEW_PL}, new int[]{R.string.GP_SETTING_TR, R.string.GP_CANCEL_TR, R.string.GP_EXIT_TR, R.string.GP_RETRY_TR, R.string.GP_DENIED_TR, R.string.GP_DESCRIPTION_DENY_TR, R.string.GP_PHONE_TR, R.string.GP_STORAGE_TR, R.string.GP_LOCATION_TR, R.string.GP_SMS_TR, R.string.GP_CONTACTS_TR, R.string.GP_DESCRIPTION_EXIT_TR, R.string.GP_DENY_PHONE_TR, R.string.GP_DENY_STORAGE_TR, R.string.GP_DENY_LOCATION_TR, R.string.GP_DENY_SMS_TR, R.string.GP_DENY_CONTACTS_TR, R.string.GP_DESCRIPTION_CONTINUE_TR, R.string.GP_EXPLAIN_PHONE_TR, R.string.GP_EXPLAIN_STORAGE_TR, R.string.GP_EXPLAIN_LOCATION_TR, R.string.GP_EXPLAIN_SMS_TR, R.string.GP_EXPLAIN_CONTACTS_TR, R.string.GP_DESCRIPTION_ALLOW_TR, R.string.GP_CANNOT_GO_BACK_TR, R.string.GP_FIRST_EXPLAIN_PHONE_TR, R.string.GP_FIRST_EXPLAIN_STORAGE_TR, R.string.GP_FIRST_EXPLAIN_LOCATION_TR, R.string.GP_FIRST_EXPLAIN_SMS_TR, R.string.GP_FIRST_EXPLAIN_CONTACTS_TR, R.string.GP_REQUEST_TITLE_TR, R.string.GP_OK_TR, R.string.GP_CAMERA_TR, R.string.GP_DENY_CAMERA_TR, R.string.GP_EXPLAIN_CAMERA_TR, R.string.GP_FIRST_EXPLAIN_CAMERA_TR, R.string.GP_DENY_SMS_AND_PHONE_TR, R.string.GP_EXPLAIN_SMS_AND_PHONE_TR, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_TR, R.string.GP_DENY_PHONE_NEW_TR, R.string.GP_EXPLAIN_PHONE_NEW_TR, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_TR, R.string.GP_DESCRIPTION_CONTINUE_NEW_TR, R.string.GP_DESCRIPTION_ALLOW_NEW_TR}, new int[]{R.string.GP_SETTING_AR, R.string.GP_CANCEL_AR, R.string.GP_EXIT_AR, R.string.GP_RETRY_AR, R.string.GP_DENIED_AR, R.string.GP_DESCRIPTION_DENY_AR, R.string.GP_PHONE_AR, R.string.GP_STORAGE_AR, R.string.GP_LOCATION_AR, R.string.GP_SMS_AR, R.string.GP_CONTACTS_AR, R.string.GP_DESCRIPTION_EXIT_AR, R.string.GP_DENY_PHONE_AR, R.string.GP_DENY_STORAGE_AR, R.string.GP_DENY_LOCATION_AR, R.string.GP_DENY_SMS_AR, R.string.GP_DENY_CONTACTS_AR, R.string.GP_DESCRIPTION_CONTINUE_AR, R.string.GP_EXPLAIN_PHONE_AR, R.string.GP_EXPLAIN_STORAGE_AR, R.string.GP_EXPLAIN_LOCATION_AR, R.string.GP_EXPLAIN_SMS_AR, R.string.GP_EXPLAIN_CONTACTS_AR, R.string.GP_DESCRIPTION_ALLOW_AR, R.string.GP_CANNOT_GO_BACK_AR, R.string.GP_FIRST_EXPLAIN_PHONE_AR, R.string.GP_FIRST_EXPLAIN_STORAGE_AR, R.string.GP_FIRST_EXPLAIN_LOCATION_AR, R.string.GP_FIRST_EXPLAIN_SMS_AR, R.string.GP_FIRST_EXPLAIN_CONTACTS_AR, R.string.GP_REQUEST_TITLE_AR, R.string.GP_OK_AR, R.string.GP_CAMERA_AR, R.string.GP_DENY_CAMERA_AR, R.string.GP_EXPLAIN_CAMERA_AR, R.string.GP_FIRST_EXPLAIN_CAMERA_AR, R.string.GP_DENY_SMS_AND_PHONE_AR, R.string.GP_EXPLAIN_SMS_AND_PHONE_AR, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_AR, R.string.GP_DENY_PHONE_NEW_AR, R.string.GP_EXPLAIN_PHONE_NEW_AR, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_AR, R.string.GP_DESCRIPTION_CONTINUE_NEW_AR, R.string.GP_DESCRIPTION_ALLOW_NEW_AR}, new int[]{R.string.GP_SETTING_TH, R.string.GP_CANCEL_TH, R.string.GP_EXIT_TH, R.string.GP_RETRY_TH, R.string.GP_DENIED_TH, R.string.GP_DESCRIPTION_DENY_TH, R.string.GP_PHONE_TH, R.string.GP_STORAGE_TH, R.string.GP_LOCATION_TH, R.string.GP_SMS_TH, R.string.GP_CONTACTS_TH, R.string.GP_DESCRIPTION_EXIT_TH, R.string.GP_DENY_PHONE_TH, R.string.GP_DENY_STORAGE_TH, R.string.GP_DENY_LOCATION_TH, R.string.GP_DENY_SMS_TH, R.string.GP_DENY_CONTACTS_TH, R.string.GP_DESCRIPTION_CONTINUE_TH, R.string.GP_EXPLAIN_PHONE_TH, R.string.GP_EXPLAIN_STORAGE_TH, R.string.GP_EXPLAIN_LOCATION_TH, R.string.GP_EXPLAIN_SMS_TH, R.string.GP_EXPLAIN_CONTACTS_TH, R.string.GP_DESCRIPTION_ALLOW_TH, R.string.GP_CANNOT_GO_BACK_TH, R.string.GP_FIRST_EXPLAIN_PHONE_TH, R.string.GP_FIRST_EXPLAIN_STORAGE_TH, R.string.GP_FIRST_EXPLAIN_LOCATION_TH, R.string.GP_FIRST_EXPLAIN_SMS_TH, R.string.GP_FIRST_EXPLAIN_CONTACTS_TH, R.string.GP_REQUEST_TITLE_TH, R.string.GP_OK_TH, R.string.GP_CAMERA_TH, R.string.GP_DENY_CAMERA_TH, R.string.GP_EXPLAIN_CAMERA_TH, R.string.GP_FIRST_EXPLAIN_CAMERA_TH, R.string.GP_DENY_SMS_AND_PHONE_TH, R.string.GP_EXPLAIN_SMS_AND_PHONE_TH, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_TH, R.string.GP_DENY_PHONE_NEW_TH, R.string.GP_EXPLAIN_PHONE_NEW_TH, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_TH, R.string.GP_DESCRIPTION_CONTINUE_NEW_TH, R.string.GP_DESCRIPTION_ALLOW_NEW_TH}, new int[]{R.string.GP_SETTING_VI, R.string.GP_CANCEL_VI, R.string.GP_EXIT_VI, R.string.GP_RETRY_VI, R.string.GP_DENIED_VI, R.string.GP_DESCRIPTION_DENY_VI, R.string.GP_PHONE_VI, R.string.GP_STORAGE_VI, R.string.GP_LOCATION_VI, R.string.GP_SMS_VI, R.string.GP_CONTACTS_VI, R.string.GP_DESCRIPTION_EXIT_VI, R.string.GP_DENY_PHONE_VI, R.string.GP_DENY_STORAGE_VI, R.string.GP_DENY_LOCATION_VI, R.string.GP_DENY_SMS_VI, R.string.GP_DENY_CONTACTS_VI, R.string.GP_DESCRIPTION_CONTINUE_VI, R.string.GP_EXPLAIN_PHONE_VI, R.string.GP_EXPLAIN_STORAGE_VI, R.string.GP_EXPLAIN_LOCATION_VI, R.string.GP_EXPLAIN_SMS_VI, R.string.GP_EXPLAIN_CONTACTS_VI, R.string.GP_DESCRIPTION_ALLOW_VI, R.string.GP_CANNOT_GO_BACK_VI, R.string.GP_FIRST_EXPLAIN_PHONE_VI, R.string.GP_FIRST_EXPLAIN_STORAGE_VI, R.string.GP_FIRST_EXPLAIN_LOCATION_VI, R.string.GP_FIRST_EXPLAIN_SMS_VI, R.string.GP_FIRST_EXPLAIN_CONTACTS_VI, R.string.GP_REQUEST_TITLE_VI, R.string.GP_OK_VI, R.string.GP_CAMERA_VI, R.string.GP_DENY_CAMERA_VI, R.string.GP_EXPLAIN_CAMERA_VI, R.string.GP_FIRST_EXPLAIN_CAMERA_VI, R.string.GP_DENY_SMS_AND_PHONE_VI, R.string.GP_EXPLAIN_SMS_AND_PHONE_VI, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_VI, R.string.GP_DENY_PHONE_NEW_VI, R.string.GP_EXPLAIN_PHONE_NEW_VI, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_VI, R.string.GP_DESCRIPTION_CONTINUE_NEW_VI, R.string.GP_DESCRIPTION_ALLOW_NEW_VI}, new int[]{R.string.GP_SETTING_SC, R.string.GP_CANCEL_SC, R.string.GP_EXIT_SC, R.string.GP_RETRY_SC, R.string.GP_DENIED_SC, R.string.GP_DESCRIPTION_DENY_SC, R.string.GP_PHONE_SC, R.string.GP_STORAGE_SC, R.string.GP_LOCATION_SC, R.string.GP_SMS_SC, R.string.GP_CONTACTS_SC, R.string.GP_DESCRIPTION_EXIT_SC, R.string.GP_DENY_PHONE_SC, R.string.GP_DENY_STORAGE_SC, R.string.GP_DENY_LOCATION_SC, R.string.GP_DENY_SMS_SC, R.string.GP_DENY_CONTACTS_SC, R.string.GP_DESCRIPTION_CONTINUE_SC, R.string.GP_EXPLAIN_PHONE_SC, R.string.GP_EXPLAIN_STORAGE_SC, R.string.GP_EXPLAIN_LOCATION_SC, R.string.GP_EXPLAIN_SMS_SC, R.string.GP_EXPLAIN_CONTACTS_SC, R.string.GP_DESCRIPTION_ALLOW_SC, R.string.GP_CANNOT_GO_BACK_SC, R.string.GP_FIRST_EXPLAIN_PHONE_SC, R.string.GP_FIRST_EXPLAIN_STORAGE_SC, R.string.GP_FIRST_EXPLAIN_LOCATION_SC, R.string.GP_FIRST_EXPLAIN_SMS_SC, R.string.GP_FIRST_EXPLAIN_CONTACTS_SC, R.string.GP_REQUEST_TITLE_SC, R.string.GP_OK_SC, R.string.GP_CAMERA_SC, R.string.GP_DENY_CAMERA_SC, R.string.GP_EXPLAIN_CAMERA_SC, R.string.GP_FIRST_EXPLAIN_CAMERA_SC, R.string.GP_DENY_SMS_AND_PHONE_SC, R.string.GP_EXPLAIN_SMS_AND_PHONE_SC, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_SC, R.string.GP_DENY_PHONE_NEW_SC, R.string.GP_EXPLAIN_PHONE_NEW_SC, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_SC, R.string.GP_DESCRIPTION_CONTINUE_NEW_SC, R.string.GP_DESCRIPTION_ALLOW_NEW_SC}, new int[]{R.string.GP_SETTING_ES_LATAM, R.string.GP_CANCEL_ES_LATAM, R.string.GP_EXIT_ES_LATAM, R.string.GP_RETRY_ES_LATAM, R.string.GP_DENIED_ES_LATAM, R.string.GP_DESCRIPTION_DENY_ES_LATAM, R.string.GP_PHONE_ES_LATAM, R.string.GP_STORAGE_ES_LATAM, R.string.GP_LOCATION_ES_LATAM, R.string.GP_SMS_ES_LATAM, R.string.GP_CONTACTS_ES_LATAM, R.string.GP_DESCRIPTION_EXIT_ES_LATAM, R.string.GP_DENY_PHONE_ES_LATAM, R.string.GP_DENY_STORAGE_ES_LATAM, R.string.GP_DENY_LOCATION_ES_LATAM, R.string.GP_DENY_SMS_ES_LATAM, R.string.GP_DENY_CONTACTS_ES_LATAM, R.string.GP_DESCRIPTION_CONTINUE_ES_LATAM, R.string.GP_EXPLAIN_PHONE_ES_LATAM, R.string.GP_EXPLAIN_STORAGE_ES_LATAM, R.string.GP_EXPLAIN_LOCATION_ES_LATAM, R.string.GP_EXPLAIN_SMS_ES_LATAM, R.string.GP_EXPLAIN_CONTACTS_ES_LATAM, R.string.GP_DESCRIPTION_ALLOW_ES_LATAM, R.string.GP_CANNOT_GO_BACK_ES_LATAM, R.string.GP_FIRST_EXPLAIN_PHONE_ES_LATAM, R.string.GP_FIRST_EXPLAIN_STORAGE_ES_LATAM, R.string.GP_FIRST_EXPLAIN_LOCATION_ES_LATAM, R.string.GP_FIRST_EXPLAIN_SMS_ES_LATAM, R.string.GP_FIRST_EXPLAIN_CONTACTS_ES_LATAM, R.string.GP_REQUEST_TITLE_ES_LATAM, R.string.GP_OK_ES_LATAM, R.string.GP_CAMERA_ES_LATAM, R.string.GP_DENY_CAMERA_ES_LATAM, R.string.GP_EXPLAIN_CAMERA_ES_LATAM, R.string.GP_FIRST_EXPLAIN_CAMERA_ES_LATAM, R.string.GP_DENY_SMS_AND_PHONE_ES_LATAM, R.string.GP_EXPLAIN_SMS_AND_PHONE_ES_LATAM, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_ES_LATAM, R.string.GP_DENY_PHONE_NEW_ES_LATAM, R.string.GP_EXPLAIN_PHONE_NEW_ES_LATAM, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_ES_LATAM, R.string.GP_DESCRIPTION_CONTINUE_NEW_ES_LATAM, R.string.GP_DESCRIPTION_ALLOW_NEW_ES_LATAM}, new int[]{R.string.GP_SETTING_CN, R.string.GP_CANCEL_CN, R.string.GP_EXIT_CN, R.string.GP_RETRY_CN, R.string.GP_DENIED_CN, R.string.GP_DESCRIPTION_DENY_CN, R.string.GP_PHONE_CN, R.string.GP_STORAGE_CN, R.string.GP_LOCATION_CN, R.string.GP_SMS_CN, R.string.GP_CONTACTS_CN, R.string.GP_DESCRIPTION_EXIT_CN, R.string.GP_DENY_PHONE_CN, R.string.GP_DENY_STORAGE_CN, R.string.GP_DENY_LOCATION_CN, R.string.GP_DENY_SMS_CN, R.string.GP_DENY_CONTACTS_CN, R.string.GP_DESCRIPTION_CONTINUE_CN, R.string.GP_EXPLAIN_PHONE_CN, R.string.GP_EXPLAIN_STORAGE_CN, R.string.GP_EXPLAIN_LOCATION_CN, R.string.GP_EXPLAIN_SMS_CN, R.string.GP_EXPLAIN_CONTACTS_CN, R.string.GP_DESCRIPTION_ALLOW_CN, R.string.GP_CANNOT_GO_BACK_CN, R.string.GP_FIRST_EXPLAIN_PHONE_CN, R.string.GP_FIRST_EXPLAIN_STORAGE_CN, R.string.GP_FIRST_EXPLAIN_LOCATION_CN, R.string.GP_FIRST_EXPLAIN_SMS_CN, R.string.GP_FIRST_EXPLAIN_CONTACTS_CN, R.string.GP_REQUEST_TITLE_CN, R.string.GP_OK_CN, R.string.GP_CAMERA_CN, R.string.GP_DENY_CAMERA_CN, R.string.GP_EXPLAIN_CAMERA_CN, R.string.GP_FIRST_EXPLAIN_CAMERA_CN, R.string.GP_DENY_SMS_AND_PHONE_CN, R.string.GP_EXPLAIN_SMS_AND_PHONE_CN, R.string.GP_FIRST_EXPLAIN_SMS_AND_PHONE_CN, R.string.GP_DENY_PHONE_NEW_CN, R.string.GP_EXPLAIN_PHONE_NEW_CN, R.string.GP_FIRST_EXPLAIN_PHONE_NEW_CN, R.string.GP_DESCRIPTION_CONTINUE_NEW_CN, R.string.GP_DESCRIPTION_ALLOW_NEW_CN}};
    public static boolean bfA = false;

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (d.bfm != 0) {
            setRequestedOrientation(0);
        }
        if (!au.biO) {
            Intent intent = new Intent();
            intent.putExtra("StartFromOutMem", "Yes");
            intent.setClassName(getPackageName(), getPackageName() + ".Start");
            intent.addFlags(268468224);
            startActivity(intent);
            finish();
            onDestroy();
            return;
        }
        bex = this;
        f(getIntent().getExtras().getStringArray("permission"));
        bfx = false;
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        au.Ql();
    }

    @Override // javax.microedition.midlet.BaseActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        String str = "On Resume:" + bfx;
        if (d.bfm != 0) {
            setRequestedOrientation(0);
        }
    }

    public void f(String[] strArr) {
        String upperCase;
        if (strArr != null) {
            try {
                String strA = au.A("save_game_language", "", getPackageName());
                String str = "gamelanguage:" + strA;
                if (strA != null && !strA.equals("")) {
                    upperCase = strA.toUpperCase();
                    if (upperCase.equals("ZH")) {
                        upperCase = "SC";
                    }
                    if (upperCase.equals("ZT")) {
                        upperCase = "CN";
                    }
                } else {
                    upperCase = au.Pk().toUpperCase();
                    if (upperCase.equals("KO")) {
                        upperCase = "KR";
                    }
                    if (upperCase.equals("JA")) {
                        upperCase = "JP";
                    }
                    if (upperCase.equals("ZH")) {
                        if (au.Pl().toUpperCase().equals("TW")) {
                            upperCase = "CN";
                        } else {
                            upperCase = "SC";
                        }
                    }
                    if (upperCase.equals("ES")) {
                        upperCase = "SP";
                    }
                    if (upperCase.equals("EC")) {
                        upperCase = "ES_LATAM";
                    }
                    if (upperCase.equals("PT") && au.Pl().toUpperCase().equals("BR")) {
                        upperCase = "BR";
                    }
                }
            } catch (Exception e) {
                upperCase = "EN";
            }
            DG = -1;
            for (int i = 0; i < bfy.length; i++) {
                if (upperCase.equals(bfy[i])) {
                    DG = i;
                    break;
                }
            }
            if (DG == -1) {
                DG = 0;
            }
            String str2 = "languageIndex:" + DG;
            bfw = null;
            bfw = b(strArr, 0);
            if (bfw != null && !g(bfw)) {
                h(bfw);
            } else if (g(strArr)) {
                i(strArr);
            } else {
                setResult(-1);
                finish();
            }
        }
    }

    public boolean g(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            boolean zB = au.b("game_permission_denied", false, strArr[i]);
            if (!shouldShowRequestPermissionRationale(strArr[i]) && zB) {
                return false;
            }
        }
        return true;
    }

    public void h(String[] strArr) {
        bex.runOnUiThread(new i(this, strArr));
    }

    public void i(String[] strArr) {
        bex.runOnUiThread(new p(this, strArr));
    }

    public void j(String[] strArr) {
        bex.runOnUiThread(new s(this, strArr));
    }

    public void OA() {
        String[] strArrB = b(bfw, 0);
        if (strArrB != null) {
            for (String str : strArrB) {
                String str2 = "Need Allow:" + str;
            }
            if (g(strArrB)) {
                j(strArrB);
                return;
            } else {
                h(strArrB);
                return;
            }
        }
        setResult(-1);
        finish();
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        for (String str : strArr) {
            String str2 = "Permission: " + str + " is " + (iArr[0] == 0 ? "ALLOWED" : "DENIED");
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (strArr[i2].equals("android.permission.READ_PHONE_STATE")) {
                if (iArr[i2] == 0) {
                    au.biw = true;
                } else {
                    au.biw = false;
                }
                au.a("game_permission_denied", Boolean.valueOf(!au.biw), strArr[i2]);
            }
            if (strArr[i2].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                if (iArr[i2] == 0) {
                    au.biy = true;
                    if (Build.VERSION.SDK_INT > 25) {
                        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
                        au.biz = true;
                    }
                } else {
                    au.biy = false;
                }
                au.a("game_permission_denied", Boolean.valueOf(!au.biy), strArr[i2]);
            }
            if (strArr[i2].equals("android.permission.ACCESS_COARSE_LOCATION")) {
                if (iArr[i2] == 0) {
                    au.bix = true;
                } else {
                    au.bix = false;
                }
                au.a("game_permission_denied", Boolean.valueOf(!au.bix), strArr[i2]);
            }
            if (strArr[i2].equals("android.permission.SEND_SMS")) {
                if (iArr[i2] == 0) {
                    au.biA = true;
                    if (Build.VERSION.SDK_INT > 25) {
                        requestPermissions(new String[]{"android.permission.READ_SMS", "android.permission.RECEIVE_SMS"}, 0);
                        au.biB = true;
                        au.biC = true;
                    }
                } else {
                    au.biA = false;
                }
                au.a("game_permission_denied", Boolean.valueOf(!au.biA), strArr[i2]);
            }
            if (strArr[i2].equals("android.permission.GET_ACCOUNTS")) {
                if (iArr[i2] == 0) {
                    au.biD = true;
                } else {
                    au.biD = false;
                }
                au.a("game_permission_denied", Boolean.valueOf(!au.biD), strArr[i2]);
            }
            if (strArr[i2].equals("android.permission.CAMERA")) {
                if (iArr[i2] == 0) {
                    au.biE = true;
                } else {
                    au.biE = false;
                }
                au.a("game_permission_denied", Boolean.valueOf(!au.biE), strArr[i2]);
            }
        }
        OA();
    }

    public String[] b(String[] strArr, int i) {
        String[] strArr2;
        ArrayList arrayList = new ArrayList();
        if (strArr != null) {
            try {
                for (String str : strArr) {
                    if (checkSelfPermission(str) != 0) {
                        if (str.equals("android.permission.READ_PHONE_STATE") && au.biF > i) {
                            arrayList.add("android.permission.READ_PHONE_STATE");
                        }
                        if (str.equals("android.permission.WRITE_EXTERNAL_STORAGE") && au.biG > i) {
                            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
                        }
                        if (str.equals("android.permission.ACCESS_COARSE_LOCATION") && au.biL > i) {
                            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
                        }
                        if (str.equals("android.permission.SEND_SMS") && au.biI > i) {
                            arrayList.add("android.permission.SEND_SMS");
                        }
                        if (str.equals("android.permission.GET_ACCOUNTS") && au.biM > i) {
                            arrayList.add("android.permission.GET_ACCOUNTS");
                        }
                        if (str.equals("android.permission.CAMERA") && au.biN > i) {
                            arrayList.add("android.permission.CAMERA");
                        }
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        if (arrayList.size() <= 0) {
            strArr2 = null;
        } else {
            strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
        return strArr2;
    }
}
