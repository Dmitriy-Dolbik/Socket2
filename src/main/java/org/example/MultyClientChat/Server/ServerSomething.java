package org.example.MultyClientChat.Server;

import java.io.*;
import java.net.Socket;

public class ServerSomething extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ServerSomething(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Server.story.printStory(out);
        start();
    }

    @Override
    public void run() {
        String word;
        try {
            word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while (true) {
                    word = in.readLine();
                    if (word.equals("stop")) {
                        this.downService();
                        break;
                    }
                    for (ServerSomething vr : Server.serverList) {
                        vr.send(word);
                    }
                }
            } catch (NullPointerException ignored) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomething vr : Server.serverList) {
                    if (vr.equals(this)) vr.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
