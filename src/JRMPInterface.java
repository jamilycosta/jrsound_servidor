import java.io.IOException;

public interface JRMPInterface {
    void decode(String mensagem) throws IOException;
    void execute(String mensagem, String[] params) throws IOException;
    void tocar(String[] params);
    void reproduzir(String[] params);
    void listar(String[] params);
    void enviar(String[] params);
    void sair(String[] params) throws IOException;
}
