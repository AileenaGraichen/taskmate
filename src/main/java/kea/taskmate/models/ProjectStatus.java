package kea.taskmate.models;

import java.util.List;

public class ProjectStatus {
    private int done;
    private int inProgress;
    private int notStarted;
    private final double persentageDone;

    public ProjectStatus(int done, int inProgress, int notStarted) {
        this.done = done;
        this.inProgress = inProgress;
        this.notStarted = notStarted;

        int total = done + inProgress + notStarted;
        double percentage = total != 0 ? ((double) done / total * 100.0) : 0.0;

        persentageDone = Double.parseDouble(String.format("%.0f", percentage));
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getInProgress() {
        return inProgress;
    }

    public void setInProgress(int inProgress) {
        this.inProgress = inProgress;
    }

    public int getNotStarted() {
        return notStarted;
    }

    public void setNotStarted(int notStarted) {
        this.notStarted = notStarted;
    }

    public double getPersentageDone() {
        return persentageDone;
    }
}
