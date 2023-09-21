import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Clase que almacena la configuración visual y de audio de la interfaz de configuración
 * Se crean los elementos que contienen el canva(labels, botones,...)
 */
public class InterfaceClient_next extends JFrame implements ActionListener {
    private JLabel num_rows, num_columns, hm_players, title;
    private JButton back, next, plus;
    private JTextArea hm_rows, hm_columns, player_name;


    /**
     * Configuración de los elementos que contiene la interfaz
     */
    public InterfaceClient_next() {
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hola, soy Bryan");
        getContentPane().setBackground(new Color(119, 147, 180));

        // Configurar la ventana
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        title = new JLabel("Connect Dots");
        title.setBounds(450, 40, 500, 60);
        title.setBackground(new Color(119, 147, 180));
        title.setFont(new Font("Andale Mono", 3, 65));
        add(title);

        plus = new JButton("+");
        plus.setBounds(780, 203,70, 40);
        plus.setBackground(new Color(171, 219, 227));
        plus.setFont(new Font("Andale Mono", 3, 20));
        plus.setForeground(Color.BLACK);
        plus.setFocusPainted(false);
        plus.addActionListener(this);
        add(plus);

        hm_players = new JLabel("Please enter the initial of the player's name:");
        hm_players.setBounds(40, 200, 800, 50);
        hm_players.setBackground(new Color(119, 147, 180));
        hm_players.setFont(new Font("Andale Mono", 3, 30));
        add(hm_players);

        player_name = new JTextArea();
        player_name.setBounds(670, 200, 70, 45);
        player_name.setBackground(new Color(171, 219, 227));
        player_name.setFont(new Font("Andale Mono", 3, 35));
        player_name.setForeground(new Color(255, 255, 255));
        add(player_name);

        num_rows = new JLabel("Enter how many rows:");
        num_rows.setBounds(40, 290, 800, 50);
        num_rows.setBackground(new Color(119, 147, 180));
        num_rows.setFont(new Font("Andale Mono", 3, 30));
        add(num_rows);

        hm_rows = new JTextArea();
        hm_rows.setBounds(370, 290, 50, 45);
        hm_rows.setBackground(new Color(171, 219, 227));
        hm_rows.setFont(new Font("Andale Mono", 3, 35));
        hm_rows.setForeground(new Color(255, 255, 255));
        add(hm_rows);

        num_columns = new JLabel("Enter how many columns:");
        num_columns.setBounds(40, 380, 800, 50);
        num_columns.setBackground(new Color(119, 147, 180));
        num_columns.setFont(new Font("Andale Mono", 3, 30));
        add(num_columns);

        hm_columns = new JTextArea();
        hm_columns.setBounds(430, 380, 50, 45);
        hm_columns.setBackground(new Color(171, 219, 227));
        hm_columns.setFont(new Font("Andale Mono", 3, 35));
        hm_columns.setForeground(new Color(255, 255, 255));
        add(hm_columns);

        back = new JButton("Back");
        back.setBounds(400, 550, 220, 100);
        back.setBackground(new Color(65, 75, 178));
        back.setFont(new Font("Andale Mono", 3, 30));
        back.setForeground(new Color(244, 236, 247));
        back.addActionListener(this);
        add(back);

        next = new JButton("Continue");
        next.setBounds(750, 550, 220, 100);
        next.setBackground(new Color(143, 209, 79));
        next.setFont(new Font("Andale Mono", 3, 30));
        next.setForeground(new Color(244, 236, 247));
        next.addActionListener(this);
        add(next);
    }
    /**
     * Método principal que inicializa la configuración de pantalla
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfaceClient_next();
            }
        });
    }
    /**
     * Método principal que almacena el comportamiento de los botones al ser presionados
     * @param back Devuelve al usuario a la ventana de bienvenida
     * @param next Inicializa la ventana de juego preiamente configurada por el usuario 
     */
    @Override
    public void  actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            InterfaceClient formulario = new InterfaceClient();
            formulario.setExtendedState(JFrame.MAXIMIZED_BOTH);
            formulario.setUndecorated(true);
            formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            formulario.setVisible(true);
            formulario.setResizable(false);
            formulario.setLocationRelativeTo(null);
            //sonido de clic al presionar los botones
            playSound("sounds/clic.wav");
        }

        if (e.getSource() == next) {
            try{
                
                int numRows = Integer.parseInt(hm_rows.getText());// variable que almacena el número de filas configurada por el usuario
                int numCols = Integer.parseInt(hm_columns.getText()); // variable que almacena el número de columnas configurada por el usuario

                JFrame frame = new JFrame();
                GamePanel stage = new GamePanel(numRows, numCols);//Método que envía la configuración de usuario a la representación gráfica en GamePanel

                frame.setBackground(new Color(47, 47, 47));
                frame.setResizable(true);
                frame.add(stage);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                stage.isSelecting = true;
                Point2D.Double startPoint = stage.points.get(0); //obtiene el primer punto de la lista points en el objeto stage y lo asigna a la variable startPoint
                Point2D.Double endPoint = stage.points.get(1);//obtiene el segundo punto de la lista points en el objeto stage y lo asigna a la variable endPoint.
                stage.selectedLine = new Line2D.Double(startPoint, endPoint);
            }catch(NumberFormatException ex){ //control de error
                JOptionPane.showMessageDialog(null, "Try again ");
            }
        }
        //música de fondo
        playSound("sounds/clic.wav");
    }
    /**
     * @param playSound Método que mantiene de fondo música en reproducción
     */
    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}