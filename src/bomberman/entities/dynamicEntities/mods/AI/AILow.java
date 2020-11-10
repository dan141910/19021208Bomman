package bomberman.entities.dynamicEntities.mods.AI;

/**
 * ignorant AI, just random up, down, left, right direction :)).
 */
public class AILow extends AI {

	@Override
	public int calculateDirection() {
		return (int)(Math.random() * 5);
	}
}
