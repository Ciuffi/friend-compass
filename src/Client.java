import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Austin on 2018-12-01.
 */
public class Client {

    public static void main(String[] main){
        JoinSession("127.0.0.1", 3000);
    }


    public Client(){

    }


    public static void JoinSession(String ip, int port){
        try {
            Socket kkSocket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader( new InputStreamReader(kkSocket.getInputStream()));
            System.out.println("Client sending test...");
            out.println("5.0/6.0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String GetHostIp(String code){
        String[] parts = code.split("z");

        String ipAddress = "";

        for(String part : parts){
            for (int i = 0; i < part.length(); i++){
                int c = part.charAt(i);
                ipAddress = ipAddress + (char) (c - 49);
            }
            ipAddress = ipAddress + ".";
        }

        System.out.println(ipAddress.substring(0,ipAddress.length() - 1));
        return ipAddress;
    }
}
