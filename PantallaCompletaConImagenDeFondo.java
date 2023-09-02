import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaCompletaConImagenDeFondo extends JFrame {
    public PantallaCompletaConImagenDeFondo() {
        // Configurar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Elimina la decoración de la ventana (bordes, barra de título, etc.)
        setResizable(false); // Impide que la ventana sea redimensionable

        // Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);

        // Crear un panel para colocar los componentes
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo
                ImageIcon imageIcon = new ImageIcon("images/labelsfondo.jpg"); // Asegúrate de que la imagen existe en
                                                                               // el directorio "images"
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // Desactivar el diseño automático del panel

        // Crear botón "Close"
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(50, 50, 100, 30);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Crear botón "Next"
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(200, 50, 100, 30);

        // Agregar los botones al panel
        panel.add(closeButton);
        panel.add(nextButton);

        // Agregar el panel a la ventana
        add(panel);

        // Mostrar la ventana en pantalla completa
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PantallaCompletaConImagenDeFondo();
        });
    }
}
