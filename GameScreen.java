import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.ArrayList;

public class GameScreen {
    //initialize variables
    Minefield minefield;
    int flagsLeft;
    Boolean firstClick = true;
    int hiddenTiles;
    int rows;
    int cols;
    int numMines;
    String mode = "";
    ArrayList<Coord> minePos;
    boolean gameActive;

    //initialize JFrame and JPanels
    JFrame f = new JFrame("Minesweeper by Liam McCarthy");
    JPanel timerBack = new JPanel();
    JPanel flagBack = new JPanel();

    //initialize JButtons
    JButton face = new JButton();
    JButton beginner = new JButton();
    JButton intermediate = new JButton();
    JButton expert = new JButton();
    JButton[][] buttons = new JButton[30][30];

    //initialize JLabels
    JLabel flags;
    JLabel clock;

    //initialize game timer and clock
    Timer timer = new Timer();
    Clock task;

    //create the initial game window
    public void makeScreen() {
        //initializes the board
        f.setSize(800, 600);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //present the user with a menu of game modes
    public void gameMode() {
        //add each game mode button to the frame
        beginner.setBounds(200, 50, 400, 100);
        beginner.setIcon(new ImageIcon("beginner.png"));
        intermediate.setBounds(200, 200, 400, 100);
        intermediate.setIcon(new ImageIcon("intermediate.png"));
        expert.setBounds(200, 350, 400, 100);
        expert.setIcon(new ImageIcon("expert.png"));


        //add an ActionListener for each button
        beginner.addActionListener(e -> {
            if(mode.equals("")) {
                mode = "beginner";
                this.clearModes();
                face.setBounds(250, 50, 100, 100);
                makeBoard();
            }
        });
        intermediate.addActionListener(e -> {
            if(mode.equals("")) {
                mode = "intermediate";
                this.clearModes();
                face.setBounds(500, 50, 100, 100);
                makeBoard();
            }
        });
        expert.addActionListener(e -> {
            if(mode.equals("")) {
                mode = "expert";
                this.clearModes();
                face.setBounds(700, 50, 100, 100);
                makeBoard();
            }
        });

        //add the buttons to the frame
        f.add(beginner);
        f.add(intermediate);
        f.add(expert);
    }

    //clear the game mode buttons and start the game
    public void clearModes() {
        beginner.setVisible(false);
        intermediate.setVisible(false);
        expert.setVisible(false);
        gameActive = true;
    }

    //assign the number of rows, cols, and flags for the game
    public void startGame(int row, int col, int mines, int screenWidth, int screenHeight) {
        rows = row;
        cols = col;
        flagsLeft = mines;
        numMines = mines;
        minefield = new Minefield(rows, cols);
        f.setSize(screenWidth, screenHeight);
        flagsLeft = numMines;
    }




    //create the grid of tiles for the game
    public void makeTiles() {
        //initialize how many tiles have not been left-clicked
        hiddenTiles = rows * cols;

        //create the rows and cols of tiles
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                //initialize the traits for each tiles]
                buttons[row][col] = new JButton("");
                buttons[row][col].setIcon(new ImageIcon("minesweeper_tile.png"));
                buttons[row][col].setBounds(col * 50 + 50, row * 50 + 200, 50, 50);
                buttons[row][col].putClientProperty("row", row);
                buttons[row][col].putClientProperty("col", col);
                buttons[row][col].putClientProperty("clicked", false);
                buttons[row][col].putClientProperty("flagged", false);

                //when a user left-clicks a tile
                int finalRow = row;
                int finalCol = col;
                buttons[row][col].addActionListener(e -> {
                    int buttonRow = (Integer) buttons[finalRow][finalCol].getClientProperty("row");
                    int buttonCol = (Integer) buttons[finalRow][finalCol].getClientProperty("col");
                    clickTile(buttonRow, buttonCol);
                });

                //when a user right-clicks a tile
                buttons[row][col].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        //places flag if tile isn't flagged and player has flags remaining
                        if (e.getButton() == MouseEvent.BUTTON3 &&
                                buttons[finalRow][finalCol].getClientProperty("clicked").equals(false)) {
                            if (buttons[finalRow][finalCol].getClientProperty("flagged").equals(false)) {
                                if (flagsLeft > 0) {
                                    buttons[finalRow][finalCol].putClientProperty("flagged", true);
                                    buttons[finalRow][finalCol].setIcon(new ImageIcon("minesweeper_flag.png"));
                                    flagsLeft--;
                                    flags.setText(String.valueOf(flagsLeft));
                                }
                            }
                            //removes flag if tile is flagged
                            else {
                                buttons[finalRow][finalCol].putClientProperty("flagged", false);
                                buttons[finalRow][finalCol].setIcon(new ImageIcon("minesweeper_tile.png"));
                                flagsLeft++;
                                flags.setText(String.valueOf(flagsLeft));
                            }
                        }
                    }
                });

                //adds all the tiles to the frame
                f.add(buttons[row][col]);
            }
        }
    }

    //add all the game elements to the frame
    public void makeBoard() {
        //assign the correct starting configuration
        if (mode.equals("beginner")) {
            timerBack.setBounds(400, 50, 150, 100);
            this.startGame(9, 10, 10, 600, 800);
        }
        if (mode.equals("intermediate")) {
            timerBack.setBounds(800, 50, 150, 100);
            this.startGame(13, 18, 40, 1000, 1000);
        }
        if (mode.equals("expert")){
            timerBack.setBounds(1300, 50, 150, 100);
            this.startGame(13, 28, 80, 1850, 1000);
        }

        //add the flag counter to the frame
        flagBack.setBounds(50, 50, 150, 100);
        flagBack.setBackground(Color.black);
        flags = new JLabel();
        flags.setText(String.valueOf(flagsLeft));
        flags.setFont(new Font("Dialog", Font.BOLD, 75));
        flags.setForeground(Color.red);
        flags.setBounds(0, 0, 150, 75);
        flagBack.add(flags);
        f.add(flagBack);

        //add the timer to the frame
        timerBack.setBackground(Color.black);
        clock = new JLabel();
        task = new Clock(clock);
        timer.schedule(task, 0, 1000);
        clock.setFont(new Font("Dialog", Font.BOLD, 75));
        clock.setForeground(Color.red);
        clock.setBounds(0, 0, 150, 75);
        timerBack.add(clock);
        f.add(timerBack);

        //set the image for the face button
        face.setIcon(new ImageIcon("smiley_face.jpg"));

        //reset the game if the user clicks the face button
        face.addActionListener(e -> {
            this.reset();
        });

        //add the face button to the frame
        f.add(face);

        //add the tiles to the frame
        this.makeTiles();
    }

    //reveals information when the user left-clicks on a tile
    public void clickTile(int row, int col) {
        //the user can only click tiles while the game is active
        if (gameActive) {
            //assigns mines to positions after the user makes their first click
            if (firstClick.equals(true)) {
                firstClick = false;
                minefield.placeMines(row, col, numMines);
                minePos = minefield.findMines();
            }

            //reveals how many mines surround a clicked tile
            if (buttons[row][col].getClientProperty("clicked").equals(false)) {
                hiddenTiles--;
                buttons[row][col].putClientProperty("clicked", true);
                buttons[row][col].setIcon(new ImageIcon("minesweeper_" +
                        minefield.countMines(row, col) + ".png"));

                //checks if the player lost and ends the game
                if (minefield.countMines(row, col).equals("mine")) {
                    face.setIcon(new ImageIcon("frowny_face.png"));
                    gameActive = false;
                    showMines(row, col);
                }

                //checks if the player won and ends the game
                if (hiddenTiles == numMines) {
                    face.setIcon(new ImageIcon("winning_face.jpg"));
                    gameActive = false;
                }

                //checks surrounding tiles of tiles with no mines neighboring it
                if (minefield.countMines(row, col).equals("0")) {
                    //recur on row above tile
                    if (row > 0) {
                        //recur directly above tile
                        clickTile(row - 1, col);

                        //recur to top-left of tile
                        if (col > 0) {
                            clickTile(row - 1, col - 1);
                        }

                        //recur to top-right of tile
                        if (col < cols - 1) {
                            clickTile(row - 1, col + 1);
                        }
                    }

                    //recur on row below tile
                    if (row < rows - 1) {
                        clickTile(row + 1, col);

                        //recur to bottom-left of tile
                        if (col > 0) {
                            clickTile(row + 1, col - 1);
                        }

                        //recur to bottom-right of tile
                        if (col < cols - 1) {
                            clickTile(row + 1, col + 1);
                        }
                    }

                    //recur to left of tile
                    if (col > 0) {
                        clickTile(row, col - 1);
                    }

                    //recur to right of tile
                    if (col < cols - 1) {
                        clickTile(row, col + 1);
                    }
                }
            }
        }
    }

    //reveal the mines
    public void showMines(int row, int col) {
        //reveals every mine that is not in the parameter's location
        for(Coord mine : minePos) {
            if (mine.x != row || mine.y != col) {
                buttons[mine.x][mine.y].setIcon(new ImageIcon("minesweeper_mines.png"));
            }
        }
    }

    //restart the game (without changing the game mode)
    public void reset() {
        //remove the current buttons from the frame
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                buttons[row][col].setVisible(false);
            }
        }

        //clear mines and reset all variables
        minefield.clearMines();
        firstClick = true;
        timer.cancel();
        timer.purge();
        timer = new Timer();
        task = new Clock(clock);
        timer.schedule(task, 0, 1000);
        flagsLeft = numMines;
        flags.setText(String.valueOf(flagsLeft));
        gameActive = true;

        //reset the buttons
        face.setIcon(new ImageIcon("smiley_face.jpg"));
        this.makeTiles();
    }
}