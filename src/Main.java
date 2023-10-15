import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            // ao ser iniciado o servidor aguardará solicitações de programas clientes na porta 1122
            ServerSocket servidor = new ServerSocket(1122);

            // verifica se o servidor já foi vinculado a um endereço IP
            if (!servidor.isBound()) {
                servidor.bind(new InetSocketAddress("127.0.0.1", 1122));
            }
            System.out.println("Servidor iniciado!");

            // cria uma conexão com um cliente e o encaminha para sua thread
            while (true) {
                Socket cliente = servidor.accept();
                Runnable connectionHandler = new ConnectionHandler(cliente);
                new Thread(connectionHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}