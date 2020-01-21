package com.my.kozhukhar.client;

import com.my.kozhukhar.exception.AppException;
import com.my.kozhukhar.message.ErrorMessages;
import com.my.kozhukhar.message.Messages;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private int port;

    private String host;

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    private static final String FORMAT = "UTF-8";

    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void connect() throws AppException {
        try {
            Socket socket = new Socket(host, port);
            LOG.info(Messages.SUCCESS_CONNECTION);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());

            handler(socket, reader);
        } catch (IOException ex) {
            throw new AppException(ErrorMessages.ERROR_CONNECTION, ex);
        }
    }

    private void handler(Socket socket, BufferedReader reader) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), FORMAT));
        Scanner scan = new Scanner(System.in, FORMAT);

        new Thread(() -> send(out, scan)).start();
        new Thread(() -> receive(reader)).start();
    }

    private void receive(BufferedReader reader) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println(reader.readLine());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void send(PrintWriter out, Scanner scan) {
        while (!Thread.currentThread().isInterrupted()) {
            String line = scan.nextLine();
            out.println(line);
            out.flush();
        }
    }
}
