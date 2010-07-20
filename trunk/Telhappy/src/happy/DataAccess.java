package happy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccess {
	
	// nomes de campos das tabelas
	public static String CODIGO = "codigo";	
	public static String TELEFONE = "telefone";
	public static String USUARIO = "usuario";
	public static String DONO = "dono";
	public static String MORADOR = "morador";
	
	public static Map<String, TelInfo> telMap = new HashMap<String, TelInfo>();
	public static List<String> moradores = new ArrayList<String>();
	
	private static Connection conn;
	
	// carrega banco de dados em telMap e moradores
	public static void loadDataBase() {
		
		// conecta com banco de dados
		conn = null;
		try {
			
			Class.forName("org.sqlite.JDBC"); // testa se driver está disponível
			conn = DriverManager.getConnection("jdbc:sqlite:./resources/telhappy.db");
		} catch (ClassNotFoundException e1) {
			System.out.println("Driver não encontrado");
		} catch (SQLException e) {
			System.out.println("Problemas com a conexão com telhappy.db");
		}		
		
		// tabela moradores
		String sql = "SELECT * FROM moradores";
		Statement stm;
		ResultSet rs;
		try {

			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next())
				moradores.add(rs.getString("MORADOR"));
		} catch (SQLException e) {
			System.out.println("Problemas com SQL");
			e.printStackTrace();
		}
		
		// tabela telefones
		sql = "SELECT * FROM telefones";
		try {

			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) { // percorre tabela telefones
				
				String tel = rs.getString(TELEFONE);
				
				if (!telMap.containsKey(tel)) { // primeira vez q tel é encontrado
					
					TelInfo info = new TelInfo(rs.getString(DONO), rs.getString(USUARIO));
					telMap.put(tel, info);
				} else { // já tem esse tel
					
					TelInfo info = telMap.get(tel);
					info.addUsuario(rs.getString(USUARIO));
				}
			}
		} catch (SQLException e) {
			System.out.println("Problemas com SQL");
			e.printStackTrace();
		}

	}
	
	public static boolean insertMorador(String morador) {
		
		// insere no banco de dados
		String sql = "INSERT INTO moradores (morador) VALUES ('" + morador + "')";
		try {

			Statement stm = conn.createStatement();
			stm.execute(sql);
		} catch (SQLException e) {
			System.out.println("Problemas com SQL");
			e.printStackTrace();
			return false;
		}
		
		// atualiza lista
		moradores.add(morador);
		
		return true; // se chegou até aqui, é porque não deu exception
	}

	public static boolean removeMorador(String morador) {
		
		// remove do banco de dados
		String sql = "DELETE FROM moradores WHERE morador = '" + morador + "'";
		try {

			Statement stm = conn.createStatement();
			stm.execute(sql);
		} catch (SQLException e) {
			System.out.println("Problemas com SQL");
			e.printStackTrace();
			return false;
		}
		
		// atualiza lista
		moradores.remove(morador);
		
		return true; // se chegou até aqui, é porque não deu exception
	}

	public static boolean inserTel(String tel, String usuario, String dono) {
		
		String sql = "INSERT INTO telefones (telefone, usuario, dono) VALUES " +
				"('" + tel + "', '" + usuario + "', '" + dono + "')";
		try {

			Statement stm = conn.createStatement();
			stm.execute(sql);
		} catch (SQLException e) {
			System.out.println("Problemas com SQL");
			e.printStackTrace();
			return false;
		}
		
		// atualiza mapa
		telMap.put(tel, new TelInfo(dono, usuario));
		
		return true; // se chegou até aqui, é porque não deu exception
	}
	
	public static List<String> buscaTel(String telefone) {

		List<String> result = new ArrayList<String>();
		String t = formatTel(telefone, 11); // TODO: isto está certo?
		String sql = "SELECT * FROM telefones WHERE telefone like '%" + t + "%'";
		try {

			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				String tel = rs.getString(TELEFONE);
				if (!result.contains(tel))
					result.add(tel);
			}
		} catch (SQLException e) {
			System.out.println("Problemas com SQL");
			e.printStackTrace();
		}
		
		return result;
	}

	// limpeza na base de dados
	public static void apagaRepetidos() {
		
		String sql = "SELECT * FROM telefones";
		List<Tuple> tuples = new ArrayList<Tuple>();
		List<Tuple> repetidos = new ArrayList<Tuple>();
		
		// pega toda a base de dados!!!
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				
				int cod = rs.getInt(CODIGO);
				String u = rs.getString(USUARIO);
				String tel = rs.getString(TELEFONE);
				String dono = rs.getString(DONO);
				
				Tuple tuple = new Tuple (cod, u, tel, dono);
				tuples.add(tuple);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// vê uma a uma se tem repetidos; se tiver põe na lista de repetidos
		for (Tuple t: tuples) {
			
			for (Tuple t2: tuples) {

				if (t.equals(t2) && (t.getCodigo() != t2.getCodigo()))
					if (!repetidos.contains(t))
						repetidos.add(t);
			}
		}
		
		// percorre lista de repetidos, finalmente excluindo do banco de dados
		for (Tuple t: repetidos) {
			
			sql = "DELETE FROM telefones WHERE codigo = " + Integer.toString(t.getCodigo());

			try {

				System.out.println("Excluindo " + t.getCodigo());
				Statement stm = conn.createStatement();
				stm.execute(sql);
			} catch (SQLException e) {
				System.out.println("Problemas com SQL");
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * Altera o formato do telefone para d{10}
	 * @param tel telefone original do mysql
	 * @param dddDefault ddd usado qd não houver um no tel de origem 
	 * @return número de telefone bem fomrado, sem parênteses, com ddd
	 */
	public static String formatTel(String tel, int dddDefault) {
		
		String ddd = Integer.toString(dddDefault);
		
		// tira lixo
		tel = tel.replaceAll("[^\\d]", ""); // some com tudo que não for dígito
		
		// coloca ddd
		if (tel.length() <= 8)
			tel = ddd + tel; 
		
		return tel;
	}

}
