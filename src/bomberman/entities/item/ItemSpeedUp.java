package bomberman.entities.item;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.dynamicEntities.Player;
import bomberman.graphics.Map;

public class ItemSpeedUp extends Power {

    public ItemSpeedUp(int x, int y, Player player) {
        super(x, y, "res/img/icon_power_speedup.png", player);
        setValue(1);
    }

    @Override
    public void toActive() {
        this._active = true;
        Map.setEntityAtLocate(getX(), getY(), null);
        this._player.setSpeed(this._player.getSpeed() + this.getValue());

        InfoPanel.notice("+1 speed in 5000s");
    }

    @Override
    public void removed() {
        super.removed();
        this._player.setSpeed(this._player.getSpeed() - this.getValue());
        InfoPanel.notice("Speed buff end! ");
    }
}
