package com.my.kozhukhar.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

public class SocketReceiving implements Runnable {

    private List<SocketAction> allClients;

    private Socket clientSocket;

    public SocketReceiving(Socket clientSocket, List<SocketAction> allClients) {
        this.allClients = allClients;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                InputStream inputStream = clientSocket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                String newMessage = dataInputStream.readLine();
                allClients.forEach(client -> client.setMessageForAll(newMessage));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
