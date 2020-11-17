package bomberman.entities.dynamicEntities.mods.AI;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.Mob;


public class SuperAI extends AIHigh {

    public SuperAI(Player player, Mob mob) {
        super(player, mob);
    }
    @Override
    public int calculateDirection() {
        super.calculateDirection();
        // should use new algorithm
        return _curDirection;
    }
}
