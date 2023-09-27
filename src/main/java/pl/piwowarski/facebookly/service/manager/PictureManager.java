package pl.piwowarski.facebookly.service.manager;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.PictureSavingProcessException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class PictureManager {

    public byte[] fromPathToBytesArray(String imagePath) {
        if(imagePath == null){
            return null;
        }
        try{
            BufferedImage bImage = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos);
            return bos.toByteArray();
        }catch(Exception e){
            throw new PictureSavingProcessException(PictureSavingProcessException.MESSAGE);
        }
    }
}
