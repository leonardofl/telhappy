package gui;

import happy.DataAccess;
import happy.TelInfo;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BuscaForm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton goButton;
	private JTextField telText;
	private JTextArea resultTextArea;

	public BuscaForm() {
		
		super("TelHappy 2.0");
		
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel label1 = new JLabel(" TellHappy 2.0 - Busca de telefonemas ", JLabel.CENTER);
		JLabel label2 = new JLabel("Telefone: ");
		Font font = new Font("Serif", Font.PLAIN, 15);
		label1.setFont(font);

		resultTextArea = new JTextArea(10, 1); // rows, columns
		resultTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(resultTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

		goButton = new JButton("Buscar");
		goButton.addActionListener(new BuscaListener());

		telText = new JTextField();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 15, 5);
		getContentPane().add(label1, c);
		c.gridy = 2;
		getContentPane().add(label2, c);
		c.gridy = 3;
		getContentPane().add(telText, c);
		c.gridy = 5;
		getContentPane().add(goButton, c);
		c.anchor = GridBagConstraints.ABOVE_BASELINE;
		c.weighty = 2;
		c.gridy = 9;
		getContentPane().add(scrollPane, c);
		
		pack();
	}
	
	private class BuscaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String result = "Telefones encontrados:\n";
			
			List<String> tels = DataAccess.buscaTel(telText.getText());
			for (String t: tels) {
				
				TelInfo info = DataAccess.telMap.get(t);
				result += "\nTelefone: " + t + "\n";
				result += "Prováveis usuários: " + info.getUsuarios().toString() + "\n";
				result += "Dono: " + info.getDono() + "\n";
			}
			
			resultTextArea.setText(result);
		}
	}

}
