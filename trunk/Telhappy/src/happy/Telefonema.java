package happy;

import java.text.DecimalFormat;

/**
 * Representa um telefonema efetuado para um certo número, em um certo momento
 * @author leonardo
 *
 */
public class Telefonema {

	DecimalFormat decimal = new DecimalFormat( "0.00" );

	private String telefone, timeStamp;
	private double valor;

	public Telefonema() {
		
	}

	public Telefonema(String telefone, String timeStamp, double valor) {
		super();
		this.telefone = telefone;
		this.timeStamp = timeStamp;
		this.valor = valor;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result
				+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Telefonema other = (Telefonema) obj;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (Double.doubleToLongBits(valor) != Double
				.doubleToLongBits(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Telefonema [telefone=" + telefone + ", timeStamp=" + timeStamp
				+ ", valor=" + decimal.format(valor) + "]";
	}

	
	
}
