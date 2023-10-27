import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class JRMPDecoder implements JRMPInterface {

    private final Socket socket;
    private final PrintStream ps;
    private String nomeMusica = "musica";

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
        // Não terá essa funcionalidade (feito apenas para teste)
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
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./music/+"+ this.nomeMusica +".jrmi", "UTF-8");
            for(String param: params) {
                writer.println(param);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } finally {
            writer.close();
        }
    }

    @Override
    public void sair(String[] params) throws IOException {
        socket.close();
    }
}
