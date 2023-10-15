import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class JRMPDecoder {

    private final Socket socket;
    private final PrintStream ps;

    public JRMPDecoder(Socket cliente) throws IOException {
        this.socket = cliente;
        this.ps = new PrintStream(socket.getOutputStream());
    }

    public void decode(String mensagem) throws IOException {
        switch (mensagem.toLowerCase()) {
            case "ping":
                ping();
                break;
            case "pong":
                pong();
                break;
            case "sair":
                sair();
                break;
        }
    }

    public void ping() {
        ps.println("pong");
    }

    public void pong() {
        System.out.println("pong");
    }

    public void sair() throws IOException {
        socket.close();
    }
}
