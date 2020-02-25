package com.my.kozhukhar.server.process;

import com.my.kozhukhar.message.ErrorMessages;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerReceiver implements Runnable {

    private List<ServerSender> allClients;

    private Socket clientSocket;

    private static final Logger LOG = Logger.getLogger(ServerReceiver.class);

    public ServerReceiver(Socket clientSocket, List<ServerSender> allClients) {
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
                String newMessage = dataInputStream.readUTF();

                List<ServerSender> clientsTemp = new ArrayList<>(allClients);
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
