package com.my.kozhukhar.client;

import com.my.kozhukhar.client.process.ClientReceiver;
import com.my.kozhukhar.client.process.ClientSender;
import com.my.kozhukhar.exception.AppException;
import com.my.kozhukhar.message.ErrorMessages;
import com.my.kozhukhar.message.Messages;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private int port;

    private String host;

    private Integer sleepTimeMls;

    private static final Integer DEF_SLEEP_TIME_MLS = 10_000;

    private static final Logger LOG = Logger.getLogger(Client.class);

    private static final String FORMAT = "UTF-8";

    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void connect() throws AppException {
        if (sleepTimeMls == null) {
            sleepTimeMls = DEF_SLEEP_TIME_MLS;
        }
        try {
            Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            successMessage(reader);

            handler(socket.getOutputStream(), reader);
        } catch (IOException ex) {
            throw new AppException(ErrorMessages.ERROR_CONNECTION, ex);
        }
    }

    private void handler(OutputStream out, BufferedReader reader) {
        Scanner scan = new Scanner(System.in, FORMAT);

        new Thread(new ClientSender(scan, out, sleepTimeMls)).start();
        new Thread(new ClientReceiver(reader, sleepTimeMls)).start();
    }

    private void successMessage(BufferedReader reader) throws IOException {
        LOG.info(Messages.SUCCESS_CONNECTION);
        System.out.println(reader.readLine());
    }

    public void setSleepTimeMls(Integer sleepTimeMls) {
        this.sleepTimeMls = sleepTimeMls;
    }
}
