import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server {

    public static void main(String[] args) {

        int port = Integer.parseInt(args[0]);
        ArrayList<UserThread> users = new ArrayList<UserThread>();

        ServerSocket server;

        System.out.println("Server Running");
        System.out.println("Listening on port " + port + "...\n");

        try {
            
            server = new ServerSocket(port);
            
            
            while(true){

                Socket socket = server.accept();

                UserThread user = new UserThread(socket, users);

                users.add(user);

                Thread newUser = new Thread(user);

                newUser.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}