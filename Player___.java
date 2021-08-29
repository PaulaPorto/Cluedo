import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents the Players who will be playing the game.
 * Contains the moves done by the player, the cards they have in their hand, the statuses of their turn, win, lose etc.
 *
 * @author
 */
public class Player {

  //list for the cards that is in the hand of the player
  private List<Cards> hand = new ArrayList<>();

  //the character the player is controlling in the game
  private PlayableCharacters charToken;
  private Character letter;

  //the moves the player has done during their turn
  Stack<Tile> movesDone = new Stack<>();

  //turn status
  private boolean isTurn = false;

  //win status
  private boolean win = false;

  //losing status
  private boolean lost = false;

  /**
   * Constructor for the Player object
   * @param character the character associated with the player
   */
  public Player(PlayableCharacters character, Character letter){
    this.charToken = character;
    this.letter = letter;
  }

  /**
   * Populates the hand of the player with cards
   * @param card the cards that are the player's given cards
   */
  public void setHand(Cards card){
    this.hand.add(card);
  }

  /**
   * Changes the boolean to indicate that the specific player has won the game
   */
  public void setWin(){
    this.win = true;
  }

  /**
   * Changes the boolean to indicate that the specific player has lost
   * (used after the player has made the wrong accusation)
   */
  public void setLost(){
    this.lost = true;
  }

  /**
   * Sets the players turn to true/false indicating it is their turn/end of their turn
   */
  public void setTurn(boolean bool){
    this.isTurn = bool;
  }

  /**
   * Returns the win status of the player
   * @return  true if the player has won, false otherwise
   */
  public boolean getWinStatus(){
    return this.win;
  }

  /**
   * Returns the lost status of the player
   * @return  true if the player has made the wrong accusation, false otherwise
   */
  public boolean getLostStatus(){
    return this.lost;
  }

  /**
   * Returns the turn status of the player
   * @return  true if it is the player's turn, false otherwise
   */
  public boolean getTurnStatus(){
    return this.isTurn;
  }

  /**
   * Returns the hand of the player
   * @return  the hand of cards that the player has
   */
  public List<Cards> getHand(){
    return this.hand;
  }

  /**
   * Returns the character the player is associated with
   * @return  the character token
   */
  public PlayableCharacters getCharToken() { return  this.charToken; }

  /**
   * Returns the stack of moves done by the player during their turn
   * @return  the stack of moves done
   */
  public Stack<Tile> getMovesDone(){
    return this.movesDone;
  }

  /**
   * Gets the letter associated with the player
   * @return  the letter associated with player
   */
  public Character getLetter(){
    return this.letter;
  }

  /**
   * Clears the moves done by the player at each turn
   */
  public void clearMovesDone(){
    if(this.isTurn == false){
      this.movesDone.clear();
    }
  }

}
