package gui;

import happy.DataAccess;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class MoradoresForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultListModel listModel;
	private JList lista;
	private JButton add, remove;

	public MoradoresForm() {
		
		super("TelHappy 2.0");
		
		listModel = new DefaultListModel();
		for (String s: DataAccess.moradores)
			listModel.addElement(s);
		
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		lista = new JList(listModel);

		JLabel label1 = new JLabel(" TellHappy 2.0 - Gerência de moradores ", JLabel.CENTER);
		Font font = new Font("Serif", Font.PLAIN, 15);
		label1.setFont(font);
		
		add = new JButton("+");
		add.addActionListener(new AddListener());
		remove = new JButton("x");
		remove.addActionListener(new RemoveListener());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 15, 5);

		getContentPane().add(label1, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(add, c);
		c.gridx = 2;
		getContentPane().add(remove, c);
		c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 2;
		getContentPane().add(lista, c);

		pack();
	}
	
	private class AddListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			String morador = (String)JOptionPane.showInputDialog("Nome do novo morador: ");
			if (morador != null) {
				DataAccess.insertMorador(morador);
				listModel.addElement(morador);
			}
		}
	}

	private class RemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (lista.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(MoradoresForm.this, "Ninguém selecionado");
				return;
			}
			
			String morador = (String) listModel.get(lista.getSelectedIndex());
			int n = JOptionPane.showConfirmDialog(
				    MoradoresForm.this,
				    "Realmente deseja excluir " + morador + "?",
				    "question",
				    JOptionPane.YES_NO_OPTION);

			if (n == 0) {
				
				DataAccess.removeMorador(morador);
				listModel.removeElement(morador);
			}
		}
	}

}
