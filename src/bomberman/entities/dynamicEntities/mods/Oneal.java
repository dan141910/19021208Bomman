package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AIMedium;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;

import java.awt.*;

public class Oneal extends Mob {

    public Oneal(int x, int y, Player player) {
        super(x, y, Images.mob_oneal, 5, 100, Configuration.game_measure / 2, player);
        setAttack(10);
        _ai = new AIMedium(player, this);
        _direction = _ai.calculateDirection();
        _player = player;
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
    }
}
