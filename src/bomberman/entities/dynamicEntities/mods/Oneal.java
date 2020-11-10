package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AIMedium;
import bomberman.gameSeting.Configuration;

public class Oneal extends Mob {

    public Oneal(int x, int y, Player player) {
        super(x, y, "res/img/mobs/Oneal.png", 5, 100, Configuration.game_measure / 2, player);
        setAttack(10);
        _ai = new AIMedium(player, this);
        _direction = _ai.calculateDirection();
        _player = player;
    }

}
