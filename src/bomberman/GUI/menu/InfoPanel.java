package bomberman.GUI.menu;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public static JLabel pointsLabel;
    public static JLabel livesLabel;

    /**
     * Construtor.
     */
    public InfoPanel() {
        setLayout (new GridLayout (4, 1));

        JLabel titleLabel = new JLabel ("=== I4 Player ===");
        titleLabel.setForeground (Color.white);
        titleLabel.setHorizontalAlignment (JLabel.CENTER);
        add (titleLabel);
        pointsLabel = new JLabel ("  Points: 0");
        pointsLabel.setForeground (Color.white);
        pointsLabel.setHorizontalAlignment (JLabel.LEFT);

        livesLabel = new JLabel ("  HP: 100");
        livesLabel.setForeground (Color.white);
        livesLabel.setHorizontalAlignment (JLabel.LEFT);

        add (pointsLabel);
        add (livesLabel);
        setBackground (new Color (0x0000000, true));
    }

    /**
     * set Hp text in infopanel;
     * @param t is current hp of player.
     */
    public static void setHp(int t) {
        livesLabel.setText("   HP: " + t);
    }

    /**
     * set Point text in infopanel;
     * @param t is current point of player.
     */
    public static void setPoints(int t) {
        pointsLabel.setText("   Points: " + t);
    }
}
