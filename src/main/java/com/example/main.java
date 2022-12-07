package com.example;

import java.util.*;

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
import javax.swing.JFrame;

public class main extends JFrame {
   
    public static void main( String[] args ) throws IOException
    {
       new LaunchPage();
       // VideoReel obj2 = new VideoReel();
       // obj2.storeReel("@84scootering", "@84scootering", "test");
       
    }
}