package com.statemachine.util;

import org.springframework.shell.Bootstrap;
import org.springframework.shell.support.logging.HandlerUtils;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by zn.wang on 17/4/24.
 */
public class IsbBootstrap extends Bootstrap{

    private static IsbBootstrap isbBootstrap;

    private static StopWatch sw = new StopWatch("Spring Shell");

    public IsbBootstrap(String[] args, String[] contextPath){
        super(args , contextPath);
    }

    public static void main(String[] args , String[] contextPath) throws IOException {
        try {
            isbBootstrap = new IsbBootstrap(args , contextPath);
            isbBootstrap.run();
        }
        catch (RuntimeException t) {
            throw t;
        }
        finally {
            HandlerUtils.flushAllHandlers(Logger.getLogger(""));
        }
    }
}
