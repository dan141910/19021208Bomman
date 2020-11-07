package bomberman.GUI.menu;

import bomberman.GameController;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    public Menu(int width, int height, GameController gameController) {
        setBounds( new Rectangle(width,height) );
        setBackground( Color.DARK_GRAY );
        setLayout( new GridLayout(2,1) );

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        GameTool gameTool = new GameTool(gameController);
        btnPanel.add(gameTool.getNewGame());
        btnPanel.add(gameTool.getResumeGame());
        btnPanel.add(gameTool.getPointRank());
        btnPanel.add(gameTool.getSetting());
        btnPanel.add( gameTool.getExit());
        btnPanel.setBackground(new Color(0x0000000, true));

        add(new InfoPanel());

        //add(btnPanel);

    }

}
