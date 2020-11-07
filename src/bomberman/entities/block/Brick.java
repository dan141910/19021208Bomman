package bomberman.entities.block;

import bomberman.entities.Entities;
import bomberman.gameSeting.Configuration;

import java.awt.*;

public class Brick extends Entities {
    public Brick(int x, int y) {
        setX(x);
        setY(y);
        setImage("res/img/icon_stone.png");
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(get_image(), getX(),getY(), Configuration.game_measure, Configuration.game_measure, null);
    }

    @Override
    public void collide(Entities e) {
        super.collide(e);
    }
}
