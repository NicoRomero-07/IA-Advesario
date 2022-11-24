package conectak;


import jugadores.Evaluador;

/**
 * Clase EvaluadorCK que implementa un evaluador heurístico para el problema del Conecta-K.
 * basado en una matriz de posibilidades.
 * La matriz de posibilidades recoge, para cada posición del tablero, las posibilidades de
 * conectar k fichas en las que interviene.
 * La evaluación se realiza de la siguiente manera: dado un estado del juego, se calcula el
 * número de posibilidades de conectar k fichas que le quedan a MAX (A), y el número de 
 * posibilidades de conectar k fichas que le quedan a MIN (I). El resultado de la evaluación
 * es A - I.
 * 
 * @author José Miguel Horcas Aguilera, Lorenzo Mandow
 * 
 * @version 2017-11-20 (anterior 2013-10-22)
 *
 */
public class EvaluadorCK extends Evaluador<ConectaK> {

	private MatrizPosibilidades matriz;

	/**
	 * Constructor.
	 * Crea la matriz de posibilidades.
	 * 
	 * @param nFilas		Número de filas.
	 * @param nColumnas		Número de columnas.
	 * @param k				Longitud ganadora.
	 */
	public EvaluadorCK(int nFilas, int nColumnas, int k) {
		matriz = new MatrizPosibilidades(nFilas, nColumnas, k);
	}
	
	/**
	 * Devuelve un vector con las características del estado para el turno indicado, que son:
	 * 
 	 * 0.- un valor que vale siempre 1
	 * 1.- el número de posibilidades de MAX,
	 * 2.- el número de posibilidades de MIN.
	 * 
	 * @return Vector de 3 características
	 */
	public int[] caracteristicas (ConectaK estado, boolean miTurno){
	
		Ficha fmax;
		Ficha fmin;
		
		if (miTurno){ //miTurno == true -> primer jugador
			fmax = estado.fichaPrimero();
			fmin = estado.fichaSegundo();
		} else {
			fmax = estado.fichaSegundo();
			fmin = estado.fichaPrimero();
		}//if miTurno
		
		int n = matriz.numPosibilidades();
		Tablero tab = estado.tablero();	
		int[] posiMax = new int[n];
		int[] posiMin = new int[n];
		
		// Inicialmente se consideran todas las posibilidades abiertas (1) para ambos jugadores.
		for (int i = 0; i < n; i++) {
			posiMax[i] = 1;
			posiMin[i] = 1;
		}
		
		// A continuación se marcan aquellas posibilidades cerradas (0) por las fichas del oponente.
		for (int f = 0; f < tab.nFilas(); f++) {
			for (int c = 0; c < tab.nColumnas(); c++) {
				for (Integer op : matriz.posibilidades(f, c)) {
					if (tab.contenido(f, c).equals(fmin)) {
						posiMax[op] = 0;
					} else if (tab.contenido(f, c).equals(fmax)) {
						posiMin[op] = 0;
					}
				}
			}
		}
		//Se devuelven los resultados
		int[] resultado = new int[3];
		resultado[0] = 1;              //término independiente para la codificación
		resultado[1] = sumatorioArray(posiMax);
		resultado[2] = sumatorioArray(posiMin);
		return resultado;
	}

	@Override
	protected double evaluacion(ConectaK estado, boolean miTurno){
		
		int[] resultados = caracteristicas(estado, miTurno);
		return resultados[1] - resultados[2];
	}
	
	/**
	 * 
	 * @param a Array.
	 * @return	Suma de todos los elementos del array unidimensional a.
	 */
	private int sumatorioArray(int[] a) {
		int suma = 0;
		
		for (int i = 0; i < a.length; i++) {
			suma += a[i];
		}
		return suma;
	}
}
