/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.application;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

/**
 *
 * @author ujash
 */
public class ChatClient {
    
    static JFrame chatWindow=new JFrame("Chat Application");
    static JTextArea chatArea=new JTextArea(22,40);
    static JTextField textField=new JTextField(40);
    static JLabel blankLabel=new JLabel("   ");
    static JButton sendButton=new JButton("send");
    static BufferedReader in;
    static PrintWriter out;
    
    
    
    ChatClient()
    {
        chatWindow.setLayout(new FlowLayout());
        chatWindow.add(new JScrollPane(chatArea));
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);
        
        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatWindow.setSize(475,500);
        chatWindow.setVisible(true);
        
        
        textField.setEditable(false);
        chatArea.setEditable(false);
        
    }
    
    
    
    public void startGui() throws IOException
    {
        String ipAddress=JOptionPane.showInputDialog(chatWindow,"Enter Server IP Address","IP Address Required !!",JOptionPane.PLAIN_MESSAGE);
        
        Socket soc=new Socket(ipAddress,9806);
        in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out=new PrintWriter(soc.getOutputStream(),true);
    }
    
    public static void main(String args[]) throws IOException
    {
        ChatClient c=new ChatClient();
        c.startGui();
    }
    
            
    
}
