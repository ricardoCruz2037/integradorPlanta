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
		BigInteger primeP = BigInteger.probablePrime(tamPrimo, new Random());
		BigInteger primeQ = BigInteger.probablePrime(tamPrimo, new Random());
		// Now set p and q
		this.p = primeP;
		this.q = primeQ;
		// Inicializa p y q con valores por defecto o calculados.
		generaClaves(p, q); // Genera e y d

	}

	public encriptadoRSA(BigInteger p, BigInteger q, int tamPrimo) {
		this.tamPrimo = tamPrimo;
		BigInteger primeP = BigInteger.probablePrime(tamPrimo, new Random());
		BigInteger primeQ = BigInteger.probablePrime(tamPrimo, new Random());
		// Now set p and q
		this.p = primeP;
		this.q = primeQ;
		// Inicializa p y q con valores por defecto o calculados.
		generaClaves(p, q); // Genera e y d

	}

	// Método para generar numeros primos.

	// Método para generar claves.
	public void generaClaves(BigInteger valueP, BigInteger valueQ) {
		// this.p = valueP;
		// this.q = valueQ;
		// n = p * q
		n = valueP.multiply(valueQ);
		// toltient = (p-1)*(q-1)
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
		int i;
		byte[] temp = new byte[1];
		byte[] digitos = mensaje.getBytes();
		BigInteger[] bigdigitos = new BigInteger[digitos.length];

		for (i = 0; i < bigdigitos.length; i++) {
			temp[0] = digitos[i];
			bigdigitos[i] = new BigInteger(temp);
		}

		BigInteger[] encriptado = new BigInteger[bigdigitos.length];

		for (i = 0; i < bigdigitos.length; i++)
			encriptado[i] = bigdigitos[i].modPow(e, n);

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
		BigInteger[] desencriptado = new BigInteger[encriptado.length];

		for (int i = 0; i < desencriptado.length; i++)
			desencriptado[i] = encriptado[i].modPow(d, n);

		char[] charArray = new char[desencriptado.length];

		for (int i = 0; i < charArray.length; i++)
			charArray[i] = (char) (desencriptado[i].intValue());

		return (new String(charArray));
	}

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
