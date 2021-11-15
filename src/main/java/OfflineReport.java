import com.maxmind.geoip2.exception.GeoIp2Exception;

import java.io.IOException;
import java.net.UnknownHostException;

public class OfflineReport extends Report {
    //Data set
    private String object = null;

    public OfflineReport() {
        super();
    }

    public OfflineReport(String ipAddress) throws IOException, GeoIp2Exception {
        super(ipAddress);
        this.object = "animal";
    }

    @Override
    public String toString() {
        return "OfflineReport[" +
                "object='" + object + '\'' +
                "] " + super.toString();
    }

    public static void main(String[] args) throws UnknownHostException, IOException, GeoIp2Exception {
        OfflineReport ol = new OfflineReport("123.16.89.255");
        System.out.println(ol.toString());
    }
}
