package test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import gui.InicialForm;
import happy.ContaAutomatica;
import happy.ContaCSV;
import happy.ContaNET;
import happy.DataAccess;
import happy.DataConverter;
import happy.TelInfo;
import happy.Telefonema;

public class Testing {

	public static void main(String[] args) {

		DataAccess.loadDataBase();
		
		// form
//		InicialForm form = new InicialForm();
//		form.setVisible(true);
		
		// executa conta
		File f = new File("./resources/conta.csv");
		ContaCSV aut = new ContaCSV(f);
		Map<String, Double> map = null;
		try {
			map = aut.executar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String u: map.keySet()) {
			
			double v = map.get(u);
			System.out.println(u + " - " + v);
		}
		
		System.out.println("\nConflitos:");
		Map<Telefonema, TelInfo> conflitos = aut.conflitos();
		for (Telefonema tel: conflitos.keySet()) {
			
			System.out.println(tel.getTelefone() + " - " + conflitos.get(tel));
		}
		
		// converte do mysql
//		File mysql = new File("./resources/mysql.sql");
//		DataConverter conv = new DataConverter(mysql);
//		try {
//			conv.convert();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Done =)");
		
		// limpa o sistema
//		DataAccess.apagaRepetidos();
	}

}
