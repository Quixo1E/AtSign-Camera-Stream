package com.example;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;
import org.atsign.common.AtSign;
public final class constants{
    private constants(){
    }

    public static final String ROOT_URL = "root.atsign.org:64";
    public static final String ATSIGN_STR_SHARED_BY = "@22tooeasy"; // my atSign (sharedBy)
    public static final String ATSIGN_STR_SHARED_WITH = "@84scootering"; // other atSign (sharedWith)
    public static final AtSign sharedBy = new AtSign(ATSIGN_STR_SHARED_BY);
    public static final AtSign sharedWith = new AtSign(ATSIGN_STR_SHARED_WITH);
    public static final boolean VERBOSE = true;
    public static final String KEY_NAME_VIDEO = "video";
    public static final String KEY_NAME_AUDIO = "audio";
    public static final int ttl = 30 * 60 * 1000; // 30 minutes
    public static final boolean status = true;
    public static final int sampleRate = 44100;
    public static final int channels = 2;
    public static final int sampleSize = 16;
    public static final boolean bigEndian = true;
    public static final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
    public static final AudioFormat format = new AudioFormat(encoding, sampleRate, sampleSize, channels, (sampleSize / 8) * channels, sampleRate, bigEndian);
    public static final DataLine.Info TargetDataLineInfo = new DataLine.Info(TargetDataLine.class, format);
    public static final DataLine.Info SourceDataLineInfo = new DataLine.Info(SourceDataLine.class, format);

}