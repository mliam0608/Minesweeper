public class Tile {
    //initialize variables
    int row;
    int col;
    Boolean mine;
    Boolean checked;


    //constructor for Tile class
    Tile(int row, int col, Boolean mine, Boolean checked) {
        this.row = row;
        this.col = col;
        this.mine = mine;
        this.checked = checked;
    }


}
