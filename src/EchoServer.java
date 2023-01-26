
import java.io.*;
import java.net.*;

public class EchoServer {
    public EchoServer() {

    }
    public void establish() {
        ServerSocket serverSocket = null;
        try {
            serverSocket= new ServerSocket(8888);
        }catch (IOException e) {
            System.out.println("Could not listen on port: 8888");
            System.exit(-1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        }catch (IOException e) {
            System.out.println("Accept failed: 8888");
            System.exit(-1);
        }
        ObjectOutputStream out=null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }
        PersistentTime time;
        try {
            time = (PersistentTime) in.readObject();
            System.out.println("Time received from client: " + time.getTime());
            out.writeObject(time);
            out.flush();
        }catch (IOException ioe) {
            System.out.println("Failed in reading or writing object");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            System.out.println("Failed in casting object");
            System.exit(-1);
        }
        try {
            clientSocket.close();
            serverSocket.close();
        }catch (IOException e) {
            System.out.println("Could not close");
            System.exit(-1);
        }
    }
}
