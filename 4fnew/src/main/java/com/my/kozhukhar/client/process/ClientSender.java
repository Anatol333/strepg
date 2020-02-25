package com.my.kozhukhar.client.process;

import com.my.kozhukhar.message.ErrorMessages;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class ClientSender implements Runnable {

    private Scanner scan;

    private OutputStream out;

    private Integer sleepMls;

    private static final Logger LOG = Logger.getLogger(ClientReceiver.class);

    public ClientSender(Scanner scan, OutputStream out, Integer sleepMls) {
        this.scan = scan;
        this.out = out;
        this.sleepMls = sleepMls;
    }

    @SneakyThrows
    @Override
    public void run() {
        DataOutputStream dos = new DataOutputStream(out);
        while (!Thread.currentThread().isInterrupted()) {
            String line = ">> " + scan.nextLine();
            try {
                dos.writeUTF(line);
                dos.flush();
            } catch (IOException ex) {
                LOG.error(ErrorMessages.CANNOT_SEND_MESSAGE + " Try again after " + sleepMls / 1000 + " sec.");
                Thread.sleep(sleepMls);
            }
        }
    }
}
