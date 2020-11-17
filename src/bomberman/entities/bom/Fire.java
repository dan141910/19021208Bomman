package bomberman.entities.bom;

import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Gate;
import bomberman.entities.dynamicEntities.DynamicEntities;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Fire extends Entities {
    private int _dame;
    private int _time;

    public Fire(int x, int y, int dame) {
        setX(x);
        setY(y);
        setDame(dame);
        setTime(40);
        setImage (Images.fire);
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
        if (getTime() > 0) {
            setTime(getTime() - 1);
            //check collide
            collide(Map.getEntityAtLocate(getX(), getY()));
        }
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
        if (e == null) return;
        if (e instanceof DynamicEntities) {
            ((DynamicEntities) e).setHp(((DynamicEntities) e).getHp() - this.getDame());
        }
        else if (e instanceof Boom) {
            ((Boom) e).setTime(0);
        }
        else if (e instanceof Gate) {
            if (!((Gate) e).isShow()) ((Gate) e).showed();
        }
    }
}
