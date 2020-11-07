package bomberman.entities.bom;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.dynamicEntities.Player;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Map;

import java.awt.*;

public class Boom extends Entities {
    private int range;
    private int dame;
    private int time;
    private final Player player;
    private final Board board;

    public Boom( int x, int y, int dame, int range, Player player, Board board) {
        setDame( dame );
        setRange( range );
        setX(x);
        setY(y);
        setTime( 200 );
        setImage("res/img/icon_bomber.png");
        this.board = board;
        this.player = player;

    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDame(int dame) {
        this.dame = dame;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getTime() {
        return time;
    }

    public int getDame() {
        return dame;
    }

    public int getRange() {
        return range;
    }

    /**
     * Explore
     */
    private boolean canFire(int x, int y){
        int col = x / Configuration.game_measure;
        int row = y / Configuration.game_measure;

        return Map._maps[row].charAt(col) != '#';
    }
    public void fire() {
        this.board.addEntity(new Fire(getX(),getY(),getDame()));
        boolean up, right, left, down;
        up = right = left = down = true;
        for (int i = 0; i <= getRange(); i++) {
            if ( left && canFire(getX() + i * Configuration.game_measure, getY()) ) {
                this.board.addEntity(new Fire(getX() + i * Configuration.game_measure,getY(),getDame()));
            } else left = false;
            if (right && canFire(getX() - i * Configuration.game_measure,getY())) {
                this.board.addEntity(new Fire(getX() - i * Configuration.game_measure,getY(),getDame()));
            } else right = false;

            if (up && canFire(getX(),getY() + i * Configuration.game_measure)) {
                this.board.addEntity(new Fire(getX(),getY() + i * Configuration.game_measure,getDame()));
            } else up = false;
            if (down && canFire(getX(),getY() - i * Configuration.game_measure)) {
                this.board.addEntity(new Fire(getX(),getY() - i * Configuration.game_measure,getDame()));
            } else down = false;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(),getY(), Configuration.game_measure, Configuration.game_measure, null);
    }

    @Override
    public void update(){
        if (getTime() > 0) {
            setTime(getTime() - 2);
        }
        else {
            fire();
            player.setNumBoom(player.getNumBoom() + 1 );
            removed();
        }
    }
}
