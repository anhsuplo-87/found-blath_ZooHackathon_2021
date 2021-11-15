import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;

import java.net.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.io.File;
import java.io.IOException;

public class Report {
    //ip address
    private String ip_address;
    private String host_name;

    //location
    private String location;

    //time
    private String time;

    public Report() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.time = dtf.format(now);

        ip_address = null;
        host_name = null;
        location = null;
    }

    public Report(String ipAddress) throws UnknownHostException, IOException, GeoIp2Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.time = dtf.format(now);

        InetAddress ip;
        String hostname;

        /* old method to get Ip Address from local host
        ip = InetAddress.getLocalHost();
        hostname = ip.getHostName();
        System.out.println(ip.getHostAddress());
        */

        ip = InetAddress.getByName(ipAddress);
        hostname = ip.getHostName();

        this.ip_address = ip.getHostAddress();
        this.host_name = hostname;

        File database = new File("GeoLite2/GeoLite2-City_20211109/GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

        CityResponse response = reader.city(ip);
        Country country = response.getCountry();
        City city = response.getCity();

        Location location = response.getLocation();

        this.location = country.getName() + " - "
                + city.getName() + " - "
                + "X:" + location.getLatitude() + ", "
                + "Y:" + location.getLongitude();
    }

    @Override
    public String toString() {
        return "Report[" +
                "ip_address='" + ip_address + '\'' +
                ", host_name='" + host_name + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                ']';
    }

    public static void main(String[] args) throws UnknownHostException, IOException, GeoIp2Exception {
        Report report = new Report("123.16.89.255");
        System.out.println(report.toString());
    }
}
