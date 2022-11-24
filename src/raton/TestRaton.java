package raton;

import jugadores.Jugador;
import jugadores.JugadorAleatorio;
import mundoadversario.Juego;

public class TestRaton {
    public static void main(String[] args) {

        Raton r = new Raton(4);
        System.out.printf("%30s %10s %10s %10s", " ", "Gana 1", "Empate", "Gana 2");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

        System.out.format("%30s", "Aleatorio vs Aleatorio");
        JugadorAleatorio<Raton> jugadorAleatorio = new JugadorAleatorio<Raton>();
        JugadorAleatorio<Raton> jugadorAleatorio2 = new JugadorAleatorio<Raton>();
        Juego<Raton, JugadorAleatorio<Raton>, JugadorAleatorio<Raton>> juego = new Juego(jugadorAleatorio, jugadorAleatorio2, r);

        System.out.format("%30s", "Aleatorio vs Evaluar");
        juego.jugarPartida(true);
    }
}
