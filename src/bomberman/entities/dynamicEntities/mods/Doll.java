package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AILow;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Doll extends Mob {

	public Doll(int x, int y, Player player) {
		super(x, y, Images.mob_doll, 2, 50,
			  Configuration.game_measure / 5 * 2, player);

		setAttack(5);

		_ai = new AILow();
		_direction = _ai.calculateDirection();
	}

	public void calculateMove() {
		int xa = getX(), ya = getY();

		if(_steps <= 0){
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}

		if(_direction == 0) ya -= getSpeed();
		if(_direction == 2) ya += getSpeed();
		if(_direction == 3) xa -= getSpeed();
		if(_direction == 1) xa += getSpeed();

		if (Map.getEntityAtLocate(getX(), getY()) == this) Map.setEntityAtLocate(getX(), getY(), null);

		if(canMove(xa, getY()) && canMove(xa + getSize(), getY()) &&
				canMove(xa, getY() + getSize() * 2) && canMove(xa + getSize(), getY() + getSize() * 2) )  {
			setX(xa);
		}

		if(canMove(getX(), ya) && canMove(getX() + getSize(), ya + getSize() * 2) &&
				canMove(getX(), ya + getSize() * 2) && canMove(getX() + getSize(), ya)) {
			setY(ya);
		}

		_steps -= 1;
		// change matrix
		collide(Map.getEntityAtLocate(getX(), getY()));
		if (Map.getEntityAtLocate(getX(), getY()) == null) Map.setEntityAtLocate(getX(), getY(), this);
	}


	/**
	 * check next position in matrix. Must null or is player's location.
	 * @param x is delta x.
	 * @param y is delta y.
	 * @return whether next position is suitable.
	 */
	public boolean canMove(int x, int y) {
		Entities tmp = Map.getEntityAtLocate(x, y);
		if (tmp == null) return true;
		if (tmp instanceof Brick) return true;
		return tmp instanceof Player;
	}

	@Override
	public void collide(Entities entities) {
		if (entities instanceof Brick) {
			((Brick) entities).breaked();
		} else super.collide(entities);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(get_image(), getX(), getY(), getSize(), getSize() * 2, null);
	}

	@Override
	public void removed() {
		_player.setSpeed(_player.getSpeed() - 1);
		super.removed();
	}
}
