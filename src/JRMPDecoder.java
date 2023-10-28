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

    @Override
    public void decode(String mensagem) throws IOException {
        String rawScript = mensagem.replace("\n", "").replace("\r", "");
        String[] commands = rawScript.split("@\\{");
        for (String command: commands) {
            String[] commandParts = command.trim().replace("}", "").split(" ");
            if (commandParts[0].isEmpty())
                continue;
            execute(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
        }
    }

    @Override
    public void execute(String mensagem, String[] params) throws IOException {
        switch (mensagem.toLowerCase()) {
            case "tocar" -> tocar(params);
            case "listar" -> listar(params);
            case "nomemusica" -> nomeMusica(params);
            case "enviar" -> enviar(params);
            case "sair" -> sair(params);
            default -> mensagemErro();
        }
    }

    @Override
    public void tocar(String[] params) throws IOException {
        FileReader fr = new FileReader("./music/" + String.join("_", params) + ".jrmi");
        BufferedReader br = new BufferedReader(fr);
        String jrmi = "";
        String line;

        while ((line = br.readLine()) != null) {
            jrmi = jrmi + line + " ";
        }
        ps.println("@{TOCAR " + jrmi + "}");
    }

    @Override
    public void listar(String[] params) {
        String musicas = "";
        File folder = new File("./music");
        File[] listOfFiles = folder.listFiles();

        for (File file: listOfFiles) {
            musicas = musicas + file.getName() + " ";
        }
        ps.println("@{LISTAR " + musicas + "}");
    }

    @Override
    public void nomeMusica(String[] params) {
        this.nomeMusica = String.join("_", params);
    }

    @Override
    public void enviar(String[] params) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("./music/" + this.nomeMusica + ".jrmi", "UTF-8");
            for(String param: params) {
                writer.print(param + " ");
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

    @Override
    public void mensagemErro() {
        System.out.println("Erro! Comando inválido!");
        ps.println("INVALIDO");
    }
}
