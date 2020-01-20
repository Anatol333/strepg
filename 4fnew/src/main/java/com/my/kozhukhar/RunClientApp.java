package com.my.kozhukhar;

import com.my.kozhukhar.client.Client;
import com.my.kozhukhar.exception.AppException;
import org.apache.log4j.BasicConfigurator;

public class RunClientApp {

    public static void main(String[] args) throws AppException {
        BasicConfigurator.configure();
        Client client = new Client("localhost", 5500);
        client.connect();
    }

}
