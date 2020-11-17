package bomberman.entities.block;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Gate  extends Entities {
    boolean _show;
    boolean _active;
    int animate = 0;
    public Gate(int x, int y) {
        setX(x);
        setY(y);
        setImage(Images.brick);
        _active = false;
        _show = false;
    }

    @Override
    public void update() {
        if (isActive()) {
            setImage("res/img/gate/gate_" + animate++ + ".png");
            if (animate > 3) animate = 0;
        } else if (isShow()) {
            setImage(Images.gate);
            if (Map._numMobs == 0) actived();
        }


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(), getY(),
                    Configuration.game_measure, Configuration.game_measure,null);
    }

    //=========================================================================================================
    // getter and setter
    //=========================================================================================================

    public void actived() {
        this._active = true;
        InfoPanel.notice("The Gate is opened !");
    }

    public boolean isActive() {
        return _active;
    }

    public void showed() {
        this._show = true;
        InfoPanel.notice("You finded The Gate!");
    }

    public boolean isShow() {
        return _show;
    }
}
