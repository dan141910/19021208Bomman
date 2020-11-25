package bomberman.entities.dynamicEntities.mods.AI;

import bomberman.entities.Entities;
import bomberman.entities.block.Brick;
import bomberman.entities.block.Wall;
import bomberman.entities.dynamicEntities.Player;
import bomberman.entities.dynamicEntities.mods.Mob;
import bomberman.gameSeting.Configuration;
import bomberman.graphics.Map;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class AIHigh extends  AI{
    Player _player;
    Mob _mob;
    int _curDirection;
    int _target;
    boolean[] _mark;
    PriorityQueue<Integer> queue;

    public AIHigh(Player player, Mob mob) {
        _player = player;
        _mob = mob;
        _mark = new boolean[Configuration.game_cols * Configuration.game_rows + 1];
        Arrays.fill(_mark, false);
        _curDirection = 1;
        queue = new PriorityQueue<>();
        _target = 0;

    }

    @Override
    public int calculateDirection() {
        int measure = Configuration.game_measure;

        int playerCol = Map.getRealX(_player.getX()) / Configuration.game_measure;
        int playerRow = Map.getRealY(_player.getY()) / Configuration.game_measure;
        int mobCol = Map.getRealX(_mob.getX()) / Configuration.game_measure;
        int mobRow = Map.getRealY(_mob.getY()) / Configuration.game_measure;
        if ((playerCol - mobCol) * (playerCol - mobCol) + (playerRow - mobRow) * (playerRow - mobRow) >  9 ) {
            // no target and away from player
            _curDirection = new Random().nextInt(4);
            _target = 0;
        }
        else {
            if (_target == mobRow * Configuration.game_cols + mobCol) _target = 0;

            if (_target == 0) {
                // find new target
                int index = playerRow * Configuration.game_cols + playerCol;
                _target = index;

                Arrays.fill(_mark, false);
                Entities x;

                while (index != mobRow * Configuration.game_cols + mobCol) {

                    int tmpCol = index % Configuration.game_cols; // x
                    int tmpRow = index / Configuration.game_cols; // y
                    x = Map.getEntityAtLocate(tmpCol * measure, tmpRow * measure);
                    if (!( x instanceof Wall || x instanceof Brick )) {
                        _mark[index] = true;
                        if (!_mark[( tmpRow + 1 ) * Configuration.game_cols + tmpCol]) {
                            queue.add(( tmpRow + 1 ) * Configuration.game_cols + tmpCol);
                        }
                        if (!_mark[( tmpRow - 1 ) * Configuration.game_cols + tmpCol])
                            queue.add(( tmpRow - 1 ) * Configuration.game_cols + tmpCol);
                        if (!_mark[tmpRow * Configuration.game_cols + tmpCol + 1])
                            queue.add(tmpRow * Configuration.game_cols + tmpCol + 1);
                        if (!_mark[tmpRow * Configuration.game_cols + tmpCol - 1])
                            queue.add(tmpRow * Configuration.game_cols + tmpCol - 1);
                    }
                    if (queue.size() == 0) break;
                    else index = queue.poll();

                }

            }

            // go to target point
            if (_mark[( mobRow + 1 ) * Configuration.game_cols + mobCol]) {
                // down - 2
                _curDirection = 2;
                _mark[( mobRow + 1 ) * Configuration.game_cols + mobCol] = false;
            } else if (_mark[( mobRow - 1 ) * Configuration.game_cols + mobCol]) {
                // up - 0
                _curDirection = 0;
                _mark[( mobRow - 1 ) * Configuration.game_cols + mobCol] = false;
            } else if (_mark[mobRow * Configuration.game_cols + mobCol + 1]) {
                // right - 1
                _curDirection = 1;
                _mark[mobRow * Configuration.game_cols + mobCol + 1] = false;
            } else if (_mark[mobRow * Configuration.game_cols + mobCol - 1]) {
                // left - 3
                _curDirection = 3;
                _mark[mobRow * Configuration.game_cols + mobCol - 1] = false;
            } else {
                _curDirection =  new Random().nextInt(4);
                _target = 0; // need find new way;
            }

        }

        return _curDirection;
    }

}
