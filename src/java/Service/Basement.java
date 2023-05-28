package Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.stereotype.Service;

@Service
public class Basement {

    public String getBasementDirectory() {
        if (getApplicationHostName().equals("LAPTOP")) {
            return "C:\\Users\\Michail Sitmalidis\\pet4U_3.0";
        }
        if (getApplicationHostName().equals("var-apoth")) {
            return "C:\\pet4U_3.0";
        }
        if (getApplicationHostName().equals("pet4uLinuxServer")) {
            return "/home/basement";
        } else {
            //this is on amazon server
            return "/home/admin/basement";
        }
    }

    public String getApplicationHostName() {
        InetAddress ip;
        String hostname = "Ν/Α";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostname;
    }
}
