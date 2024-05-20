package planta;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class plantaMetodos {

	int tamPrimo;

	// Método para guardar datos.
	public void guardarDatos(String fecha, String humedad, float altura, float voltaje) {
		// Revisar existencia del archivo datosPlanta.txt
		Path path = Path.of("datosPlanta.txt");
		if (!Files.exists(path)) {
			try {
				path = Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String stFecha = fecha;
		String stHumedad = humedad;
		float stAltura = altura;
		float stVoltaje = voltaje;
		// Buffer escritor para escribir los datos recibidos.
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
			bufferedWriter.write(separador());
			bufferedWriter.write("\n ° Fecha de los datos: " + stFecha + " ° \n");
			bufferedWriter.write("° Tipo de planta: " + String.valueOf(stHumedad) + " ° \n");
			bufferedWriter.write("° Altura de la lechuga: " + String.valueOf(stAltura) + " ° Cm \n");
			bufferedWriter.write("° Voltaje recivido: " + String.valueOf(stVoltaje) + " ° (a) \n");
			bufferedWriter.newLine(); // Nueva línea al final

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Files.lines(path).forEach(line -> System.out.println(line));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método separador
	public String separador() {
		String separadorString = "  ───────── > NUEVA ENTRADA DE DATOS < ─────────";
		return separadorString;
	}

	// Metodo para abrir ruta
	public void abrir(String rutaDirect) {
		String directoryPath = "C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador";

		// Objeto File para el directorio
		File directory = new File(directoryPath);

		// Verificar existencia directorio existe y es un directorio
		if (directory.exists() && directory.isDirectory()) {
			// Abrir el directorio en el explorador de archivos
			try {
				Desktop.getDesktop().open(directory);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error, no se pudo abrir!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error, no existe o no es un directorio!");

		}
	}

	// Método eliminar
	public void eliminar(String rutaArchivo) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
			// No es necesario escribir nada, el archivo se sobrescribirá vacío.
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Método limpiar campos.
	public void limpiar(JTextField textValorP, JTextField textValorQ, JTextField textFecha, JTextField textHumedad,
			JTextField textAltura, JTextField textVoltaje, JTextField textModulo, JTextField textToot,
			JTextField textClaveD, JTextField textClaveE) {
		// Settear null a cada textLabel
		textValorP.setText(null);
		textValorQ.setText(null);
		textFecha.setText(null);
		textHumedad.setText(null);
		textAltura.setText(null);
		textVoltaje.setText(null);
		textModulo.setText(null);
		textToot.setText(null);
		textClaveD.setText(null);
		textClaveE.setText(null);

	}

	// Método para escribir dentro del txt
	public String escribir(String rutaString) {
		StringBuilder contenido = new StringBuilder();

		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador\\datosPlanta.txt"))) {
			String linea;

			while ((linea = br.readLine()) != null) {
				contenido.append(linea).append("\n"); // Añade cada línea al contenido, seguida de un salto de línea
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "Error al leer el archivo.";
		}

		return contenido.toString();
	}

	public void iniciarSesion() {
		String username = "user";
		String password = "test";

		String inputUsername = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario:");
		String inputPassword = JOptionPane.showInputDialog(null, "Ingresa tu contraseña:");

		if (username.equals(inputUsername) && password.equals(inputPassword)) {
			JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso!");
		} else {
			JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos.");
			System.exit(0);
		}
	}
}
