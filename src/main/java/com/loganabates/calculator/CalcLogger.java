package com.loganabates.calculator;

import java.io.IOException;
import java.util.logging.*;


/**
 * This logger code has be derived from the Vogella tutorial on java logging
 * (http://www.vogella.com/tutorials/Logging/article.html)
 */
public class CalcLogger {
    public CalcLogger() {
    }

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;
    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging.txt");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }

}


