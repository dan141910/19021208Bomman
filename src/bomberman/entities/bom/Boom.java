package bomberman.entities.bom;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Wall;
import bomberman.entities.dynamicEntities.Player;
import bomberman.gameSeting.Configuration;
import bomberman.gameSeting.Sound;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Boom extends Entities implements Animation {
    private int range;
    private int dame;
    private int time;
    private final int TIME_COUNT = 20; // Time to burn
    private int _timeAfter; // Time to burn + time for player exit
    private final Player player;
    private final Board board;
    //sound
    private Sound sound;
    private Sound sound_fire;

    public Boom( int x, int y, int dame, int range, Player player, Board board) {
        setDame( dame );
        setRange( range );
        setX(x);
        setY(y);
        setTime( 200 );
        Map.setEntityAtLocate(getX(), getY(), this);
        setImage(Images.boom);
        this.board = board;
        this.player = player;
        this.player.setNumBoom(this.player.getNumBoom() - 1);
        this._timeAfter = TIME_COUNT;

        try {
            this.sound = new Sound(Sound.sound_detecBoom, true);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
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
        Entities tmp = Map.getEntityAtLocate(x, y);
        if (tmp instanceof Wall) return false;
        if (tmp instanceof Brick) {
            ((Brick) tmp).breaked();
            return  false; // to break Brick
        }
        return true;
    }

    public void fire() {
        this.board.addEntity(new Fire(getX(),getY(),getDame()));

        boolean up, right, left, down;
        up = right = left = down = true;
        int measure = Configuration.game_measure;
        for (int i = 1; i <= getRange(); i++) {

            if (left &&  canFire(getX() + i * measure, getY())) {
                this.board.addEntity( new Fire(getX() + i * measure,getY(),getDame()));
            } else left = false;

            if (right && canFire(getX() - i * measure,getY())) {
                this.board.addEntity(new Fire(getX() - i * measure,getY(),getDame()));
            } else right = false;

            if (up && canFire(getX(),getY() + i * measure)) {
                this.board.addEntity(new Fire(getX(),getY() + i * measure,getDame()));
            } else up = false;

            if (down && canFire(getX(),getY() - i * measure)) {
                this.board.addEntity(new Fire(getX(),getY() - i * measure,getDame()));
            } else down = false;
        }

        //sound fire
        try {
            this.sound_fire = new Sound(Sound.sound_fire, false);
            sound_fire.play();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
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
            animate();
        } else {
            if (_timeAfter == TIME_COUNT) fire();
            //sound
            if (_timeAfter > 0) {
                _timeAfter--;
                if (!sound.isPlay()) sound.play();
            } else {
                removed();
                player.setNumBoom(player.getNumBoom() + 1 );
                sound.stop();
                sound_fire.stop();
            }
        }
    }

    @Override
    public void removed() {
        super.removed();
        Map.setEntityAtLocate(getX(), getY(), null);
    }

    @Override
    public void animate() {
        if (getTime() % 40 == 20) setImage(Images.boom1);
        else if (getTime() % 40 == 0) setImage(Images.boom);
    }
}
