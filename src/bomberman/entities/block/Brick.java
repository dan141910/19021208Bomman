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
            if (_time == 16) setImage(Images.brick_break1);
            else if (_time == 12) setImage(Images.brick_break2);
            else if (_time == 8) setImage(Images.brick_break3);
            else if (_time == 4) setImage(Images.brick_break4);
            else if (_time == 2) setImage(Images.brick_break5);

        }
        if (_time <= 0) removed();
    }

    @Override
    public void render(Graphics g) {
        if (_break){
            g.setColor(new Color(0xCB4F954A, true));
            g.fillRect(getX(),getY(), Configuration.game_measure,Configuration.game_measure);
        }
        g.drawImage(get_image(), getX(),getY(), Configuration.game_measure, Configuration.game_measure, null);
    }

    @Override
    public void removed() {
        super.removed();
        Map.setEntityAtLocate(getX(), getY(), null);
    }

    public void breaked() {
        this._break = true;
        setImage(Images.brick_break);
    }
}
