package GUI;
import Logika.Logika;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


 /**
  *@author Amir Zulic
  */

public class GUI extends JFrame implements ActionListener{
  
  /** @param logika cemo koristiti kao poveznicu logike i GUI-a */
    Logika logika;
   /**  @param rezultat cuvanje rezultata */
    public int rezultat;
   /** @param rez atribut koji se koristi kao upisnica */
    JTextField rez;
  
  
  
  /** Konstruktor bez parametara za GUI koji postavlja broj redova na 8
   * i postavlja broj kolona na 10. Velicina prozora je proporcionalna
   * velicini prozora za igru (tj. broju kolona i redova).
    */
  public GUI() {
	logika = new Logika(this);
    logika.redovi = 8;
    logika.kolone = 10;
    this.setSize(logika.kolone * 100, logika.redovi * 100);
    this.setTitle("CandyCrush");
    
    /** Dva panela, igrica je panel za igru sa dugmadima ("figurama"), a drugi je za ispis
     * rezultata, tj. zbira bodova.
     */
    JPanel igrica = new JPanel(new GridLayout(logika.redovi, logika.kolone));
    JPanel statistika = new JPanel(new GridLayout(1,2));
    
    JTextField tekst = new JTextField("Rezultat:");
    rez = new JTextField("");
    rez.setEditable(false);
    tekst.setEditable(false);
    statistika.add(tekst);
    statistika.add(rez);
    
    
    logika.dugmad = new JButton[logika.redovi][logika.kolone];
    
    /** Lista boja rezervisanih za igru, simulacija figura*/
    Color boje[] = {Color.orange, Color.magenta, Color.pink, Color.white, Color.cyan, Color.red, Color.yellow, Color.LIGHT_GRAY};
    
    /** Dvostruka petlja za matricu dugmadi
     * Unutar petlji se postavljaju actionListeneri i dodaju boje na dugmadi
     * Boje su random boje iz liste boja
     */
    for (int i = 0; i < logika.redovi; i++) {
      for(int j = 0; j < logika.kolone; j++) {
        logika.dugmad[i][j] = new JButton();
        logika.dugmad[i][j].addActionListener(this);
        logika.dugmad[i][j].setBackground(boje[(int)(Math.random() * boje.length)]);
        igrica.add(logika.dugmad[i][j]);
      }
    }
    
    
    /** Novi GridBagLayout za pravljenje layout-a u programu.
     * Constraintom c postavljamo velicine panela i njihove pozicije
     */
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    
    c.gridx = 0;
    c.gridy = 0;
    
    /** prostor prosirenja panela */
    c.weightx = 1;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    this.add(igrica, c);
    c.weightx = 0;
    c.weighty = 0;
    c.fill = GridBagConstraints.NONE;
    
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 0.2;
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    this.add(statistika, c);
    c.weightx = 0;
    c.weighty = 0;
    c.fill = GridBagConstraints.NONE;
    
    
    
    /** Prozor se postavlja na visible,
     * Postavlja se defaultna operacija pri zatvaranju prozora, tj. "terminate"
     * Prozor se centrira sa relativnom null lokacijom
     */
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
  
  /**
   * Postavlja se akcija pri pritisku nekog dugmeta uz pomoc ActionEventa e.
   * */
  public void actionPerformed(ActionEvent e) {
    
    /** @param pritisnutaFigura varijabla je kreirana da se zapamti pritisnuto dugme, "figura"*/
    JButton pritisnutaFigura = (JButton) e.getSource();
    
    /** Ovom petljom cemo prolaziti kroz matricu i traziti indeks pritisnutog dugmeta
    Pod indexom se podrazumijevaju i,j koordinate*/
    for (int i = 0; i < logika.redovi; i++) {
      for(int j = 0; j < logika.kolone; j++) {
        if(pritisnutaFigura == logika.dugmad[i][j]) {
          if (logika.odabraniRed == -1)
            logika.prviKlik(i, j, logika.dugmad);
          else
            logika.drugiKlik(i, j, logika.dugmad);
          	String bodovi = String.valueOf(rezultat);
          	rez.setText(bodovi);
        }
      }
    }
  }
  
}
  
  