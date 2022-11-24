package raton;

import jugadores.Evaluador;

public class EvaluadorRaton extends Evaluador<Raton> {

    @Override
    protected double evaluacion(Raton estado, boolean miTurno) {
        double T;
        if(miTurno) T = 1;
        else T = -1;

        return T * (nDelante(estado) + distRaton(estado));
    }

    private double distRaton(Raton estado) {
        return estado.n() - estado.fRaton() - 1;
    }

    private double nDelante(Raton estado) {
        return estado.numGatosColumnaRaton();
    }
}

