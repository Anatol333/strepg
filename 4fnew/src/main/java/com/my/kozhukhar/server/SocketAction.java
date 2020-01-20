package com.my.kozhukhar.server;

import com.my.kozhukhar.message.Messages;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class SocketAction implements Runnable {

    private String message;

    private Socket clientSocket;

    private List<SocketAction> allClients;

    private static final Logger LOG = Logger.getLogger(SocketAction.class);

    public SocketAction(Socket clientSocket, List<SocketAction> allClients) {
        this.clientSocket = clientSocket;
        this.allClients = allClients;
    }


    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            allClients.forEach(SocketAction::sendMessage);
        }
        LOG.info(Messages.USER_WAS_DISCONNECTED);
    }

    private void sendMessage() {
        try {
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            if (message != null) {
                printWriter.println(message);
                message = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageForAll(String message) {
        allClients.forEach(client -> client.setMessage(message));
    }
}
