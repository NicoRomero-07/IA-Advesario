package jugadores;

import mundoadversario.EstadoJuego;
import mundoadversario.EstadoJuegoAprox;


/**
* Jugador que evalúa los sucesores inmediatos usando una función de evaluación lineal.
* 
* @author Lorenzo Mandow
* @param <E>  Clase de estados del juego a los que jugará el jugador
* 
* @versión: 2018-10-03
*
*/

public class JugadorEvaluarLineal<E extends EstadoJuegoAprox<E>> extends JugadorEntrenable<E> {
    
    public JugadorEvaluarLineal(E estadoIni) {
    	super(new EvaluadorAproxLineal<E>(estadoIni.codifica().length));
    }
    
    public JugadorEvaluarLineal(E estadoIni, double alfa) {
    	super(new EvaluadorAproxLineal<E>(estadoIni.codifica().length, alfa));
    }
    
    public double[] consultarPesos(){
    	return ((EvaluadorAproxLineal<E>) this.evaluador).consultarPesos();
    }
    
}
