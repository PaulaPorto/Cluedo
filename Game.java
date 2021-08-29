import java.util.*;
/**
 * Game class that contains the logic behind the game itself, player actions, initialising the game, handing and shuffling cards etc.
 */
public class Game {

    //the minimum and maximum values the dice can be
    private static final int min = 2;
    private static final int max = 12;

    //value of the dice
    private static int diceValue = 0;

    //number of people playing
    private String numPeeps;

    //boolean to check if player has won the game, OR possibly using an enum instead
    private boolean win = false;

    //the current game state
    private GameState currentState = GameState.ONGOING;

    //the murder circumstances that needs to be guessed
    private List<Cards> murderCircumstances = new ArrayList<>();

    //lists that contain different types of Cards
    private List<CharacterCard> characters = new ArrayList<>();
    private List<WeaponCard> weapons = new ArrayList<>();
    private List<RoomCard> rooms = new ArrayList<>();

    //players playing
    private List<Player> players = new ArrayList<>();

    //stack of cards that will be dealt out to players
    private Stack<Cards> dealingOut = new Stack<>();

    //the game board
    private Board board;

    //the current player who's turn it is
    private PlayableCharacters currentPlayer;

    //input Scanner
    private static Scanner input = new Scanner(System.in);


    /**
     * Constructor for the game
     */
    public Game(){

        //creates the board and prints it out
        this.board = new Board();
        this.board.readBoard();
        this.board.printLegend();
        this.board.printBoard();

        //creates the players and initialises the game
        List<Player> players = createPlayers();
        initialiseGame(players);

        //while the game is ongoing go through the each player's turns
        while(this.currentState == GameState.ONGOING){
            printHandCards();
            askRoll();
            askMove();
            this.board.printBoard();
            break;
        }
    }

