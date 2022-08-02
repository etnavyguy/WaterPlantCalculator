package Classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Measurement {
    Double percent;
    Double height;
    LocalDateTime time;
    public Measurement(Double height, Double percent, LocalDateTime time){
        this.percent = percent;
        this.time = time;
        this.height = height;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString(){
        return (height + " ft   " + (int)(percent * 100) + "%  @ " + time.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
