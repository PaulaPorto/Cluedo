
/**
 * Tile class that determines the different types of tiles that are found on the board
 * i.e empty tile, room tile, wall tile etc.
 */

public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //the position (row and col) of the tile in the 2D array
  private int row;
  private int col;

  //the character associated with the tile
  private Character character;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(Character ch, int row, int col) {
    this.character = ch;
    this.row = row;
    this.col = col;
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * Returns the row position of the tile
   * @return  the row position
   */
  public int getRow(){
    return this.row;
  }

  /**
   * Returns the col position of the tile
   * @return  the col position
   */
  public int getCol(){
    return this.col;
  }

  /**
   * Returns the character associated with the tile object
   * @return  the character associated with the tile
   */
  public Character getChar()
  {
    return this.character;
  }

}