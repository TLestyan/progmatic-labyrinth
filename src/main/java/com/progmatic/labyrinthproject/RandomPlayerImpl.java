/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.List;

/**
 *
 * @author TLestyan
 */
public class RandomPlayerImpl implements Player{

    public RandomPlayerImpl() {
    }
    
    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> possibleMoves = l.possibleMoves();
        
        int moveIndex = (int) (Math.random() * possibleMoves.size());
        
        return possibleMoves.get(moveIndex);
    }
    
}
