package core;

import org.json.JSONPropertyIgnore;

import java.time.LocalDateTime;

public class Container {
  protected String nom;
  protected String descripcio;
  protected Long durada;
  protected LocalDateTime dataInici;
  protected LocalDateTime dataFinal;
  protected Container pare;

//constructor
  public Container(){}
  public Container(String nom, String desc, Container pare ) {
    this.nom=nom;
    this.descripcio=desc;
    this.dataInici= null;
    this.dataFinal= null;
    this.pare=pare;
  }

  //----------set & get-----------
  public void setNom(String nom) {
    this.nom = nom;
  }
  public String getNom() {
    return nom;
  }

  public void setDescripcio(String descripcio) {
    this.descripcio = descripcio;
  }
  public String getDescripcio() {
    return descripcio;
  }

  public void setDurada(Long durada) {
    this.durada = durada;
  }
  public Long getDurada() {
    return durada;
  }

  public void setTempsInici(LocalDateTime tempsInici) {
    this.dataInici = tempsInici;
  }
  public LocalDateTime getTempsInici() {
    return dataInici;
  }

  public void setTempsFinal(LocalDateTime tempsFinal) {
    this.dataFinal = tempsFinal;
  }
  public LocalDateTime getTempsFinal() {
    return dataFinal;
  }
  public void setPare(Container pare) {
    this.pare = pare;
  }

  @JSONPropertyIgnore
  public Container getPare() {
    System.out.println("hey");
    return pare;
  }

  //------X----set & get------X-----



  // --------------actualitzadors recursius --------------
  public void actualitzaDataInici(LocalDateTime dInici){ /**Actualitzo la data inici recursivament**/
    dataInici=dInici;
    if(pare!=null){
      if(pare.dataInici==null){
        pare.actualitzaDataInici(dInici);
      }
    }
  }
  public void actualitzaDataFinal(LocalDateTime dFinal){ /**Actualitzo la data final recursivament**/
    dataFinal=dFinal;
    if(pare!=null){
      pare.actualitzaDataFinal(dFinal);
    }
  }

  public void actualitzaDurada(){}

  // -------X-------actualitzadors recursius -------X-------



}
