package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.nio.charset.StandardCharsets;

public class LoggingManager {
    private Logger logger;
    private String[] className;
    public LoggingManager(String... className) {
        this.className = className;
    }
    private synchronized void setLogger() {
        String classN = LoggingManager.class.getName();
        if (className.length > 0) {
            classN = className[0];
        }
        logger = LogManager.getLogger(classN);
    }

    public synchronized void logError(String msg) {
        String str = new String(msg.getBytes(StandardCharsets.UTF_8));
        setLogger();
        logger.error(str);
        Assert.fail(str);
    }

    public synchronized void logInfo(String msg) {
        String str = new String(msg.getBytes(StandardCharsets.UTF_8));
        setLogger();
        logger.info(str);
    }

    public synchronized void logWarn(String msg) {
        String str = new String(msg.getBytes(StandardCharsets.UTF_8));
        setLogger();
        logger.warn(str);
    }
}
