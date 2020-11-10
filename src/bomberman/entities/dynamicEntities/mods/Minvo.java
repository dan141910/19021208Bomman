package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AIHigh;
import bomberman.entities.dynamicEntities.mods.AI.AIMedium;
import bomberman.gameSeting.Configuration;

public class Minvo extends Mob {

    public Minvo(int x, int y, Player player) {
        super(x, y, "res/img/mobs/Minvo.png", 1, 100, Configuration.game_measure / 4, player);

        _ai = new AIHigh(player, this);
        _direction = _ai.calculateDirection();
    }

}
