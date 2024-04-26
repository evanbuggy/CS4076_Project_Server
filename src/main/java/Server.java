import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

public class Server {
    private static final int PORT = 9999;
    private static ServerSocket sock;
    private static int totalConnections = 0;
    static int currentConnections = 0;
    static Input command = new Input();

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try {
            sock = new ServerSocket(PORT);
            System.out.println("New Socket with port " + PORT);
        }
        catch (IOException e) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }

        do {

            Socket link = null;

            try {
                // Uncomment the below lines to quickly test early lectures functionality

                //System.out.println("Waiting for connection...");
                //command.put("ADD_EarlyClass_dumbassRoom_2024-04-26_9");
                //command.put("ADD_AnotherEarlyClass_Schooman_2024-04-27_9");
                //command.put("ADD_LOL_Schooman_2024-04-27_10");
                //command.put("ADD_LMAO_help_2024-04-27_14");
                //command.put("ADD_AbdulLecture_Kemmy_2024-04-27_10");
                //command.put("ADD_CoolClass_Schooman_2024-04-27_16");
                //command.put("EARLYLECTURES");
                //System.out.println(command.put("VIEW_2024-04-26"));
                //System.out.println(command.put("VIEW_2024-04-27"));

//                command.put("ADD_LMAO_help_2024-04-27_14");
//                command.put("EARLYLECTURES");
//                System.out.println(command.put("VIEW_2024-04-27"));
                link = sock.accept();
                Thread thr = new Thread(new ClientThread(link));
                totalConnections++;
                currentConnections++;
                System.out.println("Current connections: " + currentConnections);
                System.out.println("Total connections: " + totalConnections);
                thr.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    static class ClientThread implements Runnable {

        private Socket link;

        public ClientThread(Socket link) {
            this.link = link;
        }

        public void run() {
            try {
                System.out.println("Connection established with " + link.getInetAddress());
                BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
                System.out.println("InputStream established");
                PrintWriter out = new PrintWriter(link.getOutputStream(), true);
                System.out.println("OutputStream established");

                String message = "";
                System.out.println("Waiting to read user input stream...");

                try {
                    message = in.readLine();
                }
                catch (SocketException e) {
                    System.out.println("Client has abruptly exited/failed to pass an output stream!");
                }

                System.out.println("Message: " + message);

                String response = command.put(message);
                out.println(response);
                System.out.println("Response: " + response);
            }
            catch(IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("\n*** DISCONNECTING... ***");
                    link.close();
                    Server.currentConnections--;
                }
                catch (IOException e) {
                    System.out.println("\n*** UNABLE TO DISCONNECT, EXITING... ***");
                    System.exit(1);
                }
            }
        }
    }
}
