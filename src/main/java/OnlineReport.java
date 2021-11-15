import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.net.UnknownHostException;

public class OnlineReport extends Report {
    private String content = null;
    private String platform = null;
    private String object = null;

    public OnlineReport() {
        super();
    }

    public OnlineReport(String ip_address) throws UnknownHostException, IOException, GeoIp2Exception {
        super(ip_address);
        this.platform = null;
        this.object = null;
        this.content = null;
    }

    void importFormPicture(String file, String platform, String object) {
        this.content = GetImgText.query(file, "vie").trim();
        this.platform = platform;
        this.object = object;
    }

    void importFormLink(String url, String object) {
        if (url.contains("facebook") || url.contains("fb")) {
            this.platform = "facebook";
        }
        this.content = "none"; //some api that get detail facebook post. . .
        this.object = object; //some api that identify the animal . . .
    }

    @Override
    public String toString() {
        return "OnlineReport[" +
                "content='" + content + '\'' +
                ", platform='" + platform + '\'' +
                ", object='" + object + '\'' +
                "] " + super.toString();
    }

    public static void main(String[] args) throws UnknownHostException, IOException, GeoIp2Exception {
        OnlineReport ol = new OnlineReport("123.16.89.255");
        ol.importFormPicture("pictures/vie.png", "facebook", "animal");
        System.out.println(ol.toString());

        ol.importFormLink("https://www.facebook.com/WildAidVietnam/posts/4509648162428977", "animal");
        System.out.println(ol.toString());
    }
}
