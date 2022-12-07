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
import org.atsign.common.Keys.SharedKey;
import com.github.sarxos.webcam.Webcam;
import java.util.Base64;
import org.atsign.client.util.ImageUtil;
import static com.example.constants.*;
import com.github.sarxos.webcam.WebcamPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.Image;
import com.example.SV;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import org.atsign.client.util.CameraUtil;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.io.ByteArrayInputStream;


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

public class SV extends AtException implements Runnable{
    AtClient atClient;
    Keys.AtKey key;
    AtSign atSign;
    AtSign SHARED_WITH;
    String s;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;
    public SV (String me, String pk){
        super("atsign exception");
        this.atSign = new AtSign(me);
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
    }
    public SV (String me, String you, String sk){
        super("atsign exception");
        this.atSign = new AtSign(me);
        SHARED_WITH = new AtSign(you);
         this.sk = new KeyBuilders.SharedKeyBuilder(atSign, SHARED_WITH).key(sk).build();
        
        this.publicKey = false;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
    }
    public void run() {
        while (true){
            try{
            byte[] byteArray = ImageUtil.toByteArray(CameraUtil.getSingleImage());
            this.s = Base64.getEncoder().encodeToString(byteArray);
            if (this.publicKey){
                atClient.put(this.pk, s);
            }
            else {
                atClient.put(this.sk, s);
            }
            Thread.sleep(120);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get images");
            }
            
        }
    }
}