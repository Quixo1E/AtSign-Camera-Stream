package com.example;
import javax.swing.*;

import org.atsign.common.Keys.SharedKey;

import java.awt.event.*;
import java.util.*;
import com.example.SV;
import static com.example.constants.*;
public class LaunchPage implements ActionListener {
   
  JFrame frame = new JFrame();
  JButton sAButton = new JButton("Send Audio");
  JButton rAButton = new JButton("Receive Audio");
  JButton sButton = new JButton("Send Stream");
  JButton rButton = new JButton("View Stream");
 ImageIcon image = new ImageIcon();
 public JTextField atMe, atTheirs, key;
 JLabel atMELabel = new JLabel("Your Atsign");
JLabel atTheirLabel = new JLabel("Sender AtSign");
 JLabel atKey = new JLabel();
 JCheckBox checkbox = new JCheckBox();
   LaunchPage() {
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(1000, 1000);
       frame.setTitle("Camera Stream");
       //frame.setLayout(null);
  
       sButton.setBounds(100, 650, 120, 50); 
       sButton.addActionListener(this);
  
       rButton.setBounds(100, 700, 120, 50);
        rButton.addActionListener(this);
       
        sAButton.setBounds(220, 650, 120, 50);
        sAButton.addActionListener(this);

        rAButton.setBounds(220, 700, 120, 50);
        rAButton.addActionListener(this);
  //ImageIcon image = new ImageIcon("webcam.jpg");
  
       image = new ImageIcon(getClass().getResource("w2.png"));
       JLabel label = new JLabel(image);
       label.setBounds(760, 100, 400, 400);
       atMELabel = new JLabel("Your AtSign");
       atMELabel.setBounds(20, 360, 300, 300);
 
 atTheirLabel = new JLabel("Their AtSign");
 atTheirLabel.setBounds(20, 490, 200, 90);
 
 atMe = new JTextField();
 atMe.setBounds(100, 500, 125, 20);
 
 atTheirs = new JTextField();
 atTheirs.setBounds(100, 525, 125, 20);
 
 atKey = new JLabel("@Key here");
 atKey.setBounds(20, 515, 100, 90);
 key = new JTextField();
 key.setBounds(100, 550, 125, 20);

 checkbox = new JCheckBox(); 
 checkbox.setText("Mark for public key");
   checkbox.setFocusable(false);
 checkbox.setBounds(245, 545, 200, 30);


frame.add(sAButton);
frame.add(rAButton);
 frame.add(checkbox);
 frame.add(key);
 frame.add(atKey);
 frame.add(atTheirLabel);
 frame.add(atMELabel);
 frame.add(atTheirs);
 frame.add(atMe);
   frame.add(sButton);
   frame.add(rButton);
   frame.add(label);
   frame.setVisible(true);
   
   }

   @Override   
   public void actionPerformed(ActionEvent e) {
       String  myAt = atMe.getText();
       String theirAt = atTheirs.getText();
       String newKey = key.getText();
       SendVideo vidsk = new SendVideo();
       
       if (e.getSource()==sButton) {

           if(checkbox.isSelected() == true ){
           
              System.out.println(myAt + " " + newKey);
              SV sendVideo = new SV( myAt,newKey);
              Thread svideo = new Thread(sendVideo);
              svideo.start();
              
           }
           else {
            SV sendVidSK = new SV( myAt, theirAt, newKey);
            Thread svideo = new Thread(sendVidSK);
            svideo.start();
           
           }

       }
       else if (e.getSource()==rButton) {
         
           if(checkbox.isSelected() == true ){

            ReceiveVideo receiveVideo= new ReceiveVideo(theirAt, newKey);
   
            Thread rVideo = new Thread(receiveVideo);
            rVideo.start();
           }
           else {
            ReceiveVideo receiveVideo= new ReceiveVideo( myAt, theirAt, newKey);
   
            Thread rVideo = new Thread(receiveVideo);
            rVideo.start();
           }
       }
       else if (e.getSource()==sAButton) {
         
           if(checkbox.isSelected() == true ){

            sendAudio sendAudio = new sendAudio(myAt, newKey);
            Thread sAudio = new Thread(sendAudio);
            sAudio.start();
           }
           else {
            sendAudio sendAudio = new sendAudio(myAt, theirAt, newKey);
            Thread sAudio = new Thread(sendAudio);
            sAudio.start();
           }
       }
       else if (e.getSource()==rAButton) {
         
           if(checkbox.isSelected() == true ){

               receiveAudio receive = new receiveAudio(theirAt, newKey);
               Thread rec = new Thread (receive);
               rec.start();
           }
           else {
            receiveAudio receive = new receiveAudio(myAt, theirAt, newKey);
            Thread rec = new Thread (receive);
            rec.start();
           }
       }

   }
}
