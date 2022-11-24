package conectak;

/**
 * Representación de las fichas para los juegos.
 *  
 * @author José Miguel Horcas Aguilera (2010)
 * 
 * @version 2010 / 2018
 *
 */
public class Ficha {

	private char f;			// Representación de la ficha.
	
	
	/**
	 * Constructor.
	 * 
	 * @param f	Caracter que representa la ficha.
	 */
	public Ficha (char c) {
		this.f = c;
	}
	
	/**
	 * Selector.
	 * @return	Caracter que representa la ficha.
	 */
	public char f () {
		return f;
	}
	
//	public boolean equals (Object o) {
//		return o instanceof Ficha && this.f() == ((Ficha) o).f();
//	}
	
	public String toString () {
		return "" + f();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + f;
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
		Ficha other = (Ficha) obj;
		if (f != other.f)
			return false;
		return true;
	}
}
