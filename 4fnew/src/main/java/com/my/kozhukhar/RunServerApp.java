package com.my.kozhukhar;

import com.my.kozhukhar.exception.AppException;
import com.my.kozhukhar.server.Server;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class RunServerApp {

    private static final Logger LOG = Logger.getLogger(RunServerApp.class.getName());

    private static int PORT = 5500;

    public static void main(String[] args) throws AppException {
        BasicConfigurator.configure();
        initPort(args);
        Server server = new Server(PORT);
        server.start();
    }

    private static void initPort(String[] args) {
        if (args.length > 0) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                LOG.error(ex.getMessage());
            }
        }
    }

}
