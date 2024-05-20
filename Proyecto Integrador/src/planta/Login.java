package planta;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class Login {
	public static void main(String[] args) {

		String username = "lechuga";
		String password = "lechugafria";

		String inputUsername = JOptionPane.showInputDialog(null, "Ingresa tu nombre de usuario:");

		JPanel panel = new JPanel();
		JLabel label = new JLabel("Ingresa tu contraseña:");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[] { "OK"};
		int option = JOptionPane.showOptionDialog(null, panel, "Contraseña", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (option == 0) // pressing OK button
		{
			char[] inputPassword = pass.getPassword();
			String strPassword = new String(inputPassword);

			if (username.equals(inputUsername) && password.equals(strPassword)) {
				JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso!");
			} else {
				JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos.");
				System.exit(0);
			}
		}
	}
}
