import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
// Definición de la clase Node que representa un nodo en una lista enlazada
class Node<T> {
    T data;
    Node<T> next;

    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

// Definición de la clase LinkedList que implementa una lista enlazada genérica
class LinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
        head = null;
        tail = null;
    }

    // Método para agregar un elemento al final de la lista
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

    // Método para obtener un elemento en un índice específico
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

    // Método para obtener el tamaño de la lista
    public int size() {
        int count = 0;
        Node<T> current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    // Clase interna LinkedListIterator que implementa un iterador para la lista enlazada
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

class Client extends Thread {
    private String identifier; // Almacenar el nuevo identificador de jugador
    private Socket socket;

    public static void main(String[] args) {
        Client client = new Client();
        client.connectToServer();
        client.start();
    }

    public void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 3000);
            System.out.println("Connected!!");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            char letter = (char) ('A' + ThreadLocalRandom.current().nextInt(0, 26));
            identifier = Character.toString(letter);

            out.println(identifier);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }

            System.out.println("Disconnected from server");

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


public class GamePanel extends JPanel implements KeyListener {

    public LinkedList<Point2D.Double> points = new LinkedList<Point2D.Double>();
    public LinkedList<Line2D.Double> lines = new LinkedList<Line2D.Double>();
    public int currentRow = 0;
    public int currentCol = 0;
    public static int numRows = 9; // Número de filas predeterminado
    public static int numCols = 9; // Número de columnas predeterminado
    public int pointSize = 20;
    public Color selectedLineColor = Color.BLUE;
    public boolean isSelecting = false;
    public Line2D.Double selectedLine = null;
    public Set<Line2D.Double> connectedLines = new HashSet<Line2D.Double>();
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    private Clip soundClip;

    // Método principal para ejecutar la aplicación
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePanel stage = new GamePanel(numRows, numCols);

        frame.setBackground(new Color(47, 47, 47));
        frame.setResizable(true);
        frame.add(stage);
        frame.add(new GamePanel(numRows, numCols));
        frame.setBounds(0, 0, 800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        stage.isSelecting = true;
        Point2D.Double startPoint = stage.points.get(0);
        Point2D.Double endPoint = stage.points.get(1);
        stage.selectedLine = new Line2D.Double(startPoint, endPoint);

        // Inicia el cliente en un hilo separado
        new Client().start();
    }

    // Constructor de la clase GamePanel
    public GamePanel(int numRows, int numCols) {

        setFocusable(true);
        addKeyListener(this);

        this.numRows = numRows;
        this.numCols = numCols;

        // Ajustar las dimensiones del panel (tamaño del GamePanel)
        int panelWidth = 800; // Tamaño deseado en anchura
        int panelHeight = 600; // Tamaño deseado en altura
    setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Inicializar la lista de puntos disponibles para dibujar
        for (int i = 1; i <= numRows + 1; i++) {
            for (int j = 1; j <= numCols + 1; j++) { // Agregar una columna
                points.add(new Point2D.Double(55 * j, i * 55)); // Agregar una fila
            }
        }

        // Crear un JPanel para las opciones
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alinea los componentes a la derecha

        // Agregar componentes de opciones al panel de opciones
        // Ejemplo: Agregar un botón
        

        // Agregar el panel de opciones a tu GamePanel
        this.setLayout(new BorderLayout());
        this.add(optionsPanel, BorderLayout.NORTH); // Agrega el panel en la esquina superior derecha

        // Asegura de que el panel de opciones sea visible
        optionsPanel.setVisible(true);

        JButton backButton = new JButton("Nueva partida");
        backButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(GamePanel.this);
                currentFrame.dispose();  // Cierra el frame actual

                InterfaceClient_next newInterface = new InterfaceClient_next();
                newInterface.setExtendedState(JFrame.MAXIMIZED_BOTH);
                newInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                newInterface.setVisible(true);
                newInterface.setResizable(false);
                newInterface.setLocationRelativeTo(null);
            }
        });
        


        optionsPanel.add(backButton);
        new Client().start();
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

    

    // Método para dibujar en el panel
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4));

        g2.setColor(Color.BLUE);
        for (Line2D.Double l : lines) {
            g2.setColor(Color.ORANGE);
            g2.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
        }

        for (Point2D.Double p : points) {
            g2.setColor(Color.darkGray);
            g2.fillRoundRect((int) (p.x - (pointSize / 2)), (int) (p.y - (pointSize / 2)), pointSize, pointSize,
                    pointSize, pointSize);
        }

        if (isSelecting && selectedLine != null) {
            g2.setColor(selectedLineColor);
            g2.drawLine((int) selectedLine.getX1(), (int) selectedLine.getY1(), (int) selectedLine.getX2(),
                    (int) selectedLine.getY2());
        }
    }

    // Método para manejar eventos de teclado
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER) {
            if (isSelecting && selectedLine != null) {
                lines.add(selectedLine);
                connectedLines.add(selectedLine);

                isSelecting = false;
                selectedLine = null;

                //Reproducir el sonido de las conexiones
                playPingSound();
            } else {
                isSelecting = true;
                Point2D.Double startPoint = points.get(currentRow * (numCols + 1) + currentCol);
                selectedLine = new Line2D.Double(startPoint, startPoint);
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

            Point2D.Double currentPoint = points.get(currentRow * (numCols + 1) + currentCol);
            Point2D.Double newPoint = points.get(newRow * (numCols + 1) + newCol);

            selectedLine.setLine(currentPoint, newPoint);

            currentRow = newRow;
            currentCol = newCol;
        }

        repaint();
    }

    // Método para reproducir el sonido "ping.wav"
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
}