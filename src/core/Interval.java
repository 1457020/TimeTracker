package core;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;


public class Interval implements Observer {
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private Long duration;
    private int id;
    private Task fatherTask;
    private Long period;

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Interval() {
    }

    public Interval(Task fatherTask, LocalDateTime initialDate, Long period) {
        this.initialDate = initialDate;
        this.finalDate = null;
        this.fatherTask = fatherTask;
        this.fatherTask.updateInitialDate(initialDate);
        this.duration = 0L;
        this.period = period;
    }

    public void setId(int num) {
        this.id = num;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Observer update functionality which receives a signal to update the duration and dates from the task and
     * fathers.
     *
     * @param o
     * @param arg the final date.
     */
    public void update(Observable o, Object arg) {
        this.finalDate = (LocalDateTime) arg;
        this.duration += this.period;

        System.out.println("ID:" + id + "            dateInici:" + initialDate + "                dateFinal:" + finalDate + "                                 durada: " + duration);

        this.fatherTask.updateFinalDate(this.finalDate);

        this.fatherTask.updateDuration(period);


    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }


    public Task getFatherTask() {
        return fatherTask;
    }

    public void setFatherTask(Task fatherTask) {
        this.fatherTask = fatherTask;
    }

}
