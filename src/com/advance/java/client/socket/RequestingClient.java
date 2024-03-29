/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advance.java.client.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RunnableFuture;

/**
 *
 * @author User
 */
public class RequestingClient implements Runnable, ConnectionSubject {

    private static final String host = "127.0.0.1";
    private int port = 7000;
    private Socket socket = null;

    private List<ConnectionObserver> lstObservers = new LinkedList<ConnectionObserver>();

    public RequestingClient(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Scanner kb = new Scanner(System.in);
        try {
            socket = new Socket(host, port);
            BufferedReader stdIn
                    = new BufferedReader(
                            new InputStreamReader(System.in));
            PrintStream out
                    = new PrintStream(socket.getOutputStream());
            //DataInputStream in  = new DataInputStream(socket.getInputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            System.out.println("Loading...");

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            System.out.print(doRead(in));
                        }catch (IOException e){}
                    }
                }
            }).start();
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                }
            }
        }
        //System.out.println("Connection End");
    }

    private String doRead(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (in.ready()) {
            sb.append((char)in.read());
        }
        String str = new String(sb.toString().getBytes(), "UTF-8");
        return str;
    }

    @Override
    public void registerObserver(ConnectionObserver observer) {
        lstObservers.add(observer);
    }

    @Override
    public void removeObserver(ConnectionObserver observer) {
        lstObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String msg) {
        for (ConnectionObserver observer : lstObservers) {
            observer.update(msg);
        }
    }
}
