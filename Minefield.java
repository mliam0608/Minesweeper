import java.util.Random;
import java.util.ArrayList;

public class Minefield {
    //initialize variables
    static Random rand = new Random();
    int rows;
    int cols;
    static Tile[][] board = new Tile[30][30];

    //constructor for Minefield
    Minefield(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = this.setTiles();
    }

    //create a board of Tiles
    Tile[][] setTiles() {
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.cols; col++) {
                board[row][col] = new Tile(row, col, false, false);
            }
        }
        return board;
    }

    //assign each mine to a random coordinate
    public void placeMines(Integer startRow, Integer startCol, int numMines) {
        while(numMines >= 0) {
            int row = rand.nextInt(this.rows - 1);
            int col = rand.nextInt(this.cols - 1);

            if(board[row][col].mine == false && Math.abs(row - startRow) > 1 || Math.abs(col - startCol) > 1) {
                board[row][col].mine = true;
                numMines--;
            }
        }
    }

    //create an ArrayList of all Mines' coordinates
    public ArrayList<Coord> findMines() {
        ArrayList<Coord> minePos = new ArrayList<Coord>();
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.cols; col++) {
                if(board[row][col].mine) {
                    minePos.add(new Coord(row, col));
                }
            }
        }
        return minePos;
    }

    //remove all Mines from the board
    public void clearMines() {
        for(int row = 0; row < this.rows; row++) {
            for(int col = 0; col < this.cols; col++) {
                board[row][col].mine = false;
            }
        }
    }

    //count the number of mines around a coordinate
    public String countMines(int row, int col) {
        int count = 0;
        board[row][col].checked = true;

        //check row above the tile
        if(row > 0) {
            //check directly above for mine
            if(board[row - 1][col].mine == true) {
                count++;
            }

            //check top-left for mine
            if(col > 0 && board[row - 1][col - 1].mine == true) {
                count++;
            }

            //check top-right for mine
            if(col < this.cols - 1 && board[row - 1][col + 1].mine == true) {
                count++;
            }
        }

        //check row below the tile
        if(row < this.rows - 1) {
            //check directly below for mine
            if(board[row + 1][col].mine == true) {
                count++;
            }

            //check bottom-left for mine
            if(col > 0 && board[row + 1][col - 1].mine == true) {
                count++;
            }

            //check bottom-right for mine
            if(col < this.cols - 1 && board[row + 1][col + 1].mine == true) {
                count++;
            }
        }

        //check to left of tile for mine
        if(col > 0 && board[row][col - 1].mine == true) {
            count++;
        }

        //check to right of tile for mine
        if(col < this.cols - 1 && board[row][col + 1].mine == true) {
            count++;
        }

        //check if the tile has a mine
        if(board[row][col].mine == true) {
            return "mine";
        }

        //return the count of mines
        return String.valueOf(count);
    }
}
