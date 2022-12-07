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
import static com.example.constants.*;
class sendAudio extends AtException implements Runnable{
    AtClient atClient;
    Keys.AtKey key;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;
    AtSign atSign, SHARED_WITH;
    String s;
    TargetDataLine targetDataLine;
    public sendAudio(String me, String pk){
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
        try {
            this.targetDataLine = (TargetDataLine) AudioSystem.getLine(TargetDataLineInfo);
            this.targetDataLine.open(format);
            this.targetDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public sendAudio(String myAt, String theirAt, String sk){
        super("atsign exception");
        this.atSign = new AtSign(myAt);
        SHARED_WITH = new AtSign(theirAt);
         this.sk = new KeyBuilders.SharedKeyBuilder(atSign, SHARED_WITH).key(sk).build();
        
        this.publicKey = false;
        try {
            atClient = AtClient.withRemoteSecondary("root.atsign.org:64", this.atSign);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get to atclient");
        }
        try {
            this.targetDataLine = (TargetDataLine) AudioSystem.getLine(TargetDataLineInfo);
            this.targetDataLine.open(format);
            this.targetDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true){
            try{
                byte[] bytebuffer = new byte[20000];
                this.targetDataLine.read(bytebuffer, 0, bytebuffer.length);
                System.out.println(bytebuffer);
                this.s = Base64.getEncoder().encodeToString(bytebuffer);
                if (this.publicKey){
                    atClient.put(this.pk, s);
                }
                else {
                    atClient.put(this.sk, s);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get images");
            }
            
        }
    }
}
class receiveAudio extends AtException implements Runnable{
    AtClient atClient;
    Keys.AtKey key;
    boolean publicKey;
    PublicKey pk;
    SharedKey sk;
    AtSign atSign, SHARED_WITH;
    String s;

    SourceDataLine sourceDataLine;
    public receiveAudio(String  theirAt, String pk){
        super("atsign exception");
        this.atSign = new AtSign(theirAt);
        this.key = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.pk = new KeyBuilders.PublicKeyBuilder(this.atSign).key(pk).build();
        this.publicKey = true;

        try {
            this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(SourceDataLineInfo);
            this.sourceDataLine.open(format);
            this.sourceDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public receiveAudio(String me, String theirAt, String sk){
        super("atsign exception");
        this.atSign = new AtSign(me);
        SHARED_WITH = new AtSign(theirAt);
         this.sk = new KeyBuilders.SharedKeyBuilder(atSign, SHARED_WITH).key(sk).build();
        
        this.publicKey = false;

        try {
            this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(SourceDataLineInfo);
            this.sourceDataLine.open(format);
            this.sourceDataLine.start();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true){
            try{
                String data = null;
                if (this.publicKey){
                    String command = "plookup:bypassCache:true:" + this.pk.name + this.pk.sharedBy.toString();
                    data = this.atClient.executeCommand(command, false).data;
                }
                else {
                    data = this.atClient.get(sk).get();
                }
                byte[] bytes = Base64.getDecoder().decode(data);
                System.out.println(bytes);
                sourceDataLine.write(bytes, 0, bytes.length);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get audio");
            }
            
        }
    }
}