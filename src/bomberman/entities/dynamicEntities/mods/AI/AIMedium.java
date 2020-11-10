package bomberman.entities.dynamicEntities.mods.AI;

import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.Mob;
import bomberman.graphics.Map;

import java.util.Random;


public class AIMedium extends AI {
	Player _player;
	Mob _mob;
	Random random;
	
	public AIMedium(Player player, Mob mob) {
		_player = player;
		_mob = mob;
		random  = new Random();
	}

	@Override
	public int calculateDirection() {
		
		if(_player == null)
			return random.nextInt(4);
		
		int vertical = random.nextInt(2);
		
		if(vertical == 1) {
			int v = calculateRowDirection();
			if(v != -1)
				return v;
			else
				return calculateColDirection();
			
		} else {
			int h = calculateColDirection();
			
			if(h != -1)
				return h;
			else
				return calculateRowDirection();
		}
		
	}
	
	protected int calculateColDirection() {
		if(_player.getX() < _mob.getX())
			return 3;
		else if(_player.getX() > _mob.getX())
			return 1;
		return -1;
	}
	
	protected int calculateRowDirection() {
		if(_player.getY() < _mob.getY())
			return 0;
		else if(_player.getY() > _mob.getY())
			return 2;
		return -1;
	}

}
