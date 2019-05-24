/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TLestyan
 */
public class WallFollowerPlayerImpl implements Player{
    
    private Direction lastMove;
    
    private static final Map<Direction, Direction> leftDirection = new EnumMap<>(Direction.class);
    private static final Map<Direction, Direction> rigthDirection = new EnumMap<>(Direction.class);
    
    static {
        leftDirection.put(Direction.NORTH, Direction.WEST);
        leftDirection.put(Direction.WEST, Direction.SOUTH);
        leftDirection.put(Direction.SOUTH, Direction.EAST);
        leftDirection.put(Direction.EAST, Direction.NORTH);
        
        for (Map.Entry<Direction, Direction> entry : leftDirection.entrySet()) {
            rigthDirection.put(entry.getValue(), entry.getKey());
        }
    }
    
    public WallFollowerPlayerImpl() {
        lastMove = null;
    }

    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> possibleMoves = l.possibleMoves();
        
        if (null == possibleMoves
                || possibleMoves.isEmpty()) {
            throw new IllegalArgumentException("Can't move in the given Labyrinth");
        }
        
        if (null == lastMove) {
            lastMove = possibleMoves.get(0);
            return lastMove;
        }
        
        lastMove = leftDirection.get(lastMove);
        
        if (possibleMoves.contains(lastMove)) {
            return lastMove;
        }
        
        for (int i = 0; i < 5; i++) {
            lastMove = rigthDirection.get(lastMove);
            
            if (possibleMoves.contains(lastMove)) {
                return lastMove;
            }
        }
        
        return null;
    }
    
    
    
}
