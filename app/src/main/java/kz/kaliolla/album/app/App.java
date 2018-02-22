package kz.kaliolla.album.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.text.MessageFormat;

import kz.kaliolla.album.net.RestApi;
import kz.kaliolla.album.utils.LogUtil;
import retrofit2.Retrofit;

import static kz.kaliolla.album.net.Constants.BASE_URL;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class App extends Application {
    private static RestApi reatApi;

    @Override
    public void onCreate() {
        super.onCreate();
        initRestApi();
        initializeLogger();
    }

    private void initializeLogger() {
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(final Thread thread, final Throwable ex) {
                LogUtil.e(this,
                        MessageFormat.format("CRASH ALERT: uncaught exception in {0}", thread), ex);
                // TODO: Maybe send some information to our service?
                defaultHandler.uncaughtException(thread, ex);
            }
        });
        // -----------------------------------------------------------------------------------------
        // Log various info
        try {
            // Application version
            final PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            LogUtil.i(this, MessageFormat.format("Application version: {0} ({1})", info.versionName, info.versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(this, "Error getting package info", e);
        }
        // OS version
        LogUtil.i(this, MessageFormat.format("Android version: {0} {1} ({2})",
                Build.VERSION.RELEASE, Build.VERSION.CODENAME, Build.VERSION.INCREMENTAL));
        // Device model
        LogUtil.i(this,
                MessageFormat.format("Device: {0} ({1})", Build.MODEL, Build.MANUFACTURER));
    }

    private void initRestApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        reatApi = retrofit.create(RestApi.class);
    }

    public static RestApi getReatApi() {
        return reatApi;
    }
}
