package bomberman.entities.item;

import bomberman.entities.Entities;
import bomberman.entities.dynamicEntities.Player;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Map;

import java.awt.*;

public abstract class Power extends Entities {
    public Player _player;
    public int _value;
    public boolean _active;
    public int _time;
    public Power(int x, int y, String img, Player player){
        setX(x);
        setY(y);
        setImage(img);
        this._player = player;
        setTime(5000);
        toLock();
    }

    @Override
    public void render(Graphics g) {
        if(!isActive()) {
            int measure = Configuration.game_measure;
            g.drawImage(get_image(), Map.getRealX(getX()) + measure / 5,
                    Map.getRealY(getY()) + measure / 5,
                    measure / 5 * 4, measure / 5 * 4, null);
        }
    }

    @Override
    public void update() {
        if (isActive()) {
            setTime(getTime() - 1);
        }

        if (getTime() == 0) {
            removed();
        }
    }
    //==================================================================================================================
    // getter and setter
    //==================================================================================================================

    public int getTime() {
        return _time;
    }

    public int getValue() {
        return _value;
    }

    public void setValue(int value) {
        this._value = value;
    }

    public void setTime(int _time) {
        this._time = _time;
    }

    public abstract void toActive();

    public void toLock() {
        this._active = false;
    }

    public boolean isActive() {
        return _active;
    }
}
