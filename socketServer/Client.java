package socketServer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;

public class Client {
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.printf("InetAddress: %s\n", inetAddress);
        }
        System.out.printf("\n");
    }
    public static void main(String args[]) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the number of clients to create: ");
            int numClients = scanner.nextInt();
            for(int i =0; i < numClients; i++) {
                Socket socket = new Socket(hostName, portNumber);

                //writing to the server
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //reading from the server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {

                    System.out.println("Enter one of the following commands \n"
                            + "1- Host Date/Time. \n"
                            + "2- Host uptime. \n"
                            + "3- Host memory.\n"
                            + "4- Host Netstat.\n"
                            + "5- Host current user.\n"
                            + "6- Host Running process.\n"
                            + "7- To Exit");
                    int input = scanner.nextInt();
                    out.println(input);

                    String response = in.readLine();
                    System.out.println("Server response: " + response);
                    long startTime = System.currentTimeMillis();
                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;
                    double averageTime = totalTime / (double) numClients;

                    System.out.println("Average time of response: " + averageTime + "ms");
                    System.out.println("Total turn around time: " + totalTime + "ms");
                    break;
                }
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
