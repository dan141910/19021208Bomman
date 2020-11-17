package bomberman.entities.dynamicEntities;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Gate;
import bomberman.entities.block.Wall;
import bomberman.entities.bom.Boom;
import bomberman.entities.item.ItemBoomUp;
import bomberman.entities.item.ItemFlameUp;
import bomberman.entities.item.ItemSpeedUp;
import bomberman.entities.item.Power;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Player extends DynamicEntities{
    private int _numBoom = 1;
    private int _timeToDecBom = 0;
    private int _dameRange = 1;
    private Keyboard _input;
    private final Board _board;

    public Player(int x, int y, Board board) {
        setHp( 100 );
        setPoint( 0 );
        setSpeed( 2 );
        setX( x );
        setY( y );
        setAttack( 20 );
        setSize(Configuration.game_measure / 2);
        _remove = false;
        _board = board;
        _timeAfter = 20;
        setImage(Images.player);
    }

    /**
     * Check new the location, if they can move to.
     * @param x is canvas X coordinate.
     * @param y is canvas Y coordinate.
     * @return true or false.
     */
    private boolean canMove(int x, int y) {
        Entities tmp = Map.getEntityAtLocate(x, y);
        if (tmp instanceof Wall) return false;
        if (tmp instanceof Gate && !((Gate) tmp).isActive()) return false;
        if (tmp instanceof Boom ) {
            if (Map.getEntityAtLocate(getX() , getY()) instanceof Boom ) return true;
            if (Map.getEntityAtLocate(getX() + getSize(), getY()) instanceof Boom ) return true;
            if (Map.getEntityAtLocate(getX(), getY() + getSize()) instanceof Boom ) return true;
            return Map.getEntityAtLocate(getX() + getSize(), getY() + getSize()) instanceof Boom;
        }
        return !(tmp instanceof Brick);
    }

    //=============================== update segment =============================================
    private void updateMove() {
        // update location
        int xa = getX(), ya = getY();
        if(_input.up) ya -= this.getSpeed();
        if(_input.down) ya += this.getSpeed();
        if(_input.left) xa -= this.getSpeed();
        if(_input.right) xa += this.getSpeed();

        // change matrix
        if (Map.getEntityAtLocate(getX(), getY()) instanceof Player)
            Map.setEntityAtLocate(getX(), getY(),null);

        if(canMove(xa, getY()) && canMove(xa + getSize(), getY()) &&
                canMove(xa, getY() + getSize()) && canMove(xa + getSize(), getY() + getSize()) )  {
            setX(xa);
        }

        if(canMove(getX(), ya) && canMove(getX() + getSize(), ya + getSize()) &&
                canMove(getX(), ya + getSize()) && canMove(getX() + getSize(), ya)) {
            setY(ya);
        }

        // change matrix
        if (Map.getEntityAtLocate(getX(), getY()) == null) Map.setEntityAtLocate(getX(), getY(), this);

        // check gravity
        collide( Map.getEntityAtLocate(getX() + getSize() / 2, getY() + getSize() / 2) );
    }

    private void detectBoom() {
        //update boom action
        if(_input.space) {

            if (_timeToDecBom <= 0 && 0 < getNumBoom()) {
                _timeToDecBom = 20;
                Boom x = new Boom(Map.getRealX(getX()), Map.getRealY(getY()),
                        20, get_dameRange(), this, this._board);
                _board.addEntity(x);
            }
        }
        if (_timeToDecBom > 0) _timeToDecBom --;
    }

    @Override
    public void update() {
        if (isLive()) {
            updateMove();
            detectBoom();
        }
        else killed();
    }
    @Override
    protected void killed() {
        if(_timeAfter > 0) {
            setImage("res/img/mobs/modDead.png");
            _timeAfter--;
        }
        else removed();
    }

    @Override
    public void collide(Entities e) {
        if (e instanceof Power) {
            if (e instanceof ItemFlameUp) {
                ((ItemFlameUp) e).toActive();
            }else if (e instanceof ItemSpeedUp) {
                ((ItemSpeedUp) e).toActive();
            }else if (e instanceof ItemBoomUp) {
                ((ItemBoomUp) e).toActive();
            }
        }
        else if (e instanceof Gate && ((Gate) e).isActive()) {
            _board.setPassGame(true);
            setSpeed(0);
        }
    }

    @Override
    public void render(Graphics g) {
         g.drawImage(get_image(), getX(),getY(), getSize(), getSize(),null);
    }

    //==================================================================================================================
    // GETTER AND SETTER
    //==================================================================================================================

    public int getNumBoom() {
        return _numBoom;
    }

    public void setNumBoom(int numBoom) {
        this._numBoom = Math.min(numBoom,3);
    }

    public void set_input(Keyboard _input) {
        this._input = _input;
    }

    public void setDameRange(int _dameRange) {
        this._dameRange = _dameRange;
    }

    public int get_dameRange() {
        return _dameRange;
    }

}
