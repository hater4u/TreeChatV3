package com.company;

public class Main {
    public static void main(String[] args) {
        if (args.length == 3 || args.length == 5) {
            String name = args[0];
            Integer lossPercentage = null;
            Integer selfPort = null;
            String parentIp = null;
            Integer parentPort = null;

            try {
                lossPercentage = Integer.parseInt(args[1]);
                selfPort = Integer.parseInt(args[2]);

                if (args.length == 5) {
                    parentIp = args[3];
                    parentPort = Integer.parseInt(args[4]);
                }
            } catch (NumberFormatException ex) {
                System.err.println(ex.getMessage());
            }

            try {
                Server server;
                if (parentIp == null && parentPort == null) {
                    server = new Server(name, lossPercentage, selfPort);
                } else {
                    server = new Server(name, lossPercentage, selfPort, parentIp, parentPort);
                }
                server.start();
            } catch (Exception ex) {

            }
        }
    }
}


