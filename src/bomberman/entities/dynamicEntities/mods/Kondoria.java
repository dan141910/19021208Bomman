package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.Board;
import bomberman.entities.dynamicEntities.mods.AI.SuperAI;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;

public class Kondoria extends Mob {
	
	
	public Kondoria(int x, int y, Board board) {
		super(x, y, Images.mob_kondoria1, 2, 250,
			  Configuration.game_measure / 4 * 3, board.getPlayer());
		setAttack(50);

		_ai = new SuperAI(_player, this);
		_direction  = _ai.calculateDirection();
	}

}