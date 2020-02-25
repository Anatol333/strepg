package com.my.kozhukhar.server;

import com.my.kozhukhar.message.Messages;
import com.my.kozhukhar.server.process.MessageSender;
import com.my.kozhukhar.server.process.ServerSender;
import com.my.kozhukhar.server.process.ServerReceiver;
import org.apache.log4j.Logger;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerCommHandler {

    private List<ServerSender> allClients;

    private static final String SPLITTER = ":";

    private static final Logger LOG = Logger.getLogger(ServerCommHandler.class);

    public ServerCommHandler() {
        this.allClients = new ArrayList<>();
        new Thread(new MessageSender(allClients)).start();
        LOG.info(Messages.HANDLER_WAS_INIT);
    }

    public void addSocket(Socket connectionSocket) {
        ServerSender serverSender = new ServerSender(connectionSocket, allClients);
        ServerReceiver serverReceiver = new ServerReceiver(connectionSocket, allClients);
        allClients.add(serverSender);

        initProcesses(serverSender, serverReceiver);

        serverSender.setMessageForAll(
                connectionSocket.getInetAddress() + SPLITTER + Messages.NEW_ARE_CONNECTED
        );
    }

    private void initProcesses(ServerSender serverSender, ServerReceiver serverReceiver) {
        new Thread(serverSender).start();
        new Thread(serverReceiver).start();
    }
}
