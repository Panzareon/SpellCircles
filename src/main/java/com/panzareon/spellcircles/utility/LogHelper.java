package com.panzareon.spellcircles.utility;

import com.panzareon.spellcircles.reference.Reference;
import org.apache.logging.log4j.*;

public class LogHelper
{
    private final static Logger logger = LogManager.getLogger(Reference.MOD_ID);

    public static void log(Level logLevel, Object object)
    {
        logger.log(logLevel, String.valueOf(object));
    }

    public static void info(Object object)
    {
        log(Level.INFO,object);
    }
    public static void warn(Object object)
    {
        log(Level.WARN,object);
    }
    public static void error(Object object)
    {
        log(Level.ERROR,object);
    }
}
