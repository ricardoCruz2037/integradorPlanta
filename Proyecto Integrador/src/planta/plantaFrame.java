package planta;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
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
	private plantaMetodos metodos; // Instancia de la clase plantaMetodos
	private JPanel contentPane;
	private JTextField textValorP;
	private JTextField textValorQ;
	private JTextField textFecha;
	private JTextField textHumedad;
	private JTextField textAltura;
	private JTextField textVoltaje;
	private JTextArea AreaDatos;

	String fecha;
	float humedad;
	float altura;
	float voltaje;
	private JTextField textModuloN;
	private JTextField textTot;
	private JTextField textLlaveE;
	private JTextField textLlaveD;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					plantaFrame frame = new plantaFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public plantaFrame() {
		setBackground(new Color(100, 204, 0));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\52773\\Downloads\\plant.ico"));
		setTitle("Circuitos Verdes");
		setResizable(false);

		metodos = new plantaMetodos();
		encriptadoRSA rsa = new encriptadoRSA(8);

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
		textValorP.setEditable(false);
		textValorP.setBounds(124, 137, 86, 20);
		contentPane.add(textValorP);
		textValorP.setColumns(10);

		JLabel lblValorDeQ = new JLabel("Valor de Q:");
		lblValorDeQ.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblValorDeQ.setBounds(235, 134, 80, 20);
		contentPane.add(lblValorDeQ);

		textValorQ = new JTextField();
		textValorQ.setFont(new Font("Consolas", Font.PLAIN, 14));
		textValorQ.setEditable(false);
		textValorQ.setColumns(10);
		textValorQ.setBounds(338, 137, 86, 20);
		contentPane.add(textValorQ);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnGuardar.setEnabled(false);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					// Obtenemos los datos de los labels

					fecha = textFecha.getText();
					humedad = Float.parseFloat(textHumedad.getText());
					altura = Float.parseFloat(textAltura.getText());
					voltaje = Float.parseFloat(textVoltaje.getText());
					// Lo enviamos al contructor con los valores.
					metodos.guardarDatos(fecha, humedad, altura, voltaje);

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
		btnCifrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					BufferedReader reader = new BufferedReader(new FileReader(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt"));
					String linea;
					StringBuilder contenido = new StringBuilder();
					StringBuilder contenidoEnString = new StringBuilder();

					while ((linea = reader.readLine()) != null) {
						contenido.append(linea).append("\n");
					}
					reader.close();

					// Encriptar el contenido
					BigInteger[] contenidoEncriptado = rsa.encripta(contenido.toString());

					// Guardar el contenido encriptado en un nuevo archivo
					FileWriter writer = new FileWriter(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlantaEncriptados.txt");
					for (BigInteger bigInt : contenidoEncriptado) {
						writer.write(bigInt.toString(16) + "\n"); // Guardar como hexadecimal
						contenidoEnString.append(bigInt.toString(16)).append("\n");
					}
					writer.close();
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

		btnDescifrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Leer el archivo encriptado
					BufferedReader reader = new BufferedReader(new FileReader(
							"C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlantaEncriptados.txt"));
					String linea;
					List<BigInteger> contenidoEncriptadoList = new ArrayList<>();

					while ((linea = reader.readLine()) != null) {
						// Asumiendo que cada línea es un número en base 16 (hexadecimal)
						contenidoEncriptadoList.add(new BigInteger(linea, 16));
					}
					reader.close();

					// Convertir la lista a un arreglo para desencriptar
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
		btnLlave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Verificar que p y q no sean null
				if (rsa.getP() != null && rsa.getQ() != null) {
					rsa.generaClaves();

					textModuloN.setText(rsa.getN().toString());
					textTot.setText(rsa.getTotient().toString());
					textLlaveE.setText(rsa.getE().toString());
					textLlaveD.setText(rsa.getD().toString());

					JOptionPane.showMessageDialog(null, "Llave generada!");
					btnLlave.setEnabled(false);
					btnGuardar.setEnabled(true);
					btnCifrar.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Primero debes generar los números primos.");
				}

			}
		});
		btnLlave.setEnabled(false);

		JButton btnPrimos = new JButton("Generar números primos");
		btnPrimos.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnPrimos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					textValorP.setText(null);
					textValorQ.setText(null);
					rsa.generaPrimos();
					BigInteger p = rsa.getP();
					BigInteger q = rsa.getQ();
					textValorP.setText(p.toString());
					textValorQ.setText(q.toString());
					btnLlave.setEnabled(true);
				} catch (NumberFormatException a) {
					JOptionPane.showMessageDialog(null, "Error, no se pudo generar!");
				}
			}
		});

		btnPrimos.setBounds(10, 167, 200, 26);
		contentPane.add(btnPrimos);
		btnLlave.setBounds(235, 167, 189, 26);
		contentPane.add(btnLlave);

		JLabel lblNewLabel_1 = new JLabel("Fecha");
		lblNewLabel_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(117, 327, 78, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Humedad");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(117, 358, 78, 14);
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
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
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
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodos.abrir("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador");
			}
		});
		btnMostrar.setBounds(161, 557, 121, 23);
		contentPane.add(btnMostrar);

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
				metodos.limpiar(textValorQ, textValorP, textFecha, textHumedad, textAltura, textVoltaje);
			}
		});
		btnLimpiar.setBounds(161, 491, 121, 21);
		contentPane.add(btnLimpiar);

		JButton btnMostrarDatos = new JButton("Mostrar datos ");
		btnMostrarDatos.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnMostrarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String contenidoArchivo = metodos
						.escribir("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt");
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

		JLabel lblNewLabel_2_1_1 = new JLabel("Clave privada E");
		lblNewLabel_2_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_2_1_1.setBounds(205, 255, 110, 19);
		contentPane.add(lblNewLabel_2_1_1);

		textLlaveE = new JTextField();
		textLlaveE.setFont(new Font("Consolas", Font.PLAIN, 14));
		textLlaveE.setEditable(false);
		textLlaveE.setColumns(10);
		textLlaveE.setBounds(325, 257, 86, 20);
		contentPane.add(textLlaveE);

		textLlaveD = new JTextField();
		textLlaveD.setFont(new Font("Consolas", Font.PLAIN, 14));
		textLlaveD.setEditable(false);
		textLlaveD.setColumns(10);
		textLlaveD.setBounds(325, 226, 86, 20);
		contentPane.add(textLlaveD);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Clave publica D");
		lblNewLabel_2_1_1_1.setFont(new Font("Franklin Gothic Book", Font.PLAIN, 16));
		lblNewLabel_2_1_1_1.setBounds(205, 223, 110, 20);
		contentPane.add(lblNewLabel_2_1_1_1);

	}
}