/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5074.a43557235 modeling language!*/


import java.util.*;

/**
 * Tile class that determines the different types of tiles that are found on the board
 * i.e empty tile, room tile, wall tile etc.
 */
// line 23 "model.ump"
// line 99 "model.ump"
// line 118 "model.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Associations
  private Board board;
  private List<Player> players;
  private Player player;
  private int row;
  private int col;
  private boolean isBorder;
  private Character room;


  //------------------------
  // CONSTRUCTOR
  //------------------------

//  public Tile(Board aBoard, Player player, int x, int y)
//  {
//    this.player = player;
//    this.x = x;
//    this.y = y;
//    isBorder = false;
//
//
//    boolean didAddBoard = setBoard(aBoard);
//    if (!didAddBoard)
//    {
//      throw new RuntimeException("Unable to create tile due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    players = new ArrayList<Player>();
//
//  }

  public Tile(Character ch, int row, int col) {
    this.room = ch;
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

  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public Character getRoom()
  {
    return room;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to tile
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (49)
    if (aBoard.numberOfTiles() >= Board.maximumNumberOfTiles())
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeTile(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addTile(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_IsNumberOfValidMethod */
//  public boolean isNumberOfPlayersValid()
//  {
//    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
//    return isValid;
//  }
//  /* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfPlayers()
//  {
//    return 3;
//  }
//  /* Code from template association_MaximumNumberOfMethod */
//  public static int maximumNumberOfPlayers()
//  {
//    return 6;
//  }
//  /* Code from template association_AddMNToOnlyOne */
//  public Player addPlayer(boolean aTurns, String aCharacter, String aRoom, int aLocationy, int aLocationx, Game aGame)
//  {
//    if (numberOfPlayers() >= maximumNumberOfPlayers())
//    {
//      return null;
//    }
//    else
//    {
//      return new Player(aTurns, aCharacter, aRoom, aLocationy, aLocationx, this, aGame);
//    }
//  }

//  public boolean addPlayer(Player aPlayer)
//  {
//    boolean wasAdded = false;
//    if (players.contains(aPlayer)) { return false; }
//    if (numberOfPlayers() >= maximumNumberOfPlayers())
//    {
//      return wasAdded;
//    }
//
//    Tile existingTile = aPlayer.getTile();
//    boolean isNewTile = existingTile != null && !this.equals(existingTile);
//
//    if (isNewTile && existingTile.numberOfPlayers() <= minimumNumberOfPlayers())
//    {
//      return wasAdded;
//    }
//
//    if (isNewTile)
//    {
//      aPlayer.setTile(this);
//    }
//    else
//    {
//      players.add(aPlayer);
//    }
//    wasAdded = true;
//    return wasAdded;
//  }

//  public boolean removePlayer(Player aPlayer)
//  {
//    boolean wasRemoved = false;
//    //Unable to remove aPlayer, as it must always have a tile
//    if (this.equals(aPlayer.getTile()))
//    {
//      return wasRemoved;
//    }
//
//    //tile already at minimum (3)
//    if (numberOfPlayers() <= minimumNumberOfPlayers())
//    {
//      return wasRemoved;
//    }
//    players.remove(aPlayer);
//    wasRemoved = true;
//    return wasRemoved;
//  }

  /* Code from template association_AddIndexControlFunctions */
//  public boolean addPlayerAt(Player aPlayer, int index)
//  {
//    boolean wasAdded = false;
//    if(addPlayer(aPlayer))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
//      players.remove(aPlayer);
//      players.add(index, aPlayer);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }

//  public boolean addOrMovePlayerAt(Player aPlayer, int index)
//  {
//    boolean wasAdded = false;
//    if(players.contains(aPlayer))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
//      players.remove(aPlayer);
//      players.add(index, aPlayer);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addPlayerAt(aPlayer, index);
//    }
//    return wasAdded;
//  }

//  public void delete()
//  {
//    Board placeholderBoard = board;
//    this.board = null;
//    if(placeholderBoard != null)
//    {
//      placeholderBoard.removeTile(this);
//    }
//    for(int i=players.size(); i > 0; i--)
//    {
//      Player aPlayer = players.get(i - 1);
//      aPlayer.delete();
//    }
//  }

}
