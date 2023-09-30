import java.io.*;
import java.net.*;
import java.util.ArrayList;

/////////////////////////////////Sever!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * Esta clase representa el servidor que acepta conexiones de clientes.
 */
public class Server {
    private ServerSocket serverSocket;
    private Client_Handler clientHandler;

    /**
     * Constructor de la clase Server.
     *
     * @param serverSocket El objeto ServerSocket utilizado para aceptar conexiones de clientes.
     */
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;

    }

    /**
     * Inicia el servidor y comienza a aceptar conexiones de clientes.
     *
     * @param contador Un contador que realiza un seguimiento de la cantidad de clientes conectados.
     */
    public void startServer(int contador){
        try{
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());
                contador = contador+1;
                Client_Handler clientHandler = new Client_Handler(clientSocket,contador);
                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        }catch(IOException e){

        }
    }
    /**
     * Método principal que crea y configura un servidor.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        try {
            int contador = 0;
            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("Servidor esperando conexiones...");
            
            Server server = new Server(serverSocket);
            server.startServer(contador);

                
            


            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}


