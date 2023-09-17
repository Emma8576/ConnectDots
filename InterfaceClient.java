import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;

public class InterfaceClient extends JFrame implements ActionListener {
    public static final int RowsValue = 0;
    public static final int ColsValue = 0;
    private JLabel label1, label2, label3, label4;
    private JButton close, play;
    private JTextField text;
    private ImageIcon imagen;
    public static String texto = "";
    private Clip backgroundMusic;
    private boolean isMusicPlaying = false;

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
        imagen = new ImageIcon("images/Logo.png");
        label1 = new JLabel(imagen);
        label1.setBounds(40, 40, 512, 512);
        add(label1);

        //Establecer logo ConnectDots 
        imagen = new ImageIcon("images/dots.png");
        label1 = new JLabel(imagen);
        label1.setBounds(550, 1, 800, 512);
        add(label1);

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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            System.exit(0);
        }
        if (e.getSource() == play) {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new InterfaceClient_next();
                }
            });
            startBackgroundMusic();
        }
    }

    private void startBackgroundMusic() {
        if (!isMusicPlaying && backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            isMusicPlaying = true;
        }
    }

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
