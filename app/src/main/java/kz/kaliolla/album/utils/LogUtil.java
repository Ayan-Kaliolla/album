package kz.kaliolla.album.utils;

import android.util.Log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import kz.kaliolla.album.app.Constants;


/**
 * Created by akaliolla on 08.01.2018.
 */

public class LogUtil {
    private static final String APP_TAG = "JakkoMarket";

    public static void t(final Object sender, final String message) {
        if (Constants.IS_DEBUG) {
            final Logger log = getLogger(sender);
            if (log != null) {
                if (log.isEnabledFor(Level.TRACE)) {
                    log.trace(message);
                }
            } else {
                Log.v(APP_TAG, message);
            }
        }
    }

    public static void d(final Object sender, final String message) {
        if (Constants.IS_DEBUG) {
            final Logger log = getLogger(sender);
            if (log != null) {
                if (log.isEnabledFor(Level.DEBUG)) {
                    log.debug(message);
                }
            } else {
                Log.d(APP_TAG, message);
            }
        }
    }

    public static void i(final Object sender, final String message) {
        if (Constants.IS_DEBUG) {
            final Logger log = getLogger(sender);
            if (log != null) {
                if (log.isEnabledFor(Level.INFO)) {
                    log.info(message);
                }
            } else {
                Log.i(APP_TAG, message);
            }
        }
    }

    public static void w(final Object sender, final String message) {
        if (Constants.IS_DEBUG) {
            final Logger log = getLogger(sender);
            if (log != null) {
                if (log.isEnabledFor(Level.WARN)) {
                    log.warn(message);
                }
            } else {
                Log.w(APP_TAG, message);
            }
        }
    }

    public static void e(final Object sender, final String message) {
        if (Constants.IS_DEBUG) {
            final Logger log = getLogger(sender);
            if (log != null) {
                if (log.isEnabledFor(Level.ERROR)) {
                    log.error(message);
                }
            } else {
                Log.e(APP_TAG, message);
            }
        }
    }

    public static void e(final Object sender, final String message, final Throwable e) {
        if (Constants.IS_DEBUG) {
            final Logger log = getLogger(sender);
            if (log != null) {
                if (log.isEnabledFor(Level.ERROR)) {
                    log.error(message, e);
                }
            } else {
                Log.e(APP_TAG, message, e);
            }
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        throwable.printStackTrace(printWriter);
        return result.toString();
    }

    private static Logger getLogger(final Object sender) {
        final Class<?> clazz = sender instanceof Class ? (Class<?>) sender : sender.getClass();
        return Logger.getLogger(clazz);
    }
}
