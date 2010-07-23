package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SobreForm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public SobreForm() {
		
		getContentPane().setLayout(new GridLayout(7, 1));

		JLabel label1 = new JLabel("TellHappy 2.0", JLabel.CENTER);
		JLabel label2 = new JLabel("  -- We are watching you, we will get you! oO --  ");		
		JLabel label3 = new JLabel("  Feito por Leonardo Leite (leonardofl87@gmail.com)  ");		
		JLabel label4 = new JLabel("  Licenciado sob a GPL v3  ");		
		JLabel label5 = new JLabel("  Fontes: http://code.google.com/p/telhappy  ");		
		Font font = new Font("Serif", Font.PLAIN, 20);
		label1.setFont(font);
		
		
		getContentPane().add(label1);
		getContentPane().add(label2);
		getContentPane().add(new JLabel(""));
		getContentPane().add(label3);
		getContentPane().add(label4);
		getContentPane().add(label5);
		
		pack();
	}

}
