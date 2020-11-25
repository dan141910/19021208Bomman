package bomberman.entities.block;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Gate  extends Entities implements Animation {
    boolean _show;
    boolean _active;
    int animate = 100;
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
            animate();
        } else if (isShow()) {
            setImage(Images.gate);
            if (Map._numMobs <= 0) actived();
        }


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(), getY(),
                    Configuration.game_measure, Configuration.game_measure,null);
    }

    @Override
    public void animate() {
        animate--;
        if (animate < 0) animate = 30;
        if (animate % 30 == 8) {
            setImage(Images.gate1);
        } else if (animate % 30 == 18) {
            setImage(Images.gate2);
        } else if (animate % 30 == 26) {
            setImage(Images.gate3);
        }
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
    }

    public boolean isShow() {
        return _show;
    }

}
