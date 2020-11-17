package bomberman.entities.block;

import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Brick extends Entities {
    private boolean _break;
    private int _time;
    public Brick(int x, int y) {
        setX(x);
        setY(y);
        setImage(Images.brick);
        _break = false;
        _time = 20;
    }

    @Override
    public void update() {
        if (_break) {
            _time--;
            setImage(Images.brick_break);
        }
        if (_time <= 0) removed();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(),getY(), Configuration.game_measure, Configuration.game_measure, null);
    }

    @Override
    public void removed() {
        super.removed();
        Map.setEntityAtLocate(getX(), getY(), null);
    }

    public void breaked() {
        this._break = true;
    }
}
