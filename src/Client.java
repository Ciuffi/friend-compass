import jdk.nashorn.internal.codegen.DumpBytecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Created by Austin on 2018-12-01.
 */

public class Client {

    private static Socket kkSocket;
    private static PrintWriter out;
    private static Runnable uHandler;
    private static BufferedReader in;
    private static boolean connected = false;
    private static Map<String, Double[]> positions = new HashMap<>();

    public Client(String ip, int port) {
        JoinSession(ip, port);

        uHandler = new UpdateDictHandler();
        new Thread(uHandler).start();

        Runnable positionUpdateHandler = new SendPositionUpdateHandler(kkSocket);
        new Thread(positionUpdateHandler).start();
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

    static void UpdateDict() {
        while (true) {
            try {
                String recieved = in.readLine();
                if (recieved != null) {
                    positions = DictDecoder.DecodeDict(recieved);
                    PrintPositions();
                }
            } catch (java.io.IOException ex) {
            }
        }
    }

    public static Double[] getLatLonById(String id) {
        return positions.get(id);
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


    public static void PrintPositions(){
        for (Map.Entry<String, Double[]> entry : positions.entrySet()) {
            String key = entry.getKey();
            Double[] value = entry.getValue();
            System.out.println("Key: " + key + ", Position: " + Arrays.toString(value));
        }
    }
}
