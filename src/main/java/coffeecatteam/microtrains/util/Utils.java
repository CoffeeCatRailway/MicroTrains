package coffeecatteam.microtrains.util;

import coffeecatteam.microtrains.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = LogManager.getFormatterLogger(Reference.NAME.replace("\\s+", ""));
        }
        return logger;
    }
}
