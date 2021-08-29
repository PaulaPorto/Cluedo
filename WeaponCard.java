/**
 * WeaponCard that represents the 6 weapons in the game
 */
public class WeaponCard extends Cards
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the description of the card
    private String description;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public WeaponCard(String weaponName)
    {
        this.description = weaponName;
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
