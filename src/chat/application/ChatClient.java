
package chat.application;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

public class ChatClient{
    
    static JFrame chatWindow=new JFrame("Chat Application");
    static JTextArea chatArea=new JTextArea(22,40);
    static JTextField textField=new JTextField(40);
    static JLabel blankLabel=new JLabel("   ");
    static JButton sendButton=new JButton("send");
    static JLabel nameLabel = new JLabel("         ");

    static BufferedReader in;
    static PrintWriter out;

    ChatClient(){
        chatWindow.setLayout(new FlowLayout());
        chatWindow.add(new JScrollPane(chatArea));
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton);

        //Ensures that application exits successfully when user clicks on close icon.
        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatWindow.setSize(475,500);
        chatWindow.setVisible(true);

        textField.setEditable(false);
        chatArea.setEditable(false);
    }

    public void startGui() throws IOException {

        //Creates a dialog box which prompts user to add IP Address.
        String ipAddress=JOptionPane.showInputDialog(
                chatWindow,
                "Enter Server IP Address",
                "IP Address Required !!",
                JOptionPane.PLAIN_MESSAGE);
        
        Socket soc=new Socket(ipAddress,9806);
        in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out=new PrintWriter(soc.getOutputStream(),true);

        while (true){
            String str = in.readLine();
            if (str.equals("NAMEREQUIRED"))
            {
                //Creates a dialog box which prompts user to enter a unique name.
                String name = JOptionPane.showInputDialog(
                        chatWindow,
                        "Enter a unique name:",
                        "Name Required!!",
                        JOptionPane.PLAIN_MESSAGE);

                out.println(name);

            }
            else if(str.equals("NAMEALREADYEXISTS"))
            {
                String name = JOptionPane.showInputDialog(
                        chatWindow,
                        "Enter another name:",
                        "Name Already Exits!!",
                        JOptionPane.WARNING_MESSAGE);

                out.println(name);
            }
            else if (str.startsWith("NAMEACCEPTED")){
                textField.setEditable(true);
                nameLabel.setText("You are logged in as: "+str.substring(12));

            }
            else {
                chatArea.append(str + "\n");
            }
        }
    }
    
    public static void main(String args[]) throws IOException {
        ChatClient c=new ChatClient();
        c.startGui();
    }
}
