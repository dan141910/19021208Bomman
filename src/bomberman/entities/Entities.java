package bomberman.entities;

import bomberman.graphics.Render;

import java.awt.*;

public abstract class Entities implements Render {
    protected int _x, _y;
    protected boolean _remove = false; // to render or not
    protected Image _image;

    public abstract void update();

    public abstract void render(Graphics g);

    public void collide(Entities e) {
        // check boom and fire
    }

    //==================================================================================================================
    // GETTER AND SETTER
    //==================================================================================================================

    public void removed() {
        _remove = true;
    }

    public boolean isRemoved() {
        return _remove;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void setX(int _x) {
        this._x = _x;
    }

    public void setY(int _y) {
        this._y = _y;
    }

    public Image get_image() {
        return _image;
    }

    public void setImage(String path) {
        this._image = Toolkit.getDefaultToolkit().getImage(path);
    }
}
