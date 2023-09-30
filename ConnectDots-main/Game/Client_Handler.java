
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/** 
 * Esta clase es la encargada de manejar la comunicación con un cliente en un chat.
*/
public class Client_Handler implements Runnable {

    public static ArrayList<Client_Handler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private int contador1;

    /**
     * Este constructor es el encargado de que se inicialicen los flujos de entrada/salida, así como obtener el nombre de usuario del cliente que se conecto al servidor.
     *
     * @param socket El socket de la conexión con el cliente.
     */
    public Client_Handler(Socket socket, int contador){
        try{
            this.contador1 = contador;
            this.socket = socket;
            this.bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine(); 
            clientHandlers.add(this); 
            broadcastMessage("SERVER: "+clientUsername+" ha entrado al chat!");
            String ahh = "Me cago";
            broadcastMessage(ahh);
            contador1 = contador +1;

            //System.out.println(contador);
        
        } catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run(){
        String messageFromClient;

        while (socket.isConnected()){
            try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            String newUserMessage = "Nuevo usuario conectado: " + socket.getInetAddress().getHostAddress();
            broadcastMessage(newUserMessage);

            System.out.println(contador1);
            if (contador1==4){
            contador1 = contador1 +1;
                messageFromClient = in.readLine();
                String messageToSend = "Cliente " + socket.getInetAddress().getHostAddress() + ": " + messageFromClient;
                broadcastMessage(messageToSend);
                String ahh = "Inicio";
                final Scanner sc = new Scanner(System.in);
                System.out.println("Indique el numero de columnas");
                String columnas = sc.nextLine();//Obtiene el usuario escrito
                System.out.println("Indique el numero de filas");
                String filas = sc.nextLine();//Obtiene el usuario escrito
                
                broadcastMessage("Inicio" + " " + columnas + " " + filas);
                  }
            
            boolean manzana = true;
            while (manzana == true) {
                // Procesa y retransmite el mensaje a todos los clientes
                messageFromClient = in.readLine();
                System.out.println(messageFromClient);
                broadcastMessage(messageFromClient);
                
                

                
            }

            }catch(IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                //break;
            }
        }

      


        



    }
    /**
     * Este método es utilizado para transmitir un mensaje a todos los clientes conectados por ende este mensaje sería uno de broadcast.
     *
     * @param messageToSend Es el mensaje que se va a enviar a todos los clientes.
     */
    public void broadcastMessage(String messageToSend){
        for(Client_Handler clientHandler: clientHandlers){
            try{
                if (!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch(IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }
    }
    /**
     * Método para el manejador de cliente, al igual que el avisar a los demás clientes que un usuario se desconecto del chat.
     */
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER"+clientUsername+" ha abandonado el chat");
    }
    
    /**
     * Este método es utilizado para cerrar los flujos de entrada/salida y el socket.
     *
     * @param socket          El socket a cerrar.
     * @param bufferedReader  El lector de flujo de entrada a cerrar.
     * @param bufferedWriter  El escritor de flujo de salida a cerrar.
     */
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    
}
