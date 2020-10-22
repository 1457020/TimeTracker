package core;

import java.util.ArrayList;
import java.util.List;

public class Project extends Container{

  private List<Container> llistaFills= new ArrayList<Container>();

//constructor
  public Project(){}
  public Project(String nom, String desc, Container pare) {
    // Auto-generated constructor
    super(nom, desc, pare);
  }

  //-----------set & get------------

  public Container getFill(){
    if(!llistaFills.isEmpty()) {
      return llistaFills.get(0);
    }
    return null;
  }

  //------X----set & get------X-----




  public void inserirFill(Container fill){
    llistaFills.add(fill);
    fill.pare=this;
  }

  public List<Container> getLlistaFills() {
    return llistaFills;
  }

  public void setLlistaFills(List<Container> llistaFills) {
    this.llistaFills = llistaFills;
  }
}
