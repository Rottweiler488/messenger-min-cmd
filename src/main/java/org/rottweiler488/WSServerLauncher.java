package org.rottweiler488;

import org.glassfish.tyrus.server.Server;

import java.util.Scanner;

public class WSServerLauncher {
    public static final String ip = "0.0.0.0";
    public static final int port = 4052; //TODO: Запрашивать

    public static void main(String[] args) {
        Server server = new Server(ip, port, "/ws", null, ChatEndpoint.class);

        try {
            server.start();
            System.out.printf("Server started on ws://%s:%d/ws/chat\n", ip, port);
            new Scanner(System.in).nextLine();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            server.stop();
        }
    }
}
