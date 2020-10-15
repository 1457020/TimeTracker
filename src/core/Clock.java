package core;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable implements Runnable {
  private static LocalDateTime date;
  private static Timer timer;
  private static int period; //seconds

//constructor
  public Clock(int period){
    date = LocalDateTime.now();
    this.period = period;
    timer = new Timer();
  }

//crea una timertask que es repetira en el temps "schedule". la task es un print de now().
@Override
public void run() {
  TimerTask repeatedTask = new TimerTask() {

    @Override
    public void run() { //instance of anonymous class
      date = LocalDateTime.now();
      //System.out.println("Run() done on " + date);

      //marca que el valor ha cambiat
      setChanged();
      //exectuta la notificacio
      notifyObservers(date);
    }
  };
  timer.scheduleAtFixedRate(repeatedTask,0,1000*period);

}

  public void stop(){timer.cancel();}
  public int getPeriod(){return period;}
  public LocalDateTime getDate(){return date;}



}
