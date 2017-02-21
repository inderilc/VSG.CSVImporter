package com.ilc.util;

import com.evnt.client.common.ClientProperties;
import com.fbi.util.logging.FBLogger;
import com.getsentry.raven.Raven;
import com.getsentry.raven.dsn.Dsn;
import com.getsentry.raven.event.EventBuilder;
import com.getsentry.raven.event.interfaces.ExceptionInterface;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by Israel Lopez on 9/21/2016.
 */
public class SOLogger {

    public static boolean CEP = true;
    private static final Logger LOGGER = FBLogger.getLogger(SOLogger.class);
    private static Raven sentry = SORavenFactory.ravenInstance(new Dsn("https://c86194408605416099bc388e2e5364ec:3fd2997f6ab14310a87c16feadd32e64@sentry.io/104299"));

    public static void log(Level level, String message, Throwable throwable)
    {
        LOGGER.log(level, " - " + message + " - " + throwable.getMessage(), throwable);
    }

    public static void error(String message, Throwable throwable) {
        if(throwable != null)
            LOGGER.log(Level.ERROR, " - " + message + " - " + throwable.getMessage(), throwable);

        if(throwable == null)
            LOGGER.log(Level.ERROR, message);
        if(CEP){
            if(sentry == null){
                log(Level.ERROR, "Sentry Not Available.", null);
            }else{
                EventBuilder eb = new EventBuilder();
                eb.withServerName(ClientProperties.getProperty(ClientProperties.P_SERVER_ADDRESS));
                eb.withMessage(message);
                if (throwable != null) {
                    eb.withMessage(throwable.getMessage());
                    eb.withSentryInterface(new ExceptionInterface(throwable));
                }
                sentry.sendEvent( eb );
            }
        }
    }

    public static void debug(String message) {
        LOGGER.log(Level.DEBUG, " - " + message);
    }
}