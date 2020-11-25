package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AIHigh;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;

import java.awt.*;

public class Minvo extends Mob {

    public Minvo(int x, int y, Player player) {
        super(x, y, Images.mob_minvo, 2, 100, Configuration.game_measure / 5 * 2 , player);
        setAttack(5);
        _ai = new AIHigh(player, this);
        _direction = _ai.calculateDirection();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
    }
}
