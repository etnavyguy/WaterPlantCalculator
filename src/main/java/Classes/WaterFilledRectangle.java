package Classes;

import javafx.scene.shape.Rectangle;

public class WaterFilledRectangle {
    int volume;
    int flow;
    Rectangle air;
    Rectangle water;

    public WaterFilledRectangle(Rectangle air, Rectangle water){
        this.air = air;
        this.water = water;
    }

    public void setPercent(double percent){
        this.air.setHeight(this.water.getHeight() - (this.water.getHeight() * percent));

    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public Rectangle getAir() {
        return air;
    }

    public void setAir(Rectangle air) {
        this.air = air;
    }

    public Rectangle getWater() {
        return water;
    }

    public void setWater(Rectangle water) {
        this.water = water;
    }
}
