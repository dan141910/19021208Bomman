package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.Entities;
import bomberman.entities.dynamicEntities.DynamicEntities;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AI;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public abstract class Mob extends DynamicEntities {
    protected AI _ai;
    protected Player _player;
    //necessary to correct move
    protected int _steps;

    public Mob(int x, int y, String img, int speed, int points,int size, Player player) {
        setX(x);
        setY(y);
        setImage(img);
        setPoint(points);
        setSpeed(speed);
        setSize(size);

        _timeAfter = 20;
        _player = player;
        _steps = Configuration.game_measure / speed;
    }

    //=============================================================================================================
    //Mob Render & Update
    //=============================================================================================================

    @Override
    public void update() {
        if(!isLive()) {
            setImage(Images.mob_dead);
            killed();
        }
        else {
            calculateMove();
        }
    }

    protected void killed() {
        if (Map.getEntityAtLocate(getX(), getY()) == this){
            Map._numMobs--;
            Map.setEntityAtLocate(getX(), getY(), null);
            _player.setPoint(_player.getPoint() + this.getPoint());
            InfoPanel.notice("Killed Mob +" + getPoint() + "point");
        }

        if(_timeAfter > 0) {
            _timeAfter--;
        }
        else removed();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0xCB4F954A, true));
        g.fillRect(_preX, _preY, getSize(), getSize());
    }

    /*
	|--------------------------------------------------------------------------
	| Mob Move
	|--------------------------------------------------------------------------
	 */

    public void calculateMove() {
        _preX = getX(); _preY = getY();
        int xa = _preX, ya = _preY;

        if(_steps <= 0){
            _direction = _ai.calculateDirection();
            _steps = Configuration.game_measure / getSpeed();
        }

        if(_direction == 0) ya -= getSpeed(); // 0 = up
        if(_direction == 2) ya += getSpeed(); // 2 = down
        if(_direction == 3) xa -= getSpeed(); // 3 = left
        if(_direction == 1) xa += getSpeed(); // 1 = right

        if (Map.getEntityAtLocate(getX(), getY()) == this) Map.setEntityAtLocate(getX(), getY(), null);
        if (checkBound(xa, getY(), getSize(), getSize())) setX(xa);_steps -= 1;
        if (checkBound(getX(), ya, getSize(), getSize())) setY(ya);_steps -= 1;

        // change matrix
        Entities tmp = Map.getEntityAtLocate(getX(), getY());
        collide(tmp);
        if (tmp == null || tmp instanceof Player) Map.setEntityAtLocate(getX(), getY(), this) ;
    }


    /**
     * check next position in matrix. Must null or is player's location.
     * @param x is delta x.
     * @param y is delta y.
     * @return whether next position is suitable.
     */
    public boolean canMove(int x, int y) {
        Entities tmp = Map.getEntityAtLocate(x, y);
        if (tmp instanceof DynamicEntities) return true;
        return tmp == null ;
    }

    /**
     * check 4 point of image.
     * @param xa new x.
     * @param ya new y.
     * @param width width image.
     * @param height height image.
     * @return whether any point will be collide.
     */
    protected boolean checkBound(int xa, int ya, int width, int height) {
        return canMove(xa, ya) && canMove(xa + width, ya) &&
                canMove(xa, ya + height) && canMove(xa + width, ya + height);
    }
}
