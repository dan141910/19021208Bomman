package bomberman.GUI.menu;

import bomberman.GameController;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameTool implements MouseListener {
    private final JButton newGame;
    private final JButton resumeGame;
    private final JButton pointRank;
    private final JButton exit;
    private final JButton setting;
    private final GameController gameController;

    public JButton getExit() {
        return exit;
    }

    public JButton getNewGame() {
        return newGame;
    }

    public JButton getPointRank() {
        return pointRank;
    }

    public JButton getResumeGame() {
        return resumeGame;
    }

    public JButton getSetting() {
        return setting;
    }

    //=================================================================================================================
    // method
    //=================================================================================================================
    public GameTool(GameController gameController) {
        newGame = new JButton("New");
        newGame.addMouseListener(this);
        resumeGame = new JButton("Resume Game");
        resumeGame.addMouseListener(this);
        exit = new JButton("Exit");
        exit.addMouseListener(this);
        pointRank = new JButton("High Scores");
        pointRank.addMouseListener(this);
        setting= new JButton("Setting");
        setting.addMouseListener(this);
        this.gameController = gameController;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gameController.stop();
        if (e.getSource() == exit) { System.exit(1);}
        else if (e.getSource() == newGame) {
            System.out.println("restart");
        } else if (e.getSource() == resumeGame) {
            gameController.run();
        } else if (e.getSource() == pointRank) {
            System.out.println("dank : 1 bilion point");
        } else if (e.getSource() == setting) {
            System.out.println("setting");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
