package happy;

import java.io.IOException;
import java.util.Map;

public interface ContaAutomatica {

	// use esta chave para ver o valor dos telefonemas não identificados
	public static String NAO_IDENTIFICADOS = "Não identificados";
	// use esta chave para ver o valor dos telefonemas com conflito
	public static String CONFLITO = "Conflitos";
	
	/**
	 * Executa o cálculo de divisão da conta telefônica
	 * @return mapa onde as chaves são so nomes dos moradores e o valor é o quanto eles devem pagar
	 */
	public Map<String, Double> executar() throws IOException;
	
	/**
	 * Após executar(), retorna as ligações com conflito (mais de um provável usuário)
	 * @return mapa onde chave é o telefonema efetuado, e valor é informação sobre o telefone  
	 */
	public Map<Telefonema, TelInfo> conflitos();

}
