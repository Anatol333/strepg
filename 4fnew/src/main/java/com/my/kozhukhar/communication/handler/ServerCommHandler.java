package com.my.kozhukhar.communication.handler;

import com.my.kozhukhar.message.Messages;
import com.my.kozhukhar.server.MessageSender;
import com.my.kozhukhar.server.SocketMonitoring;
import com.my.kozhukhar.server.SocketReceiving;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommHandler {

    private List<SocketMonitoring> allClients;

    private static final String SPLITTER = ":";

    private static final Logger LOG = Logger.getLogger(ServerCommHandler.class);

    public ServerCommHandler() {
        this.allClients = new ArrayList<>();
    }

    public void addSocket(Socket connectionSocket) {
        SocketMonitoring socketMonitoring = new SocketMonitoring(connectionSocket, allClients);
        SocketReceiving socketReceiving = new SocketReceiving(connectionSocket, allClients);
        allClients.add(socketMonitoring);

        initActions(socketMonitoring, socketReceiving);

        socketMonitoring.setMessageForAll(
                connectionSocket.getInetAddress() + SPLITTER + Messages.NEW_ARE_CONNECTED
        );
    }

    private void initActions(SocketMonitoring socketMonitoring, SocketReceiving socketReceiving) {
        new Thread(socketMonitoring).start();
        new Thread(socketReceiving).start();
        new Thread(new MessageSender(allClients, this)).start();
    }
}
