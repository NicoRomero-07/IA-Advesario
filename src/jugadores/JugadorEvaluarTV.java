package jugadores;

import mundoadversario.EstadoJuego;
import mundosolitario.OverrideHashCode;

/**
 * Jugador que elige el siguiente movimiento evaluando los sucesores
 * inmediatos a la posición actual usando una Tabla de Valor calculada
 * mediante apriendizaje con refuerzo.
 * 
 * @author Lorenzo Mandow
 * @versión: 2018-10-03
 * */
public class JugadorEvaluarTV<E extends OverrideHashCode & EstadoJuego<E>> extends JugadorEntrenable<E> {
    
    public JugadorEvaluarTV(){
        super(new EvaluadorTV<E>());
    }
    
    public JugadorEvaluarTV(double alfa) {
        super(new EvaluadorTV<E>(alfa));
    }
}
