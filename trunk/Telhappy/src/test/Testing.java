package test;

import java.io.File;
import java.io.IOException;

import gui.InicialForm;
import happy.ContaAutomatica;
import happy.ContaCSV;
import happy.ContaNET;
import happy.DataAccess;
import happy.DataConverter;

public class Testing {

	public static void main(String[] args) {

		DataAccess.loadDataBase();
		
		// form
//		InicialForm form = new InicialForm();
//		form.setVisible(true);
		
		// executa conta
		File f = new File("./resources/conta.csv");
		ContaCSV aut = new ContaCSV(f);
		try {
			aut.executar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
