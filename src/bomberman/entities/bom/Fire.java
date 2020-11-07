package bomberman.entities.bom;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;

import java.awt.*;

public class Fire extends Entities {
    private int _dame;
    private int _time;
    public Fire(int x, int y, int dame) {
        setX(x);
        setY(y);
        setDame(dame);
        setTime(40);
        setImage ("res/img/icon_fire.png");
    }

    //==================================================================================================================
    // getter and setter
    //==================================================================================================================

    public void setTime(int time) {
        this._time = time;
    }

    public int getTime() {
        return this._time;
    }

    public void setDame(int dame) {
        this._dame = dame;
    }

    public int getDame() {
        return this._dame;
    }

    //==================================================================================================================
    // method
    //==================================================================================================================

    @Override
    public void update() {
        if (getTime() > 0) setTime(getTime() - 1);
        else {
            removed();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(),getY(), Configuration.game_measure, Configuration.game_measure, null);
    }

    @Override
    public void collide(Entities e) {
        super.collide (e);
    }
}
