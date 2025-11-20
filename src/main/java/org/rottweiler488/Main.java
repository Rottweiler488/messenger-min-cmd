package org.rottweiler488;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public final static String file = "build/libs/messenger-1.2-all.jar";
    public final static Map<String, String> classLaunchers = Map.ofEntries(Map.entry("server", "WSLocalServer"), Map.entry("client", "WSClientLauncher"));

    public static void main(String[] args) {
        try {
            System.out.println("Launch: ");
            Scanner scanner = new Scanner(System.in);
            String classLauncher = classLaunchers.get(scanner.nextLine());

            String osCmd = getOSCmd(System.getProperty("os.name").toLowerCase());
            String workDirectory = System.getProperty("user.dir");
            String command = String.format("java -cp %s org.rottweiler488.%s", file, classLauncher);
            Process process = new ProcessBuilder(osCmd, "-с", command).directory(new java.io.File(workDirectory)).inheritIO().start();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

//    public static String getClassLauncher(String className) {
//        return os
//    }

    public static String getOSCmd(String osName) {
        return osName.contains("win") ? "cmd" : "bash";
    }
}
