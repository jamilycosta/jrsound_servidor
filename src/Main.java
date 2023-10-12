import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(1122);
        if (!servidor.isBound()){
            servidor.bind(new InetSocketAddress("127.0.0.1", 1122));
        }
        System.out.println("Servidor iniciado!");
        while(true) {
            Socket cliente = servidor.accept();
            Runnable connectionHandler = new ConnectionHandler(cliente);
            new Thread(connectionHandler).start();
        }
    }
}