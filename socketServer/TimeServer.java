package socketServer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

public class TimeServer {

    public static void main(String[] args) {
        /* create a new serversocket object
        to listen on a specific port. choose a port
        that is not already dedicated to some other
        service. 3000 or 8000
         */
        if (args.length < 1) return;

        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            System.out.println("Single threaded server is running on " + portNumber);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Accepted connection from client " + socket.getInetAddress());
                List<Socket> connections = new ArrayList<>();
                connections.add(socket);

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                //print users
                static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
                    System.out.printf("Display name: %s\n", netint.getDisplayName());
                    out.printf("Name: %s\n", netint.getName());
                    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                        System.out.printf("InetAddress: %s\n", inetAddress);
                    }
                    System.out.printf("\n");
                }
                //get all connected cli
                //date and time
                int input = 0;
                    switch(input){
                        case 1: writer.println(new Date().toString());
                                System.out.println("Average time of response: " );
                                System.out.println("Total turn around time: ");
                        break;
                        case 2:
                            RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
                            long uptimeInMillis = runtimeBean.getUptime();
                            String uptimeString = "Uptime: " + uptimeInMillis + " milliseconds";
                            writer.println(uptimeString);
                            break;
                        case 3:
                            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
                            MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
                            long usedHeapMemory = heapMemoryUsage.getUsed();
                            System.out.println("Used heap memory: " + usedHeapMemory + " bytes");
                            break;
                        case 4: //get enumerations of server addresses
                            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
                            for (NetworkInterface netint : Collections.list(nets))
                                displayInterfaceInformation(netint);



                    }

                }
            }//END OF TRY
            catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
