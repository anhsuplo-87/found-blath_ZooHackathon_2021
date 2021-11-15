import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.net.UnknownHostException;

public class TemplateMatching {
    public static void match(String filePath, String sourceFileName, String templateFileName) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source=null;
        Mat template=null;
        source=Imgcodecs.imread(filePath+sourceFileName);
        template=Imgcodecs.imread(filePath+templateFileName);

        Mat outputImage=new Mat();
        int machMethod=Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);

        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc=mmr.maxLoc;

        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));

        Imgcodecs.imwrite(filePath+"match"+sourceFileName, source);
    }
    public static void main(String[] args) {
        TemplateMatching.match("pictures/","source.png", "template.png");
        System.out.println("Complated.");
    }
}