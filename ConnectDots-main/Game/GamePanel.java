import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javafx.stage.Stage;
import java.util.*;
import java.util.List;
import java.util.Queue;
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Definición de la clase Node que representa un nodo en una lista enlazada.
 *
 * @param <T> Tipo genérico para los datos almacenados en el nodo.
 */
class Node<T> {
    T data;
    Node<T> next;

    /**
 * Constructor de la clase Node.
 *
 * @param data El dato que se almacenará en el nodo.
 */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

/**
 * Definición de la clase LinkedList que implementa una lista enlazada genérica.
 *
 * @param <T> Tipo genérico para los elementos de la lista.
 */
class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    /**
     * Constructor de la clase LinkedList.
     */
    public LinkedList() {
        head = null;
        tail = null;
    }

    /**
     * Método para agregar un elemento al final de la lista.
     *
     * @param data El elemento que se agregará a la lista.
     */
    public void add(T data) {
        Node<T> newNode = new Node<T>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

        /**
     * Método para obtener un elemento en un índice específico.
     *
     * @param index El índice del elemento que se desea obtener.
     * @return El elemento en el índice especificado.
     * @throws IllegalArgumentException Si el índice es negativo.
     * @throws IndexOutOfBoundsException Si el índice está fuera de los límites de la lista.
     */
    public T get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        Node<T> current = head;
        int currentIndex = 0;
        while (current != null) {
            if (currentIndex == index) {
                return current.data;
            }
            current = current.next;
            currentIndex++;
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    /**
     * Método para obtener el tamaño de la lista.
     *
     * @return El número de elementos en la lista.
     */
    public int size() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
        /**
     * Devuelve un iterador sobre los elementos de la lista.
     *
     * @return Un iterador que permite recorrer los elementos de la lista.
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Clase interna LinkedListIterator que implementa un iterador para la lista enlazada.
     */
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;

        /**
         * Verifica si hay más elementos en la lista.
         *
         * @return true si hay más elementos, false si no.
         */
        public boolean hasNext() {
            return current != null;
        }
        /**
         * Obtiene el siguiente elemento de la lista.
         *
         * @return El siguiente elemento en la lista.
         * @throws NoSuchElementException Si no hay más elementos para iterar.
         */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
        /**
         * No se admite la operación de eliminación en este iterador.
         *
         * @throws UnsupportedOperationException Si se intenta usar la operación "remove".
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
/**
 * Clase Client que representa un cliente de red.
 */
class Client extends Thread {
    private String identifier; // Almacenar el nuevo identificador de jugador
    private Socket socket;
    private GamePanel gamePanel;

    /**
     * Constructor de la clase Client.
     *
     * @param test     Una cadena que indica el tipo de cliente (para pruebas, etc.).
     * @param socket1  El socket para la comunicación con el servidor.
     * @param columna  La columna en el juego.
     * @param fila     La fila en el juego.
     */
    public Client(String test,Socket socket1,int columna, int fila){
        if(test.equals("comiezo")){
            this.gamePanel = new GamePanel(fila-1, columna-1, this, "", "Test", socket1);
        
            try {
            gamePanel.testing(socket1,columna-1,fila-1);
        
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * Método principal que inicia la aplicación del cliente.
     *
     * @param args Argumentos de línea de comandos (no utilizados aquí).
     * @throws IOException Excepción en caso de error de E/S.
     */
    public static void main(String[] args) throws IOException {
        Socket socket4 = null;
        GamePanel gamepanel1 = null;
        Client client = new Client("test",socket4,0,0);
        client.connectToServer();
        client.start();
        client.run(gamepanel1,socket4);        
    }

    /**
     * Establece una conexión con el servidor.
     */
    public void connectToServer() {
        
        try {
            final Scanner sc = new Scanner(System.in); // Sirve para obtener la informacón que se encuentra en la terminal la cual fue escrita por el teclado
            System.out.println("Indique el nombre de usuario con el que se desea ingresar");//Captura del nombre de usuario
            String username = sc.nextLine();//Obtiene el usuario escrito
            
            socket = new Socket("127.0.0.1", 3000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(username);//Envia el username escrito
            out.flush();
            System.out.println("Connected!!");
                    
            char letter = (char) ('A' + ThreadLocalRandom.current().nextInt(0, 26));
            identifier = Character.toString(letter);

            out.println(identifier);
            out.flush();
            Client client = new Client("test",socket,0,0);
            GamePanel gamepanel1 = null;
            client.run(gamepanel1,socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Método que maneja la comunicación con el servidor.
     *
     * @param gamePanel El panel de juego asociado.
     * @param socket    El socket para la comunicación con el servidor.
     * @throws IOException Excepción en caso de error de E/S.
     */
    public void run(GamePanel gamePanel,Socket socket) throws IOException {
        
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
                String serverMessage;
            
                while ((socket.isConnected())) {
                    try{
                     
                    serverMessage = in.readLine(); 
                 
                    if (serverMessage != null) {
                        if (serverMessage.equals("Test")){
                            if (serverMessage != null) {
                                serverMessage = in.readLine(); 
                                
                                gamePanel.processServerMove(serverMessage);
                            }
                        }
                        if (serverMessage.equals("Pintar")){
                            if (serverMessage != null) {
                            
                                serverMessage = in.readLine(); 
                              
                                gamePanel.pinta_socket(serverMessage);
                            }
                        }
                        String[] parts = serverMessage.split(" ");
                        if (parts.length == 3 && parts[0].equals("Inicio")){
                            int columna = Integer.parseInt(parts[1]);
                            int fila = Integer.parseInt(parts[2]);
                            out.println("Primera prubea");
                            out.flush();
                            Client client = new Client("comiezo",socket,columna,fila);
                            break;
                        }
                    }
            
                    }catch (Exception E) {
                        E.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Método para enviar un movimiento al servidor.
     *
     * @param row     Fila de inicio del movimiento.
     * @param col     Columna de inicio del movimiento.
     * @param socket  El socket para la comunicación con el servidor.
     * @param newrow  Nueva fila después del movimiento.
     * @param newcol  Nueva columna después del movimiento.
     */
    public void sendMoveToServer(int row, int col, Socket socket,int newrow, int newcol) {
        
    try {
         
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String moveMessage = "MOVE " + row + " " + col + " " + newrow + " " + newcol; // Formato del mensaje de movimiento
        String testing= "Test";
        out.println(testing);
        out.flush();
        out.println(moveMessage);
        out.flush();
        
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}
    /**
     * Método para enviar una acción de pintura al servidor.
     *
     * @param pintar Valor que indica la acción de pintura.
     * @param socket El socket para la comunicación con el servidor.
     * @param row    Fila en la que se realiza la acción de pintura.
     * @param col    Columna en la que se realiza la acción de pintura.
     */
    public void sendpintar(int pintar,Socket socket,int row,int col) {
        try {
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String moveMessage1 = "MOVE " + pintar + " " + row + " " + col; // Formato del mensaje de movimiento
            String testing= "Pintar";
            out.println(testing);
            out.flush();
            out.println(moveMessage1);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

}

/**
 * Clase GamePanel que representa el panel de juego.
 */
public class GamePanel extends JPanel implements KeyListener {

    public LinkedList<Point2D.Double> points = new LinkedList<Point2D.Double>();
    public LinkedList<Line2D.Double> lines = new LinkedList<Line2D.Double>();
    public LinkedList<Line2D.Double> linescua = new LinkedList<Line2D.Double>();
    public LinkedList<Line2D.Double> linescuauser = new LinkedList<Line2D.Double>();
    public LinkedList<Line2D.Double> linescuarival = new LinkedList<Line2D.Double>();
    public Set<Object> lista = new HashSet<>();
    public Set<Object> coorcuadrado = new HashSet<>();
    public Set<Line2D.Double> paintedLines = new HashSet<>();
    public int currentRow = 0;
    public int currentCol = 0;
    public int NewRow;
    public int NewCol;
    public static int numRows = 8; // Número de filas predeterminado
    public static int numCols = 8; // Número de columnas predeterminado
    public int pointSize = 20;
    public Color selectedLineColor = Color.BLUE;
    public boolean isSelecting = false;
    public Line2D.Double selectedLine = null;
    public Set<Line2D.Double> connectedLines = new HashSet<Line2D.Double>();
    private Clip soundClip;
    private Client client;
    private Socket socket5;
    private int contadorcuauser = 0;
    private int contadorpuuser = 0;
    private int contadorpurival = 0;
    private int puntosUtilizados = 0;
    private int maxPointCount; // Declaración de variable
    private int numLineasVerticales =0;
    private int numLineasHorizontales=0;
    private JTextField jugador1TextField = new JTextField(20);
    private JTextField rivalTextField = new JTextField(20);




    // Método principal para ejecutar la aplicación
    public static void main(String[] args) throws IOException {
        Client.main(args);
    }

    /**
     * Método para realizar pruebas del panel de juego.
     *
     * @param socket4  Socket para la comunicación con el servidor.
     * @param columna  Número de columnas en el juego.
     * @param fila     Número de filas en el juego.
     * @throws Exception Excepción en caso de error.
     */
    public void testing(Socket socket4,int columna,int fila)throws Exception{
        
        JFrame frame = new JFrame();
        //Socket socket4 = null;
        GamePanel stage = new GamePanel(fila,columna,client,"","",socket4);
        frame.setBackground(new Color(47, 47, 47));
        frame.setResizable(true);
        frame.add(stage);
        //frame.add(new GamePanel(numRows, numCols));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setBounds(0, 0, 800, 600);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        stage.isSelecting = true;
        Point2D.Double startPoint = stage.points.get(0);
        Point2D.Double endPoint = stage.points.get(1);
        stage.selectedLine = new Line2D.Double(startPoint, endPoint);
        client.run(stage,socket4);
        
        }
    
        /**
     * Constructor de la clase GamePanel.
     *
     * @param numRow    Número de filas en el juego.
     * @param numCol    Número de columnas en el juego.
     * @param client    Cliente asociado a este panel.
     * @param message   Mensaje del servidor.
     * @param box       Nombre del jugador.
     * @param socket5   Socket para la comunicación con el servidor.
     */
    public GamePanel(int numRow, int numCol,Client client,String message,String box,Socket socket5) {
        this.socket5 = socket5;
        this.client=client;
        
        //Manejo del sistema
        if(box.equals("Test")){
            processServerMove(message);
        }
        else{
    
            this.client=client;
            this.numRows = numRow;
            this.numCols = numCol;
           

            
            setFocusable(true);
            addKeyListener(this);

            // Ajustar las dimensiones del panel (tamaño del GamePanel) a pantalla completa
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int screenWidth = gd.getDisplayMode().getWidth();
            int screenHeight = gd.getDisplayMode().getHeight();
            setPreferredSize(new Dimension(screenWidth, screenHeight));

            // Inicializar la lista de puntos disponibles para dibujar
            for (int i = 1; i <= numRow + 1; i++) {
                for (int j = 1; j <= numCol + 1; j++) { // Agregar una columna
                    points.add(new Point2D.Double(55 * j, i * 55)); // Agregar una fila
                }
            }

            // Crear un JPanel para las opciones
            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alinea los componentes a la derecha


            //Elementos visuales
            
            JButton backButton = new JButton("Nueva partida");
            JLabel jugador1Label = new JLabel("Jugador:");
            JLabel rivalLabel = new JLabel("Rival:");
            


            //Añadir elementos
            optionsPanel.add(backButton);
            optionsPanel.add(jugador1Label);
            optionsPanel.add(jugador1TextField);
            optionsPanel.add(rivalLabel);
            optionsPanel.add(rivalTextField);

 
            numLineasVerticales = (numCol + 1) * numRow;
            numLineasHorizontales = (numRow + 1) * numCol;
            maxPointCount = numLineasHorizontales+numLineasVerticales;

            // Agregar el panel de opciones a tu GamePanel
            this.setLayout(new BorderLayout());
            this.add(optionsPanel, BorderLayout.NORTH); // Agrega el panel en la esquina superior derecha
            // Asegura de que el panel de opciones sea visible
            optionsPanel.setVisible(true);

            /////////////////////////////Dejar por aparte
            Socket socket4 = null;
            new Client("",socket4,0,0).start();
            // Cargar el archivo de sonido
            try {
                File soundFile = new File("sounds/ping.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                soundClip = AudioSystem.getClip();
                soundClip.open(audioIn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

       
    }
    
    /**
     * Método para dibujar en el panel.
     *
     * @param graphics El contexto gráfico en el que se dibujará.
     */
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        Graphics2D g3 = (Graphics2D) graphics;
        Graphics2D g4 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4));

        g2.setColor(Color.BLUE);
        for (Line2D.Double l : lines) {
            g2.setColor(Color.ORANGE);
            if (paintedLines.contains(l)) {
                // Cambia el color de la línea para indicar que ya está pintada
                g2.setColor(Color.RED); // Puedes elegir otro color o estilo
            }
            g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
        }

        for (Point2D.Double p : points) {
            g2.setColor(Color.darkGray);
            g2.fillRoundRect((int) (p.x - (pointSize / 2)), (int) (p.y - (pointSize / 2)), pointSize, pointSize, pointSize, pointSize);
        }

        if (isSelecting && selectedLine != null) {
            
            g2.setColor(selectedLineColor);
            g2.drawLine((int) selectedLine.getX1(), (int) selectedLine.getY1(), (int) selectedLine.getX2(), (int) selectedLine.getY2());
                        
        }
        //g2.setColor(Color.GREEN);
        this.contadorcuauser = 0;
        if(contadorcuauser == 0){
            this.contadorcuauser = 1;
            g3.setColor(Color.GREEN);
        for (Line2D.Double m : linescuauser) {
            g3.drawLine((int) m.getX1(), (int) m.getY1(), (int) m.getX2(), (int) m.getY2());
        }

        
    }
    this.contadorcuauser = 1;
    if(contadorcuauser == 1){
        g4.setColor(Color.GRAY);
        for (Line2D.Double h : linescuarival) {
            g4.drawLine((int) h.getX1(), (int) h.getY1(), (int) h.getX2(), (int) h.getY2());
        }
    }

    }

    /**
     * Implementación del método de interfaz KeyListener: keyPressed.
     *
     * @param e Evento de tecla presionada.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER) {


            if (isSelecting && selectedLine != null) {
                String puntos = (" "+selectedLine.getP1()+selectedLine.getP2());
                if(lista.contains(puntos)){
                    System.out.println("No se puede pintar");
                }else{
                lines.add(selectedLine);
                connectedLines.add(selectedLine);
                //Nuevo código
                String puntos1 = (" "+selectedLine.getP1()+selectedLine.getP2());
                String puntosinverso = (" "+selectedLine.getP2()+selectedLine.getP1());
                lista.add(puntos1);
                lista.add(puntosinverso);
                puntosUtilizados += 1;

                isSelecting = false;
                selectedLine = null;
                //Reproducir el sonido de las conexiones
                playPingSound();
                
                client.sendpintar(keyCode,socket5,currentRow,currentCol);
                this.contadorcuauser = 0;
                verificarCuadrados();
            }
            } 
            else {
                isSelecting = true;
                Point2D.Double startPoint = points.get(currentRow * (numCols + 1) + currentCol);
   

                selectedLine = new Line2D.Double(startPoint, startPoint);
                client.sendpintar(keyCode,socket5,currentRow,currentCol);
            }
        } else if (isSelecting) {
            int newRow = currentRow;
            int newCol = currentCol;

            if (keyCode == KeyEvent.VK_LEFT && currentCol > 0) {
                newCol--;
            } else if (keyCode == KeyEvent.VK_RIGHT && currentCol < numCols) {
                newCol++;
            } else if (keyCode == KeyEvent.VK_UP && currentRow > 0) {
                newRow--;
            } else if (keyCode == KeyEvent.VK_DOWN && currentRow < numRows) {
                newRow++;
            }

            Point2D.Double currentPoint1 = points.get(currentRow * (numCols + 1) + currentCol);
            Point2D.Double newPoint1 = points.get(newRow * (numCols + 1) + newCol);

            selectedLine.setLine(currentPoint1, newPoint1);


            //Punto 0,0
            if(currentRow == 0 && currentCol == 0 && newRow == 0 && newCol == 0){
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
            client.sendMoveToServer(currentRow,currentCol,socket5,NewRow,NewCol);
            }
                else if(currentRow == 0 && newRow == 0 && currentCol == 0 && newCol == 1){
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
            client.sendMoveToServer(currentRow,currentCol-1,socket5,NewRow,NewCol);
            }
            else if(currentRow == newRow && currentCol < newCol ){
          
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
                client.sendMoveToServer(currentRow,currentCol,socket5,NewRow,NewCol-1);
            }
            //Fuera del mapa
            else if(currentRow == newRow && currentCol == newCol ){
              
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
                client.sendMoveToServer(currentRow,currentCol,socket5,NewRow,NewCol);
            }
            
            //Izquierda
            else if(currentRow == newRow && currentCol > newCol ){
                
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
                client.sendMoveToServer(currentRow,currentCol,socket5,NewRow,NewCol+1);
            }
            //Arriba
            else if(currentCol == newCol && currentRow>newRow ){
            
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
                client.sendMoveToServer(currentRow,currentCol,socket5,NewRow+1,NewCol);
            }
            //Abajo
            else if(currentCol == newCol && currentRow<newRow ){
         
                currentRow = newRow;
                currentCol = newCol;
                NewRow = newRow;
                NewCol = newCol;
                client.sendMoveToServer(currentRow,currentCol,socket5,NewRow-1,NewCol);
            }
            
        }
        repaint();

    }

    /**
     * Método para reproducir el sonido "ping.wav".
     */
    private void playPingSound() {
        try {
            if (soundClip != null) {
                soundClip.stop();
                soundClip.setFramePosition(0); // Reiniciar al principio
                soundClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    /**
     * Método para procesar el movimiento del servidor.
     *
     * @param message Mensaje del servidor.
     */
    public void processServerMove(String moveMessage) {
        String[] parts = moveMessage.split(" ");
        if (parts.length == 5 && parts[0].equals("MOVE")) {
            
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);
            int newrow = Integer.parseInt(parts[3]);
            int newcol = Integer.parseInt(parts[4]);
            
        
            Point2D.Double startPoint = points.get(row * (numCols + 1) + col);
            selectedLine = new Line2D.Double(startPoint, startPoint);
            
            Point2D.Double currentPoint = points.get(row * (numCols + 1) + col);
            Point2D.Double newPoint = points.get(newrow * (numCols + 1) + newcol);
            selectedLine.setLine(newPoint, currentPoint);
            repaint(); 
            
        }
    }
    /**
     * Método para pintar una línea en el panel basado en un mensaje recibido del servidor.
     *
     * @param moveMessage El mensaje que contiene la información de la línea a pintar.
     */
    public void pinta_socket(String moveMessage) {
        
        String[] parts = moveMessage.split(" ");
        if (parts.length == 4 && parts[0].equals("MOVE")) {


            int key = Integer.parseInt(parts[1]);
            int row = Integer.parseInt(parts[2]);
            int col = Integer.parseInt(parts[3]);

            if (key == KeyEvent.VK_ENTER) {


            if (isSelecting && selectedLine != null) {
                String puntos = (" "+selectedLine.getP1()+selectedLine.getP2());
                if(lista.contains(puntos)){
                    System.out.println("No se puede pintar");
                }else{
                    
                lines.add(selectedLine);
                connectedLines.add(selectedLine);
                //Nuevo código
                String puntos1 = (" "+selectedLine.getP1()+selectedLine.getP2());
                String puntosinverso = (" "+selectedLine.getP2()+selectedLine.getP1());
                lista.add(puntos1);
                lista.add(puntosinverso);

                isSelecting = false;
                selectedLine = null;
                //Reproducir el sonido de las conexiones
                playPingSound();
                this.contadorcuauser = 1;
                puntosUtilizados += 1;
                verificarCuadrados();
            }
            } 
            else {
                isSelecting = true;
                Point2D.Double startPoint = points.get(currentRow * (numCols + 1) + currentCol);

                selectedLine = new Line2D.Double(startPoint, startPoint);
            }
        }
        }
    }
    /**
     * Método para verificar cuadrados formados por las líneas en el panel.
     */
    public void verificarCuadrados() {
        // Recorrer el array de líneas
        for (int i = 0; i < lines.size(); i++) {
            Line2D.Double linea1 = lines.get(i);
    
            for (int j = i + 1; j < lines.size(); j++) {
                Line2D.Double linea2 = lines.get(j);
    
                for (int k = j + 1; k < lines.size(); k++) {
                    Line2D.Double linea3 = lines.get(k);
    
                    for (int l = k + 1; l < lines.size(); l++) {
                        Line2D.Double linea4 = lines.get(l);
    
                        // Verificar si estas cuatro líneas forman un cuadrado
                        if (esCuadrado(linea1, linea2, linea3, linea4)) {
                            if(this.contadorcuauser==0){
                            linescuauser.add(linea1);
                            linescuauser.add(linea2);
                            linescuauser.add(linea3);
                            linescuauser.add(linea4);
                            
                            this.contadorpuuser = contadorpuuser+1;
                            jugador1TextField.setText(String.valueOf(""));
                            jugador1TextField.setText(String.valueOf(contadorpuuser));
                             
                            }else if(this.contadorcuauser==1){
                            linescuarival.add(linea1);
                            linescuarival.add(linea2);
                            linescuarival.add(linea3);
                            linescuarival.add(linea4);
                            this.contadorpurival = contadorpurival+1;
                            rivalTextField.setText(String.valueOf(""));
                            rivalTextField.setText(String.valueOf(contadorpurival));
                            }
                            // Realiza acciones adicionales aquí si se encuentra un cuadrado
                        }
                        
                    }
                }
            }
        }
        if (puntosUtilizados >= maxPointCount) {
            System.out.println("No se pueden hacer más líneas.");
            parartodo();
            // Realiza acciones adicionales si es necesario cuando no se pueden hacer más líneas
        }
        
    }
    /**
     * Comprueba si las líneas dadas forman un cuadrado.
     *
     * @param linea1 La primera línea.
     * @param linea2 La segunda línea.
     * @param linea3 La tercera línea.
     * @param linea4 La cuarta línea.
     * @return `true` si las líneas forman un cuadrado, de lo contrario `false`.
     */
    private boolean esCuadrado(Line2D.Double linea1, Line2D.Double linea2, Line2D.Double linea3, Line2D.Double linea4) {

        double longitud1 = linea1.getP1().distance(linea1.getP2());
        double longitud2 = linea2.getP1().distance(linea2.getP2());
        double longitud3 = linea3.getP1().distance(linea3.getP2());
        double longitud4 = linea4.getP1().distance(linea4.getP2());

        String puntoslista69 = (" "+linea1.getP1()+linea1.getP2()+(" "+linea2.getP1()+linea2.getP2()))+(" "+linea3.getP1()+linea3.getP2())+(" "+linea4.getP1()+linea4.getP2());


        boolean cruzan12 = linea1.intersectsLine(linea2);
        boolean cruzan23 = linea2.intersectsLine(linea3);
        boolean cruzan34 = linea3.intersectsLine(linea4);
        boolean cruzan41 = linea4.intersectsLine(linea1);

        if(!coorcuadrado.contains(puntoslista69)){

        if((cruzan12 && cruzan23 && cruzan34 && cruzan41)){
            
            boolean longitud = ((longitud1 == longitud2) && (longitud2 == longitud3) && (longitud3 == longitud4));

            
            double tolerancia = 1.0; // 1 grado

            // Verificar si los ángulos son aproximadamente 90 grados
            boolean cruzanEnUnPunto = cruzan12 && cruzan23 && cruzan34 && cruzan41;

            // Verificar si todas las intersecciones son puntos únicos (evitar líneas superpuestas)
            boolean interseccionesUnicas = getIntersectionsCount(linea1, linea2, linea3, linea4) == 4;

            boolean angulos90Grados = Math.abs(calcularAnguloEntreLineas(linea1, linea2) - 90) < tolerancia &&
                Math.abs(calcularAnguloEntreLineas(linea2, linea3) - 90) < tolerancia &&
                Math.abs(calcularAnguloEntreLineas(linea3, linea4) - 90) < tolerancia &&
                Math.abs(calcularAnguloEntreLineas(linea4, linea1) - 90) < tolerancia;

            if (cruzanEnUnPunto && interseccionesUnicas) {

            String puntoslista1 = (" "+linea1.getP1()+linea1.getP2()+(" "+linea2.getP1()+linea2.getP2()))+(" "+linea3.getP1()+linea3.getP2())+(" "+linea4.getP1()+linea4.getP2());
            
            coorcuadrado.add(puntoslista1);
            return (longitud1 == longitud2) && (longitud2 == longitud3) && (longitud3 == longitud4);
            }
        }else{
            return(false);
        }
        
    }
    return(false);
    }

    /**
     * Calcula el ángulo entre dos líneas.
     *
     * @param linea1 La primera línea.
     * @param linea2 La segunda línea.
     * @return El ángulo en grados entre las dos líneas.
     */
    private double calcularAnguloEntreLineas(Line2D.Double linea1, Line2D.Double linea2) {
        // Obtener los ángulos de ambas líneas utilizando la función Math.atan2
        double angulo1 = Math.atan2(linea1.getY2() - linea1.getY1(), linea1.getX2() - linea1.getX1());
        double angulo2 = Math.atan2(linea2.getY2() - linea2.getY1(), linea2.getX2() - linea2.getX1());
    
        // Convertir los ángulos a grados
        angulo1 = Math.toDegrees(angulo1);
        angulo2 = Math.toDegrees(angulo2);
    
        // Calcular la diferencia de ángulos (puede ser negativa o positiva)
        double diferenciaAngulos = angulo1 - angulo2;
    
        // Asegurarse de que la diferencia esté en el rango de -180 a 180 grados
        if (diferenciaAngulos > 180) {
            diferenciaAngulos -= 360;
        } else if (diferenciaAngulos < -180) {
            diferenciaAngulos += 360;
        }
        // Devolver el valor absoluto de la diferencia de ángulos
        return Math.abs(diferenciaAngulos);
    }

    /**
     * Obtiene el número de intersecciones únicas entre un conjunto de líneas.
     *
     * @param lines Un conjunto de líneas.
     * @return El número de intersecciones únicas entre las líneas.
     */    
    private int getIntersectionsCount(Line2D.Double... lines) {
        // Contar el número de intersecciones únicas entre las líneas
        Set<Point2D.Double> intersections = new HashSet<>();
    
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                Line2D.Double line1 = lines[i];
                Line2D.Double line2 = lines[j];
    
                if (line1.intersectsLine(line2)) {
                    // Obtener el punto de intersección
                    double x1 = line1.getX1();
                    double y1 = line1.getY1();
                    double x2 = line1.getX2();
                    double y2 = line1.getY2();
                    double x3 = line2.getX1();
                    double y3 = line2.getY1();
                    double x4 = line2.getX2();
                    double y4 = line2.getY2();
    
                    double denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
    
                    if (denom != 0) {
                        double px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denom;
                        double py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denom;
                        intersections.add(new Point2D.Double(px, py));
                    }
                }
            }
        }
    
        return intersections.size();
    }
    /**
     * Detiene la aplicación y sale del programa.
     */
    public static void parartodo() {
            System.exit(0);    
    }
    
}