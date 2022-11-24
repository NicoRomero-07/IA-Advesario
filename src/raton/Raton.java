package raton;

import mundoadversario.EstadoJuego;
import mundoadversario.EstadoJuegoAprox;
import mundosolitario.OverrideHashCode;

import java.util.ArrayList;
import java.util.List;

public class Raton extends OverrideHashCode implements EstadoJuegoAprox<Raton> {

    private char[][] tablero;
    private boolean turno1;

    public Raton(int n){
        tablero = new char[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                tablero[i][j] = ' ';
            }
        }
        for(int i= 0; i < n; i++){
            if(i%2 == 0) tablero[0][i] = 'G';
        }
        tablero[n-1][1] = 'R';
        turno1 = true;
    }

    public Raton(int n, int fr, int cr, int[] fgatos, int[] cgatos, boolean turno1)
    {
        tablero = new char[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                tablero[i][j] = ' ';
            }
        }
        for(int i = 0; i < fgatos.length; i++){
            tablero[fgatos[i]][cgatos[i]] = 'G';
        }
        tablero[fr][cr] = 'R';
        this.turno1 = turno1;
    }
    public int numGatosColumnaRaton(){
        int filaRaton = fRaton();
        int cont = 0;
        for(int i = 0; i < filaRaton; i++){
            if(tablero[i][1] == 'G') cont++;
        }
        return cont;
    }
    public int fRaton(){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero.length; j++){
                if(tablero[i][j] == 'R') return i;
            }
        }
        return -1;
    }
    public int cRaton(){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero.length; j++){
                if(tablero[i][j] == 'R') return j;
            }
        }
        return -1;
    }
    public int[] fGatos(){
        int[] res = new int[(tablero.length+1)/2];
        int cont = 0;
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero.length; j++){
                if(tablero[i][j] == 'G') {
                    res[cont] = i;
                    cont++;
                }
            }
        }
        return res;
    }
    public int[] cGatos(){
        int[] res = new int[(tablero.length+1)/2];
        int cont = 0;
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero.length; j++){
                if(tablero[i][j] == 'G') {
                    res[cont] = j;
                    cont++;
                }
            }
        }
        return res;
    }

    public int n(){
        return tablero.length;
    }

    @Override
    public boolean turno1() {
        return this.turno1;
    }

    @Override
    public boolean ganaActual() {
        return false;
    }

    @Override
    public boolean ganaOtro() {
        return (!turno1 && fRaton() == 0) || (calculaSucesores().size() == 0);
    }

    @Override
    public boolean agotado() {
        return false;
    }

    @Override
    public void ver() {
        String turnoDe = "RATON";
        if(!turno1) turnoDe = "GATOS";

        System.out.println("TURNO "+turnoDe);
        System.out.println("-----------------");
        for(int i = 0; i < tablero.length; i++){
            System.out.print("|");
            for(int j = 0; j < tablero.length; j++){
                System.out.print(tablero[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    @Override
    public int[] codifica() {
        return new int[0];
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        Raton raton = (Raton) obj;
        boolean iguales = true;
        int i = 0;
        int j = 0;
        while(iguales && i < tablero.length){
            while (iguales && j < tablero.length){
                if(tablero[i][j] != raton.tablero[i][j]) iguales = false;
                j++;
            }
            i++;
        }
        return iguales;
    }

    @Override
    public List<Raton> calculaSucesores() {
        List<Raton> sucesores = new ArrayList<>();

        if(turno1){
            sucesores = calculaSucesoresRaton();
        }else{
            sucesores = calculaSucesoresGato();
        }

        return sucesores;
    }


    private void añadirEstadoGato(int n, int[] fgatos, int[] cgatos, List<Raton> sucesores, int f,int c, int i){
        int[] fgatos2 = new int[n];
        int[] cgatos2 = new int[n];
        for(int j = 0; j < n; j++){
            fgatos2[j] = fgatos[j];
            cgatos2[j] = cgatos[j];
        }
        fgatos2[i] = f;
        cgatos2[i] = c;
        sucesores.add(new Raton(tablero.length,fRaton(),cRaton(),fgatos2,cgatos2,!turno1));
    }
    private List<Raton> calculaSucesoresGato() {
        List<Raton> sucesores = new ArrayList<>();
        int[] fgatos = fGatos();
        int[] cgatos = cGatos();
        int n = fgatos.length;
        int f,c;
        int i=0;
        while(i<n){
            f = fgatos[i];
            c = cgatos[i];

            if(f+1<tablero.length){
                if(c+1<tablero[0].length && tablero[f+1][c+1] == ' '){
                    añadirEstadoGato(n, fgatos, cgatos, sucesores, f+1, c+1, i);
                }
                if(c>0 && tablero[f+1][c-1] == ' '){
                    añadirEstadoGato(n, fgatos, cgatos, sucesores, f+1, c-1, i);
                }
            }
            i++;
        }
        return sucesores;
    }




    private List<Raton> calculaSucesoresRaton() {
        List<Raton> sucesores = new ArrayList<>();
        int f = fRaton(), c = cRaton();
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                if(i!=0 && j!=0){
                    if(f+i>=0 && f+i<tablero.length && c+j>=0 && c+j<tablero.length){
                        if(tablero[f+i][c+j] == ' '){
                            sucesores.add(new Raton(tablero.length, f+i, c+j, fGatos(), cGatos(), false));
                        }
                    }
                }
            }
        }
        return sucesores;
    }
}
