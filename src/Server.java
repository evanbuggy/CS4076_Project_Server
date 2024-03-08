import java.io.*;
import java.net.*;
import java.util.InputMismatchException;

public class Server {

    private static ServerSocket servSock;
    private static final int PORT = 9999;

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try
        {
            servSock = new ServerSocket(PORT);
            System.out.println("New Socket with port " + PORT);
        }
        catch(IOException e)
        {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }

        do
        {
            run();
        }while (true);

    }

    private static void run()
    {
        Socket link = null;
        try {
            System.out.println("Waiting for connection...");
            link = servSock.accept();
            System.out.println("Connection established with " + link.getInetAddress());
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            System.out.println("InputStream established");
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);
            System.out.println("OutputStream established");

            try {
                System.out.println("Waiting for readLine()...");
                String message = in.readLine();
                System.out.println("Command: " + message);
                out.println("Response from Server (Capitalized Message): " + message.toUpperCase());
            }
            catch (InputMismatchException e) {
                System.out.println("The message received from the client is not a valid input!");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                System.out.println("\n* Closing connection... *");
                link.close();				    //Step 5.
            }
            catch(IOException e)
            {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    } // finish run method
} // finish the class
