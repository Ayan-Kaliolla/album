package kz.kaliolla.album.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class Constants {
    public static boolean IS_DEBUG = true;
    public static final String APP_LOG_FOLDER = Environment.getExternalStorageDirectory() + File.separator + "data" + File.separator + "kz.kaliolla.album" + File.separator + "log";
}
