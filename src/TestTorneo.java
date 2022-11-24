import conectak.ConectaK;
import conectak.EvaluadorCK;
import jugadores.*;
import mundoadversario.Juego;

public class TestTorneo {

    public static void main(String[] args) {
        int nCol=3,nFil=3,longGanadora=3, n=1000;
        ConectaK conectaK= new ConectaK(nFil,nCol,longGanadora);

        System.out.printf("%30s %10s %10s %10s", " ", "Gana 1", "Empate", "Gana 2");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");

        System.out.format("%30s", "Aleatorio vs Aleatorio");
        JugadorAleatorio<ConectaK> aleatorio1 = new JugadorAleatorio<ConectaK>();
        JugadorAleatorio<ConectaK> aleatorio2 = new JugadorAleatorio<ConectaK>();
        jugarTorneo(aleatorio1,aleatorio2,conectaK,n);

        System.out.format("%30s", "Aleatorio vs Evaluar");
        Evaluador<ConectaK> evaluador = new EvaluadorCK(nFil,nCol,longGanadora);
        JugadorAleatorio<ConectaK> manolo = new JugadorAleatorio<ConectaK>();
        JugadorEvaluar<ConectaK> juan = new JugadorEvaluar<ConectaK>(evaluador);
        jugarTorneo(manolo,juan,conectaK,n);

        for(int i=2; i<7;i++){
            System.out.format("%30s", "Aleatorio vs AlfaBeta p("+i+")");
            JugadorAleatorio<ConectaK> aleatorio3 = new JugadorAleatorio<ConectaK>();
            JugadorAlfaBeta<ConectaK> alfaBeta = new JugadorAlfaBeta<ConectaK>(evaluador,i);
            jugarTorneo(aleatorio3,alfaBeta,conectaK,n);
        }



    }

    private static void jugarTorneo(Jugador<ConectaK> j1, Jugador<ConectaK> j2, ConectaK conectaK, int n){
        Juego<ConectaK,Jugador<ConectaK>,Jugador<ConectaK>> juego = new Juego(j1,j2,conectaK);
        juego.realizarNPartidas(n);
    }
}
