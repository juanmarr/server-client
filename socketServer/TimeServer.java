package socketServer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class TimeServer {

    public static void main(String[] args) {
        if (args.length < 1) return;

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            System.out.println("Multi-threaded server is running on " + portNumber);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from client " + socket.getInetAddress());
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream output = socket.getOutputStream();
                PrintWriter out = new PrintWriter(output, true);

                String input;
                while ((input = in.readLine()) != null) {
                    switch (input) {
                        case "1":
                            // Code to create and process client requests goes here
                            out.println(new Date().toString());

                            break;
                        case "2":
                            // Code to create and process client requests goes here
                            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
                            long uptimeInMillis = runtimeBean.getUptime();
                            String uptimeString = "Uptime: " + uptimeInMillis + " milliseconds";
                            out.println(uptimeString);
                            break;
                        case "3":
                            // Code to create and process client requests goes here
                            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
                            MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
                            long usedHeapMemory = heapMemoryUsage.getUsed();
                            System.out.println("Used heap memory: " + usedHeapMemory + " bytes");
                            break;
                        case "4": //get enumerations of server addresses
                            try {
                                Process process = Runtime.getRuntime().exec("netstat");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    System.out.println(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "5": //print Current Users - list of users currently connected to the server
                            try {
                                Process process = Runtime.getRuntime().exec("who");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    out.println(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "6":
                            //print all the Running Processes - list of programs currently running on the server
                            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
                            java.util.List<String> arguments = runtimeMXBean.getInputArguments();
                            for (String arg : arguments) {
                                out.println(arg);
                            }
                            break;
                        case "7":
                            socket.close();

                            return;
                        default:
                            out.println("Invalid input");
                            break;
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
