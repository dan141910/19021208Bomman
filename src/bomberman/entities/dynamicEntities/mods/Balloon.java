package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AILow;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;

public class Balloon extends Mob {

    public Balloon(int x, int y, Player player) {
        super(x, y, Images.mob_balloon, 1, 10,
              Configuration.game_measure / 2, player);
        setAttack(5);

        _ai = new AILow();
        _direction = _ai.calculateDirection();
    }

}
