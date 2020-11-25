package bomberman.entities.dynamicEntities;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Gate;
import bomberman.entities.block.Wall;
import bomberman.entities.bom.Boom;
import bomberman.entities.dynamicEntities.mods.Mob;
import bomberman.entities.item.ItemBoomUp;
import bomberman.entities.item.ItemFlameUp;
import bomberman.entities.item.ItemSpeedUp;
import bomberman.entities.item.Power;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Player extends DynamicEntities implements Animation {
    private int _numBoom = 1;
    private int _timeToDecBom = 0;
    private int _dameRange = 1;
    private Keyboard _input;
    private final Board _board;
    private boolean _ismove;
    private int _animate = 0;

    public Player(int x, int y, Board board) {
        setHp( 100 );
        setPoint( 0 );
        setSpeed( 2 );
        setX( x );
        setY( y );
        setAttack( 20 );
        setSize(Configuration.game_measure / 5 * 4);
        _remove = false;
        _board = board;
        _timeAfter = 20;
        setImage(Images.player);
    }

    /**
     * Check new the location, if we can move to.
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
        _preX = getX(); _preY = getY();
        int xa = _preX, ya = _preY;
        if(_input.up) ya -= this.getSpeed();
        if(_input.down) ya += this.getSpeed();
        if(_input.left) xa -= this.getSpeed();
        if(_input.right) xa += this.getSpeed();

        _direction = -1;
        if (xa > _preX) _direction = 1; // right;
        if (xa < _preX) _direction = 3; // left
        if (ya > _preY) _direction =  2; // down
        if (ya < _preY) _direction = 0; // up

        // change matrix
        if (Map.getEntityAtLocate(getX(), getY()) instanceof Player) Map.setEntityAtLocate(getX(), getY(),null);

        // change coordinate
        if(canMove(xa, getY()) && canMove(xa + getSize(), getY()) &&
                canMove(xa, getY() + getSize()) && canMove(xa + getSize(), getY() + getSize()) )  {
            setX(xa);
        }

        if(canMove(getX(), ya) && canMove(getX() + getSize(), ya + getSize()) &&
                canMove(getX(), ya + getSize()) && canMove(getX() + getSize(), ya)) {
            setY(ya);
        }

        _ismove = xa != _preX || ya != _preY;
        // set animation


        // check collide
        collide( Map.getEntityAtLocate(getX() + getSize() / 2, getY() + getSize() / 2) );

        // change matrix
        if (Map.getEntityAtLocate(getX(), getY()) == null) Map.setEntityAtLocate(getX(), getY(), this);
    }

    private void detectBoom() {
        //update boom action
        if(_input.space) {
            if (Map.getEntityAtLocate(getX() + getSize() / 2, getY() + getSize() / 2) instanceof Player) {
                if (_timeToDecBom <= 0 && 0 < getNumBoom()) {
                    _timeToDecBom = 20;
                    Boom x = new Boom(Map.getRealX(getX() + getSize() / 2),
                                      Map.getRealY(getY() + getSize() / 2),
                                      20, get_dameRange(), this, this._board);
                    _board.addEntity(x);
                }
            }
        }
        if (_timeToDecBom > 0) _timeToDecBom --;
    }

    @Override
    public void update() {
        if (isLive()) {
            updateMove();
            detectBoom();
            animateTime();
            animate();
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

        } else if (e instanceof Mob) {
            setHp(getHp() - ( (Mob) e ).getAttack());

        } else if (e instanceof Gate && ((Gate) e).isActive()) {
            _board.setPassGame(true);
            setSpeed(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0xCB4F954A, true));
        g.fillRect(_preX,_preY, getSize(), getSize());
         g.drawImage(get_image(), getX(),getY(), getSize(), getSize(),null);
    }

    // animate
    private void animateTime() {
        int _MAX_ANIMATE = 7500;
        if(_animate < _MAX_ANIMATE) _animate++; else _animate = 0; //reset animation
    }

    private String movingSprite(String x1, String x2, int animate) {
        int diff = 20 / 2;
        return (animate % 20 > diff) ? x1 : x2;
    }

    @Override
    public void animate() {
        switch (_direction) {
            case 0 -> {
                setImage(Images.player_up_1);
                if (_ismove) {
                    setImage(movingSprite(Images.player_up_2, Images.player_up_3, _animate));
                }
            }
            case 1 -> {
                setImage(Images.player_right_1);
                if (_ismove) {
                    setImage(movingSprite(Images.player_right_2, Images.player_right_3, _animate));
                }
            }
            case 2 -> {
                setImage(Images.player_down_1);
                if (_ismove) {
                    setImage(movingSprite(Images.player_down_2, Images.player_down_3, _animate));
                }
            }
            case 3 -> {
                setImage(Images.player_left_1);
                if (_ismove) {
                    setImage(movingSprite(Images.player_left_2, Images.player_left_3, _animate));
                }
            }
            default -> setImage(Images.player);
        }
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
