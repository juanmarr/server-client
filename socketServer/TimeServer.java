package socketServer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class TimeServer {
    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
        System.out.printf("Display name: %s\n", netint.getDisplayName());
        System.out.printf("Name: %s\n", netint.getName());
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.printf("InetAddress: %s\n", inetAddress);
        }
        System.out.printf("\n");
    }

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
                //List<Socket> connections = new ArrayList<>();
                //connections.add(socket);
                /*As the OutputStream provides only low-level methods
                 (writing data as a byte array), you can wrap it in a PrintWriter to send data in text format.
                 */
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                //print users
                //get all connected cli
                //date and time
                int input = 0;
                    switch(input){
                        case 1://prompt user for number of clients
                            Scanner scanner = new Scanner(System.in);
                            System.out.print("Enter the number of clients to create: ");
                            int numClients = scanner.nextInt();
                            long startTime = System.currentTimeMillis();
                            for (int i = 0; i < numClients; i++) {
                                // Code to create and process client requests goes here
                                writer.println(new Date().toString());
                            }
                            long endTime = System.currentTimeMillis();
                            long totalTime = endTime - startTime;
                            double averageTime = totalTime / (double)numClients;

                            System.out.println("Average time of response: " + averageTime + "ms");
                            System.out.println("Total turn around time: " + totalTime + "ms");
                            break;
                        case 2:
                            Scanner scn = new Scanner(System.in);
                            System.out.print("Enter the number of clients to create: ");
                            int numClient = scn.nextInt();
                            for (int i = 0; i < numClient; i++) {
                                // Code to create and process client requests goes here
                                RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
                                long uptimeInMillis = runtimeBean.getUptime();
                                String uptimeString = "Uptime: " + uptimeInMillis + " milliseconds";
                                writer.println(uptimeString);

                            }
                            break;
                        case 3:
                            Scanner scan = new Scanner(System.in);
                            System.out.print("Enter the number of clients to create: ");
                            int numClient3 = scan.nextInt();
                            for (int i = 0; i < numClient3; i++) {
                                // Code to create and process client requests goes here
                                MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
                                MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
                                long usedHeapMemory = heapMemoryUsage.getUsed();
                                System.out.println("Used heap memory: " + usedHeapMemory + " bytes");
                            }
                            break;
                        case 4: //get enumerations of server addresses
                            Scanner scans = new Scanner(System.in);
                            System.out.print("Enter number of clients to create: ");
                            int numClients4 = scans.nextInt();
                            for (int i = 0; i < numClients4; i++) {
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
                            }
                            break;
                        case 5: //promt user for number of clients
                            //print a list of Current Users - list of users currently connected to the server
                            //prompt user for number of clients
                            Scanner sc = new Scanner(System.in);
                            System.out.print("Enter the number of clients to create: ");
                            int numClient5 = sc.nextInt();

                            // Code to create and process client requests goes here
                            List<Socket> connections = new ArrayList<>();
                            for (int i = 0; i < numClient5; i++) {
                                Socket clientSocket = serverSocket.accept();
                                connections.add(clientSocket);
                            }

                            // Print list of users currently connected to the server
                            System.out.println("Current number of clients: " + connections.size());
                            for (int i = 0; i < connections.size(); i++) {
                                Socket currentClient = connections.get(i);
                                System.out.println("Client " + (i+1) + ": " + currentClient.getInetAddress());
                            }
                            break;
                        case 6:
                            //print all the Running Processes - list of programs currently running on the server
                            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
                            java.util.List<String> arguments = runtimeMXBean.getInputArguments();
                            for (String arg : arguments) {
                                System.out.println(arg);
                            }
                            break;
                        case 7: System.out.println("bye");
                    }

                }
            }//END OF TRY
            catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
