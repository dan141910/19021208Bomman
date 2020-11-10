package bomberman.entities.item;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.dynamicEntities.Player;
import bomberman.graphics.Map;

public class ItemFlameUp extends Power {

    public ItemFlameUp(int x, int y, Player player) {
        super(x, y, "res/img/icon_power_plameup.png", player);
        setValue(1);
    }

    @Override
    public void toActive() {
        this._active = true;
        Map.setEntityAtLocate(getX(), getY(), null);
        this._player.setDameRange(this._player.get_dameRange() + this.getValue());

        InfoPanel.notice("+1 Flame Range in 5000s");
    }

    @Override
    public void removed() {
        super.removed();
        this._player.setDameRange(this._player.get_dameRange() - this.getValue());
        InfoPanel.notice("end time buff Flame!");
    }
}
