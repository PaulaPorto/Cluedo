/**
 *  CharacterCard class that represent the CharacterCards, which are the 6 characters.
 */
public class CharacterCard extends Cards
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the description of the card
    private String description;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public CharacterCard(String characterName)
    {
        this.description = characterName;
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
