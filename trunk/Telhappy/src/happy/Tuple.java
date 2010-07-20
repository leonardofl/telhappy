package happy;

/**
 * Corresponde a registro da tabela 'telefones'
 *
 */
public class Tuple {

	int codigo;
	String usuario, telefone, dono;

	public Tuple() {
	
	}
	

	public Tuple(int codigo, String usuario, String telefone, String dono) {
		this.codigo = codigo;
		this.usuario = usuario;
		this.telefone = telefone;
		this.dono = dono;
	}

	public Tuple(String usuario, String telefone, String dono) {
		this.usuario = usuario;
		this.telefone = telefone;
		this.dono = dono;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
		result = prime * result + codigo;
		result = prime * result + ((dono == null) ? 0 : dono.hashCode());
		result = prime * result
				+ ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}


	// não compara código!
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
//		if (codigo != other.codigo)
//			return false;
		if (dono == null) {
			if (other.dono != null)
				return false;
		} else if (!dono.equals(other.dono))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Tuple [codigo=" + codigo + ", dono=" + dono + ", telefone="
				+ telefone + ", usuario=" + usuario + "]";
	}

	
}
