import net.sourceforge.tess4j.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
public class GetImgText {
    public static String query(String imageLocation, String language) {
        ITesseract instance = new Tesseract();
        instance.setLanguage(language);
        instance.setOcrEngineMode(1);
        instance.setDatapath("tessdata/");
        try
        {
            BufferedImage image = ImageIO.read(new File(imageLocation));
            String imgText = instance.doOCR(image);
            return imgText;
        }
        catch (TesseractException e)
        {
            e.getMessage();
            return "Error while reading image";
        }
        catch (IOException e)
        {
            e.getMessage();
            return "Error file";
        }
    }
}