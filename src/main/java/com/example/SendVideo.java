package com.example;
import java.util.List;

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
import java.util.Base64;
import org.atsign.common.Keys.SharedKey;
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
public class SendVideo {

    public void sendVideoSK(String myAt, String theirAt, String skey) {
       
        AtClient atClient = null;

        try {
            String ROOT_URL = "root.atsign.org:64";
            String ATSIGN_STR_SHARED_BY = myAt;
            String ATSIGN_STR_SHARED_WITH =  theirAt; // other atSign (sharedWith)
            boolean VERBOSE = true;
            String KEY_NAME = skey;
            int ttl = 30 * 60 * 1000; // 30 minutes


            AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY);
            AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
            // 3. atClient factory method

            try {
                atClient = AtClient.withRemoteSecondary(ROOT_URL, sharedBy, VERBOSE);
            } catch (AtException e)  {
                System.err.println("Failed to create AtClient instance " + e);
                e.printStackTrace();
            }
// 4. create SharedKey instance
            SharedKey sk = new KeyBuilders.SharedKeyBuilder(sharedBy, sharedWith).key(KEY_NAME).build();
            sk.metadata.ttl = ttl;
           
          // String s = Base64.getEncoder().encodeToString(byteArray);
            
          
          // byte array
          



       

        String imageString = null;
          while (true) {
          byte[] byteArray = ImageUtil.toByteArray(CameraUtil.getSingleImage());
          
     
          // encode to string
          // string s is what we will send to the server
          String s = Base64.getEncoder().encodeToString(byteArray);
          
         imageString = atClient.put(sk, s).get();

          }

        }
        catch ( Exception e) {
            e.printStackTrace();
            System.out.println("NOT SUCCESS");
            



           


    }
}
public void sendVideoPK(String myAt, String theirAt, String pkey) {
       
    AtClient atClient = null;

    try {
        String ROOT_URL = "root.atsign.org:64";
        String ATSIGN_STR_SHARED_BY = myAt;
        String ATSIGN_STR_SHARED_WITH =  theirAt; // other atSign (sharedWith)
        boolean VERBOSE = true;
        String KEY_NAME = pkey;
        int ttl = 30 * 60 * 1000; // 30 minutes


        AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY);
        AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
        // 3. atClient factory method

        try {
            atClient = AtClient.withRemoteSecondary(ROOT_URL, sharedBy, VERBOSE);
        } catch (AtException e)  {
            System.err.println("Failed to create AtClient instance " + e);
            e.printStackTrace();
        }
// 4. create SharedKey instance
        
       
        PublicKey pk = new KeyBuilders.PublicKeyBuilder(sharedBy).key(KEY_NAME).build();
        pk.metadata.ttl = ttl;
      // String s = Base64.getEncoder().encodeToString(byteArray);
        
      
      // byte array
      



   

    String imageString = null;
      while (true) {
      byte[] byteArray = ImageUtil.toByteArray(CameraUtil.getSingleImage());
      
 
      // encode to string
      // string s is what we will send to the server
      String s = Base64.getEncoder().encodeToString(byteArray);
      
     imageString = atClient.put(pk, s).get();

      }

    }
    catch ( Exception e) {
        e.printStackTrace();
        System.out.println("NOT SUCCESS");
        



       


}
}
    public void sendReel( String myAt, String theirAt, String skey) {
      
        while(skey.length() < 100) { 
       
                AtClient atClient = null;
        
                try {
                    String ROOT_URL = "root.atsign.org:64";
                    String ATSIGN_STR_SHARED_BY = myAt;
                    String ATSIGN_STR_SHARED_WITH =  theirAt; // other atSign (sharedWith)
                    boolean VERBOSE = true;
                    String KEY_NAME = skey;
                    int ttl = 30 * 60 * 1000; // 30 minutes
        
        
                    AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY);
                    AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
                    // 3. atClient factory method
        
                    try {
                        atClient = AtClient.withRemoteSecondary(ROOT_URL, sharedBy, VERBOSE);
                    } catch (AtException e)  {
                        System.err.println("Failed to create AtClient instance " + e);
                        e.printStackTrace();
                    }
        // 4. create SharedKey instance
                    SharedKey sk = new KeyBuilders.SharedKeyBuilder(sharedBy, sharedWith).key(KEY_NAME).build();
                    sk.metadata.ttl = ttl;
                   
                  // String s = Base64.getEncoder().encodeToString(byteArray);
                    
                  
                  // byte array
                  
        
        
        
               
        
                String imageString = null;
                  
                  byte[] byteArray = ImageUtil.toByteArray(CameraUtil.getSingleImage());
                  
             
                  // encode to string
                  // string s is what we will send to the server
                  String s = Base64.getEncoder().encodeToString(byteArray);
                  
                 imageString = atClient.put(sk, s).get();
        
                 

                 sendReel( myAt,  theirAt, skey + 'a');
                }
                catch ( Exception e) {
                    e.printStackTrace();
                    System.out.println("NOT SUCCESS");
                    
        
        
        
            }

        }
    }
}
  