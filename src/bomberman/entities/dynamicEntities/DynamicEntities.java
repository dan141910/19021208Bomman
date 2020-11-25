package bomberman.entities.dynamicEntities;

import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;

public abstract class DynamicEntities extends Entities {
    protected int _point = 1;
    protected int _hp = 1;
    protected int _speed = 1; //move
    protected int _direction;
    private int _attack = 0;
    protected int _timeAfter; // time to dead :>
    private int _size = Configuration.game_measure;
    protected int _preX;
    protected int _preY;

    protected abstract void killed();

    //==================================================================================================================
    // GETTER AND SETTER
    //==================================================================================================================
    public void setAttack(int _attack) {
        this._attack = _attack;
    }

    public void setHp(int hp) {
        this._hp = hp;
    }

    public void setPoint(int point) {
        this._point = point;
    }

    public int getSpeed() {
        return _speed;
    }

    public int getAttack() {
        return _attack;
    }

    public int getHp() {
        return _hp;
    }

    public int getPoint() {
        return _point;
    }

    public void setSpeed(int speed) {
        this._speed = Math.min(speed, 5);
    }

    public boolean isLive() {
        return getHp() > 0;
    }

    public int getSize() {
        return _size;
    }

    public void setSize(int size) {
        this._size = size;
    }
}
