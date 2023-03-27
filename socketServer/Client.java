package socketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);


        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of clients you wish to create:");
        int numClients = scanner.nextInt();

        for (int i = 0; i < numClients; i++) {
            new Thread(() -> startClient(hostName, portNumber)).start();
        }
    }

    private static void startClient(String hostName, int portNumber) {
        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            int input;
            do {
                System.out.println("Enter one of the following commands \n"
                        + "1- Host Date/Time. \n"
                        + "2- Host uptime. \n"
                        + "3- Host memory.\n"
                        + "4- Host Netstat.\n"
                        + "5- Host current user.\n"
                        + "6- Host Running process.\n"
                        + "7- To Exit");
                input = scanner.nextInt();
                long startTime = System.currentTimeMillis();
                out.println(input);

                if (input == 7) {
                    socket.close();
                    break;
                }
                List<Long> responseTimes = new ArrayList<>();
                long endTime = System.currentTimeMillis();
                long responseTime = endTime - startTime;
                responseTimes.add(responseTime);
                String response = in.readLine();
                System.out.println("Server response: " + response);
                long totalTurnaroundTime = responseTimes.stream().mapToLong(Long::longValue).sum();
                double averageResponseTime = totalTurnaroundTime / (double) responseTimes.size();

                System.out.println("Average time of response: " + averageResponseTime + "ms");
                System.out.println("Total turn around time: " + totalTurnaroundTime + "ms");


            } while (input != 7);

            //scanner.close();
            //in.close();
            //out.close();
            //socket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
