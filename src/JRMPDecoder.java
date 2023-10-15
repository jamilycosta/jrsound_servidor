import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class JRMPDecoder {

    private final Socket socket;
    private final PrintWriter print;

    public JRMPDecoder(Socket cliente) throws IOException {
        this.socket = cliente;
        this.print = new PrintWriter(socket.getOutputStream());
    }

    public void decode(String mensagem) {
        switch (mensagem.toLowerCase()) {
            case "ping":
                ping();
                break;
        }
    }

    public void ping() {
        print.println("pong");
    }
}
