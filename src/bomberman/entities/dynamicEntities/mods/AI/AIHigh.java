package bomberman.entities.dynamicEntities.mods.AI;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.Mob;

import java.util.Random;

public class AIHigh extends  AI{
    Player _player;
    Mob _mob;

    public AIHigh(Player player, Mob mob) {
        _player = player;
        _mob = mob;
    }
    @Override
    public int calculateDirection() {
        return (int)(Math.random() * 5);
    }
}
