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
import static com.example.constants.*;


public class ReceiveVideo extends AtException implements Runnable{
    JLabel label;
    Keys.AtKey key;
    AtClient atClient;
    AtSign atSign;
    String prev;
    JFrame frame;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;   
    public ReceiveVideo(String  theirAt, String pk){
        super("atsign exception");
        this.atSign = new AtSign(theirAt);
        this.key = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.pk = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.publicKey = true;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
    


        this.label = new JLabel();
        this.frame = new JFrame();
        prev = null;
    }
    public ReceiveVideo(String me, String sharedby, String sk){
        super("atsign exception");
        this.atSign = new AtSign(me);
       AtSign SHARED_BY = new AtSign(sharedby);
        this.sk = new KeyBuilders.SharedKeyBuilder( SHARED_BY, atSign).key(sk).build();
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64",SHARED_BY);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
    


        this.label = new JLabel();
        this.frame = new JFrame();
        prev = null;

    }

    public void run(){
        while (true){
            try {
                Thread.sleep(80);
                String data = null;
                if (this.publicKey){
                    String command = "plookup:bypassCache:true:" + this.pk.name + this.pk.sharedBy.toString();
                    data = atClient.executeCommand(command, false).data;
                }
                else {
                    data = atClient.get(sk).get();
                }

                if (!data.equals(prev)) {
                    System.out.println("new data");
                    prev = data;
                    byte[] bytes = Base64.getDecoder().decode(data);
                    //turn byte array to ByteArrayInputStream
                    ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                    // turn the ByteArrayInputStream into a bufferImage
                    BufferedImage bImage2 = ImageIO.read(byteStream);
                    ImageIcon icon = new ImageIcon(bImage2);
                    label.setIcon(icon);
                    this.frame.add(label);
                    this.frame.setVisible(true);
                    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    this.frame.setLayout(new FlowLayout());
                    this.frame.setSize(640,480);
        
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get string from server");
            }
        }
    }
    // public static void main( String[] args )
    // {
    //     String ROOT_URL = "root.atsign.org:64";
    //     String ATSIGN_STR_SHARED_BY = "@22easy"; // my atSign (sharedBy)
    //     String ATSIGN_STR_SHARED_WITH = "@84scootering"; // other atSign (sharedWith)
    //     AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY);
    //      AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
    //      boolean VERBOSE = true;
    //     String KEY_NAME_VIDEO = "video";
    //     String KEY_NAME_AUDIO = "audio";
       
    //     AtClient atClient = null;
    //     try {
    //         AtSign atSign = sharedWith;
    //         System.out.println(atSign.toString());
    //         atClient = AtClient.withRemoteSecondary(ROOT_URL, atSign);
    //         // putting the pk
    //         PublicKey pk = new KeyBuilders.PublicKeyBuilder(sharedBy).key(KEY_NAME_VIDEO).build();
    //         //PublicKey pk2 = new KeyBuilders.PublicKeyBuilder(atSign).key(KEY_NAME_AUDIO).build();
    //        // SharedKey sk = new KeyBuilders.SharedKeyBuilder(sharedBy, sharedWith).key(KEY_NAME_VIDEO).build();
    //         final AtClient aC= atClient;
            
    //         ReceiveVideo receiveVideo= new ReceiveVideo(aC, pk);
   
    //         Thread rVideo = new Thread(receiveVideo);
    //         rVideo.start();
    //     }
    //     catch (Exception e) {
    //         e.printStackTrace();
    //         System.out.println("error connecting...");
    //     }
    // }
}