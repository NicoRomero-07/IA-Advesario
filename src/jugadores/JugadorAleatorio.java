package jugadores;

import java.util.List;
import java.util.Random;

import mundoadversario.EstadoJuego;

/**
 * Jugador aleatorio.
 * Este jugador puede jugar a cualquier juego que implemente la interfaz EstadoJuego.
 * 
 * @author (2013) José Miguel Horcas Aguilera, Lorenzo Mandow
 * @param <E>
 *
 * @versión: 2018-10-03
 *
 */
public class JugadorAleatorio<E extends EstadoJuego<E>> implements Jugador<E> {

	private static Random rd = new Random();
	
	@Override
	public E mueve(E e) {
		List<E> lh = e.calculaSucesores();
		int n = rd.nextInt(lh.size());
		return lh.get(n);
	}
}
