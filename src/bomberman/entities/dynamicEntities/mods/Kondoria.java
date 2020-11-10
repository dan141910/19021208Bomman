package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.Board;
import bomberman.entities.dynamicEntities.mods.AI.AIHigh;
import bomberman.entities.dynamicEntities.mods.AI.AIHighAvoid;
import bomberman.gameSeting.Configuration;

public class Kondoria extends Mob {
	
	
	public Kondoria(int x, int y, Board board) {
		super(x, y, "res/img/mobs/kondoria1.png", 2, 250,
			  Configuration.game_measure / 2, board.getPlayer());
		setAttack(50);

		_ai = new AIHighAvoid(_player, this);
		_direction  = _ai.calculateDirection();
	}

}
