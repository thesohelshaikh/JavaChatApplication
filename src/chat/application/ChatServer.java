package chat.application;

import static chat.application.ChatClient.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    //List that stores all the unique names of the users connected to the server.
    static ArrayList<String> userNames = new ArrayList<String>();

    //List of all the printwriters that server requires to send to each client.
    static ArrayList<PrintWriter> printWriters = new ArrayList<PrintWriter>();

    public static void main(String args[]) throws IOException {
        System.out.println("Waiting for Clients");

        //Creates a server socket object and binds it to port 9806.
        ServerSocket ss = new ServerSocket(9806);

        //Waits incoming client connections.
        while (true) {
            //After a successful connection, returns a socket object.
            Socket sc = ss.accept();
            System.out.println("Connection established");
            ConnectionHandller ch = new ConnectionHandller(sc);
            ch.start();
        }
    }
}

class ConnectionHandller extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String name;

    public ConnectionHandller(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //Waits for the until user adds a nique name.
            int count = 0;
            while (true) {
                if(count > 0){
                    out.println("NAMEALREADYEXISTS");
                }else{
                    out.println("NAMEREQUIRED");
                }

                name = in.readLine();

                if (name == null){
                    return;
                }

                if(!ChatServer.userNames.contains(name)){
                    ChatServer.userNames.add(name);
                    break;
                }
                count++;
            }
            out.println("NAMEACCEPTED");
            ChatServer.printWriters.add(out);


        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
