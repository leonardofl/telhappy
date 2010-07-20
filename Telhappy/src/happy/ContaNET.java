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

/**
 * Classe que realiza a conta automática da NET
 * 06/2010
 * @author leonardo
 *
 */
public class ContaNET implements ContaAutomatica {
	
	private static String TEL_ER = "\\d{10}";
	private static String DIN_ER = "\\d*?,\\d{2}";
	private static String DAT_ER = "\\d{2} / \\d{2} / \\d{2}";
	private static String INI = "^CODIGO DA CONTA:.*"; // código da franquia poderia ser confundido com um telefone 
	private static String LOCAL = "LIGACOES LOCAIS PARA TELEFONES FIXOS - DURACA";
		
	// aqui fica o resultado final do nosso cálculo de divisão da conta
	private Map<String, Double> conta;
	private Map<Telefonema, TelInfo> conflitos; // em casos de conflitos, ver o time-stamp ajuda ao usuário identificar o telefonema na caderneta
	
	// estados da máquina de busca
	private enum Estados {STARTING, PARSING};
	
	private BufferedReader reader;

	public ContaNET (File sourceFile) {
		
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
	public Map<String, Double> executar() throws IOException {
		
		Pattern telPattern = Pattern.compile(TEL_ER);
		Pattern dinPattern = Pattern.compile(DIN_ER);
		Pattern datPattern = Pattern.compile(DAT_ER);
		Pattern iniPattern = Pattern.compile(INI);
		
		List<String> tels = new ArrayList<String>();
		List<String> dins = new ArrayList<String>();
		List<String> dats = new ArrayList<String>();
				
		Matcher matcher;
		Estados estado = Estados.STARTING;
		int b = 0; // balanceamento de tels x dins
		boolean local = false; // indica que estamos na região dos telefonemas para fixo locais
		String line = reader.readLine();
		
		while (line != null) {

			switch (estado) {
			
			case STARTING:
				matcher = iniPattern.matcher(line);
				if (matcher.matches()) { 
					estado = Estados.PARSING;
				}
				break;
				
			case PARSING:
				
				// procura por telefonemas
				matcher = telPattern.matcher(line);
				while (matcher.find()) {
					
					String tel = matcher.group(); 
					tels.add(tel);
					b++;
					if (local)
						dats.add("");
				}
				
				if (line.equals(LOCAL)) 
					local = true;
				
				// procura por datas
				if (!local) { // ligações locais não têm time-stamp
					matcher = datPattern.matcher(line);
					while (matcher.find()) {
						
						String dat = matcher.group();
						dat = dat.replaceAll(" ", "");
						dats.add(dat);
					}
				}
				
				// procura por dinheiro
				matcher = dinPattern.matcher(line);
				while (matcher.find()) {
					
					if (b!=0) { // evita que se pegue os sub-totais
						String din = matcher.group();
						din = din.replace(',', '.'); // ajudando o java nas conversões ^^
						dins.add(din);
						b--;
					}				

					local = false;
				}
				
				break;
			}
			
			line = reader.readLine();
		}
		
//		int i = 0;
//		for (String t: tels) 
//			System.out.println(t + " - " + dats.get(i) + " - " + dins.get(i++));

		conta = new HashMap<String, Double>();
		conflitos = new HashMap<Telefonema, TelInfo>();
		
		conta.put(NAO_IDENTIFICADOS, 0d);
		conta.put(CONFLITO, 0d);
		
		// encontra responsáveis pelas ligações
		int i = 0;
		for (String t: tels) {

			Double din = Double.parseDouble(dins.get(i));
			String timeStamp = dats.get(i);
			i++;
			TelInfo info = DataAccess.telMap.get(t);
			
			
			if ((info == null) || (info.getUsuarios().size() == 0) ||
					(info.getUsuarios().get(0).equals(""))) { // telefonema não identificado
				
				Double valor = conta.get(NAO_IDENTIFICADOS);
				if (valor == null)
					valor = 0d;
				valor += din;
				conta.put(NAO_IDENTIFICADOS, valor);
			} else {
				
				if (info.getUsuarios().size() > 1) { // conflito

					Double valor = conta.get(CONFLITO);
					if (valor == null)
						valor = 0d;
					valor += din;
					conta.put(CONFLITO, valor);
					
					Telefonema telefonema = new Telefonema(t, timeStamp, valor);
					conflitos.put(telefonema, info);
				}

				if (info.getUsuarios().size() == 1) { // encontramos o culpado!

					String user = info.getUsuarios().get(0);
					Double valor = conta.get(user);
					if (valor == null)
						valor = 0d;
					valor += din;
					conta.put(user, valor);
				}

			}
		}
				
		return conta;
	}
	
	public void printSource() throws IOException {
		
		String line = reader.readLine();
		while (line != null) {
			
			System.out.println(line);
			line = reader.readLine();
		}
	}

	@Override
	public Map<Telefonema, TelInfo> conflitos() {

		return conflitos;
	}

}
