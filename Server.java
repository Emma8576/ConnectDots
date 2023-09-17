import java.io.*;
import java.net.*;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private ClientHandler next;
    private static int nextIdentifier = 1;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    public void run() {
        try {
            // Obtener flujos de entrada y salida del cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            int identifier = getNextIdentifier();
            out.println(identifier);

            // Agregar el manejador del cliente a la lista enlazada
            addClient(this);

            // Notificar a todos los clientes que un nuevo usuario se ha conectado
            String newUserMessage = "Nuevo usuario conectado: " + clientSocket.getInetAddress().getHostAddress();
            broadcast(newUserMessage);

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                // Procesa y retransmite el mensaje a todos los clientes
                String messageToSend = "Cliente " + clientSocket.getInetAddress().getHostAddress() + ": " + clientMessage;
                broadcast(messageToSend);
            }

            // El cliente ha salido del bucle, por lo que se elimina de la lista enlazada y se notifica a los demás
            removeClient(this);
            String userLeftMessage = "Usuario desconectado: " + clientSocket.getInetAddress().getHostAddress();
            broadcast(userLeftMessage);

            // Cierra la conexión con el cliente
            clientSocket.close();
        } catch (IOException e) {
            // Maneja la excepción de E/S cuando el cliente se desconecta
            System.out.println("Cliente desconectado desde " + clientSocket.getInetAddress().getHostAddress());
            removeClient(this);
            String userLeftMessage = "Usuario desconectado: " + clientSocket.getInetAddress().getHostAddress();
            broadcast(userLeftMessage);
        }
    }
    private static synchronized int getNextIdentifier() {
        return nextIdentifier++;
    }

    // Método para enviar un mensaje en broadcast a todos los clientes
    private static void broadcast(String message) {
        ClientHandler current = firstClient;
        while (current != null) {
            current.out.println(message);
            current = current.next;
        }
    }

    // Implementa una lista enlazada personalizada para los clientes
    private static ClientHandler firstClient;

    private static void addClient(ClientHandler client) {
        if (firstClient == null) {
            firstClient = client;
        } else {
            ClientHandler current = firstClient;
            while (current.next != null) {
                current = current.next;
            }
            current.next = client;
        }
    }

    private static void removeClient(ClientHandler client) {
        if (firstClient == client) {
            firstClient = client.next;
        } else {
            ClientHandler current = firstClient;
            while (current != null && current.next != client) {
                current = current.next;
            }
            if (current != null) {
                current.next = client.next;
            }
        }
    }
}

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());

                // Crea un nuevo hilo para manejar el cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


