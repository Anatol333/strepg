package com.cfr.kozhukhar;

import com.cfr.kozhukhar.client.Client;
import com.cfr.kozhukhar.exception.AppException;
import org.apache.log4j.BasicConfigurator;

public class RunClientApp {

    public static void main(String[] args) throws AppException {
        BasicConfigurator.configure();
        Client client = new Client("localhost", 5555);
        client.connect();
    }

}
