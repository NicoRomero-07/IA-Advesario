package raton;

import jugadores.Evaluador;

public class EvaluadorRaton extends Evaluador<Raton> {

    private double T;

    public EvaluadorRaton(double T){
        super();
        this.T = T;
    }
    @Override
    protected double evaluacion(Raton estado, boolean miTurno) {
        return T * (nDelante(estado) + distRaton(estado));
    }

    private double distRaton(Raton estado) {
        return estado.fRaton();
    }

    private double nDelante(Raton estado) {
        return estado.numGatosColumnaRaton();
    }
}

