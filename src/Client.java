import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Austin on 2018-12-01.
 */

class updateHandler implements Runnable {
    public void run(){
        while (true){
            Client.updateDict();
        }
    }
}

public class Client {

    private static Socket kkSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static boolean connected = false;
    private static Map<String, Double[]> IPDict = new HashMap<>();
    public static void main(String[] main){
        JoinSession("127.0.0.1", 3000);
    }


    public Client(){

    }


    public static void JoinSession(String ip, int port){
        try {
            kkSocket = new Socket(ip, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader( new InputStreamReader(kkSocket.getInputStream()));
            System.out.println("Client sending test...");
            out.println("client test");
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDict(){
        try{
            String recieved = in.readLine();
            if (recieved != null){
                int index;
                String newLon;
                String newLat;
                index = recieved.indexOf('/');
                newLat = recieved.substring(0, index);
                newLon = recieved.substring(index+1, -1);
                Double[] latlon = {
                        Double.parseDouble(recieved.substring(0, index)),
                        Double.parseDouble(recieved.substring(index+1, -1))};
                IPDict.put(latlon);

            }
        }catch (java.io.IOException ex){

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
