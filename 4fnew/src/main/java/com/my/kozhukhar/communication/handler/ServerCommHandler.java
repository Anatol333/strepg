package com.my.kozhukhar.communication.handler;

import com.my.kozhukhar.message.Messages;
import com.my.kozhukhar.server.SocketAction;
import com.my.kozhukhar.server.SocketReceiving;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommHandler {

    private List<SocketAction> allClients;

    private static final String SPLITTER = ":";

    private static final Logger LOG = Logger.getLogger(ServerCommHandler.class);

    public ServerCommHandler() {
        this.allClients = new ArrayList<>();
    }

    public void addSocket(Socket connectionSocket) {
        SocketAction socketAction = new SocketAction(connectionSocket, allClients);
        SocketReceiving socketReceiving = new SocketReceiving(connectionSocket, allClients);
        allClients.add(socketAction);

        initActions(socketAction, socketReceiving);

        socketAction.setMessageForAll(
                connectionSocket.getInetAddress() + SPLITTER + Messages.YOU_ARE_CONNECTED
        );
    }

    private void initActions(SocketAction socketAction, SocketReceiving socketReceiving) {
        new Thread(socketAction).start();
        new Thread(socketReceiving).start();
    }
}
