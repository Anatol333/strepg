package com.cfr.kozhukhar.client;

import com.cfr.kozhukhar.exception.AppException;
import com.cfr.kozhukhar.message.ErrorMessages;
import com.cfr.kozhukhar.message.Messages;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private int port;

    private String host;

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void connect() throws AppException {
        try {
            Socket socket = new Socket(host, port);
            LOG.info(Messages.SUCCESS_CONNECTION);
        } catch (IOException ex) {
            throw new AppException(ErrorMessages.ERROR_CONNECTION, ex);
        }
    }
}
