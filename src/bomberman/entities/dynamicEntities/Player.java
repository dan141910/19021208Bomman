package bomberman.entities.dynamicEntities;

import bomberman.GUI.Board;
import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.Entities;
import bomberman.entities.bom.Boom;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Keyboard;
import bomberman.graphics.Map;

import java.awt.*;

public class Player extends DynamicEntities{
    private int _numBoom = 1;
    private int _timeToDecBom = 0;
    private int _dameRange = 2;
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
        System.out.println("Create Player!");
        setImage("res/img/icon_bomberman.png");
    }

    /**
     * Process collide event.
     * @param e is other entities.
     */

    @Override
    public void collide(Entities e) {
        if (e instanceof Boom) {
            setHp( getHp() - ((Boom) e).getDame());
        }
    }

    /**
     * Check new the location, if they can move to.
     * @param x is canvas X coordinate.
     * @param y is canvas Y coordinate.
     * @return true or false.
     */
    private boolean isMove(int x, int y) {
        int col = Map.getRealX(x) / Configuration.game_measure;
        int row = Map.getRealY(y) / Configuration.game_measure;

        switch (Map._maps[row].charAt(col)) {
            case '#': return false;
            case '*': return false;
        }
        return true;
    }
    @Override
    public void update() {
        // update location
        int xa = getX(), ya = getY();
        if(_input.up) ya -= this.getSpeed();
        if(_input.down) ya += this.getSpeed();
        if(_input.left) xa -= this.getSpeed();
        if(_input.right) xa += this.getSpeed();

//        if(xa > 0 && xa < Configuration.game_width / 10 * 9 - Configuration.game_measure - 10)  {
        if(isMove(xa, getY()) && isMove(xa + getSize(), getY()) &&
                isMove(xa, getY() + getSize()) && isMove(xa + getSize(), getY() + getSize()) )  {
            // check 4 goc
            setX(xa);
        }
//        if(ya > 0 && ya < Configuration.game_height - Configuration.game_measure * 2) {
        if(isMove(getX(), ya) && isMove(getX() + getSize(), ya + getSize()) &&
                isMove(getX(), ya + getSize()) && isMove(getX() + getSize(), ya)) {
            setY(ya);
        }

        //update boom action
        if(_input.space) {

            if (_timeToDecBom <= 0 && 0 < getNumBoom() ) {
                setNumBoom(getNumBoom() - 1);
                _timeToDecBom = 20;
                Boom x = new Boom(Map.getRealX(getX()), Map.getRealY(getY()), 20, get_dameRange(), this, this._board);
                _board.addEntity(x);
            }
        }
        if (_timeToDecBom > 0) _timeToDecBom --;

        //update info to pannel
        InfoPanel.setHp(getHp());
        InfoPanel.setPoints(getPoint());
    }

    public void render(Graphics g) {
        g.setColor(new Color(0xCB4F954A, true));
        g.fillRect(Map.getRealX(getX()), Map.getRealY(getY()), Configuration.game_measure, Configuration.game_measure);
        g.drawImage(get_image(), getX(),getY(), getSize(), getSize(),null);

    }


    //==================================================================================================================
    // GETTER AND SETTER
    //==================================================================================================================

    public int getNumBoom() {
        return _numBoom;
    }

    public void setNumBoom(int numBoom) {
        this._numBoom = numBoom;
    }

    public void set_input(Keyboard _input) {
        this._input = _input;
    }

    public void setdameRange(int _dameRange) {
        this._dameRange = _dameRange;
    }

    public int get_dameRange() {
        return _dameRange;
    }
}
