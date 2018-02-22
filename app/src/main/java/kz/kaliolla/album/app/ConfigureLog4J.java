package kz.kaliolla.album.app;

import org.apache.log4j.Level;

import java.io.File;
import java.text.MessageFormat;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import kz.kaliolla.album.utils.LogUtil;


public class ConfigureLog4J {

    public static void configure() {
        try {
            final LogConfigurator logConfigurator = new LogConfigurator();
            logConfigurator.setFileName(MessageFormat.format("{0}{1}logs{1}jakko_market.log",
                    Constants.APP_LOG_FOLDER, File.separator));
            logConfigurator.setUseFileAppender(true);
            logConfigurator.setUseLogCatAppender(true);
            logConfigurator.setMaxBackupSize(2);
            logConfigurator.setMaxFileSize(1024 * 1024);
            logConfigurator.setFilePattern("%d{dd MMM yyyy HH:mm:ss,SSS} [%p] {%t} %c{3}: %m%n");

            // Specify logging levels for various categories
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setLevel("org.apache", Level.ERROR);
            logConfigurator.configure();
        } catch (RuntimeException e) {
            LogUtil.e(ConfigureLog4J.class, "Error configuring log4j", e);
        }
    }

    private ConfigureLog4J() {
    }
}
