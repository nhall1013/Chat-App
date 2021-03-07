import java.net.*;
import java.io.*;


public class User {

    public static void main(String[] args) {

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try {

            Socket socket = new Socket(host, port);

            ServerOutput serverOut = new ServerOutput(socket);
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);

            Thread output = new Thread(serverOut);
            
            output.start();

            while(true){

                String userIn = userInput.readLine();

                clientOutput.println(userIn);

                if(userIn.equals("bye")){
                    break;
                }

            }

            socket.close();
            userInput.close();
            clientOutput.close();
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}