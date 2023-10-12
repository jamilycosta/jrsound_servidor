import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler implements Runnable{
    private Socket cliente;

    public ConnectionHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        System.out.println("Nova conexão com o cliente " +
                cliente.getInetAddress().getHostAddress()
        );
        Scanner s = null;
        try {
            s = new Scanner(cliente.getInputStream());
            while (s.hasNextLine()) {
                System.out.println(
                        cliente.getInetAddress().getHostAddress() + ": " + s.nextLine()
                );
            }
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
