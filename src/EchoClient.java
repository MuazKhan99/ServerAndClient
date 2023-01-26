import java.io.*;
import java.net.*;


public class EchoClient {
    public EchoClient() {
    }

    public void establish() {
        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            echoSocket = new Socket(InetAddress.getLocalHost(), 8888);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            System.exit(1);
        }
        try {
            PersistentTime time = new PersistentTime();
            out.writeObject(time);
            out.flush();
            System.out.println("Time sent to server: " + time.getTime());
            time = (PersistentTime) in.readObject();
            System.out.println("Time received from server: " + time.getTime());

            out.close();
            in.close();
            echoSocket.close();
        } catch (IOException ioe) {
            System.out.println("Failed");
            System.exit(-1);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Failed, class not found");
            System.exit(-1);
        }
    }
}

