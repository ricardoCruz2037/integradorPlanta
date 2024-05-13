package planta;

import java.math.BigInteger;

public class test {

	public static void main(String[] args) {
		BigInteger e = new BigInteger("2851");
		BigInteger totient = new BigInteger("31752");
		BigInteger d = e.modInverse(totient);

		System.out.println("La clave privada 'd' calculada es: " + d);
	}
}
