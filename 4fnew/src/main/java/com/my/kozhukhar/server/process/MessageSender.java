package com.my.kozhukhar.server.process;

import java.util.ArrayList;
import java.util.List;

public class MessageSender implements Runnable {

    private List<ServerSender> allClients;

    public MessageSender(List<ServerSender> allClients) {
        this.allClients = allClients;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            new ArrayList<>(allClients).forEach(this::sendToAllClients);
        }
    }

    private void sendToAllClients(ServerSender client) {
        if (client != null) {
            if (client.isInterrupted()) {
                allClients.remove(client);
            }
            client.sendMessage();
        }
    }
}
