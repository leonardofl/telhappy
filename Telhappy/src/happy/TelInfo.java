package happy;

import java.util.ArrayList;
import java.util.List;

// informações de um dado de telefone (tabela telefones)
public class TelInfo {

	private List<String> usuarios; // possíveis usuários
	private String dono; // dono do telefone
	
	public TelInfo() {
		
		this.usuarios = new ArrayList<String>();
	}

	public TelInfo(String dono) {
		
		this();
		this.dono = dono;
	}

	public TelInfo(String dono, String usuario) {

		this();
		this.usuarios.add(usuario);
		this.dono = dono;
	}

	public void addUsuario(String usuario) {
		this.usuarios.add(usuario);
	}
	
	public List<String> getUsuarios() {
		
		// faz nova lista pra não vazar o encapsulamento ;-)
		List<String> result = new ArrayList<String>();
		for (String u: this.usuarios)
			result.add(u);
		return result;
	}
	
	public String getDono() {
		return dono;
	}
	
	public void setDono(String dono) {
		this.dono = dono;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dono == null) ? 0 : dono.hashCode());
		result = prime * result
				+ ((usuarios == null) ? 0 : usuarios.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TelInfo other = (TelInfo) obj;
		if (dono == null) {
			if (other.dono != null)
				return false;
		} else if (!dono.equals(other.dono))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TelInfo [dono=" + dono + ", usuarios=" + usuarios + "]";
	}
	
	
	
	
}
