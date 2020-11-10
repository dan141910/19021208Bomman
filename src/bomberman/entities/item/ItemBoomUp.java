package bomberman.entities.item;

import bomberman.GUI.menu.InfoPanel;
import bomberman.entities.dynamicEntities.Player;
import bomberman.graphics.Map;

import javax.swing.*;

public class ItemBoomUp extends Power{

    public ItemBoomUp(int x, int y, Player player) {
        super(x, y, "res/img/icon_power_boomup.png", player);
        setValue(1);
    }

    @Override
    public void toActive() {
        removed();
        this._player.setNumBoom(this._player.getNumBoom() + this.getValue());
        Map.setEntityAtLocate(getX(), getY(), null);
        InfoPanel.notice("+1 boom");
    }

    @Override
    public void removed() {
        super.removed();
    }
}
