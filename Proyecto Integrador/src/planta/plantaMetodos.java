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

	public void guardarDatos(String fecha, float humedad, float altura, float voltaje) {

		Path path = Path.of("datosPlanta.txt");
		if (!Files.exists(path)) {
			try {
				path = Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String stFecha = fecha;
		float stHumedad = humedad;
		float stAltura = altura;
		float stVoltaje = voltaje;

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
			bufferedWriter.write(separador());
			bufferedWriter.write("\n౨ Fecha de los datos: " + stFecha + " ৎ \n");
			bufferedWriter.write("౨ Humedad actual: " + String.valueOf(stHumedad) + " ৎ \n");
			bufferedWriter.write("౨ Altura de la lechuga: " + String.valueOf(stAltura) + " ৎ \n");
			bufferedWriter.write("౨ Voltaje recivido: " + String.valueOf(stVoltaje) + " ৎ \n");
			bufferedWriter.newLine(); // Esto añade una nueva línea al final

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Files.lines(path).forEach(line -> System.out.println(line));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String separador() {
		String separadorString = "  ───────── ౨ NUEVA ENTRADA DE DATOS ৎ ─────────";
		return separadorString;
	}

	public void abrir(String rutaDirect) {
		String directoryPath = "C:\\Users\\52773\\eclipse-workspace\\Proyecto Integrador";

		// Crear un objeto File para el directorio
		File directory = new File(directoryPath);

		// Verificar si el directorio existe y es un directorio
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

	public void eliminar(String rutaArchivo) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
			// No es necesario escribir nada, el archivo se sobrescribirá vacío.
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void limpiar(JTextField textValorP, JTextField textValorQ, JTextField textFecha, JTextField textHumedad,
			JTextField textAltura, JTextField textVoltaje, JTextField textModulo, JTextField textToot,
			JTextField textClaveD, JTextField textClaveE) {
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
}
