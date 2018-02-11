/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ujash
 */
public class ChatServer {
    
    public static void main(String args[]) throws IOException
    {
        System.out.println("Waiting for Client");
        ServerSocket ss=new ServerSocket(9806);
        
        
        while(true)
        {
            Socket sc=ss.accept();
            System.out.println("Connection established");
            ConnectionHandller ch=new ConnectionHandller(sc);
            ch.start();
        }
    }
    
}



class ConnectionHandller extends Thread
{
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ConnectionHandller(Socket socket) {
        
        
        this.socket=socket;
    }
    
    
    
    
    
    
    public void run()
    {
        try {
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out=new PrintWriter(socket.getOutputStream(),true);
            
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandller.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
}
