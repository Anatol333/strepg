package com.my.kozhukhar.server;

import com.my.kozhukhar.exception.AppException;
import com.my.kozhukhar.message.ErrorMessages;
import com.my.kozhukhar.message.Messages;
import lombok.Data;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Data
public class Server {

    private int port;

    private Boolean interrupted;

    private static final Logger LOG = Logger.getLogger(Server.class);

    public Server(int port) {
        this.port = port;
    }

    public void start() throws AppException {
        interrupted = false;
        ServerCommHandler handler = new ServerCommHandler();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            LOG.info(Messages.SERVER_WAS_STARTED);

            while (!interrupted) {
                Socket connectionSocket = serverSocket.accept();
                LOG.info(Messages.USER_WAS_CONNECTED);
                handler.addSocket(connectionSocket);
            }

        } catch (IOException ex) {
            throw new AppException(ErrorMessages.SERVER_IO_EXCEPTION, ex);
        }
    }
}
