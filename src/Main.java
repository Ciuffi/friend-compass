import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by Austin on 2018-12-01.
 */
public class Main {

    public static void main(String[] args){
        Host host = new Host();
        Client client = new Client();

        host.HostSession();
        client.JoinSession("127.0.0.1", 3000);
//        String code = host.GenerateHostCode();
//        System.out.println(code);
//
//        client.GetHostIp(code);
    }
}
