package com.my.kozhukhar.server;

import java.util.ArrayList;
import java.util.List;

public class MessageSender implements Runnable {

    private List<SocketMonitoring> allClients;

    private final Object HANDLER_MONITOR;

    public MessageSender(List<SocketMonitoring> allClients, Object handlerMonitor) {
        this.allClients = allClients;
        this.HANDLER_MONITOR = handlerMonitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (HANDLER_MONITOR) {
                new ArrayList<>(allClients).forEach(this::sendToAllClients);
            }
        }
    }

    private void sendToAllClients(SocketMonitoring client) {
        if (client != null) {
            if (client.isInterrupted()) {
                allClients.remove(client);
            }
            client.sendMessage();
        }
    }
}
