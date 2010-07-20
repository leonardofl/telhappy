package gui;


import happy.DataAccess;
import happy.TelInfo;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InclusaoForm extends JFrame{

	private static final long serialVersionUID = 1L;

	private JTextField telTextField, donoTextField;
	private JComboBox usuariosComboBox;
	private JButton button;
	
	public InclusaoForm() {
		
		super("TelHappy 2.0");
		
		getContentPane().setLayout(new GridLayout(13, 1));

		JLabel label1 = new JLabel(" TellHappy 2.0 - Inclusão de telefones ", JLabel.CENTER);
		JLabel label2 = new JLabel("Telefone: ");
		JLabel label3 = new JLabel("Provável usuário: ");
		JLabel label4 = new JLabel("Dono do telefone: ");
		Font font = new Font("Serif", Font.PLAIN, 15);
		label1.setFont(font);

		telTextField = new JTextField();
		donoTextField = new JTextField();
		
		int size = DataAccess.moradores.size();
		String[] list = new String[size+1];
		list[0] = "";
		for (int i=0; i<size; i++)
			list[i+1] = DataAccess.moradores.get(i);
		usuariosComboBox = new JComboBox(list);
		
		button = new JButton("Incluir");
		button.addActionListener(new ButtonListener());
		
		getContentPane().add(label1);
		getContentPane().add(new JLabel(""));
		getContentPane().add(label2);
		getContentPane().add(telTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(label3);
		getContentPane().add(usuariosComboBox);
		getContentPane().add(new JLabel(""));
		getContentPane().add(label4);
		getContentPane().add(donoTextField);
		getContentPane().add(new JLabel(""));
		getContentPane().add(button);
		getContentPane().add(new JLabel(""));
		
		pack();
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (telTextField.getText() == "") {
				JOptionPane.showMessageDialog(InclusaoForm.this, "Campo telefone está em branco!");
				return;
			}
			
			List<String> tels = DataAccess.buscaTel(telTextField.getText());
			
			int size = tels.size();
			StringBuilder result = new StringBuilder("");
			if (size > 0) {
				
				result.append("Talvez este telefone já esteja cadastrado como...\n"); 
				for (String t: tels) {
					TelInfo info = DataAccess.telMap.get(t);
					result.append("\nTelefone: " + t + "\n");
					result.append("Prováveis usuários: " + info.getUsuarios().toString() + "\n");
					result.append("Dono do telefone: " + info.getDono() + "\n");
				}
			}
			result.append("\nPor favor, confira os dados\nConfirma inclusão?");
			
			int n = JOptionPane.showConfirmDialog(
				    InclusaoForm.this,
				    result.toString(),
				    "question",
				    JOptionPane.YES_NO_OPTION);
			
			if (n == 0){
				
				String u = (String) usuariosComboBox.getSelectedItem();
				DataAccess.inserTel(telTextField.getText(), u, donoTextField.getText());
				JOptionPane.showMessageDialog(InclusaoForm.this, telTextField.getText() + " incluído!");
				telTextField.setText("");
				donoTextField.setText("");
			}
		}
	}
		
	
}
