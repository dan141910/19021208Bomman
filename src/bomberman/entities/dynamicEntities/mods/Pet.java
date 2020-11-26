package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.Entities;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AIMedium;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

/**
 * pet just is a harmless mob and just follows player.
 * it's temp no skill.
 */
public class Pet extends Mob implements Animation {
    Player _player;
    boolean _isShow;
    int _countTime;
    int _animateTime;
    public Pet(int x, int y, String image, Player player) {
        super(x, y, Images.mob_minvo, 2, 0, Configuration.game_measure / 5 * 3 , player);
        _player = player;
        _isShow = false;
        _ai = new AIMedium(player, this);
        _direction = _ai.calculateDirection();
        _animateTime = 100;
        setImage(image);
        setCountTime(500);
    }

    @Override
    public void update() {
        if (getCountTime() > 0) {
            setCountTime(getCountTime() - 1);
            if (getCountTime() == 0) InfoPanel.notice("I can summone your Pikachu");
        }
        if (isLive() && _isShow) {
            calculateMove();
            animate();
        }
        else if (!isLive()){
            setImage(Images.mob_dead);
            killed();
        }
    }

    /**
     * it's different from Mod.
     * it doesnt impact to Map class.
     */
    @Override
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

        if (!(Map.getEntityAtLocate(xa, ya) instanceof Player)) {
            if (checkBound(xa, getY(), getSize(), getSize())) setX(xa);_steps -= 1;
            if (checkBound(getX(), ya, getSize(), getSize())) setY(ya);_steps -= 1;
        }
        // check collide
        Entities tmp = Map.getEntityAtLocate(getX(), getY());
        collide(tmp);

    }

    @Override
    public void render(Graphics g) {
        if (_isShow) {
            super.render(g);
            g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
        }
    }

    @Override
    public void collide(Entities e) {
        if (e instanceof Mob) {
            InfoPanel.notice("Your pet is dared so it is disappear! \n CD 1000s");
            setCountTime(1000);
            _isShow = false;
        }
    }

    @Override
    public void animate() {

    }

    @Override
    protected void killed() {
        if(_timeAfter > 0) {
            _timeAfter--;
        }
        else removed();
    }

    //========================================== getter and setter =====================================================

    public int getCountTime() {
        return _countTime;
    }

    public void setCountTime(int countTime) {
        this._countTime = countTime;
    }

    public void summon() {
        if (getCountTime() > 0) {
            InfoPanel.notice("CD skill " + getCountTime() + "s");
            return;
        }
        this._isShow = true;
        setCountTime(500); // set cd
        // change position
        _preX = getX();
        _preY = getY();
        setX(_player.getX());
        setY(_player.getY());
    }

}
