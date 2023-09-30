import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;

/**
 * Esta clase representa la interfaz gráfica de usuario para el cliente del juego.
 */
public class InterfaceClient extends JFrame implements ActionListener {
    public static final int RowsValue = 0;
    public static final int ColsValue = 0;
    private JLabel label1, label2, label3, label4;
    private JButton close, play;
    private JTextField text;
    private ImageIcon imagen1, imagen2;
    public static String texto = "";
    private Clip backgroundMusic;
    private boolean isMusicPlaying = false;

    /**
     * Constructor de la clase InterfaceClient que inicializa la interfaz de usuario.
     */
    public InterfaceClient() {
        // Inicializa el reproductor de música de fondo
        try {
            File musicFile = new File("sounds/intro.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Agrega un controlador de eventos para detectar cuándo termina la canción y repetirla
        backgroundMusic.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                    startBackgroundMusic(); // Reproduce la música nuevamente cuando termine
                }
            }
        });

        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hola, soy Bryan");
        getContentPane().setBackground(new Color(0, 0, 0));

    
        // Establecer imagen de fondo (asegúrate de tener la imagen en la ruta especificada)

        //Establecer logo ConnectDots 
        imagen1 = new ImageIcon("images/dots.png");
        label1 = new JLabel(imagen1);
        label1.setBounds(450, 1, 800, 512);
        add(label1);

        imagen2 = new ImageIcon("images/happy_alien-removebg-preview (2) (1).png");
        label2 = new JLabel(imagen2);
        label2.setBounds(0,40,512,600);
        add(label2);


        close = new JButton("Close game");
        close.setBounds(550, 500, 220, 100);
        close.setBackground(new Color(65, 75, 178));
        close.setFont(new Font("Andale Mono", 3, 30));
        close.setForeground(new Color(244, 236, 247));
        close.addActionListener(this);
        add(close);

        play = new JButton("Start game");
        play.setBounds(900, 500, 220, 100);
        play.setBackground(new Color(143, 209, 79));
        play.setFont(new Font("Andale Mono", 3, 30));
        play.setForeground(new Color(244, 236, 247));
        play.addActionListener(this);
        add(play);
    }
    /**
     * Controla las acciones realizadas por el usuario, como cerrar el juego o comenzar el juego.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            System.exit(0);
            playSound("sounds/clic.wav");

        }
        if (e.getSource() == play) {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new InterfaceClient_next();
                }
            });
            playSound("sounds/clic.wav");

            startBackgroundMusic();
        }
    }
    /**
     * Reproduce un sonido a partir de un archivo de sonido.
     *
     * @param filePath La ruta del archivo de sonido que se va a reproducir.
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
    
      
    /**
     * Inicia la música de fondo si no se está reproduciendo actualmente.
     */
    private void startBackgroundMusic() {
        if (!isMusicPlaying && backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            isMusicPlaying = true;
        }
    }

    /**
     * Método principal que crea y configura la interfaz gráfica del cliente.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InterfaceClient formulario = new InterfaceClient();
                formulario.setExtendedState(JFrame.MAXIMIZED_BOTH);
                formulario.setUndecorated(true);
                formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                formulario.setVisible(true);
                formulario.setResizable(false);
                formulario.setLocationRelativeTo(null);

                formulario.startBackgroundMusic(); // Inicia la música de fondo
            }
        });
    }
}
