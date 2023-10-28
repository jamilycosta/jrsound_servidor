import java.io.*;
import java.net.Socket;

// classe que implementa a interface Runnable cujas instâncias são executadas por uma thread
public class ConnectionHandler implements Runnable{
    private final Socket cliente;

    public ConnectionHandler(Socket cliente) {
        this.cliente = cliente;
    }

    // implementação do método run que gerencia as conexões com os clientes
    @Override
    public void run() {
        System.out.println("Nova conexão com o cliente " +
                cliente.getInetAddress().getHostAddress()
        );
        try {
            JRMPDecoder decoder = new JRMPDecoder(cliente);
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            String s;
            while (!cliente.isClosed()) {
                s = in.readLine();
                System.out.println(cliente.getInetAddress().getHostAddress() + ": " + s);
                decoder.decode(s);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
