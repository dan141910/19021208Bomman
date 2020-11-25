package bomberman.entities.dynamicEntities.mods;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.dynamicEntities.DynamicEntities;
import bomberman.entities.dynamicEntities.mods.AI.SuperAI;
import bomberman.entities.item.Power;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Animation;
import bomberman.graphics.Images;
import bomberman.graphics.Map;

import java.awt.*;
import java.util.Random;

public class Kondoria extends Mob implements Animation {
	int animate = 100;
	Random rand = new Random();
	Board _board;
	
	public Kondoria(int x, int y, Board board) {
		super(x, y, Images.mob_kondoria0, 2, 250,
			  Configuration.game_measure / 4 * 3, board.getPlayer());
		setAttack(50);

		_ai = new SuperAI(_player, this);
		_direction  = _ai.calculateDirection();
		_board = board;
	}

	@Override
	public void update() {
		if(!isLive()) {
			setImage(Images.mob_kondoriaDead1);
			killed();
		}
		else {
			// animation
			animate();
			// movement
			calculateMove();
			//skill
			if (rand.nextInt(1000) % 400 == 0) skilled();
		}
	}

	private void skilled() {
		if (Map.getEntityAtLocate(getX(), getY()) == this) {
			int x = rand.nextInt(4);
			Entities tmp;
			if (x == 1) tmp = new Balloon(getX(), getY(), _board.getPlayer());
			else if (x == 2)  tmp = new Oneal(getX(), getY(), _board.getPlayer());
			else if (x == 3)  tmp = new Minvo(getX(), getY(), _board.getPlayer());
			else  return;

			_board.addEntity(tmp);
			Map._numMobs++;
		}
	}

	//===============================   OVERRIDE  ======================================================================

	@Override
	public void render(Graphics g) {
		super.render(g);
		g.drawImage(get_image(), getX(), getY(), getSize(), getSize(), null);
	}

	@Override
	public void collide(Entities e) {
		super.collide(e);
		if (e instanceof Brick) ( (Brick) e ).breaked();
	}

	@Override
	public boolean canMove(int x, int y) {
		Entities tmp = Map.getEntityAtLocate(x, y);
		if (tmp instanceof DynamicEntities) return true;
		if (tmp instanceof Power) return true;
		return tmp == null ;
	}

	@Override
	public void animate() {
		animate--;
		if (animate < 0) animate = 40;
		if (animate % 40 == 8) {
			setImage(Images.mob_kondoria1);
		} else if (animate % 40 == 16) {
			setImage(Images.mob_kondoria2);
		} else if (animate % 40 == 24) {
			setImage(Images.mob_kondoria3);
		} else if (animate % 40 == 32) {
			setImage(Images.mob_kondoria4);
		} else if (animate % 40 == 0)setImage(Images.mob_kondoria0);
	}
}