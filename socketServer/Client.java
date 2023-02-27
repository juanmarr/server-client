package socketServer;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scan = new Scanner(System.in);
            while (true) {
                int input = scan.nextInt();
                System.out.println("Enter one of the following commands \n"
                        + "1- Host Date/Time. \n"
                        + "2- Host uptime. \n"
                        + "3- Host memory.\n"
                        + "4- Host Netstat.\n"
                        + "5- Host current user.\n"
                        + "6- Host Running process.\n");

                System.out.println("response: " + in.readLine());

                if (input == 7) {
                    break;
                }
            }
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
