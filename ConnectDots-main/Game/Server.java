import java.io.*;
import java.net.*;
import java.util.ArrayList;

/////////////////////////////////Sever!!!!!!!!!!!!!!!!!!!!!!!!!

public class Server {
    private ServerSocket serverSocket;
    private Client_Handler clientHandler;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;

    }

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


