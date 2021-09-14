package GUI;
import Logika.Logika;
import java.util.Scanner;

import Logika.Logika;


 //@author Amir Zulic

public class ConsoleGUI{
  
  /** Inicijaliziramo logiku te pravimo varijablu matrica 
   * @param logika za povezivanje ConsoleGUI i Logika*/
  Logika logika;
  int [][] matrica;
  
  
  /** Konstruktor bez parametara za ConsoleGUI koji postavlja broj redova na 8
   * i postavlja broj kolona na 10. Ovim u konzoli dobijamo velicinu matrice
    */
  public ConsoleGUI() {
	logika = new Logika(this);
    logika.redovi = 8;
    logika.kolone = 10;
    
    
    /** @param figure Lista figura rezervisanih za igru, simulacija figura */
    int figure[] = {1, 2, 3, 4, 5};
    
    /** Dvostruka petlja za matricu figura
     * Figure su simulirane random brojevima iz liste @param figure
     */
    
    matrica = new int[logika.redovi][logika.kolone];
    for (int i = 0; i < logika.redovi; i++) {
      for(int j = 0; j < logika.kolone; j++) {
        matrica[i][j] = (figure[(int)(Math.random() * figure.length)]);
      }
    }
    
    /** Prije pocetka zelimo vidjeti stanje nase igre pa pozivamo ispis u konzolu */
    
    ispisiStanjeIgre();
    
    
    /** Kod console verzije igre cemo unostiti koordinate u konzolu */
    
    Scanner ulaz = new Scanner(System.in);
    int x = ulaz.nextInt();
    int y = ulaz.nextInt();
    
    /** Ovom petljom cemo prolaziti kroz matricu i traziti figure unesenih koordinata
    Pod indexom se podrazumijevaju i,j koordinate */
    for (int i = 0; i < logika.redovi; i++) {
      for(int j = 0; j < logika.kolone; j++) {
        if(x == i && y == j) {
          if (logika.odabraniRed == -1) {
            logika.prviKlik(i, j, matrica);
          	Scanner ulaz2 = new Scanner(System.in);
          	int x2 = ulaz.nextInt();
          	int y2 = ulaz.nextInt();
          }
          else
            logika.drugiKlik(i, j, logika.dugmad);
          	ispisiStanjeIgre();
        }
      }
    }
    
    
  }
  
  /** 
   * Metoda za ispisivanje stanjaIgre. Prolazak kroz matricu figura te njihov ispis
   */

  public void ispisiStanjeIgre() {
	  for(int i = 0; i < logika.redovi; i++) {
	    	for(int j = 0; j < logika.kolone; j++) {
	    		System.out.print(matrica[i][j] + " ");
	    	}
	    	System.out.println();
	    }
  }
  }
  
  
  
  