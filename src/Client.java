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
    public void run() {
        Client.updateDict();
    }
}

public class Client {

    private static Socket kkSocket;
    private static PrintWriter out;
    private static updateHandler uHandler;
    private static BufferedReader in;
    private static boolean connected = false;
    private static Map<String, Double[]> IPDict = new HashMap<>();

    public Client(String ip, int port) {
        JoinSession(ip, port);
        uHandler = new updateHandler();
        uHandler.run();
    }


    private static void JoinSession(String ip, int port) {
        try {
            kkSocket = new Socket(ip, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            System.out.println("Client sending initial data...");
            out.println("5.0/6.0");
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void updateDict() {
        while (true) {
            try {
                String recieved = in.readLine();
                if (recieved != null) {
                    IPDict = DictDecoder.DecodeDict(recieved);
                }
            } catch (java.io.IOException ex) {
            }
        }
    }

    public static Double[] getLatLonById(String id) {
        return IPDict.get(id);
    }


    public String GetHostIp(String code) {
        String[] parts = code.split("z");

        String ipAddress = "";

        for (String part : parts) {
            for (int i = 0; i < part.length(); i++) {
                int c = part.charAt(i);
                ipAddress = ipAddress + (char) (c - 49);
            }
            ipAddress = ipAddress + ".";
        }

        System.out.println(ipAddress.substring(0, ipAddress.length() - 1));
        return ipAddress;
    }
}
