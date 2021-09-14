package Logika;
import GUI.GUI;
import GUI.ConsoleGUI;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import GUI.GUI;

public class Logika {
	/**@param odabraniRed cuva red prve odabrane figure
	 * @param odabranaKolona cuva kolonu prve odabrane figure (dugmeta)*/
	  public int odabraniRed = -1, odabranaKolona = -1;
	  /** @param bodovi varijabla u kojoj ce se cuvati rezultat*/
	  private int bodovi = 0;
	/** @param redovi, @param kolone varijable redovi i kolone 
	 * ce predstavljati broj redova i kolona u igri*/
	  public int redovi, kolone;
	  /** @param dugmad niz dugmadi koja ce se koristiti za igranje kao simulacija candy crush figura*/
	  public JButton[][] dugmad;
	  
	  GUI gui;
	  ConsoleGUI consoleGUI;
	  
	  public Logika(GUI gui1) {
		  gui = gui1;
		  
	  }
	  
	  public Logika(ConsoleGUI consoleGUI1) {
		  consoleGUI = consoleGUI1;
	  }
	  
	  
	  
	  /**
	   * Pravimo metodu prviKlik koja ce da pamti koje je to dugme pritisnuto prvo
	   * */
	  public void prviKlik(int red, int kolona, JButton[][] dugmad) {
	    odabraniRed = red;
	    odabranaKolona = kolona;
	  }
	  
	  public void prviKlik(int red, int kolona, int[][] dugmad) {
		    odabraniRed = red;
		    odabranaKolona = kolona;
		  }
	  
	  /** Slicno metodi prviKlik, pravimo metodu drugiKlik koja ce upamtiti drugo pritisnuto dugme
	   * "figuru", a zatim se provjeravaju uslovi i mogucnosti*/
	  public void drugiKlik(int red, int kolona, JButton[][] dugmad) {
		  
	    if (daLiSuSusjedni(odabraniRed, odabranaKolona, red, kolona)) {
	      zamjeniBoje(odabraniRed, odabranaKolona, red, kolona, dugmad);
	      
	      /** Varijabla podudaranjeSmjerGore je broj istih figura u smjeru prema gore*/
	      int podudaranjeSmjerGore = brojIstihSmjerGore(odabraniRed, odabranaKolona, dugmad);
	      
	      /** Varijabla podudaranjeSmjerDole je broj istih figura u smjeru prema dole*/
	      int podudaranjeSmjerDole = brojIstihSmjerDole(redovi, odabraniRed, odabranaKolona, dugmad);
	      
	      /** Varijabla podudaranjeSmjerLijevo je broj istih figura u smjeru prema lijevo*/
	      int podudaranjeSmjerLijevo = brojIstihSmjerLijevo(odabraniRed, odabranaKolona, dugmad);
	      
	      /** Varijabla podudaranjeSmjerDesno je broj istih figura u smjeru prema desno*/
	      int podudaranjeSmjerDesno = brojIstihSmjerDesno(kolone, odabraniRed, odabranaKolona, dugmad);
	      
	      
	      /** Provjeravamo oba slucaja podudaranja, horizonatlno i vertikalno*/
	      if((podudaranjeSmjerLijevo + podudaranjeSmjerDesno >= 2) && 
	    		  (podudaranjeSmjerGore + podudaranjeSmjerDole >= 2)) {
	    	  
	    	/**U slucaju podudaranja stvaramo nove figure tamo gdje su nestale iste*/
	        stvoriFigureVertikalno(odabraniRed, odabranaKolona, podudaranjeSmjerGore, podudaranjeSmjerDole, dugmad);
	        stvoriFigureHorizontalno(odabraniRed, odabranaKolona - 1, podudaranjeSmjerLijevo - 1, 0, dugmad);
	        stvoriFigureHorizontalno(odabraniRed, odabranaKolona + 1, 0, podudaranjeSmjerDesno - 1, dugmad);
	        gui.rezultat += 20;
	      }
	      
	      /**Provjeravamo podudaranja u horizontalnom slucaj*/
	      else if(podudaranjeSmjerLijevo + podudaranjeSmjerDesno >= 2) {
	        stvoriFigureHorizontalno(odabraniRed, odabranaKolona, podudaranjeSmjerLijevo, podudaranjeSmjerDesno, dugmad);
	        gui.rezultat += 10;
	      }
	      
	      /**Provjeravamo podudaranja u vertikalnom slucaju*/
	      else if (podudaranjeSmjerGore + podudaranjeSmjerDole >= 2) {
	        stvoriFigureVertikalno(odabraniRed, odabranaKolona, podudaranjeSmjerGore, podudaranjeSmjerDole, dugmad);
	        gui.rezultat += 10;
	      }
	      else {
	        zamjeniBoje(odabraniRed, odabranaKolona, red, kolona, dugmad);
	      }
	    }
	    
	    
	    /**Resetujemo vrijednosti odabranih redova da bi mogli birati ponovno*/
	    odabraniRed = -1;
	    odabranaKolona = -1;
	  }
	  
