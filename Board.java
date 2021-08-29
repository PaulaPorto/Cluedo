import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Board class that contains the board state, the location of the player on the board
 * and the status of the board.
 */

public class Board
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the current state of the board
    private Tile[][] arrayTile;

    //the board without any players
    private Tile[][] baseBoard;

    //the number of rows and columns of the board
    private static final int rows = 25;
    private static final int cols = 24;

    /**
     * Variable for the board itself. This will help
     * stringbuilder read the board into the game.
     */
    String drawB =
            "XXXXXXXXwXXXXgXXXXXXXXXX"+
                    "XKKKXX___XBBX___XXCCCCCX"+
                    "XKKKX__XXBBBBXX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__~BBBBBB~___~XXXXX"+
                    "XXX~X__XBBBBBBX________P"+
                    "_______X~XXXX~X________X"+
                    "X__________________XXXXX"+
                    "XXXXX______________~RRRX"+
                    "XDDDXXXX__XXXXXX___XRRRX"+
                    "XDDDDDDX__XXXXXX___XRRRX"+
                    "XDDDDDD~__XXXXXX___XXX~X"+
                    "XDDDDDDX__XXXXXX________"+
                    "XDDDDDDX__XXXXXX___XX~XX"+
                    "XXXXXX~X__XXXXXX__XLLLLX"+
                    "X_________XXXXXX__~LLLLX"+
                    "m_________________XLLLLX"+
                    "X________XX~~XX____XXXXX"+
                    "XXXXX~X__XHHHHX________p"+
                    "XGGGGGX__XHHHH~________X"+
                    "XGGGGGX__XHHHHX__X~XXXXX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XXXXXXXsXXXXXXXX_XXXXXXX";

    /**
     * Variable for the board without the players,
     * used for reverting the tiles back to what it was when a player moves out of it
     */
    private final String baseBoardNP =
            "XXXXXXXX_XXXX_XXXXXXXXXX"+
                    "XKKKXX___XBBX___XXCCCCCX"+
                    "XKKKX__XXBBBBXX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__~BBBBBB~___~XXXXX"+
                    "XXX~X__XBBBBBBX_________"+
                    "_______X~XXXX~X________X"+
                    "X__________________XXXXX"+
                    "XXXXX______________~RRRX"+
                    "XDDDXXXX__XXXXXX___XRRRX"+
                    "XDDDDDDX__XXXXXX___XRRRX"+
                    "XDDDDDD~__XXXXXX___XXX~X"+
                    "XDDDDDDX__XXXXXX________"+
                    "XDDDDDDX__XXXXXX___XX~XX"+
                    "XXXXXX~X__XXXXXX__XLLLLX"+
                    "X_________XXXXXX__~LLLLX"+
                    "__________________XLLLLX"+
                    "X________XX~~XX____XXXXX"+
                    "XXXXX~X__XHHHHX_________"+
                    "XGGGGGX__XHHHH~________X"+
                    "XGGGGGX__XHHHHX__X~XXXXX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XXXXXXX_XXXXXXXX_XXXXXXX";


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board()
    {
        this.arrayTile = new Tile[rows][cols];
        this.baseBoard = new Tile[rows][cols];
    }

    //------------------------
    // INTERFACE
    //------------------------

    /**
     * Reads the board and populates the two 2d arrays with the strings
     */
    public void readBoard() {
        BufferedReader buff = new BufferedReader(new StringReader(drawB));
        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                try {
                    arrayTile[row][col] = new Tile((char)buff.read(), row, col);
                    this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col);
                }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    /**
     * Prints the board to the console in a nice way
     */
    public void printBoard() {
        StringBuilder strBuilder = new StringBuilder();
        for(int row = 0; row < rows; row++) {
            strBuilder.append("|");
            for(int col = 0; col < cols; col++) {
                strBuilder.append(arrayTile[row][col].getChar());
                strBuilder.append("|");
            }
            strBuilder.append("\n");
        }
        System.out.println(strBuilder.toString());
    }

    /**
     * Prints the legend to the console in a nice way
     */
    public void printLegend() {
        System.out.println("Legends:\n" +
                "~ = door\n" +
                "X = wall\n" +
                "\n" +
                "K = Kitchen\n" +
                "B = Ball Room\n" +
                "C = Conservatory\n" +
                "D = Dining Room\n" +
                "R = Billiard Room\n" +
                "G = Lounge\n" +
                "H = Hall\n" +
                "L = Library\n" +
                "S = Study\n" +
                "\n" +
                "w = White\n" +
                "g = Green\n" +
                "P = Peacock\n" +
                "p = Plum\n" +
                "s = Scarlett\n" +
                "m = Mustard\n");
    }


    /**
     * Returns the arrayTile 2D array
     * @return  the 2D array which represents the current state of the board
     */
    public Tile[][] getArrayTiles(){
        return this.arrayTile;
    }

    /**
     * Finds the letter in the board that is the same letter as the parameter
     * @param letter  the letter wanted to be searched
     * @return  the Tile in the location where the letter is
     */
    public Tile findLetter(Character letter){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(arrayTile[i][j].getChar().equals(letter)){ //if the tile's character is the same as the letter return the tile
                    return arrayTile[i][j];
                }
            }
        }
        return new Tile('+', 0, 0); //will never get reached as letter would always be found
    }

    /**
     * Moves a piece from one position to another.
     * Also prints out the indicator of when a player moves into a room
     * @param oldRow  the old row of the player
     * @param oldCol  the old col of the player
     * @param newRow  the new row of the player
     * @param newCol  the old row of the player
     */
    public void move(int oldRow, int oldCol, int newRow, int newCol) {
        char ch = arrayTile[oldRow][oldCol].getChar();
        arrayTile[newRow][newCol] = new Tile(ch, newRow, newCol);
        arrayTile[oldRow][oldCol] = new Tile(this.baseBoard[oldRow][oldCol].getChar(), oldRow, oldCol);
        if (this.baseBoard[newRow][newCol].getChar().equals('K')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Kitchen");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('G')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Lounge");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('H')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Hall");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('S')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Study");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('C')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Conservatory");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('D')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Dining Room");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('B')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Ball Room");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('R')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Billiard Room");
            System.out.println("--------------------------------");
        }
        if (this.baseBoard[newRow][newCol].getChar().equals('L')) {
            System.out.println("--------------------------------");
            System.out.println("You are in the room: Library");
            System.out.println("--------------------------------");
        }
    }
}
