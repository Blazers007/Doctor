package com.blazers.app.doctor.Util;

import android.os.Environment;

/**
 * Created by Blazers on 15/5/26.
 */
public class StorageConfig {
    public static final String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory()	+ "/bmobimdemo/image/";

    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;

    public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;
    public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;
    public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;
    public static final String EXTRA_STRING = "extra_string";
    public static final String ACTION_REGISTER_SUCCESS_FINISH ="register.success.finish";
}
