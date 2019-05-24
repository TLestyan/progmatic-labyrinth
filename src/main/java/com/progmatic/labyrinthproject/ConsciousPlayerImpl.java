/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author TLestyan
 */
public class ConsciousPlayerImpl implements Player{
    
    private LinkedList<Direction> move;
    private int step;
    
    public ConsciousPlayerImpl() {
        step = 0;
    }

    @Override
    public Direction nextMove(Labyrinth l) {
        if (0 == step) {
            pathFinding(l);
        }
        
        return move.get(step++);
    }
    
    private void pathFinding(Labyrinth l) {
        int height = l.getHeight();
        int width = l.getWidth();
        
        
        for (int hh = 0; hh < height; hh++) {
            for (int ww = 0; ww < width; ww++) {
                Coordinate c = new Coordinate(ww, hh);
                Node end = null;
                try {
                    if (l.getCellType(c) == CellType.END) {
                        end = new Node(c, CellType.END);
                        break;
                    }
                } catch (CellException e) {}
                
                
            }
        }
        
        
    }
    
    private void findingNeigbors(Node n, Labyrinth l) {
        int row = n.getCoordinate().getRow();
        int col = n.getCoordinate().getCol();
        
        Coordinate cN = new Coordinate(col, row - 1);
        Coordinate cE = new Coordinate(col + 1, row);
        Coordinate cS = new Coordinate(col, row + 1);
        Coordinate cW = new Coordinate(col - 1, row);
        
        
    }
    
    private static class Node {
        
        private static final Map<Coordinate, Direction> directions = new HashMap<>();
        
        private Node child;
        
        private Coordinate coordinate;
        private CellType cellType;
        
        private int pathLength;
        
        static {
            directions.put( new Coordinate(0, -1), Direction.NORTH);
            directions.put( new Coordinate(0, 1), Direction.SOUTH);
            directions.put( new Coordinate(-1, 0), Direction.WEST);
            directions.put( new Coordinate(1, 0), Direction.EAST);
        }
        
        public Node(Coordinate coordinate, CellType cellType) {
            this.cellType = cellType;
            this.coordinate = coordinate;
            
            child = null;
            
            pathLength = 0;
        }
        
        public Node(Coordinate coordinate, CellType cellType, Node n) {
            this.cellType = cellType;
            this.coordinate = coordinate;
            
            child = n;
            
            pathLength = n.getPathLength() + 1;
        }

        public int getPathLength() {
            return pathLength;
        }
        
        public Coordinate getCoordinate() {
            return coordinate;
        }
        
        public boolean makeSynaps(Node n) {
            if (n == child) {
                return false;
            }
            
            if (n.getPathLength() + 1 < pathLength) {
                child = n;
                pathLength = n.getPathLength() + 1;
                return true;
                
            } else if (n.getPathLength() > pathLength + 1) {
                n.makeSynaps(n);
                return true;
            }
            
            return false;
        }
        
        public boolean hasRootToEnd(Node n) {
            if (cellType == CellType.END) {
                return true;
            }
            
            child.hasRootToEnd(n);
            
            return false;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Node n = (Node) obj;
            return n.getCoordinate().equals(coordinate);
        }
        
        @Override
        public int hashCode() {
            return coordinate.hashCode();
        }
    }
    
}
