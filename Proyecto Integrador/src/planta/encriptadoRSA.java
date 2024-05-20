package planta;

import java.math.BigInteger;
import java.util.Random;

public class encriptadoRSA {

	int tamPrimo;
	BigInteger p;
	BigInteger q;
	public BigInteger n;
	public BigInteger totient;
	public BigInteger e, d;

	// Método para asegurarse que p y q son primos.
	boolean isPrime(BigInteger number) {
		if (number.compareTo(BigInteger.ONE) <= 0) {
			return false;
		}
		for (BigInteger i = BigInteger.valueOf(2); i.multiply(i).compareTo(number) <= 0; i = i.add(BigInteger.ONE)) {
			if (number.mod(i).equals(BigInteger.ZERO)) {
				return false;
			}
		}
		return true;
	}

	// Constructor para la clase "encriptadoRSA"
	public encriptadoRSA(int tamPrimo) {
		this.tamPrimo = tamPrimo;
		// Inicializar P Y Q
		BigInteger primeP = BigInteger.probablePrime(tamPrimo, new Random());
		BigInteger primeQ = BigInteger.probablePrime(tamPrimo, new Random());
		// Set valores a P y Q
		this.p = primeP;
		this.q = primeQ;
		// Generar e y d
		generaClaves(p, q);

	}

	public encriptadoRSA(BigInteger p, BigInteger q, int tamPrimo) {
		this.tamPrimo = tamPrimo;
		// Inicializar P Y Q
		BigInteger primeP = BigInteger.probablePrime(tamPrimo, new Random());
		BigInteger primeQ = BigInteger.probablePrime(tamPrimo, new Random());
		// Set valores a P y Q
		this.p = primeP;
		this.q = primeQ;
		// Genera e y d
		generaClaves(p, q);

	}

	// Método para generar claves.
	public void generaClaves(BigInteger valueP, BigInteger valueQ) {
		// n = p * q
		n = valueP.multiply(valueQ);
		// toltiente = (p-1) * (q-1)
		totient = valueP.subtract(BigInteger.valueOf(1));
		totient = totient.multiply(valueQ.subtract(BigInteger.valueOf(1)));
		// Elegimos un e coprimo de y menor que n
		do
			e = new BigInteger(2 * tamPrimo, new Random());
		while ((e.compareTo(totient) != -1) || (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
		// d = e^1 mod totient
		d = e.modInverse(totient);
	}

	/**
	 * Encripta el texto usando la clave pública
	 *
	 * @param mensaje Ristra que contiene el mensaje a encriptar
	 * @return El mensaje cifrado como un vector de BigIntegers
	 */

	public BigInteger[] encripta(String mensaje) {

		// Obtenemos la cadena mensaje.
		int i;
		// Array de Bytes tamaño 1.
		byte[] temp = new byte[1];
		// Array de Bytes con representacion de bytes de mensaje
		byte[] digitos = mensaje.getBytes();
		// Array Bytes de misma longitud que digitos
		BigInteger[] bigdigitos = new BigInteger[digitos.length];
		/*
		 * Recorriendo digitos. Cada byte en digitos se copia en temp y se convierte en
		 * BigInteger que se almacena en bigdigitos
		 */
		for (i = 0; i < bigdigitos.length; i++) {
			temp[0] = digitos[i];
			bigdigitos[i] = new BigInteger(temp);
		}
		// Array Bytes de misma long que bigdigitos
		/*
		 * Para cada BigInteger en bigdigitos, se eleva a 'e' y se toma el módulo n
		 * (modPow(e, n)). Resultado se almacena en encriptado.
		 */
		BigInteger[] encriptado = new BigInteger[bigdigitos.length];

		for (i = 0; i < bigdigitos.length; i++)
			encriptado[i] = bigdigitos[i].modPow(e, n);
		// retorno del mensaje cifrado.
		return (encriptado);
	}

	/**
	 * Desencripta el texto cifrado usando la clave privada
	 *
	 * @param encriptado Array de objetos BigInteger que contiene el texto cifrado
	 *                   que será desencriptado
	 * @return The decrypted plaintext
	 */

	public String desencripta(BigInteger[] encriptado) {
		// Parametro Array de BiInteger (Mensaje Cifrado)
		// Array byte de tamaño encriptado
		BigInteger[] desencriptado = new BigInteger[encriptado.length];

		// Rara cada número en encriptado se eleva a d y se toma el módulo n
		// (modPow(d,n))

		for (int i = 0; i < desencriptado.length; i++)
			desencriptado[i] = encriptado[i].modPow(d, n);
		// Array char de tamaño desencriptado
		/*
		 * Para cada número en desencriptado se convierte a valor entero y luego a
		 * carácter. El resultado se almacena en charArray
		 */
		char[] charArray = new char[desencriptado.length];

		for (int i = 0; i < charArray.length; i++)
			charArray[i] = (char) (desencriptado[i].intValue());
		// Se crea string a partir del array de parametro array (charArray)
		return (new String(charArray));
	}

	// Setters y Getters
	public BigInteger getP() {
		return (p);
	}

	public BigInteger getQ() {
		return (q);
	}

	public BigInteger getTotient() {
		return (totient);
	}

	public BigInteger getN() {
		return (n);
	}

	public BigInteger getE() {
		return (e);
	}

	public BigInteger getD() {
		return (d);
	}

	public void setP(BigInteger wangoP) {
		p = wangoP;
	}

	public void setQ(BigInteger wangoQ) {
		q = wangoQ;
	}
}
