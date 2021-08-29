/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Board class that contains the board state, the location of the player on the board
 * and the status of the board.
 */
// line 7 "model.ump"
// line 94 "model.ump"
// line 112 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private List<Tile> board;
  private List<Tile> prevBoard;
  public Tile[][] arrayTile = new Tile[rows][cols];
  public Tile[][] baseBoard = new Tile[rows][cols];


  public static final int rows = 25;
  public static final int cols = 24;

  //Board Associations
  private List<Tile> tiles;
  private Game game;

  /*
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
    board = new ArrayList<Tile>();
    prevBoard = new ArrayList<Tile>();
    tiles = new ArrayList<Tile>();
  }

  /*
   * Reads the String drawB for the board
   * */
  public void readBoard() {
    BufferedReader buff = new BufferedReader(new StringReader(drawB));
    BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
    for(int row = 0; row < rows; row++) {
      for(int col = 0; col < cols; col++) {
        try {
          arrayTile[row][col] = new Tile((char)buff.read(), row, col);
          this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col);
        }
        catch (IOException e) { e.printStackTrace(); } // TODO Auto-generated catch block
      }
    }
  }

//  public void readBaseBoard(){
//    BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
//    baseBoard = new Tile[rows][cols];
//    for(int row = 0; row < rows; row++) {
//      for(int col = 0; col < cols; col++) {
//        try { baseBoard[row][col] = new Tile((char)buffy.read()); }
//        catch (IOException e) { e.printStackTrace(); } // TODO Auto-generated catch block
//      }
//    }
//  }
  /*
   * Prints the String drawB for the board
   * */
  public void printBoard() {
    StringBuilder bobTheBuilder = new StringBuilder();
    for(int row = 0; row < rows; row++) {
      bobTheBuilder.append("|");
      for(int col = 0; col < cols; col++) {
        bobTheBuilder.append(arrayTile[row][col].getRoom());
        bobTheBuilder.append("|");
      }
      bobTheBuilder.append("\n");
    }
    System.out.println(bobTheBuilder.toString());
  }

  public Tile[][] getArrayTiles(){
    return this.arrayTile;
  }

  /**
   * Finds the letter in the board that is the same letter as the parameter
   * @param letter  the letter wanted to be searched
   * @return  the Tile in the location where the letter is
   */
  public Tile findLetter(Character letter){
    int count = 0;
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        count++;
        //System.out.println(arrayTile[i][j].getRoom() + " " + count);
        if(arrayTile[i][j].getRoom().equals(letter)){
          return arrayTile[i][j];
        }
      }
    }
    return new Tile('+', 0, 0); //might f up
  }


  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetMany */
  public boolean addBoard(Tile aBoard)
  {
    boolean wasAdded = false;
    wasAdded = board.add(aBoard);
    return wasAdded;
  }

  /**
   * Move a piece from one position to another.
   * Need to Update. Work on getting the input from player on row and col. Set that to new position while the previous one is set to null
   * @param oldRow  the old row of the player
   * @param oldCol  the old col of the player
   * @param newRow  the new row of the player
   * @param newCol  the old row of the player
   */
  public void move(int oldRow, int oldCol, int newRow, int newCol) {
    char ch = arrayTile[oldRow][oldCol].getRoom();
    arrayTile[newRow][newCol] = new Tile(ch, newRow, newCol);
    arrayTile[oldRow][oldCol] = new Tile(this.baseBoard[oldRow][oldCol].getRoom(), oldRow, oldCol);
  }

  public void updateBoard() {


  }

  public boolean removeBoard(Tile aBoard)
  {
    boolean wasRemoved = false;
    wasRemoved = board.remove(aBoard);
    return wasRemoved;
  }
  /* Code from template attribute_SetMany */
  public boolean addPrevBoard(Tile aPrevBoard)
  {
    boolean wasAdded = false;
    wasAdded = prevBoard.add(aPrevBoard);
    return wasAdded;
  }

  public boolean removePrevBoard(Tile aPrevBoard)
  {
    boolean wasRemoved = false;
    wasRemoved = prevBoard.remove(aPrevBoard);
    return wasRemoved;
  }
  /* Code from template attribute_GetMany */
  public Tile getBoard(int index)
  {
    Tile aBoard = board.get(index);
    return aBoard;
  }

  public Tile[] getBoard()
  {
    Tile[] newBoard = board.toArray(new Tile[board.size()]);
    return newBoard;
  }

  public int numberOfBoard()
  {
    int number = board.size();
    return number;
  }

  public boolean hasBoard()
  {
    boolean has = board.size() > 0;
    return has;
  }

  public int indexOfBoard(Tile aBoard)
  {
    int index = board.indexOf(aBoard);
    return index;
  }
  /* Code from template attribute_GetMany */
  public Tile getPrevBoard(int index)
  {
    Tile aPrevBoard = prevBoard.get(index);
    return aPrevBoard;
  }

  public Tile[] getPrevBoard()
  {
    Tile[] newPrevBoard = prevBoard.toArray(new Tile[prevBoard.size()]);
    return newPrevBoard;
  }

  public int numberOfPrevBoard()
  {
    int number = prevBoard.size();
    return number;
  }

  public boolean hasPrevBoard()
  {
    boolean has = prevBoard.size() > 0;
    return has;
  }

  public int indexOfPrevBoard(Tile aPrevBoard)
  {
    int index = prevBoard.indexOf(aPrevBoard);
    return index;
  }
  /* Code from template association_GetMany */
  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  public List<Tile> getTiles()
  {
    List<Tile> newTiles = Collections.unmodifiableList(tiles);
    return newTiles;
  }

  public int numberOfTiles()
  {
    int number = tiles.size();
    return number;
  }

  public boolean hasTiles()
  {
    boolean has = tiles.size() > 0;
    return has;
  }

  public int indexOfTile(Tile aTile)
  {
    int index = tiles.indexOf(aTile);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfTilesValid()
  {
    boolean isValid = numberOfTiles() >= minimumNumberOfTiles() && numberOfTiles() <= maximumNumberOfTiles();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfTiles()
  {
    return 49;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 49;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTiles()
  {
    return 49;
  }
  /* Code from template association_AddMNToOnlyOne */
//  public Tile addTile()
//  {
//    if (numberOfTiles() >= maximumNumberOfTiles())
//    {
//      return null;
//    }
//    else
//    {
//      return new Tile(this);
//    }
//  }

  public boolean addTile(Tile aTile)
  {
    boolean wasAdded = false;
    if (tiles.contains(aTile)) { return false; }
    if (numberOfTiles() >= maximumNumberOfTiles())
    {
      return wasAdded;
    }

    Board existingBoard = aTile.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aTile.setBoard(this);
    }
    else
    {
      tiles.add(aTile);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTile(Tile aTile)
  {
    boolean wasRemoved = false;
    //Unable to remove aTile, as it must always have a board
    if (this.equals(aTile.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (49)
    if (numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasRemoved;
    }
    tiles.remove(aTile);
    wasRemoved = true;
    return wasRemoved;
  }

//  public void delete()
//  {
//    for(int i=tiles.size(); i > 0; i--)
//    {
//      Tile aTile = tiles.get(i - 1);
//      aTile.delete();
//    }
//    Game existingGame = game;
//    game = null;
//    if (existingGame != null)
//    {
//      existingGame.delete();
//    }
//  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}