	  /** Ova metoda daLiSuSusjedni provjerava da li su dva odabrana dugmeta, figure
	   * susjedni, tj da li su jedan pored drugog
	   */
	  public boolean daLiSuSusjedni(int odabraniRed, int odabranaKolona, int red, int kolona) {
	    return (red == odabraniRed && (kolona - odabranaKolona == 1 || 
	    		kolona - odabranaKolona == -1)) || 
	    		(kolona == odabranaKolona && (red - odabraniRed == 1 || 
	    		red - odabraniRed == -1));
	  }
	  
	  /** Provjerava i vraca broj istih figura u smjeru prema gore
	   * uzimajuci pozadine, tj. boje dugmadi
	    */
	  public int brojIstihSmjerGore(int red, int kolona, JButton dugmad[][]) {
	    
	    int brojIstihSmjerGore = 0;
	    
	    /** Boja zamijenjenog dugmeta*/
	    Color bojaFigure = dugmad[red][kolona].getBackground();
	    
	    red--;
	    /** Pustimo petlju da trazi ista dugmad sve dok su boje iste ili ne izadjemo
	    iz okvira */
	    while (red >= 0 && dugmad[red][kolona].getBackground() == bojaFigure) {
	      brojIstihSmjerGore++;
	      red--;
	    }
	    return brojIstihSmjerGore;
	  }
	  
	  /** Provjerava i vraca broj istih figura u smjeru prema dole
	   * uzimajuci pozadine, tj. boje dugmadi, ali imamo varijablu maksimalnoRedova jer 
	   * u smjeru prema dole broj raste, za razliku od smjera gore gdje je kraj broj 0
	    */
	  public int brojIstihSmjerDole(int maksimalnoRedova, int red, int kolona, JButton dugmad[][]) {
	    
	    int brojIstihSmjerDole = 0;
	    
	    Color bojaFigure = dugmad[red][kolona].getBackground();
	    
	    red++;
	    /** Pustamo petlju sa uslovima istim kao kod prethodne metode*/
	    while (red < maksimalnoRedova && dugmad[red][kolona].getBackground() == bojaFigure) {
	      brojIstihSmjerDole++;
	      red++;
	    }
	    return brojIstihSmjerDole;
	  }

	  /** Provjerava i vraca broj istih figura u smjeru prema dole
	   * uzimajuci pozadine, tj. boje dugmadi
	    */
	   public int brojIstihSmjerLijevo(int red, int kolona, JButton dugmad[][]) {
	  
	    int brojIstihSmjerLijevo = 0;
	    
	    Color bojaFigure = dugmad[red][kolona].getBackground();
	    
	    kolona--;
	    while (kolona >= 0 && dugmad[red][kolona].getBackground() == bojaFigure) {
	      brojIstihSmjerLijevo++;
	      kolona--;
	    }
	    return brojIstihSmjerLijevo;
	   }
	  
