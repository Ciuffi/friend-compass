import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Austin on 2018-12-01.
 */
public class Host {

    static ConcurrentHashMap<String, Double[]> positions = new ConcurrentHashMap<>();

    public static void main(String[] args){
        HostSession();
    }


    public Host(){
    }


    public static void HostSession(){
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                positions.put(clientSocket.getInetAddress().getHostAddress(), new Double[]{0.0, 0.0});
                PrintPositions();
                Runnable connectionHandler = new HostConnectionHandler(clientSocket);
                new Thread(connectionHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String GenerateHostCode(){
        String ipAddress = GetIpAddress();
        String[] parts = ipAddress.split("\\.");

        String code = "";

        for(String part : parts){
            for (int i = 0; i < part.length(); i++){
                int c = part.charAt(i);
                code = code + (char) (c + 49);
            }
            code = code + "z";
        }

        return code.substring(0,code.length() - 1);
    }



    private String GetIpAddress(){
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void PrintPositions(){
        for (Map.Entry<String, Double[]> entry : positions.entrySet()) {
            String key = entry.getKey();
            Double[] value = entry.getValue();
            System.out.println("Key: " + key + ", Position: " + Arrays.toString(value));
        }
    }
}
