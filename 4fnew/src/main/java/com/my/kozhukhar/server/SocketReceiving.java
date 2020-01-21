package com.my.kozhukhar.server;

import com.my.kozhukhar.message.ErrorMessages;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketReceiving implements Runnable {

    private List<SocketMonitoring> allClients;

    private Socket clientSocket;

    private static final Logger LOG = Logger.getLogger(SocketReceiving.class);

    public SocketReceiving(Socket clientSocket, List<SocketMonitoring> allClients) {
        this.allClients = allClients;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
        try {
            while (clientSocket.isConnected()) {

                inputStream = clientSocket.getInputStream();
                dataInputStream = new DataInputStream(inputStream);
                String newMessage = dataInputStream.readLine();

                List<SocketMonitoring> clientsTemp = new ArrayList<>(allClients);
                clientsTemp.forEach(client -> client.setMessageForAll(newMessage));

            }
        } catch (IOException ex) {
            LOG.error(ErrorMessages.CANNOT_READ_THR_CLOSED);
        } finally {
            closeStream(inputStream, dataInputStream);
        }
    }

    private void closeStream(InputStream inputStream, DataInputStream dataInputStream) {
        try {
            if (dataInputStream != null) {
                dataInputStream.close();
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
