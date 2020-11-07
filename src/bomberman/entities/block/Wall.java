package bomberman.entities.block;

import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;

import java.awt.*;


public class Wall extends Entities {
    public Wall(int x, int y) {
        setX(x);
        setY(y);
        setImage("res/img/icon_wall.png");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(),getY(), Configuration.game_measure, Configuration.game_measure, null);
    }
}
