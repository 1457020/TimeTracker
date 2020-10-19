package core;

import java.util.ArrayList;
import java.util.List;

public class Task extends Container{

  protected List<Interval> llistaIntervals=new ArrayList<Interval>();

//constructor
  public Task(){}
  public Task(String nom, String desc, Container pare) {
    // Auto-generated constructor
    super(nom, desc, pare);
  }

  //-----------set & get------------

  //------X----set & get------X-----



  public void inserirFill(Interval fill){
    llistaIntervals.add(fill);
    fill.setId(llistaIntervals.size());
  }

  public List<Interval> getLlistaIntervals() {
    return llistaIntervals;
  }

  public void setLlistaIntervals(List<Interval> llistaIntervals) {
    this.llistaIntervals = llistaIntervals;
  }


  }



