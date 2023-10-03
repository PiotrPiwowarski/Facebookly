package pl.piwowarski.facebookly.service.manager;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.PictureSavingProcessException;
import pl.piwowarski.facebookly.exception.WrongPictureFormatException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class PictureManager {

    private static final String CHECK_FORMAT = ".*.jpg";
    private static final String FORMAT = "jpg";

    public byte[] fromPathToBytesArray(String imagePath) {
        if(imagePath == null){
            return null;
        }
        if(!imagePath.matches(CHECK_FORMAT)){
            throw new WrongPictureFormatException(WrongPictureFormatException.MESSAGE);
        }
        try{
            BufferedImage bImage = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, FORMAT, bos);
            return bos.toByteArray();
        }catch(Exception e){
            throw new PictureSavingProcessException(PictureSavingProcessException.MESSAGE);
        }
    }
}
