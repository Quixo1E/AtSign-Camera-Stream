package com.example;

import java.util.List;

import org.atsign.client.api.AtClient;
import org.atsign.client.util.CameraUtil;
import org.atsign.common.AtException;
import org.atsign.common.AtSign;
import org.atsign.common.KeyBuilders;
import org.atsign.common.Keys.AtKey;
import org.atsign.common.Keys.PublicKey;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import org.atsign.client.util.CameraUtil;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.atsign.client.util.ImageUtil;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import static org.mockito.Mockito.doAnswer;

import java.awt.FlowLayout;
import javax.swing.JLabel;


public class dummy extends JFrame{

    JLabel label;
    PublicKey key;
    AtClient atClient;
    String prev;
    public dummy(AtClient aC, PublicKey pk){
        this.key = pk;
        this.atClient = aC;

        this.label = new JLabel();
        prev = null;

    }
   public void walk(){

        receiveData receive = new receiveData();
        Thread r = new Thread(receive);
        r.start();
        
   }
   class receiveData extends AtException implements Runnable{
    public receiveData(){
        super("atsign exception");
        
    }
    public String data(){
        try {
            String data = atClient.get(key).get();
            return "receive:" + data;
        }
            catch (Exception e) {
                e.printStackTrace();
                return("Could not get string from server");
            }
    }
    public void run(){
        while (true){
        try {
            long startTime = System.nanoTime();
            String data = atClient.get(key).get();
            long endTime = System.nanoTime();
            System.out.println("download" + (endTime - startTime) / 1000000);
            if (!data.equals(prev)&& data != null) {
                System.out.println("new data" + prev);
            }
            prev = data;

            //System.out.println("receive:" + data);
            byte[] bytes = Base64.getDecoder().decode(data);
            //turn byte array to ByteArrayInputStream
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            // turn the ByteArrayInputStream into a bufferImage
            BufferedImage bImage2 = ImageIO.read(byteStream);
            ImageIO.write(bImage2, 
            "jpg", new File("output.jpg"));
            // the jframe will then continously display the images like a video
            
            BufferedImage img = CameraUtil.getSingleImage();
            ImageIcon icon = new ImageIcon(img);
            label.setIcon(icon);
            setLayout(new FlowLayout());
            setSize(640,480);
            add(label);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Thread.sleep(100);

        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get string from server");
        }
    }

    }

   }
}