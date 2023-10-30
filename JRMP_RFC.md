# Jamily-Ricardo Musical Protocol - 1.0

O Jamily-Ricardo Musical Protocol (JRMP) é uma protocolo de tranferência que permite que um servidor e seus clientes conectados consigam tranferir entre si músicas codificadas no formato JRMI.

Todas as tags deste protocolo possuem `@{` no início e `}` no final.

## Comandos de controle
Iniciam com uma palavra-chave que especifica a ação que é solicitada para ser executada.

Exemplo: @{NOMEMUSICA alecrim dourado}

### Tocar: TOCAR

    Cliente → Servidor: Requisita que uma música do servidor seja tocada.
    Servidor → Cliente: Envia uma música codificada em JRMI para ser reproduzida pelo cliente solicitante.
    
### Listar: LISTAR

    Requisita que o servidor liste as músicas que ele possui.

### Nome da Música: NOMEMUSICA

    Nomeia a música a ser enviada em seguida pelo cliente.

### Enviar: ENVIAR

    Requisita que o servidor armazene a música codificada em JRMI passada como parâmetro pelo cliente.

### Sair: SAIR

    Requisita que o servidor encerre a conexão com o cliente solicitante.
