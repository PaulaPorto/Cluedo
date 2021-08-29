/**
 * RoomCard that represents the 9 room cards in the game
 */
public class RoomCard extends Cards
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the description of the card
    private String description;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCard(String roomName)
    {
        this.description = roomName;
    }

    //------------------------
    // INTERFACE
    //------------------------

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return "This card is " + this.description;
    }
}
