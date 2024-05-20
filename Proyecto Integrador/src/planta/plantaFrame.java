package planta;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class plantaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private plantaMetodos metodos; // Instancia de la clase plantaMetodos.
	private JPanel contentPane;
	private JTextField textValorP;
	private JTextField textValorQ;
	private JTextField textFecha;
	private JTextField textHumedad;
	private JTextField textAltura;
	private JTextField textVoltaje;
	private JTextArea AreaDatos;

	String fecha;
	String humedad;
	float altura;
	float voltaje;
	BigInteger p;
	BigInteger q;
	private JTextField textModuloN;
	private JTextField textTot;
	private JTextField textLlaveE;
	private JTextField textLlaveD;
	static plantaMetodos memMetodos = new plantaMetodos();

	// Ejecutar Frame.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					memMetodos.iniciarSesion();
					plantaFrame frame = new plantaFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Crear Frame.
	public plantaFrame() {
		setBackground(new Color(100, 204, 0));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\52773\\Downloads\\plant.ico"));
		setTitle("Circuitos Verdes");
		setResizable(false);

		metodos = new plantaMetodos();
		encriptadoRSA rsa = new encriptadoRSA(8); // Instancia de la clase encriptadoRSA.

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setBounds(0, 0, 429, 119);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 426, 119);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(0, 0, 426, 119);
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\52773\\Downloads\\Header.png"));
		panel_1.add(lblNewLabel_3);

		JLabel lblNewLabel = new JLabel("Valor de P:");
		lblNewLabel.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 137, 80, 14);
		contentPane.add(lblNewLabel);

		textValorP = new JTextField();
		textValorP.setFont(new Font("Consolas", Font.PLAIN, 14));
		textValorP.setBounds(124, 137, 86, 20);
		contentPane.add(textValorP);
		textValorP.setColumns(10);

		JLabel lblValorDeQ = new JLabel("Valor de Q:");
		lblValorDeQ.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblValorDeQ.setBounds(235, 134, 80, 20);
		contentPane.add(lblValorDeQ);

		textValorQ = new JTextField();
		textValorQ.setFont(new Font("Consolas", Font.PLAIN, 14));
		textValorQ.setColumns(10);
		textValorQ.setBounds(338, 137, 86, 20);
		contentPane.add(textValorQ);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnGuardar.setEnabled(false);
		// Acción de guardar.
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					// Obtenemos y guardamos texto de labels.
					fecha = textFecha.getText();
					humedad = textHumedad.getText();
					altura = Float.parseFloat(textAltura.getText());
					voltaje = Float.parseFloat(textVoltaje.getText());
					// Método guardar en plantaMetodos.java
					metodos.guardarDatos(fecha, humedad, altura, voltaje);
					// Escribir en archivo txt.
					String contenidoArchivo = metodos
							.escribir("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt");
					AreaDatos.setText(contenidoArchivo);
					JOptionPane.showMessageDialog(null, "Datos guardados con exito!");

				} catch (NumberFormatException a) {

					JOptionPane.showMessageDialog(null, "Error, uno de los datos está vacio o mal!");

				}

			}
		});

		JButton btnDescifrar = new JButton("Descifrar");
		btnDescifrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnDescifrar.setEnabled(false);

		JButton btnCifrar = new JButton("Cifrar");
		btnCifrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCifrar.setEnabled(false);
		// Accion de Cifrar.
		btnCifrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// Buffer lector del archivo txt.
					BufferedReader reader = new BufferedReader(new FileReader(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt"));
					String linea;
					StringBuilder contenido = new StringBuilder();
					StringBuilder contenidoEnString = new StringBuilder();
					// Cuando se termine la cadena.
					while ((linea = reader.readLine()) != null) {
						contenido.append(linea).append("\n");
					}
					reader.close();
					// Metodo encriptado en encriptadoRSA.java
					BigInteger[] contenidoEncriptado = rsa.encripta(contenido.toString());
					// Guardar el contenido encriptado en archivo txt.
					// Escritor para el archivo txt.
					FileWriter writer = new FileWriter(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlantaEncriptados.txt");
					for (BigInteger bigInt : contenidoEncriptado) {
						writer.write(bigInt.toString(16) + "\n"); // Guardar como hexadecimal
						contenidoEnString.append(bigInt.toString(16)).append("\n");
					}
					writer.close(); // Cierre del lector.
					AreaDatos.setText(contenidoEnString.toString());
					btnDescifrar.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Archivo encriptado con éxito!");

				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Error al encriptar el archivo: " + x.getMessage());
				}

			}
		});
		btnCifrar.setBounds(161, 523, 121, 23);
		contentPane.add(btnCifrar);
		// Accion de Decifrar.
		btnDescifrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Leer el archivo encriptado.
					BufferedReader reader = new BufferedReader(new FileReader(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlantaEncriptados.txt"));
					String linea;
					List<BigInteger> contenidoEncriptadoList = new ArrayList<>();

					while ((linea = reader.readLine()) != null) {
						// Asumiendo que cada línea es un número en base 16 (hexadecimal).
						contenidoEncriptadoList.add(new BigInteger(linea, 16));
					}
					reader.close();

					// Convertir la lista a un arreglo para desencriptar.
					BigInteger[] contenidoEncriptado = contenidoEncriptadoList.toArray(new BigInteger[0]);

					// Desencriptar el contenido
					String contenidoDesencriptado = rsa.desencripta(contenidoEncriptado);

					// Mostrar el contenido desencriptado en el JTextArea
					AreaDatos.setText(contenidoDesencriptado);
					JOptionPane.showMessageDialog(null, "Archivo desencriptado con éxito!");

				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Error al desencriptar el archivo: " + x.getMessage());
				}
			}
		});

		btnDescifrar.setEnabled(false);
		btnDescifrar.setBounds(303, 523, 121, 23);
		contentPane.add(btnDescifrar);

		btnGuardar.setBounds(10, 523, 121, 23);
		contentPane.add(btnGuardar);

		JButton btnLlave = new JButton("Generar llave RSA");
		btnLlave.setFont(new Font("Consolas", Font.PLAIN, 12));
		// Acción de generar llave RSA.
		btnLlave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprobar datos en labels.
				if (textValorP.getText().isEmpty() || textValorQ.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Primero debes escribir los dos números primos.");
				} else {
					try {
						BigInteger pBigInteger = new BigInteger(textValorP.getText());
						BigInteger qBigInteger = new BigInteger(textValorQ.getText());
						// Comprobar que los números son primos.
						if (!rsa.isPrime(pBigInteger) || !rsa.isPrime(qBigInteger)) {
							JOptionPane.showMessageDialog(null, "P y Q deben ser numeros primos");
							return;
						}
						// Asignar valores para P y Q.
						rsa.setP(pBigInteger);
						rsa.setQ(qBigInteger);
						// Llamar método para generar claves.
						rsa.generaClaves(pBigInteger, qBigInteger);

						textValorP.setText(pBigInteger.toString());
						textValorQ.setText(qBigInteger.toString());
						textModuloN.setText(rsa.getN().toString());
						textTot.setText(rsa.getTotient().toString());
						textLlaveE.setText(rsa.getE().toString());
						textLlaveD.setText(rsa.getD().toString());

						JOptionPane.showMessageDialog(null, "Llave generada!");
						btnGuardar.setEnabled(true);
						btnCifrar.setEnabled(true);

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Error al generar llave.");
					}
				}
			}
		});
		btnLlave.setBounds(134, 175, 189, 26);
		contentPane.add(btnLlave);

		JLabel lblNewLabel_1 = new JLabel("Fecha");
		lblNewLabel_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(117, 333, 78, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Tipo de planta:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(94, 358, 101, 20);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Altura");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1_1_1.setBounds(117, 389, 78, 14);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Voltaje");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1.setBounds(117, 423, 78, 20);
		contentPane.add(lblNewLabel_1_1_1_1);

		textFecha = new JTextField();
		textFecha.setFont(new Font("Consolas", Font.PLAIN, 14));
		textFecha.setBounds(218, 327, 105, 20);
		contentPane.add(textFecha);
		textFecha.setColumns(10);

		textHumedad = new JTextField();
		textHumedad.setFont(new Font("Consolas", Font.PLAIN, 14));
		textHumedad.setBounds(218, 358, 105, 20);
		contentPane.add(textHumedad);
		textHumedad.setColumns(10);

		textAltura = new JTextField();
		textAltura.setFont(new Font("Consolas", Font.PLAIN, 14));
		textAltura.setBounds(218, 389, 105, 20);
		contentPane.add(textAltura);
		textAltura.setColumns(10);

		textVoltaje = new JTextField();
		textVoltaje.setFont(new Font("Consolas", Font.PLAIN, 14));
		textVoltaje.setBounds(218, 423, 105, 20);
		contentPane.add(textVoltaje);
		textVoltaje.setColumns(10);

		JButton btnEliminar = new JButton("Eliminar datos");
		btnEliminar.setFont(new Font("Consolas", Font.PLAIN, 11));
		// Acción de eliminar campos (en ambos archivos).
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// Llamar metodo eliminar en plantaMetodos.java
					metodos.eliminar("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt");
					metodos.eliminar(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlantaEncriptados.txt");

					JOptionPane.showMessageDialog(null, "Datos borrados con exito!");

					String contenidoArchivo = metodos
							.escribir("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt");
					AreaDatos.setText(contenidoArchivo);
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "Error, no se pudo borrar!");
				}

			}
		});
		btnEliminar.setBounds(303, 557, 121, 23);
		contentPane.add(btnEliminar);

		JButton btnMostrar = new JButton("Mostrar Ruta");
		btnMostrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		// Accion de mostrar ruta.
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Método para abrir ruta.
				metodos.abrir("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador");
			}
		});
		btnMostrar.setBounds(161, 557, 121, 23);
		contentPane.add(btnMostrar);

		Scrollbar scrollbar = new Scrollbar();
		scrollbar.setBounds(747, 0, 17, 591);
		contentPane.add(scrollbar);

		AreaDatos = new JTextArea();
		AreaDatos.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 14));
		AreaDatos.setEditable(false);
		AreaDatos.setWrapStyleWord(true);
		AreaDatos.setBounds(434, 0, 330, 591);
		contentPane.add(AreaDatos);

		JButton btnLimpiar = new JButton("Limpiar campos");
		btnLimpiar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.limpiar(textValorQ, textValorP, textFecha, textHumedad, textAltura, textVoltaje, textModuloN,
						textTot, textLlaveD, textLlaveE);
			}
		});
		btnLimpiar.setBounds(161, 491, 121, 21);
		contentPane.add(btnLimpiar);

		JButton btnMostrarDatos = new JButton("Mostrar datos ");
		btnMostrarDatos.setFont(new Font("Consolas", Font.PLAIN, 11));
		// Acción de mostrar datos.
		btnMostrarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// contenidoArchivo guarda el texto en archivo.
				String contenidoArchivo = metodos
						.escribir("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt");
				// Escribe el contenido en el textArea.
				AreaDatos.setText(contenidoArchivo);

			}
		});
		btnMostrarDatos.setBounds(10, 557, 121, 23);
		contentPane.add(btnMostrarDatos);

		JLabel lblNewLabel_2 = new JLabel("Modulo N:");
		lblNewLabel_2.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(20, 223, 86, 20);
		contentPane.add(lblNewLabel_2);

		textModuloN = new JTextField();
		textModuloN.setFont(new Font("Consolas", Font.PLAIN, 14));
		textModuloN.setEditable(false);
		textModuloN.setBounds(109, 223, 86, 20);
		contentPane.add(textModuloN);
		textModuloN.setColumns(10);

		JLabel lblNewLabel_2_1 = new JLabel("Totient");
		lblNewLabel_2_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(22, 254, 68, 20);
		contentPane.add(lblNewLabel_2_1);

		textTot = new JTextField();
		textTot.setFont(new Font("Consolas", Font.PLAIN, 14));
		textTot.setEditable(false);
		textTot.setColumns(10);
		textTot.setBounds(109, 254, 86, 20);
		contentPane.add(textTot);

		JLabel lblNewLabel_2_1_1 = new JLabel("Clave publica E");
		lblNewLabel_2_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_2_1_1.setBounds(205, 224, 110, 19);
		contentPane.add(lblNewLabel_2_1_1);

		textLlaveE = new JTextField();
		textLlaveE.setFont(new Font("Consolas", Font.PLAIN, 14));
		textLlaveE.setEditable(false);
		textLlaveE.setColumns(10);
		textLlaveE.setBounds(325, 226, 86, 20);
		contentPane.add(textLlaveE);

		textLlaveD = new JTextField();
		textLlaveD.setFont(new Font("Consolas", Font.PLAIN, 14));
		textLlaveD.setEditable(false);
		textLlaveD.setColumns(10);
		textLlaveD.setBounds(325, 257, 86, 20);
		contentPane.add(textLlaveD);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Clave privada D");
		lblNewLabel_2_1_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_2_1_1_1.setBounds(205, 254, 110, 20);
		contentPane.add(lblNewLabel_2_1_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Cm");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(333, 395, 78, 14);
		contentPane.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_2_1 = new JLabel("(a)");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_2_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1_2_1.setBounds(333, 425, 78, 17);
		contentPane.add(lblNewLabel_1_2_1);

	}
}
