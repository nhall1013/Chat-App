import java.io.*;
import java.net.*;
import java.util.*;

public class UserThread implements Runnable {

    private Socket socket;
    private ArrayList<UserThread> users;
    private BufferedReader clientInput;
    private PrintWriter serverOutput;
    private String userName;
    private boolean active;

    public UserThread(Socket socket, ArrayList<UserThread> users) throws IOException {
        
        this.socket = socket;
        this.users = users;
        clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverOutput = new PrintWriter(socket.getOutputStream(), true);
        userName = null;
        active = true;

    }

    public void send(String s){
        for(UserThread u : users){
            if(!u.userName.equals(userName) && u.active == true){
                u.serverOutput.println("> " + s);
            }
        }
    }

    public void allUsers(){
        serverOutput.printf("\nActive Users:\n");
        for (UserThread u : users) {
            if(u.active == true){
                serverOutput.println(u.userName);
            }
        }
    }

    @Override
    public void run() {

        try {

            serverOutput.println("Enter username: ");
            userName = clientInput.readLine();

            while(userName.equals("") || userName.equals("\n")){
                serverOutput.println("Must enter username\n");
                serverOutput.println("Enter username: ");
                userName = clientInput.readLine();
            }
            
            System.out.println("Welcome " + userName);
            serverOutput.println("\nConnection Accepted");
            serverOutput.println("Welcome " + userName + "\n");
            serverOutput.println("Send a message.\nEnter allusers to see active users.\nTo leave enter 'bye'.\n");
            send("Welcome " + userName + "\n");

            while(true){

                String userInput = clientInput.readLine();

                if(userInput.equals("") || userInput.equals("\n")){
                    serverOutput.println("Send a message.\nEnter allusers to see active users.\nTo leave enter 'bye'.\n");
                }
                else if(userInput.equalsIgnoreCase("bye")){
                    System.out.println(userName + " disconnected with a Bye message.");
                    System.out.println("Server: Goodbye " + userName);
                    send("Server: Goodbye " + userName);
                    active = false;
                    break;
                } 
                else if(userInput.equalsIgnoreCase("allusers")){
                    allUsers();
                }
                else{
                    System.out.println(userName + ": " + userInput);
                    send(userName + ": " + userInput);
                }
            } 

        }catch(IOException e) {
            e.printStackTrace();
        } finally{
            try {
                clientInput.close();
                serverOutput.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}