    /**
     * Asks the player to move, and also does the moving of the player
     */
    public void askMove(){
        boolean bool = false;
        int counter = 0;
        while(!bool){
            System.out.println("------------------------------------------------------------------------------------------------------------");
            System.out.println("According to your dice value, type in your desired moves (U for up, D for down, L for left, R for right) " +
                    "\nFor example, if you rolled a 3, your input should look something like LLU, this would move you left, left, up.");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            String movesInput = this.input.next();
            for(int i = 0; i < movesInput.length(); i++){
                //if the input is greater than the dice value, it's invalid
                if(movesInput.length() > this.diceValue){
                    System.out.println("-----------------------------------------------------------------------------");
                    System.out.println("Input does not match your number of moves you have (dice value). Try again :/");
                    System.out.println("-----------------------------------------------------------------------------");
                    break;
                }
                //if the counter is equal to or greater than the dice value, it's invalid
                if(counter >= this.diceValue){
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
                    System.out.println("The amount of moves you have made has exceeded the dice value, the moves made that exceeded the dice value have been ignored");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
                    counter = 0;
                    break;
                }
                //if the counter is less than the dice value, proceed to do the inputs
                if(counter < this.diceValue) {

                    //checking if the player input R for going right
                    if(movesInput.charAt(i) == ('R') || movesInput.charAt(i) == ('r')){
                        //checking for the character who is currently in play
                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                        //if the move is valid, move the character, otherwise don't move
                        if(isValid(currentPlayerPos.getRow(), currentPlayerPos.getCol() + 1)){
                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow(), currentPlayerPos.getCol() + 1);
                            counter++;
                            bool = true;
                        }
                        else{
                            System.out.println("-----------------------");
                            System.out.println("Invalid right :(");
                            System.out.println("-----------------------");
                            this.board.printBoard();
                            bool = false;
                            break;
                        }
                    }

                    //checking if the player input L for going left
                    if(movesInput.charAt(i) == 'L' || movesInput.charAt(i) == 'l'){
                        //checking for the character who is currently in play
                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                        //if the move is valid, move the character, otherwise don't
                        if(isValid(currentPlayerPos.getRow(), currentPlayerPos.getCol() - 1)){
                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow(), currentPlayerPos.getCol() - 1);
                            counter++;
                            bool = true;
                        }
                        else{
                            System.out.println("You made an invalid left :(");
                            System.out.println("-----------------------");
                            this.board.printBoard();
                            bool = false;
                            break;
                        }
                    }

                    //checking if the player input U for going up
                    if(movesInput.charAt(i) == ('U') || movesInput.charAt(i) == ('u')){
                        //checking for the character who is currently in play
                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                        //if the move is valid, move the character, otherwise don't
                        if(isValid(currentPlayerPos.getRow() - 1, currentPlayerPos.getCol())){
                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow() - 1, currentPlayerPos.getCol());
                            counter++;
                            bool = true;
                        }
                        else{
                            System.out.println("You made an invalid up :(");
                            System.out.println("-----------------------");
                            this.board.printBoard();
                            bool = false;
                            break;
                        }
                    }
                    //checking if the player input D for going down
                    if(movesInput.charAt(i) == ('D') || movesInput.charAt(i) == ('d')){
                        //checking for the character who is currently in play
                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                        //if the move is valid, move the character, otherwise don't
                        if(isValid(currentPlayerPos.getRow() + 1, currentPlayerPos.getCol())){
                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow() + 1, currentPlayerPos.getCol());
                            counter++;
                            bool = true;
                        }
                        else{
                            System.out.println("You made an invalid down :(");
                            System.out.println("-----------------------");
                            this.board.printBoard();
                            bool = false;
                            break;
                        }
                    }
                }
                else{
                    System.out.println("The amount of moves you have done has exceeded the dice value" + " " + counter);
                    bool = false;
                    break;
                }
            }
            //this.board.printBoard();
        }
    }

    /**
     * Ask to roll dice
     */
    public void askRoll(){
        boolean bool = false;
        while(!bool){
            System.out.println("--------------------------------------------");
            System.out.println("Are you ready to roll the dice? (Type y): ");
            String yes = input.next();
            if(yes.equalsIgnoreCase("y")){
                bool = true;
                rollDice();
            }
            else{
                System.out.println("Please type y when ready :)");
            }
        }
    }

    /**
     * Prints out the hand cards of the player who is currently playing and their character
     */
    public void printHandCards(){
        for(Player player : this.players){
            if(this.currentPlayer == player.getCharToken()){
                System.out.println("-----------------------------------");
                System.out.println("You are: " + player.getCharToken());
                System.out.println("------------------------------------");
                System.out.println("This is your hand of cards");
                for(Cards cards : player.getHand()){
                    System.out.println(cards.getDescription());
                }
            }
        }
    }

    /**
     * Deals out the cards to the players according to the number of players playing in the game
     */
    public void dealOutCards(){
        switch(numPeeps){
            //if there's 3 people playing, deal out 6 cards to each player
            case "3":
                for(int p = 0; p < this.players.size(); p++){
                    for(int i = 0; i < 6; i++){
                        this.players.get(p).setHand(this.dealingOut.pop());
                    }
                }
                break;
            //if there's 4 people playing deal out 4 cards for the first two players and 5 for the remaining
            case "4":
                Player p1 = this.players.get(0);
                for(int i = 0; i < 4; i++){
                    p1.setHand(this.dealingOut.pop());
                }

                Player p2 = this.players.get(1);
                for(int i = 0; i < 4; i++){
                    p2.setHand(this.dealingOut.pop());
                }

                Player p3 = this.players.get(2);
                for(int i = 0; i < 5; i++){
                    p3.setHand(this.dealingOut.pop());
                }

                Player p4 = this.players.get(3);
                for(int i = 0; i < 5; i++){
                    p4.setHand(this.dealingOut.pop());
                }
                break;
            //if there's 5 people playing deal out 3 cards for the first two players and 4 for the rest
            case "5":
                Player pp1 = this.players.get(0);
                for(int i = 0; i < 3; i++){
                    pp1.setHand(this.dealingOut.pop());
                }

                Player pp2 = this.players.get(1);
                for(int i = 0; i < 3; i++){
                    pp2.setHand(this.dealingOut.pop());
                }

                Player pp3 = this.players.get(2);
                for(int i = 0; i < 4; i++){
                    pp3.setHand(this.dealingOut.pop());
                }

                Player pp4 = this.players.get(3);
                for(int i = 0; i < 4; i++){
                    pp4.setHand(this.dealingOut.pop());
                }

                Player pp5 = this.players.get(4);
                for(int i = 0; i < 4; i++){
                    pp5.setHand(this.dealingOut.pop());
                }
                break;
            //if there's 6 people playing deal out 3 cards for each player
            case "6":
                for(int p = 0; p < this.players.size(); p++){
                    for(int i = 0; i < 3; i++){
                        this.players.get(p).setHand(this.dealingOut.pop());
                    }
                }
                break;
        }

    }

    /**
     * Method that initialises ALL the cards in the game.
     * (There will be 6 weapon cards, 6 character cards, and 9 room cards)
     */
    public void initialiseCards(){
        //creates the 9 room cards and adds them to the respective arraylist
        RoomCard room1 = new RoomCard("Kitchen");
        RoomCard room2 = new RoomCard("Ball Room");
        RoomCard room3 = new RoomCard("Conservatory");
        RoomCard room4 = new RoomCard("Billiard Room");
        RoomCard room5 = new RoomCard("Library");
        RoomCard room6 = new RoomCard("Study");
        RoomCard room7 = new RoomCard("Hall");
        RoomCard room8 = new RoomCard("Lounge");
        RoomCard room9 = new RoomCard("Dining Room");
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
        rooms.add(room8);
        rooms.add(room9);

        //creates the 6 weapon cards and adds them to the respective arraylist
        WeaponCard weapon1 = new WeaponCard("Candlestick");
        WeaponCard weapon2 = new WeaponCard("Dagger");
        WeaponCard weapon3 = new WeaponCard("Lead Pipe");
        WeaponCard weapon4 = new WeaponCard("Revolver");
        WeaponCard weapon5 = new WeaponCard("Rope");
        WeaponCard weapon6 = new WeaponCard("Spanner");
        weapons.add(weapon1);
        weapons.add(weapon2);
        weapons.add(weapon3);
        weapons.add(weapon4);
        weapons.add(weapon5);
        weapons.add(weapon6);

        //creates the 6 character cards and adds them to the respective arraylist
        CharacterCard character1 = new CharacterCard("Scarlett");
        CharacterCard character2 = new CharacterCard("Mustard");
        CharacterCard character3 = new CharacterCard("White");
        CharacterCard character4 = new CharacterCard("Green");
        CharacterCard character5 = new CharacterCard("Peacock");
        CharacterCard character6 = new CharacterCard("Plum");
        characters.add(character1);
        characters.add(character2);
        characters.add(character3);
        characters.add(character4);
        characters.add(character5);
        characters.add(character6);
    }

    /**
     * Method that initialises the game, picks the murder circumstances, gives the cards out to players etc.
     */
    public void initialiseGame(List<Player> playerList){
        //this.board.initialise();
        this.players.addAll(playerList);

        //the game always starts with SCARLETT
        this.currentPlayer = PlayableCharacters.SCARLETT;

        //game is ongoing
        this.currentState = GameState.ONGOING;

        initialiseCards();
        setMurderCircumstances();
        setHandCards();
        dealOutCards();
    }

    /**
     * Rolls the dice with values from 2 - 12 (2 dices) and assigns the value to a field
     */
    public static void rollDice(){
        diceValue = (int) (Math.random()*(max - min + 1) + min);
        System.out.println("You rolled the number: " + diceValue);
    }


    /**
     * Creates a certain number of players (3 - 6) based on the input of the player
     * @return  the list of player objects created based on the input number
     */
    public List<Player> createPlayers() {
        List<Player> playersPlaying = new ArrayList<>();

        System.out.println("How many players are playing? (Min: 3, Max: 6) : ");

        boolean bool = false;
        String numPlayers;
        while(!bool) {
            playersPlaying.clear();
            numPlayers = input.next();
            this.numPeeps = numPlayers;
            switch(numPlayers) {
                //if there's 3 players, create 3 players
                case "3":

                    Player scarlett = new Player(PlayableCharacters.SCARLETT, 's');
                    Player mustard = new Player(PlayableCharacters.MUSTARD, 'm');
                    Player white = new Player(PlayableCharacters.WHITE, 'w');

                    playersPlaying.add(scarlett);
                    playersPlaying.add(mustard);
                    playersPlaying.add(white);

                    bool = true;
                    break;

                //if there's 4 players, create 4 players
                case "4":

                    scarlett = new Player(PlayableCharacters.SCARLETT, 's');
                    mustard = new Player(PlayableCharacters.MUSTARD, 'm');
                    white = new Player(PlayableCharacters.WHITE, 'w');
                    Player green = new Player(PlayableCharacters.GREEN, 'g');

                    playersPlaying.add(scarlett);
                    playersPlaying.add(mustard);
                    playersPlaying.add(white);
                    playersPlaying.add(green);

                    bool = true;
                    break;

                //if there's 5 players, create 5 players
                case "5":

                    scarlett = new Player(PlayableCharacters.SCARLETT, 's');
                    mustard = new Player(PlayableCharacters.MUSTARD, 'm');
                    white = new Player(PlayableCharacters.WHITE, 'w');
                    green = new Player(PlayableCharacters.GREEN, 'g');
                    Player peacock = new Player(PlayableCharacters.PEACOCK, 'P');

                    playersPlaying.add(scarlett);
                    playersPlaying.add(mustard);
                    playersPlaying.add(white);
                    playersPlaying.add(green);
                    playersPlaying.add(peacock);

                    bool = true;
                    break;

                //if there's 6 players, create 6 players
                case "6":

                    scarlett = new Player(PlayableCharacters.SCARLETT, 's');
                    mustard = new Player(PlayableCharacters.MUSTARD, 'm');
                    white = new Player(PlayableCharacters.WHITE, 'w');
                    green = new Player(PlayableCharacters.GREEN, 'g');
                    peacock = new Player(PlayableCharacters.PEACOCK, 'P');
                    Player plum = new Player(PlayableCharacters.PLUM, 'p');

                    playersPlaying.add(scarlett);
                    playersPlaying.add(mustard);
                    playersPlaying.add(white);
                    playersPlaying.add(green);
                    playersPlaying.add(peacock);
                    playersPlaying.add(plum);

                    bool = true;
                    break;

                //if numPlayers is not any of those cases, print out error message
                default:
                    System.out.println("--------------------------------------------------");
                    System.out.println("String entered or invalid number of Players");
                    System.out.println("How many players are playing? (Min: 3, Max: 6) : ");

                    break;
            }

        }
        return playersPlaying;
    }

    /**
     * Sets the murder circumstances that the players will have to guess to win.
     * Picks a random number between 1 and 6 for the char and the weapon, and random number between 1 and 9 for room
     * gets the cards at that index and adds it into the murder circumstances list.
     */
    public void setMurderCircumstances(){
        //gets a random number between 1 and 6 (inclusive)
        double randomChar = 0 + (Math.random() * 5);

        //gets a random number between 1 and 6 (inclusive)
        double randomWeapon = 0 + (Math.random() * 5);

        //gets a random number between 1 and 9 (inclusive)
        double randomRoom = 0 + (Math.random() * 8);

        //assigns the murder circumstances and removes them from their respective lists
        CharacterCard murderChar = this.characters.get((int)randomChar);
        this.characters.remove(murderChar);

        WeaponCard murderWeapon = this.weapons.get((int)randomWeapon);
        this.weapons.remove(murderWeapon);

        RoomCard murderRoom = this.rooms.get((int)randomRoom);
        this.rooms.remove(murderRoom);

        //adds the murder circumstances to the mCircumstances list
        this.murderCircumstances.add(murderChar);
        this.murderCircumstances.add(murderWeapon);
        this.murderCircumstances.add(murderRoom);
    }

    /**
     * Sets the hand that will be DEALT out with the remaining cards in the lists (adds and shuffles)
     */
    public void setHandCards()
    {
        //adds the remaining cards into the handCards stack
        this.dealingOut.addAll(this.characters);
        this.dealingOut.addAll(this.weapons);
        this.dealingOut.addAll(this.rooms);

        //shuffles the cards
        Collections.shuffle(this.dealingOut);
    }

    /**
     * Checks if the player's movement is valid or not based on different cases
     * @return  true if the player movement is valid, otherwise false
     */
    public boolean isValid(int r, int c){

        //if the row and column are out of bounds, the player cannot move
        if(r < 0){
            System.out.println("row is < 0");
            return false;
        }
        if(r > 24 ){
            System.out.println("row is > 24 (max index of array)");
            return false;
        }
        if(c < 0){
            System.out.println("col is < 0");
            return false;
        }
        if(c > 23){
            System.out.println("row is > 23 (max index of array)");
            return false;
        }

        //if the location the player wants to move into is an 'X' then it is invalid
        else if(this.board.getArrayTiles()[r][c].getChar().equals('X')){
            System.out.println("Moving into wall (X)");
            return false;
        }

        //if the location the player wants to move into is occupied by a player then it is invalid
        else if(this.board.getArrayTiles()[r][c].getChar().equals('w') ||
                this.board.getArrayTiles()[r][c].getChar().equals('g') ||
                this.board.getArrayTiles()[r][c].getChar().equals('P') ||
                this.board.getArrayTiles()[r][c].getChar().equals('p') ||
                this.board.getArrayTiles()[r][c].getChar().equals('s') ||
                this.board.getArrayTiles()[r][c].getChar().equals('m')){
            System.out.println("Occupied by a player");
            return false;
        }

        //if the location the player wants to move into has already been done then it is invalid
        else if(!getCurrentPlayer().getMovesDone().isEmpty()) {
            if (getCurrentPlayer().getMovesDone().peek().getRow() == r && getCurrentPlayer().getMovesDone().peek().getCol() == c) {
                System.out.println("Moving into an already moved area");
                return false;
            }
        }
        //if it's not any of the false cases, the player can move into the space

        return true;
    }

    /**
     * Gets the current player who's turn it is
     * @return  the player who's turn it is
     */
    public Player getCurrentPlayer(){
        for(Player player : this.players){
            if(this.currentPlayer == player.getCharToken()){
                return player;
            }
        }
        return null; //will never reach as a player will always be in turn
    }
    public static void main(String[] arguments){
        new Game();
    }
}
