import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;

public class JRMPDecoder implements JRPMPnterface {

    private final Socket socket;
    private final PrintStream ps;

    public JRMPDecoder(Socket cliente) throws IOException {
        this.socket = cliente;
        this.ps = new PrintStream(socket.getOutputStream());
    }

    public void decode(String mensagem) throws IOException {
        String rawScript = mensagem.replace("\n", "").replace("\r", "");
        String[] commands = rawScript.split("@\\{");
        for (String command: commands) {
            String[] commandParts = command.trim().replace("}", "").split(" ");
            execute(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
        }
    }

    public void execute(String mensagem, String[] params) throws IOException {
        switch (mensagem.toLowerCase()) {
            case "tocar" -> tocar(params);
            case "reproduzir" -> reproduzir(params);
            case "listar" -> listar(params);
            case "enviar" -> enviar(params);
            case "sair" -> sair(params);
        }
    }

    @Override
    public void tocar(String[] params) {
        ps.println("@{REPRODUZIR "+ String.join(" ", params) +"}");
    }

    @Override
    public void reproduzir(String[] params) {

    }

    @Override
    public void listar(String[] params) {

    }

    @Override
    public void enviar(String[] params) {

    }

    @Override
    public void sair(String[] params) throws IOException {
        socket.close();
    }
}
