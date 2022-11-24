package raton;

import conectak.ConectaK;
import jugadores.Jugador;
import jugadores.JugadorAleatorio;
import jugadores.JugadorAlfaBeta;
import jugadores.JugadorEvaluar;
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
        jugarTorneo(jugadorAleatorio, jugadorAleatorio2, r, 1000);

        EvaluadorRaton evaluadorRaton = new EvaluadorRaton();
        System.out.format("%30s", "Aleatorio vs Evaluar");
        JugadorAleatorio<Raton> jugadorAleatorio3 = new JugadorAleatorio<Raton>();
        JugadorEvaluar<Raton> jugadorEvaluar = new JugadorEvaluar<Raton>(evaluadorRaton);
        jugarTorneo(jugadorAleatorio3, jugadorEvaluar, r, 1000);

        for(int i=2; i<=6;i+=2){
            System.out.format("%30s", "Aleatorio vs AlfaBeta p("+i+")");
            JugadorAleatorio<Raton> jugadorAleatorio4 = new JugadorAleatorio<Raton>();
            JugadorAlfaBeta<Raton> jugadorAlfaBeta = new JugadorAlfaBeta<Raton>(evaluadorRaton,i);
            jugarTorneo(jugadorAleatorio4, jugadorAlfaBeta, r, 1000);
        }

    }

    private static void jugarTorneo(Jugador<Raton> j1, Jugador<Raton> j2, Raton raton, int n){
        Juego<Raton,Jugador<Raton>,Jugador<Raton>> juego = new Juego<Raton,Jugador<Raton>,Jugador<Raton>>(j1,j2,raton);
        juego.realizarNPartidas(n);
    }
}
