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
    class sendData extends AtException implements Runnable{
        AtClient atClient;
        PublicKey pk;
        AtSign atSign;
        String s;
        public sendData(AtSign aS){
            super("atsign exception");
            this.atSign = aS;
            try {
                atClient = AtClient.withRemoteSecondary("root.atsign.org:64", atSign);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("Could not get to atclient");
            }
            this.pk = new KeyBuilders.PublicKeyBuilder(this.atSign).key("test").build();
        }
        public void run() {
            while (true){
                try{
                byte[] byteArray = ImageUtil.toByteArray(CameraUtil.getSingleImage());
                this.s = Base64.getEncoder().encodeToString(byteArray);
                long startTime = System.nanoTime();
                atClient.put(this.pk, s).get();
                long endTime = System.nanoTime();
                System.out.println("upload: " + (endTime - startTime) / 1000000);
                //System.out.println("send" + s);
                Thread.sleep(100);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Could not get images");
                }
                
            }
        }
        public String data(){
            try {
                String data = this.s;
                return "send" + data;
            }
                catch (Exception e) {
                    e.printStackTrace();
                    return("Could not get string from server");
                }
    
        }
    
    }
    