package com.example;
import org.atsign.client.api.AtClient;
import org.atsign.client.util.CameraUtil;
import org.atsign.common.AtException;
import org.atsign.common.AtSign;
import org.atsign.common.KeyBuilders;
import org.atsign.common.Keys;
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
import java.util.ArrayList;
import java.util.Base64;
import org.atsign.common.Keys.SharedKey;
import org.bouncycastle.jcajce.provider.symmetric.Shacal2;

import static org.mockito.Mockito.doAnswer;
import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;
import java.awt.FlowLayout;
import javax.swing.JLabel;
public class VideoReel {


   public  List<String> keyList = new ArrayList<String>();

    public void storeReel(String AtMe, String AtThem, String skey) throws IOException {
        if (skey.length() == 100) {
        playReel();
        return;
        }
        else {
        while (skey.length() < 100) {

            String ROOT_URL = "root.atsign.org:64";
            String ATSIGN_STR_SHARED_BY = AtThem; // my atSign (sharedBy)  
            String ATSIGN_STR_SHARED_WITH = AtMe; // other atSign (sharedWith)
            boolean VERBOSE = true;
            String KEY_NAME = skey;
        
        // 2. create AtSign objects
            AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY);
            AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
        
        // 3. atClient factory method
            AtClient atClient = null;
            try {
            atClient = AtClient.withRemoteSecondary(ROOT_URL, sharedBy, VERBOSE);
            SharedKey sk = new KeyBuilders.SharedKeyBuilder(sharedBy, sharedWith).key(KEY_NAME).build();
            String response = atClient.get(sk).get();
            keyList.add(response);
            storeReel( AtMe, AtThem,  skey + 'a');
            } 
            
            catch (AtException e) {
            System.err.println("Failed to create AtClient instance " + e);
            e.printStackTrace();    
            }  
            
     catch (Exception e) {
        e.printStackTrace();
        System.out.println("NOT SUCCESS");
            }

        }
    }
    }

    public void playReel() throws IOException{
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(640,480);
        JLabel label=new JLabel();
    
    for (int i = 0; i < keyList.size(); ++i) {
       
       
        String response = keyList.get(i);
        byte[] bytes = Base64.getDecoder().decode(response);

        // turn byte array to ByteArrayInputStream
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        // turn the ByteArrayInputStream into a bufferImage
        BufferedImage bImage2 = ImageIO.read(byteStream);

        // ImageIO.write(bImage2, "jpg", new File("output.jpg"));
        // the jframe will then continously display the images like a video
        BufferedImage img = bImage2;
        ImageIcon icon = new ImageIcon(img);
        
        label.setIcon(icon);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        }
    }
    


    }


