package bomberman.graphics;

import bomberman.GUI.Board;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Wall;
import bomberman.entities.dynamicEntities.Player;
import bomberman.gameSeting.Configuration;

import java.io.FileReader;
import java.util.Scanner;

public class Map {
    public static final String[] _maps = new String[Configuration.game_rows + 1];

    static {
        loadmap(1);
    }
    // init map
    public static void loadmap(int level) {
        if (level < 0 || level > 5) level = 1;
        String str = "res/levels/Level" + level + ".txt";
        try{
            FileReader fileReader = new FileReader(str);
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0; i < Configuration.game_rows; i++) {
                str = scanner.nextLine();
                _maps[i] = str;
            }
        } catch (Exception e) {
            System.out.println("Error load map!");
            System.exit(-1);
        }
    }

    public static void initMap(Board board) {
        try {
            int measure = Configuration.game_measure;
            for (int i = 0; i < Configuration.game_rows; i++) {
                for (int j = 0; j < Configuration.game_cols; j++) {
                    if (_maps[i].charAt (j) == '#') {
                        board.addEntity(new Wall (j * measure, i * measure));
                    } else if(_maps[i].charAt(j) == '*') {
                        board.addEntity(new Brick(j * measure, i * measure));
                    } else if (_maps[i].charAt (j) == 'p') {
                        Player player = new Player (j * measure, i * measure, board);
                        board.addEntity (player);
                        board.setPlayer (player);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println ("error Map class! : initMap");
        }
    }
    // coordinate - x&y in matrix

    /**
     * get real index from X coordinates.
     * @param x is X coordinates in frame/canvas.
     * @return return the index column of entity in map matrix.
     */
    public static int getRealX(int x) {
        return (int)Math.floor((double)x / Configuration.game_measure) * Configuration.game_measure;
    }

    /**
     * get real index from Y coordinates.
     * @param y is Y coordinates in frame/canvas.
     * @return return the index rows of entity in map matrix.
     */
    public static int getRealY(int y) {
        return (int)Math.floor((double)y / Configuration.game_measure) * Configuration.game_measure;
    }
}
