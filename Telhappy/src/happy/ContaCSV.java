package happy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContaCSV implements ContaAutomatica{

	// tel, data, valor
	// cada grupo pode ser cercado por aspas
	// depois de cada vírgula pode ter um espaço
	// valor pode ser com "." ou com "," e pode ter R$ na frente
	// grupos podem ser separados por , ou ;
	private static String LIN_ER = "\"?([\\d()\\- ]*?)\"?[,;] ?\"?([\\d/]*?)\"?[,;] ?\"?(?:R\\$)?([\\d,.]*?)\"?"; // $ num é fim de linha?
	
	// aqui fica o resultado final do nosso cálculo de divisão da conta
	private Map<String, Double> conta;
	private Map<Telefonema, TelInfo> conflitos; // em casos de conflitos, ver o time-stamp ajuda ao usuário identificar o telefonema na caderneta

	private BufferedReader reader;

	public ContaCSV (File sourceFile) {
		
		FileReader in = null;
		try {
			in = new FileReader(sourceFile);
		} catch (FileNotFoundException e) {
			System.out.println("Problemas ao abrir o arquivo!");
			e.printStackTrace();
		}
		reader = new BufferedReader(in);
	}


	@Override
	public Map<Telefonema, TelInfo> conflitos() {

		return conflitos;
	}

	@Override
	public Map<String, Double> executar() throws IOException {

		Pattern pattern = Pattern.compile(LIN_ER);
		
		List<Telefonema> telfs = new ArrayList<Telefonema>();
		
		String linha = reader.readLine();
		
		Matcher matcher = null;
		while (linha != null) {
			
			matcher = pattern.matcher(linha);
			if (matcher.matches()) {
				
				Telefonema tel = new Telefonema();
				tel.setTelefone(DataAccess.formatTel(matcher.group(1),11));
				tel.setTimeStamp(matcher.group(2));
				String val = matcher.group(3);
				tel.setValor(Double.parseDouble(val.replaceAll(",", "."))); // precaução em caso de valor estar com vírgula
				
				telfs.add(tel);
			}
			
			linha = reader.readLine();
		}
		
//		for (Telefonema t: telfs) 
//			System.out.println(t);

		conta = new HashMap<String, Double>();
		conflitos = new HashMap<Telefonema, TelInfo>();

		conta.put(NAO_IDENTIFICADOS, 0d);
		conta.put(CONFLITO, 0d);
		
		// encontra responsáveis pelas ligações
		for (Telefonema t: telfs) {

			TelInfo info = DataAccess.telMap.get(t.getTelefone());
			
			if ((info == null) || (info.getUsuarios().size() == 0) ||
					(info.getUsuarios().get(0).equals(""))) { // telefonema não identificado
				
				Double valor = conta.get(NAO_IDENTIFICADOS);
				valor += t.getValor();
				conta.put(NAO_IDENTIFICADOS, valor);
			} else {
				
				if (info.getUsuarios().size() > 1) { // conflito

					Double valor = conta.get(CONFLITO);
					valor += t.getValor();
					conta.put(CONFLITO, valor);
					
					conflitos.put(t, info);
				}

				if (info.getUsuarios().size() == 1) { // encontramos o culpado!

					String user = info.getUsuarios().get(0);
					Double valor = conta.get(user);
					if (valor == null)
						valor = 0d;
					valor += t.getValor();
					conta.put(user, valor);
				}

			}
		}
				
		return conta;
	}

}
