package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while (true){
                    System.out.println("Вы что-то хотели сказать? Введите это здесь:");
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();
                    String serverWord = in.readLine();
                    System.out.println(serverWord);
                    if (word.equals("exit")){
                        break;
                    }
                }

            } finally {
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
