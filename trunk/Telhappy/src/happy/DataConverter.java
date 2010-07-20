package happy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converte os dados da exportação do mysql do Telhappy 1 para o Sqlite do Telhappy 2
 * @author leonardo
 *
 */
public class DataConverter {
	
	private static final String SQL_ER = "\\(\\d*?, '(.*?)', '(.*?)', '(.*?)'\\),";
	private static final int DDD_SAMPA = 11;
	
	private BufferedReader reader; // source reader
	
	public DataConverter(File mysqlFile) {
		
		FileReader in = null;
		try {
			in = new FileReader(mysqlFile);
		} catch (FileNotFoundException e) {
			System.out.println("Problemas ao abrir o arquivo!");
			e.printStackTrace();
		}
		reader = new BufferedReader(in);

	}


	public void convert() throws IOException {
		
		Pattern pattern = Pattern.compile(SQL_ER);
		
		List<Tuple> tuples = new ArrayList<Tuple>();

		Matcher matcher;
		String line = reader.readLine();

		// obtêm do arquivo mysql os telefones
		while (line != null) {
			
			matcher = pattern.matcher(line);
			if (matcher.matches()) { // é linha com dados
				
				String usuario = matcher.group(1);
				String tel = matcher.group(2);
				String dono = matcher.group(3);
				
				tel = DataAccess.formatTel(tel, DDD_SAMPA);
				
				Tuple tuple = new Tuple(usuario, tel, dono);
				tuples.add(tuple);
				System.out.print(".");
			}
			
			line = reader.readLine();
		}
		
		// agora grava no mysql o que foi obtido
		System.out.println("");
		for (Tuple tuple: tuples) {
			
			DataAccess.inserTel(tuple.getTelefone(), tuple.getUsuario(), tuple.getDono());
			System.out.print(".");
		}
	}
	
}
