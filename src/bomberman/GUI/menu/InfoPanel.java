package bomberman.GUI.menu;

import bomberman.graphics.Map;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private static JTextArea text;
    private static JTextArea message;
    private static JTextArea helpText;
    /**
     * Construtor.
     */
    public InfoPanel(int width, int height) {
        setLayout (new GridLayout(3,1));
        setBounds( new Rectangle(width,height) );
        text = new JTextArea(6, 6);
        text.setFont(new Font("Monaco", Font.BOLD, 25));
        text.setLineWrap(true);
        text.setEnabled(false);
        text.setBackground(new Color(0x956262));
        add (text);

        message = new JTextArea(6, 6);
        message.setFont(new Font("Helvetica Neue", Font.ITALIC, 15));
        message.setLineWrap(true);
        message.setEnabled(false);
        message.setMargin(new Insets(15,2,0,5));
        message.setBackground(new Color(0x956262));
        add(message);

        helpText = new JTextArea(6, 6);
        helpText.setLineWrap(true);
        helpText.setEnabled(false);
        helpText.setMargin(new Insets(5,5,5,5));
        helpText.setFont(new Font("Serif", Font.BOLD, 20));
        helpText.setBackground(new Color(0x956262));
        add(helpText);

    }


    public static void update(int hp, int point, int booms, int rangeboom, int speed) {
        String str = "L" + Map._level  + " Mobs :" + Map._numMobs +
                "\n  HP = " + hp +
                "\n  Point = " + point +
                "\n  Booms = " + booms +
                "\n  Flame = " + rangeboom +
                "\n  Speed = " + speed +
                "\n ==========";
        text.setText(str);
        helpText.setText(" ============ \nControl : w, s, a, d, space\n Tiêu diệt hết mob và tìm cổng ra");
    }

    public static void notice(String mess) {
        if (message.getLineCount() > 3 || message.getText().length() == 0)
            message.setText("Messages from GM:\n  "   + mess);
        else message.append("\n " + mess );
    }
}
