package utils;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {

    private Logger logger;

    public MyLogger(String className) {
        logger = Logger.getLogger(className);
        logger.setLevel(Level.INFO);
    }

    public Logger getLogger() {
        return logger;
    }
}
