package com.my.kozhukhar.client.process;

import com.my.kozhukhar.message.ErrorMessages;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver implements Runnable {

    private BufferedReader reader;

    private Integer sleepMls;

    private static final Logger LOG = Logger.getLogger(ClientReceiver.class);

    public ClientReceiver(BufferedReader reader, Integer sleepMls) {
        this.reader = reader;
        this.sleepMls = sleepMls;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println(reader.readLine());
            } catch (IOException ex) {
                LOG.error(ErrorMessages.CANNOT_RECEIVE_ANY_MESSAGE);
                Thread.sleep(sleepMls);
            }
        }
    }
}
