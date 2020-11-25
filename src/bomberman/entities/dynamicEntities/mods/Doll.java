package bomberman.entities.dynamicEntities.mods;

import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.AI.AILow;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;

public class Doll extends Mob implements Animation {

	public Doll(int x, int y, Player player)  {
		super(x, y, Images.mob_doll1, 2, 50,
			  Configuration.game_measure / 2, player);

		setAttack(5);

		_ai = new AILow();
		_direction = _ai.calculateDirection();
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
	public void update() {
		super.update();
		animate();
	}

	@Override
	public void collide(Entities entities) {
		if (entities instanceof Brick) {
			((Brick) entities).breaked();
		} else super.collide(entities);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
	}

	@Override
	public void removed() {
		_player.setSpeed(_player.getSpeed() - 1 == 0 ? 1 : _player.getSpeed() - 1);
		super.removed();
	}

	@Override
	public void animate() {
		if (_direction == 1) setImage(Images.mob_doll1);
		else if (_direction == 3) setImage(Images.mob_doll2);
	}
}
