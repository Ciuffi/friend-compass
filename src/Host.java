import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Austin on 2018-12-01.
 */
public class Host {

    public static void main(String[] args){
        HostSession();
    }


    public Host(){

    }


    public static void HostSession(){
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String received;
            while ((received = in.readLine()) != null) {
                System.out.println("Server received: " + received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void SendHostCode(){
        String code = GenerateHostCode();
        // TODO send host code to server
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
}
