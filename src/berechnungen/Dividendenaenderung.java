package berechnungen;

import java.util.Random;

/**
 *  Ver�ndere eine Dividende in gegebenem Intervall zuf�llig.
 *  @author Philipp Gressly Freimann(phi@gressly.eu)
 *  
 */
/*
 * History: first Implementation: Dec 20, 2011
 * Bugs   :
 */
public class Dividendenaenderung {

	static Random r = new Random();
	
	public static final int SCHWACHE_STREUUNG = 1;
	public static final int MITTLERE_STREEUNG = 3;
	public static final int STARKE_STREEUNG = 5;
	
	public static int neueDividende(double alteDividende, int streeung, int min, int max) {
		
		double neueDividende, dividendenAenderung;
		
		dividendenAenderung = Math.abs(alteDividende * streeung * r.nextGaussian() / 10);
		
		//Gleich viele Zunahmen wie Abnahmen:
		if(r.nextDouble() < 0.5) {
			dividendenAenderung *= -1;
		}
		
		neueDividende = alteDividende + dividendenAenderung;
		
		//garantiert Schranken:
		if(neueDividende < min) {
			neueDividende = min + 3.5 * Math.abs(dividendenAenderung);
		} else if(neueDividende > max) {
			neueDividende = 0.9 * max;
		}
		return (int) Math.ceil(neueDividende);
	}
} //end class Dividendenaenderung
