import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Austin on 2018-12-01.
 */
public class ConnectionHandler implements Runnable{

    PrintWriter out;
    BufferedReader in;
    Socket clientSocket;

    public ConnectionHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String received;
            while ((received = in.readLine()) != null) {
                System.out.println("Server received: " + received);
                Host.positions.replace(clientSocket.getInetAddress().getHostAddress(), ParseClientReply(received));
                Host.PrintPositions();
                //SendUpdatedPositions();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public Double[] ParseClientReply(String reply){
        String[] parts = reply.split("/");
        Double lat = Double.parseDouble(parts[0]);
        Double lon = Double.parseDouble(parts[1]);
        return new Double[]{lat, lon};
    }


    public void UpdateMap(){

    }



    public void SendUpdatedPositions(){
        out.println(DictDecoder.EncodeDict(Host.positions));
    }
}
