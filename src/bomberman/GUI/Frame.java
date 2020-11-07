package bomberman.GUI;

import bomberman.GameController;

import javax.swing.*;

/**
 * contain gameboard.
 */
public class Frame extends JPanel {
	public Frame(int width, int height, GameController gameController) {
		setBounds(width / 9 + 5, 0,width,height );
		add(gameController);
	}
}
