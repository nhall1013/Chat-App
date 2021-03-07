import java.net.*;
import java.io.*;

public class ServerOutput implements Runnable {

    private Socket socket;
    private BufferedReader serverOutput;
    
    ServerOutput(Socket socket) {
        try {
            this.socket = socket;
            serverOutput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            
            while(true){
                
                String serverOut = serverOutput.readLine();

                System.out.println(serverOut);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                serverOutput.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
