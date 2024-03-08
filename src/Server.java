import java.io.*;
import java.net.*;
import java.util.InputMismatchException;

public class Server {

    private static ServerSocket servSock;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try
        {
            servSock = new ServerSocket(PORT);      //Step 1.
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
            link = servSock.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(), true);

            try {
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
