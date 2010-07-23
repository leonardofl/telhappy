package gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InicialForm extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton moradores, inclusao, busca, automatico, ajuda, sobre;
	
	public InicialForm() {
		
		super("TelHappy 2.0");
		
		getContentPane().setLayout(new GridLayout(9, 1));

		JLabel label1 = new JLabel("TellHappy 2.0", JLabel.CENTER);
		JLabel label2 = new JLabel("  -- We are watching you, we will get you! oO --");		
		Font font = new Font("Serif", Font.PLAIN, 20);
		label1.setFont(font);
		
		moradores = new JButton("Gerenciar moradores");
		inclusao = new JButton("Incluir telefones");
		busca = new JButton("Encontrar telefonemas não identificados");
		automatico = new JButton("Fazer conta automática");
		ajuda = new JButton("Ajuda =O");
		sobre = new JButton("Sobre");
		
		moradores.addActionListener(new MoradoresListener());
		busca.addActionListener(new BuscaListener());
		ajuda.addActionListener(new AjudaListener());
		inclusao.addActionListener(new InclusaoListener());
		automatico.addActionListener(new AutomListener());
		sobre.addActionListener(new SobreListener());
		
		getContentPane().add(label1);
		getContentPane().add(label2);
		getContentPane().add(new JLabel(""));

		getContentPane().add(inclusao);
		getContentPane().add(busca);
		getContentPane().add(automatico);
		getContentPane().add(moradores);
		getContentPane().add(ajuda);
		getContentPane().add(sobre);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	// listeners
	
	private class MoradoresListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JFrame form = new MoradoresForm();
			form.setVisible(true);
		}
	}
	
	private class BuscaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JFrame form = new BuscaForm();
			form.setVisible(true);
		}
	}

	private class InclusaoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JFrame form = new InclusaoForm();
			form.setVisible(true);
		}
	}

	private class AjudaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				Runtime.getRuntime().exec(new String[]{"firefox", "./resources/help.html"});
			} catch (IOException e) {
				System.out.println("O que?! Você não tem o Firefox instalado??!!");
				e.printStackTrace();
			}  		
		}
	}

	private class AutomListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JFrame form = new AutomForm();
			form.setVisible(true);
		}
	}

	private class SobreListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JFrame form = new SobreForm();
			form.setVisible(true);
		}
	}

}
