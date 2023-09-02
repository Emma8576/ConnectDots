
<<<<<<<HEAD:Cliente.java

>>>>>>>efa5ba53b5dac48b218a060c7d32341d60136957:ClientInterface.java

/**
 * Instituto tecnológico de Costa Rica
 * Curso: ALgoritmos y Estructura de Datos
 * Proyecto #1 - Implementación del Juego Connect Dots
 * Estudiantes: Bryan Monge, Emmanuel Calvo y Enrique Villalobos
 * Grupo #1
 * Profesor: Leonardo Araya
 * Ingeniería en Computadores
 * Periodo II Semestre 2023
 */

/**
 * Importación de librerías de diseño y comunicación vía Socket
 */
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

/**
 * Definición de la clase que contiene la integración del diseño de la interfaz,
 * además del comportamiento de los dos botones disponibles en la interfaz,
 * y el empaquetamiento del mensaje para ser mostrado a través del servidor
 * hacia la computadora receptora
 */
public class Interfaz extends JFrame implements ActionListener {

    // Definición de las variables contenidas en la interfaz
    private JLabel label1;
    private JPanel gamePanel;
    private JLabel label2;
    private JLabel label3;
    private JLabel nickname;
    JButton boton1;
    JButton boton2;

    /**
     * Constructor de la clase InterfaceCliente.
     * Configuración del diseño de la interfaz
     */
    public Interfaz() {
        setLayout(null);
        Font font = new Font("Arial", Font.BOLD, 30);
        Font font2 = new Font("Arial", Font.BOLD, 15);

        // Personalizar la barra de título
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Instrucción que el usuario debe realizar #1
        label3 = new JLabel("Username:");
        label3.setFont(font2);
        label3.setForeground(Color.WHITE);
        label3.setBounds(600, 10, 200, 30);
        add(label3);

        // Espacio para escribir el username
        String nick_usuario = JOptionPane.showInputDialog("Enter your Nickname: ");
        JLabel n_nick = new JLabel(nick_usuario);
        n_nick.setBounds(690, 10, 200, 30);
        n_nick.setFont(font2);
        n_nick.setForeground(Color.LIGHT_GRAY);
        add(n_nick);
        nickname = new JLabel();
        nickname.setText(nick_usuario);
        add(nickname);

        // Se crea Botón de cerrar

        boton1 = new JButton("Exit");
        boton1.setBounds(300, 270, 200, 40);
        boton1.setFont(font);
        boton1.setMargin(new Insets(0, 0, 0, 0));
        boton1.setFocusPainted(false);
        add(boton1);
        boton1.addActionListener(this);

        // Se crea el botón de Play
        boton2 = new JButton("Play");
        boton2.setBounds(300, 170, 200, 40);
        boton2.setFont(font);
        boton2.setMargin(new Insets(0, 0, 0, 0));
        boton2.setFocusPainted(false);
        add(boton2);
        boton2.addActionListener(this);

        // Se crea el panel para el juego
        gamePanel = new JPanel();
        gamePanel.setBounds(0, 40, 800, 510);
        gamePanel.setBackground(Color.WHITE);
        add(gamePanel);

        /* Se crea etiqueta encabezado */
        label1 = new JLabel("Connect Dots");
        label1.setBounds(0, 0, 800, 40);
        label1.setBackground(Color.DARK_GRAY);
        label1.setOpaque(true);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);
        label1.setFont(font);
        add(label1);

        /* Se crea etiqueta de bienvenida #2 */
        label2 = new JLabel("Bienvenido");
        label2.setBounds(0, 40, 800, 50);
        label2.setBackground(Color.gray);
        label2.setOpaque(true);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setForeground(Color.WHITE);
        label2.setFont(font);
        add(label2);
    }

    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == boton1) {
            // Si el evento proviene del botón1, se cierra la aplicación.
            System.exit(0);
        }
        if (evento.getSource() == boton2) {

            // Ocultar los elemenetos de la pantalla principal
            label2.setVisible(false);
            boton1.setVisible(false);
            boton2.setVisible(false);

            // Se crea el contenido para la pantalla de juego
            JPanel gameContent = new JPanel();

            // Agregar botón de salida
            Font font = new Font("Arial", Font.BOLD, 30);
            JButton backButton = new JButton("Back");
            backButton.setBounds(0, 15, 200, 40);
            backButton.setFont(font);
            backButton.setFocusPainted(false);
            add(backButton);
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Se muestran los elementos del menu principal
                    label2.setVisible(true);
                    boton1.setVisible(true);
                    boton2.setVisible(true);

                    gamePanel.removeAll();

                    revalidate();
                    repaint();
                }
            });

            // Se agrea contenido a la nueva pantalla
            gameContent.add(backButton);

            // Se agrega el panel de contenido del juego
            gamePanel.add(gameContent);

            // Actualizar interfaz
            revalidate();
            repaint();
        }

    }<<<<<<<<HEAD:Interfaz.java

}

 public static void main(String args[]) {
        Interfaz interface1 = new Interfaz();
        interface1.setBounds(0, 0, 800, 600);   //tamaño de interfaz
========

    public static void main(String args[]) {
        Cliente interface1 = new Cliente();
        interface1.setBounds(0, 0, 800, 600); // tamaño de interfaz
>>>>>>>> 18a808a929f8b87005ad13e8c274e5fd5ebfd88b:ClientInterface.java
        interface1.setVisible(true);
        // Maximizar la ventana en pantalla completa
        interface1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Hacer que la ventana ocupe toda la pantalla sin bordes
        interface1.setUndecorated(true);
        interface1.setLocationRelativeTo(null);
        interface1.setResizable(false);

    }
}