package core;

import org.json.JSONPropertyIgnore;

import java.time.LocalDateTime;

public class Container {
    protected String name;
    protected String description;
    protected long duration;
    protected LocalDateTime initialDate;
    protected LocalDateTime finalDate;
    protected Container containerFather;


    //constructor
    public Container() {
    }

    public Container(String name, String desc, Container containerFather) {
        this.name = name;
        this.description = desc;
        this.initialDate = null;
        this.finalDate = null;
        this.containerFather = containerFather;
        this.duration = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }

    public void setInitialDate(LocalDateTime tempsInici) {
        this.initialDate = tempsInici;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setFinalDate(LocalDateTime tempsFinal) {
        this.finalDate = tempsFinal;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setContainerFather(Container containerFather) {
        this.containerFather = containerFather;
    }

    @JSONPropertyIgnore
    public Container getContainerFather() {
        return containerFather;
    }

    /**
     * Update initial date in a recursive manner. Taking account existent dates to not overwrite them.
     *
     * @param initialDateToBeUpdated
     */
    public void updateInitialDate(LocalDateTime initialDateToBeUpdated) {
        // For not overwriting the initial date when starting the same task again.
        if (this.initialDate == null) {
            this.initialDate = initialDateToBeUpdated;
        }
        // Make sure we are iterating correctly the tree.
        if (containerFather != null) {
            if (containerFather.initialDate == null) {
                containerFather.updateInitialDate(initialDateToBeUpdated);
            }
        }
    }

    /**
     * Update the final date in a recursive manner.
     *
     * @param finalDateToBeUpdated
     */
    public void updateFinalDate(LocalDateTime finalDateToBeUpdated) {
        this.finalDate = finalDateToBeUpdated;
        if (containerFather != null) {
            this.containerFather.updateFinalDate(finalDateToBeUpdated);
        }
    }

    /**
     * Update the duration in a recursive manner by adding the periode constantly across tasks and fathers.
     *
     * @param durationToBeUpdated
     */
    public void updateDuration(Long durationToBeUpdated) {
        this.duration += durationToBeUpdated;

        System.out.println("nom:" + name + "            dateInici:" + initialDate + "                dateFinal:" + finalDate + "                                 durada: " + this.duration);

        if (this.containerFather != null) {
            this.containerFather.updateDuration(durationToBeUpdated);
        }
    }

}
