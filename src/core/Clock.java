package core;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Observable implements Runnable {
    private static LocalDateTime date;
    private static Timer timer;
    private static Long period;

    public Clock(Long period) {
        this.date = LocalDateTime.now();
        this.period = period;
        this.timer = new Timer();
    }

    /**
     * Create a timertask instance which will repeat the code inside every x seconds and notify observers.
     */
    @Override
    public void run() {
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                date = LocalDateTime.now();

                setChanged();
                notifyObservers(date);
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, 0, 1000 * period);
    }

    public static void setDate(LocalDateTime date) {
        Clock.date = date;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void setTimer(Timer timer) {
        Clock.timer = timer;
    }

    public static void setPeriod(Long period) {
        Clock.period = period;
    }

    public void stop() {
        timer.cancel();
    }

    public Long getPeriod() {
        return period;
    }

    public LocalDateTime getDate() {
        return date;
    }


}
