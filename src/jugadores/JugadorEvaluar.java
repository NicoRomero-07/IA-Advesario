package jugadores;

import java.util.Collections;
import java.util.List;

import mundoadversario.EstadoJuego;

/**
 * Jugador que elige el siguiente movimiento evaluando los sucesores
 * inmediatos a la posición actual.
 * 
 * @author Lorenzo Mandow
 * @param <E>  Clase de estados del juego a los que jugará el jugador
 * 
 * @versión: 2018-10-03
 *
 */
public class JugadorEvaluar<E extends EstadoJuego<E>> implements Jugador<E> {
	
	public Evaluador<E> evaluador;
	
	public JugadorEvaluar(Evaluador<E> ev) {
		evaluador = ev;
	}
	
	/**
	 * Genera todos los hijos del estado recibido, y devuelve el que reciba una mayor
	 * evaluación. La lista de hijos se baraja antes de analizarla para evitar un comportamiento
	 * determinista, es decir, si hay varios hijos con la evaluación óptima, distintas
	 * llamada al método pueden devolver distintos hijos óptimos.
	 * 
	 * @param e el estado del juego a partir del cual debe elegirse el movimiento.
	 * 
	 * @return el estado del juego resultante a la realización del movimiento elegido.
	 */
	@Override
	public E mueve(E e) {
		
		boolean miTurno = e.turno1();
		
		E mejorE = null;
		double mejorV = Double.NEGATIVE_INFINITY;
		double v2;
		
		for (E e2 : barajar(e.calculaSucesores())) {
			v2 = evaluador.evalua(e2, miTurno);
			if ((v2 > mejorV)|| (mejorE == null)) {
				mejorV = v2;
				mejorE = e2;
			}
		}
		return mejorE;
	}
	
	/**
	 * Baraja aleatoriamente una lista de EstadoJuego.
	 * 
	 * @param list Lista de EstadoJuego.
	 * @return	Lista barajada aleatoriamente.
	 */
	List<E> barajar (List<E> list) {
		Collections.shuffle(list);
		return list;
	}
}
