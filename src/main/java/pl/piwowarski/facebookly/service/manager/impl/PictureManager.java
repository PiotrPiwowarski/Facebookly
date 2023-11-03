package pl.piwowarski.facebookly.service.manager.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.ImageSavingProcessException;
import pl.piwowarski.facebookly.exception.WrongImageFormatException;
import pl.piwowarski.facebookly.service.manager.Manager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class PictureManager implements Manager {

    @Value("${facebookly.picture.format}")
    private String format;

    public byte[] fromPathToBytesArray(String imagePath) {
        if(imagePath == null){
            return null;
        }
        String formatRegex = ".*." + format;
        if(!imagePath.matches(formatRegex)){
            throw new WrongImageFormatException();
        }
        try{
            BufferedImage bImage = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, format, bos);
            return bos.toByteArray();
        }catch(Exception e){
            throw new ImageSavingProcessException();
        }
    }
}
