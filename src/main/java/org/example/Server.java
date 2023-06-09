package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004);
                System.out.println("Сервер запущен");
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    while (true) {
                        String word = in.readLine();
                        if (word.equals("exit")){
                            out.write("Привет, это Сервер! Конец связи" + "\n");
                            out.flush();
                            break;
                        } else {
                            System.out.println(word);
                            out.write("Привет, это Сервер! Подветрждаю, вы написали: " + word + "\n");
                            out.flush();
                        }
                    }

                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
