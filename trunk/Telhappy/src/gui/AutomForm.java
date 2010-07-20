package gui;


import happy.ContaAutomatica;
import happy.ContaCSV;
import happy.ContaNET;
import happy.PDFConverter;
import happy.TelInfo;
import happy.Telefonema;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Formulário responsável por fazer a conta automática
 * @author leonardo
 *
 */
public class AutomForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String[] TIPOS_CONTAS = {"NET", "CSV"}; 
	private DecimalFormat decimal = new DecimalFormat( "0.00" );
	
	private JTextField text;
	private JComboBox comboBox;
	private JButton button, fileButton;
	private JTextArea textArea;
	private JFileChooser fc;
	private File file;

	public AutomForm() {
		
		super("TelHappy 2.0");
		
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();


		JLabel label1 = new JLabel(" TellHappy 2.0 - Conta automática ", JLabel.CENTER);
		Font font = new Font("Serif", Font.PLAIN, 15);
		label1.setFont(font);
		
		JLabel label2 = new JLabel("Tipo da conta: ");
		JLabel label3 = new JLabel("Arquivo: ");
		text = new JTextField();
		text.setEditable(false);
		button = new JButton("Gerar conta");
		fileButton = new JButton("::");
		textArea = new JTextArea(15, 1);
		textArea.setEditable(false);
		comboBox = new JComboBox(TIPOS_CONTAS);
		JScrollPane logScrollPane = new JScrollPane(textArea);
		fc = new JFileChooser();
		
		fileButton.addActionListener(new FileButtonListener());
		button.addActionListener(new GerarListener());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 15, 5);
		c.weighty = 1;
		c.weightx = 1;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(label1, c);

		c.gridwidth = 1;
		c.gridy = 1;
		getContentPane().add(label2, c);

		c.gridx = 1;
		getContentPane().add(comboBox, c);

		c.gridy = 2;
		c.gridx = 0;
		getContentPane().add(label3, c);

		c.gridx = 1;
		getContentPane().add(text, c);

		c.gridx = 2;
		getContentPane().add(fileButton, c);

		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		getContentPane().add(button, c);

		c.gridy = 4;
		c.gridwidth = 3;
		getContentPane().add(logScrollPane, c);

		pack();
	}
	
	private class FileButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			  int returnVal = fc.showOpenDialog(AutomForm.this);

		      if (returnVal == JFileChooser.APPROVE_OPTION) {
		        file = fc.getSelectedFile();
		        text.setText(file.getAbsolutePath()); 
		      } 	
		      
		      
		}
	}

	private class GerarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (file == null)
				return;
			
			// se for PDF, converte para txt
			Pattern pattern = Pattern.compile("pdf$"); // os três últimos caracteres
			Matcher macther = pattern.matcher(file.getName());
			if (macther.find()) {
				PDFConverter pdfer = new PDFConverter(file);
				File pdf = pdfer.convert();
				if (pdf != null)
					file = pdf;
			}
			
			// processa a conta
			
			ContaAutomatica contaAut = null;
			
			String conta = (String) comboBox.getSelectedItem();
			if (conta.equals("CSV")) {

				contaAut = new ContaCSV(file);
//				JOptionPane.showMessageDialog(AutomForm.this, "Malz, suporte a CSV ainda não implementado!");
			}
			
			if (conta.equals("NET")) {
				
				contaAut = new ContaNET(file);
			}
			
			
			if (contaAut != null) {
				
				Map<String, Double> map = null;
				try {
					map = contaAut.executar();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// atualiza textArea
				StringBuilder result = new StringBuilder("");
				result.append("Resumo:\n");
				
				// identificados normalmente
				for (String u: map.keySet()) {
					if ((!u.equals(ContaAutomatica.NAO_IDENTIFICADOS)) && (!u.equals(ContaAutomatica.CONFLITO))) {
						double v = map.get(u);
						result.append(u + " - R$" + decimal.format(v) + "\n");
					}
				}

				// não identificados
				String u = ContaAutomatica.NAO_IDENTIFICADOS;
				double v = map.get(u);
				result.append(u + " - R$" + decimal.format(v) + "\n");
				
				// conflitos
				u = ContaAutomatica.CONFLITO;
				v = map.get(u);
				result.append(u + " - R$" + decimal.format(v) + "\n");
				
				result.append("\nConflitos:\n");
				Map<Telefonema, TelInfo> conflitos = contaAut.conflitos();
				for (Telefonema telf: conflitos.keySet()) {
					
					TelInfo info = conflitos.get(telf);
					result.append(telf.getTelefone() + ", " + telf.getTimeStamp());
					result.append(" -- " + info.getUsuarios() + "\n");
				}
				
				textArea.setText(result.toString());
			}
		}
	}


}
