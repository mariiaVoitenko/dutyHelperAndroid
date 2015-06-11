package com.voitenko.dutyhelper;

import android.os.Environment;

public class ConstantsContainer {
    public static final String USER_ID = "userId";
    public static final String DUTY_ID = "dutyId";
    public static final String ENDPOINT = "http://dutyhelper-mielientiev.rhcloud.com/";
    public static final String RESTORE_PASSWORD_LINK = "http://dutyhelper-mielientiev.rhcloud.com/#/reset/request/";
    public static final String REGISTER_LINK = "http://dutyhelper-mielientiev.rhcloud.com/#/register/";
    public static final String FILEPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "email.txt";
    public static final String FILEPATH_ID = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "id.txt";


}
