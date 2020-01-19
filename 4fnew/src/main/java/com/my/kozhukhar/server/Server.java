package com.my.kozhukhar.server;

import com.my.kozhukhar.exception.AppException;
import com.my.kozhukhar.message.ErrorMessages;
import com.my.kozhukhar.message.Messages;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;

    private static final Logger LOG = Logger.getLogger(Server.class);

    public Server(int port) {
        this.port = port;
    }

    public void start() throws AppException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            LOG.info(Messages.SERVER_WAS_STARTED);

            while (true) {
                Socket connectionSocket = serverSocket.accept();
                LOG.info(Messages.USER_WAS_CONNECTED);
                connectionSocket.close();
                LOG.info(Messages.USER_WAS_DISCONNECTED);
            }

        } catch (IOException ex) {
            throw new AppException(ErrorMessages.SERVER_IO_EXCEPTION, ex);
        }
    }
}
