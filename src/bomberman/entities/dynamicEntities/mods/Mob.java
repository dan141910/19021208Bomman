package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.Entities;
import bomberman.entities.dynamicEntities.DynamicEntities;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AI;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Map;

import java.awt.*;

public abstract class Mob extends DynamicEntities {
    protected AI _ai;
    protected int _direction;
    protected Player _player;

    //necessary to correct move
    protected int MAX_STEPS;
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
        MAX_STEPS = Configuration.game_measure / speed;
        _steps = MAX_STEPS;
    }

    //=============================================================================================================
    //Mob Render & Update
    //=============================================================================================================

    @Override
    public void update() {
        if(!isLive()) {
            setImage("res/img/mobs/modDead.png");
            killed();
        }
        else calculateMove();
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
        g.fillRect(Map.getRealX(getX()), Map.getRealY(getY()), Configuration.game_measure, Configuration.game_measure);
        g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
    }

    /*
	|--------------------------------------------------------------------------
	| Mob Move
	|--------------------------------------------------------------------------
	 */
    public void calculateMove() {
        int xa = getX(), ya = getY();

        if(_steps <= 0){
            _direction = _ai.calculateDirection();
            _steps = MAX_STEPS;
        }

        if(_direction == 0) ya -= getSpeed();
        if(_direction == 2) ya += getSpeed();
        if(_direction == 3) xa -= getSpeed();
        if(_direction == 1) xa += getSpeed();

        Map.setEntityAtLocate(getX(), getY(),null);

        if(canMove(xa, getY()) && canMove(xa + getSize(), getY()) &&
                canMove(xa, getY() + getSize()) && canMove(xa + getSize(), getY() + getSize()) )  {
            setX(xa);
        }

        if(canMove(getX(), ya) && canMove(getX() + getSize(), ya + getSize()) &&
                canMove(getX(), ya + getSize()) && canMove(getX() + getSize(), ya)) {
            setY(ya);
        }

        _steps -= 1;
        // change matrix
        if (Map.getEntityAtLocate(getX(), getY()) == null) Map.setEntityAtLocate(getX(), getY(), this);
    }


    /**
     * check next position in matrix. Must null or is player's location.
     * @param x is delta x.
     * @param y is delta y.
     * @return whether next position is suitable.
     */
    public boolean canMove(int x, int y) {
        Entities tmp = Map.getEntityAtLocate(x, y);
        if (tmp == null) return true;
        return tmp instanceof Player;
    }

}
