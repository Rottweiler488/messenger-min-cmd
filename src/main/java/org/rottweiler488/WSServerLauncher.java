package org.rottweiler488;

import org.glassfish.tyrus.server.Server;

import java.util.Scanner;

public class WSServerLauncher {
    public static String ip = "0.0.0.0";
    public static int port = 4052;

    public static void main(String[] args) {
        for (String arg : args) {
            String key = "";
            String value = "";
            if(arg.contains("=")) {
                key = arg.substring(0, arg.indexOf("="));
                value = arg.substring(arg.indexOf("=")+1);
            }else{
                break;
            }
            switch (key){
                case "--port":
                    port = Integer.parseInt(value);
                    break;
                case "--ip":
                    ip = value;
                    break;
                default:
                    System.out.println("Incorrect argument passed.");
            }
        }

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
