package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AILow;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;

import java.awt.*;

public class Balloon extends Mob implements Animation {

    public Balloon(int x, int y, Player player) {
        super(x, y, Images.mob_balloon1, 1, 10,
              Configuration.game_measure / 5 * 3, player);
        setAttack(5);

        _ai = new AILow();
        _direction = _ai.calculateDirection();
    }

    @Override
    public void update() {
        super.update();
        animate();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
    }

    @Override
    public void animate() {
        if (_direction == 1) setImage(Images.mob_balloon1);
        else if (_direction == 3) setImage(Images.mob_balloon2);
    }
}
