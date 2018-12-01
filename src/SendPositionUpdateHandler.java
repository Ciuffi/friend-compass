import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Austin on 2018-12-01.
 */
class SendPositionUpdateHandler implements Runnable {

    Socket clientSocket;
    PrintWriter out;

    public SendPositionUpdateHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run(){

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Random rand = new Random();

        while(true){
            Double lat = rand.nextDouble();
            Double lon = rand.nextDouble();

            System.out.println("Client sending: " + lat.toString() + "/" + lon.toString());
            out.println(lat.toString() + "/" + lon.toString());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
