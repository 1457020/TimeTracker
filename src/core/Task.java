package core;

import java.util.ArrayList;
import java.util.List;

public class Task extends Container{

  protected List<Interval> intervalList = new ArrayList<Interval>();

  public Task(){}
  public Task(String name, String desc, Container containerFather) {
    super(name, desc, containerFather);
  }

  public void addChild(Interval child){
    intervalList.add(child);
    child.setId(intervalList.size());
  }

  public List<Interval> getIntervalList() {
    return intervalList;
  }

  public void setIntervalList(List<Interval> intervalList) {
    this.intervalList = intervalList;
  }

  public void startInterval(Clock clock, boolean forceNotifyObserver){
    Interval interval = null;
    // The first task must already start with duration 2. So needs to be handled.
    if (forceNotifyObserver) {
      interval = new Interval(this, clock.getDate().minusSeconds(clock.getPeriod()), clock.getPeriod());
      addChild(interval);
      interval.update(null, clock.getDate());
    } else {
      interval = new Interval(this, clock.getDate(), clock.getPeriod());
      addChild(interval);

    }
    clock.addObserver(interval);
  }

  public void pararInterval(Clock clock){
    // Obtain the last interval added into the list.
    Interval interval = intervalList.get(intervalList.size()-1);
    clock.deleteObserver(interval);
    interval.setFinalDate(clock.getDate());
  }

  }



