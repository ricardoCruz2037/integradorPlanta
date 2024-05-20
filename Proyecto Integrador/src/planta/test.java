package planta;

import java.math.BigInteger;

public class test {
	public static void main(String[] args) {
		// Instancia de encriptadoRSA con un tamaño de primo de 8 bits
		encriptadoRSA rsa = new encriptadoRSA(8);

		// Imprimir claves y n
		System.out.println("p: " + rsa.getP());
		System.out.println("q: " + rsa.getQ());
		System.out.println("n: " + rsa.getN());
		System.out.println("e: " + rsa.getE());
		System.out.println("d: " + rsa.getD());

		// Mensaje de prueba
		String mensaje = "Hola Mundo";

		// Encriptar el mensaje
		BigInteger[] mensajeEncriptado = rsa.encripta(mensaje);
		System.out.println("Mensaje encriptado:");
		for (BigInteger bigInteger : mensajeEncriptado) {
			System.out.println(bigInteger);
		}

		// Desencriptar el mensaje
		String mensajeDesencriptado = rsa.desencripta(mensajeEncriptado);
		System.out.println("Mensaje desencriptado: " + mensajeDesencriptado);
	}
}
