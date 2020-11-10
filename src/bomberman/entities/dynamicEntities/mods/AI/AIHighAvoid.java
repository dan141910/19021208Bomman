package bomberman.entities.dynamicEntities.mods.AI;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.Mob;



public class AIHighAvoid extends AI {
    Player _player;
    Mob _mob;

    public AIHighAvoid(Player player, Mob mob) {
        _player = player;
        _mob = mob;
    }
    @Override
    public int calculateDirection() {
        return 0;
    }
}
