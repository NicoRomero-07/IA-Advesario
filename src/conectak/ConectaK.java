package conectak;

import java.util.ArrayList;
import java.util.List;

import mundoadversario.EstadoJuegoAprox;
import mundosolitario.OverrideHashCode;


/**
 * Juego del conecta-k con dos jugadores que usan fichas diferentes y se alternan en sus movimientos.
 * Esta clase representa los distintos estados en que puede encontrarse el juego del conecta-k.
 * 
 * @author José Miguel Horcas Aguilera, Lorenzo Mandow
 *
 */
public class ConectaK extends OverrideHashCode implements EstadoJuegoAprox<ConectaK> {

    private static Ficha ficha1 = new Ficha('X');	// Ficha del jugador 1 (el que empieza el juego).
	private static Ficha ficha2 = new Ficha('O');	// Ficha del jugador 2.
    
	private TableroCK tablero;						// Tablero del juego.
    private int longGanadora;						// Longitud ganadora (K).
	
    //Los siguientes valores se conservan por motivos de eficiencia:
    private Movimiento ultimoMov;					// Último movimiento realizado.
    private int it;									// Número de movimientos (ply) realizados (iteraciones del juego).
	private boolean turno1;
	

    //Para la codificaciÃ³n de los estados
     private static EvaluadorCK ev = null;

    
	/**
	 * Constructor de un estado inicial del juego (tablero vacío, le toca al primer jugador).
	 * 
	 * @param nFil 	Número de filas.
	 * @param nCol 	Número de columnas.
	 * @param k		Longitud ganadora.
	 */
	public ConectaK (int nFil, int nCol, int k) {
		this.turno1 = true;
		tablero = new TableroCK(nFil, nCol);
	    longGanadora = k;
		
	    ultimoMov = null;
	    it = 0;
	    
	    ev = new EvaluadorCK(nFil, nCol, k);
	}
	
	

	/**
	 * Constructor que crea un nuevo estado.
	 * 
	 * @param it		Número de jugadas.
	 * @param turno1		¿Le toca jugar al primer jugador?
	 * @param tablero	Tablero.
	 * @param ultimoMov	Último movimiento realizado.
	 * @param k			Longitud ganadora.
	 */
	private ConectaK (int it, boolean turno1, TableroCK tablero, Movimiento ultimoMov, int k) {
		this.longGanadora = k;
		this.it = it;
		this.turno1 = turno1;
		this.tablero = tablero;
		this.ultimoMov = ultimoMov;
	}
	
	/**
	 * 
	 * @return Longitud Ganadora.
	 */
	public int longGanadora () {
		return longGanadora;
	}
	
	/**
	 * Por motivos de eficiencia no se proporciona una copia del tablero.
	 * El usuario no debería modificarlo.
	 * @return El tablero del estado actual.
	 */
	public Tablero tablero() {
		return tablero;
	}
	
	/**
	 * @return Ficha del primer jugador
	 */
	public Ficha fichaPrimero(){
		return ficha1;
	}
	
	/**
	 * @return Ficha del segundo jugador
	 */
	public Ficha fichaSegundo(){
		return ficha2;
	}
	
	/**
	 * @return Ficha del jugador al que le toca jugar.
	 */
	
	public Ficha fichaActual () {
		return turno1 ? ficha1 : ficha2;
	}
	
	/**
	 * @return Ficha del jugador contrario al que le toca jugar.
	 */
	
	public Ficha fichaOtro () {
		return turno1 ? ficha2 : ficha1;
	}
	
	/**
	 * @param c Columna.
	 * @return	Verdadero si es posible soltar una ficha en la columna c.
	 */
	public boolean columnaLibre (int c) {
		return 0 <= c && c < tablero.nColumnas() && tablero.columnaLibre(c);
	}

	@Override
	public boolean agotado() {
		return it == tablero.nFilas()*tablero.nColumnas();
	}

	@Override
	public boolean ganaActual() {
		return false;  //el último que movió fue el otro, y es el que podría haber ganado
	}

	@Override
	public boolean ganaOtro() {
		if (ultimoMov != null) {  //si el movimiento anterior enlazó k fichas, entonces ha ganado el otro
			int f = ultimoMov.f();
			int c = ultimoMov.c();
			int n1,n2,n3,n4;
			n1 = tablero.conectadas(f, c, 1, 0);
			n2 = tablero.conectadas(f, c, 0, 1);
			n3 = tablero.conectadas(f, c, -1, 1);
			n4 = tablero.conectadas(f, c, 1, 1);
			int max = Math.max(Math.max(Math.max(n1, n2), n3), n4);
			
			return (longGanadora <= max);
			
		} else {  //todavía no se realizó ningún movimiento
			return false;
		}//if
	}
	

	@Override
	public List<ConectaK> calculaSucesores() {
		List<ConectaK> h = new ArrayList<ConectaK>();
		
		for (int i = 0; i < tablero.nColumnas(); i++) {
			if (columnaLibre(i)) {
				h.add(elegirSucNth(i));
			}
		}
		return h;
	}

	/**
	 * 
	 * @param c Columna.
	 * @return	Estado resultado de mover en la columna c.
	 */
	
	public ConectaK elegirSucNth (int c) {
		ConectaK nuevoEstado = new ConectaK(it+1, !turno1, new TableroCK(tablero), null, longGanadora);
		nuevoEstado.ultimoMov = nuevoEstado.tablero.soltarFicha(c, fichaActual());
		return nuevoEstado;
	}
	
	public void ver () {
		String res = "";
		
		tablero.ver();
		
		res += "It: " + it + '\n';
		res += "Longitud ganadora: " + longGanadora + '\n';
		res += "Último movimiento: " + ultimoMov + '\n';
		res += "Ficha jugador 1: " + ficha1.toString() + "  Ficha jugador 2: " + ficha2.toString() + '\n';
		res += "Le toca jugar al jugador ";
		res += turno1 ? "1" : "2";
		res += '\n';
		System.out.println(res);
	}
	
	/**
	 *  MÃ©todo para la clase abstracta EstadoJuegoTV
	 *  El estado viene definido unÃ­vocamente por la situaciÃ³n del tablero y el turno.
	 */
	@Override
	public String toString () {
		return "\n" + tablero.toString() + "\n" + (turno1 ? "1" : "2");
	}
    
    /**
     * MÃ©todo de la interfaz EstadoJuegoAprox, para usar con aproximadores de funciones.
     */

	@Override
	public int[] codifica() {
		return ConectaK.ev.caracteristicas(this, true);  //tomamos como referencia el primer jugador		
	}

	@Override
	public boolean turno1() {
		return this.turno1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tablero == null) ? 0 : tablero.hashCode());
		result = prime * result + (turno1 ? 1231 : 1237);
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
		ConectaK other = (ConectaK) obj;
		if (tablero == null) {
			if (other.tablero != null)
				return false;
		} else if (!tablero.equals(other.tablero))
			return false;
		if (turno1 != other.turno1)
			return false;
		return true;
	}
	
	
}
