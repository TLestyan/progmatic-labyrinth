package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {
    
    private CellType[][] labyrinth;
    private Coordinate playerPosition;
    
    public LabyrinthImpl() {
        
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            
            labyrinth = new CellType[height][width];

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            labyrinth[hh][ww] = CellType.WALL;
                            break;
                            
                        case 'E':
                            labyrinth[hh][ww] = CellType.END;
                            break;
                            
                        case 'S':
                            labyrinth[hh][ww] = CellType.START;
                            playerPosition = new Coordinate(ww, hh);
                            break;
                            
                        default :
                            labyrinth[hh][ww] = CellType.EMPTY;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public int getWidth() {
        if (null == labyrinth) {
            return -1;
        }
        return labyrinth[0].length;
    }

    @Override
    public int getHeight() {
        if (null == labyrinth) {
            return -1;
        }
        return labyrinth.length;
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (null == labyrinth) {
            throw new CellException(c, "Labyrinth is not loaded yet.");
        }
        if (c.getRow() < 0
                || c.getRow() >= labyrinth.length
                || c.getCol() < 0
                || c.getCol() >= labyrinth[0].length) {
            throw new CellException(c, "Coordinate is out of labyrinth");
        }
        return labyrinth[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        labyrinth = new CellType[height][width];
        
        for (int hh = 0; hh < height; hh++) {
            for (int ww = 0; ww < width; ww++) {
                labyrinth[hh][ww] = CellType.EMPTY;
            }
            
        }
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        getCellType(c); // ezzel vizsgáljuk a kivételeket ismétlés nélkül
        
        labyrinth[c.getRow()][c.getCol()] = type;
        
        if (type == CellType.START) {
            playerPosition = c;
        }
    }

    @Override
    public Coordinate getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        try{
            return getCellType(playerPosition) == CellType.END;
        } catch (CellException e) {
            return false;
        }
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> possibleMoves = new ArrayList<>();
        
        int row = playerPosition.getRow();
        int col = playerPosition.getCol();
        
        try {
            if (getCellType( new Coordinate(col, row - 1)) 
                    != CellType.WALL) {
                possibleMoves.add(Direction.NORTH);
            }
        } catch (CellException e) {}
        
        try {
            if (getCellType( new Coordinate(col + 1, row)) 
                    != CellType.WALL) {
                possibleMoves.add(Direction.EAST);
            }
        } catch (CellException e) {}
        
        try {
            if (getCellType( new Coordinate(col, row + 1)) 
                    != CellType.WALL) {
                possibleMoves.add(Direction.SOUTH);
            }
        } catch (CellException e) {}
        
        try {
            if (getCellType( new Coordinate(col - 1, row)) 
                    != CellType.WALL) {
                possibleMoves.add(Direction.WEST);
            }
        } catch (CellException e) {}
        
        return possibleMoves;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        int row = playerPosition.getRow();
        int col = playerPosition.getCol();
        
        switch (direction) {
            case NORTH :
                row--;
                break;
                
            case EAST :
                col++;
                break;
                
            case SOUTH :
                row++;
                break;
                
            case WEST :
                col--;
                break;
        }
        
        try {
            Coordinate c = new Coordinate(col, row);
            if (getCellType(c) != CellType.WALL) {
                playerPosition = c;
            } else {
                throw new InvalidMoveException();
            }
        } catch (CellException e) {
            throw new InvalidMoveException();
        }
    }

}
