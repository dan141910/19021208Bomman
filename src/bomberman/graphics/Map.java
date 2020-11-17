package bomberman.graphics;

import bomberman.GUI.Board;
import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Gate;
import bomberman.entities.block.Wall;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.*;
import bomberman.entities.item.ItemBoomUp;
import bomberman.entities.item.ItemFlameUp;
import bomberman.entities.item.ItemSpeedUp;
import bomberman.gameSeting.Configuration;

import java.io.FileReader;
import java.util.Scanner;

public class Map {
    public static final Entities[] _matrix = new Entities[Configuration.game_cols * Configuration.game_rows + 1];
    public static int _numMobs = 0;
    public static int _level = 1
            ;
    // init map
    public static void initMap(Board board) {
        try {
            int measure = Configuration.game_measure;
            Entities tmp;
            String str;
            FileReader fileReader = new FileReader("res/levels/Level" + _level + ".txt");
            Scanner scanner = new Scanner(fileReader);

            for (int i = 0; i < Configuration.game_rows; i++) {
                str = scanner.nextLine();
                for (int j = 0; j < Configuration.game_cols; j++) {
                    //block
                    if (str.charAt (j) == '#') {
                        tmp = new Wall (j * measure, i * measure);
                        board.addEntity(tmp);
                        _matrix[i * Configuration.game_cols + j] = tmp;
                    }
                    else if(str.charAt(j) == '*') {
                        tmp = new Brick(j * measure, i * measure);
                        board.addEntity(tmp);
                        _matrix[i * Configuration.game_cols + j] = tmp;
                    }
                    else if(str.charAt(j) == 'x') {
                        tmp = new Gate(j * measure, i * measure);
                        board.addEntity(tmp);
                        _matrix[i * Configuration.game_cols + j] = tmp;
                    }
                    else if (str.charAt (j) == 'p') {
                        Player player = new Player (j * measure, i * measure, board);
                        board.addEntity (player);
                        board.setPlayer (player);
                        _matrix[i * Configuration.game_cols + j] = player;
                    }
                    // item
                    else if (str.charAt(j) == 'f') {
                        tmp = new ItemFlameUp(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);
                        _matrix[i * Configuration.game_cols + j] = tmp;
                    }
                    else if (str.charAt(j) == 'b') {
                        tmp = new ItemBoomUp(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);
                        _matrix[i * Configuration.game_cols + j] = tmp;
                    }
                    else if (str.charAt(j) == 's') {
                        tmp = new ItemSpeedUp(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);
                        _matrix[i * Configuration.game_cols + j] = tmp;
                    }
                    //mobs
                    else if (str.charAt(j) == '1') {
                        //Balloon mob
                        tmp = new Balloon(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);

                        _matrix[i * Configuration.game_cols + j] = tmp;
                        _numMobs++;
                    }
                    else if (str.charAt(j) == '2') {
                        //Oneal mob
                        tmp = new Oneal(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);

                        _matrix[i * Configuration.game_cols + j] = tmp;
                        _numMobs++;
                    }
                    else if (str.charAt(j) == '3') {
                        //doll mob
                        tmp = new Doll(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);

                        _matrix[i * Configuration.game_cols + j] = tmp;
                        _numMobs++;
                    }
                    else if (str.charAt(j) == '4') {
                        //Minvol mob
                        tmp = new Minvo(j * measure, i * measure, board.getPlayer());
                        board.addEntity(tmp);

                        _matrix[i * Configuration.game_cols + j] = tmp;
                        _numMobs++;
                    }
                    else if (str.charAt(j) == '5') {
                        //Kondoria mob
                        tmp = new Kondoria(j * measure, i * measure, board);
                        board.addEntity(tmp);

                        _matrix[i * Configuration.game_cols + j] = tmp;
                        _numMobs++;
                    }
                    else {
                        _matrix[i * Configuration.game_cols + j] = null;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println ("error Map class! : initMap");
        }
    }

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

    /**
     * get entity in matrix.
     * @param x is column;
     * @param y is row;
     * @return Entity instance of Entities class or null.
     */
    public static Entities getEntityAtLocate(int x, int y) {
        x = getRealX(x) / Configuration.game_measure; // col
        y = getRealY(y) / Configuration.game_measure; // row

        return  _matrix[y * Configuration.game_cols + x];
    }

    public static void setEntityAtLocate(int x, int y, Entities entity) {
        x = getRealX(x) / Configuration.game_measure; // col
        y = getRealY(y) / Configuration.game_measure; // row

        _matrix[y * Configuration.game_cols + x] = entity;
    }
}