	   /** Provjerava i vraca broj istih figura u smjeru prema desno
	    * uzimajuci pozadine, tj. boje dugmadi, ali imamo varijablu maksimalnoKolona jer 
	    * u smjeru prema desno broj raste, za razliku od smjera lijevo gdje je kraj broj 0
	     */
	  public int brojIstihSmjerDesno(int maksimalnoKolona, int red, int kolona, JButton dugmad[][]) {
	  
	    int brojIstihSmjerDesno = 0;
	 
	    Color bojaFigure = dugmad[red][kolona].getBackground();

	    kolona++;
	    while (kolona < maksimalnoKolona && dugmad[red][kolona].getBackground() == bojaFigure) {
	      brojIstihSmjerDesno++;
	      kolona++;
	    }
	    return brojIstihSmjerDesno;
	  }
	   
	   /** Iako se metoda zove stvoriFigureHorizonatlno, ova metoda je zasluzna za 
	    * stvaranje novih figura kada dodje do podudaranje 3 ili vise, a zasluzna je i za
	    * pomjeranje ostalih figura na nacin koji je potreban
	    */
	   public void stvoriFigureHorizontalno(int red, int kolona, int podudaranjeSmjerLijevo, int podudaranjeSmjerDesno, JButton[][] dugmad) {
	     
	     /** Prolazak kroz kolone za pomjeranje */
	     for (int i = kolona - podudaranjeSmjerLijevo; i <= kolona + podudaranjeSmjerDesno; i++) {
	       int trenutniRed = red;
	       
	       /** Petlja kojom spustamo dugmad */
	       while (trenutniRed > 0) {
	         dugmad[trenutniRed][i].setBackground(dugmad[trenutniRed - 1][i].getBackground());
	         trenutniRed--;
	       }
	       
	       /** Mogucnosti boja koje se mogu pojaviti prilikom stvaranja novih dugmadi
	        */
	       Color boje[] = {Color.orange, Color.magenta, Color.pink, Color.white, Color.cyan, Color.red, Color.yellow, Color.LIGHT_GRAY};
	       dugmad[trenutniRed][i].setBackground(boje[(int)(Math.random() * boje.length)]);
	     }
	   }
	   
	   /** Iako se metoda zove stvoriFigureVertikalno, ova metoda je zasluzna za 
	    * stvaranje novih figura kada dodje do podudaranje 3 ili vise, a zasluzna je i za
	    * pomjeranje ostalih figura na nacin koji je potreban
	    */
	   public void stvoriFigureVertikalno(int red, int kolona, int podudaranjeSmjerGore, int podudaranjeSmjerDole, JButton[][] dugmad) {
	     
	     /** Broj bitan za brisanje i stvaranje novih figura */
	     int brojFiguraZaStvaranje = podudaranjeSmjerGore + podudaranjeSmjerDole + 1;
	     int trenutniRed = red + podudaranjeSmjerDole;
	     
	     while (trenutniRed >= 0) {
	       try {

	         dugmad[trenutniRed][kolona].setBackground(dugmad[trenutniRed - brojFiguraZaStvaranje][kolona].getBackground());
	       }
	       catch (ArrayIndexOutOfBoundsException e) {
	    	   /** Mogucnosti boja koje se mogu pojaviti prilikom stvaranja novih dugmadi
	            */
	         Color boje[] = {Color.orange, Color.magenta, Color.pink, Color.white, Color.cyan, Color.red, Color.yellow, Color.LIGHT_GRAY};
	         dugmad[trenutniRed][kolona].setBackground(boje[(int)(Math.random() * boje.length)]);
	       }
	       trenutniRed--;
	     }
	   }
	   
	   /** Metoda koja mijenja boje dvije susjedne figure, kada metoda daLiSuSusjedni vrati 
	    * true
	    */
	   public void zamjeniBoje(int odabraniRed, int odabranaKolona, int red, int kolona, JButton[][] dugmad) {
	     /**Kopija originalne boje koja ce se prenijeti na drugo dugme*/
	     Color bojaPrveOdabraneFigure = dugmad[odabraniRed][odabranaKolona].getBackground();
	     dugmad[odabraniRed][odabranaKolona].setBackground(dugmad[red][kolona].getBackground());
	     dugmad[red][kolona].setBackground(bojaPrveOdabraneFigure);
	   }
	   
	   
	}

