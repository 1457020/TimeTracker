package core;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime dataInici;
  private LocalDateTime dataFinal;
  private int durada= -2; /**S'inicialitza a -2 ja que la primera crida es fa quan el rellotge esta a 0**/
  private int id;
  private Task pare;

  //constructor
  public Interval(){}

  public Interval(Task xpare) {
    dataInici= LocalDateTime.now();
    dataFinal = null;
    pare=xpare;
    pare.actualitzaDataInici(dataInici);
  }

  //-----------set & get------------
  public void setId(int num){ this.id=num; }
  public int getId(){return this.id;}

  //------X----set & get------X-----



  public void update(Observable o, Object arg) {
    this.dataFinal = (LocalDateTime) arg;
    this.durada+=2;

    System.out.println("ID:"+id+"            dateInici:"+dataInici+"                dateFinal:"+ dataFinal+"                  operation"+dataFinal.getNano()+"               durada: "+ durada);


  }

  public LocalDateTime getDataInici() {
    return dataInici;
  }

  public void setDataInici(LocalDateTime dataInici) {
    this.dataInici = dataInici;
  }

  public LocalDateTime getDataFinal() {
    return dataFinal;
  }

  public void setDataFinal(LocalDateTime dataFinal) {
    this.dataFinal = dataFinal;
  }

  public int getDurada() {
    return durada;
  }

  public void setDurada(int durada) {
    this.durada = durada;
  }

  public Task getPare() {
    return pare;
  }

  public void setPare(Task pare) {
    this.pare = pare;
  }
}
