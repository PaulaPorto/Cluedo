import java.util.*;

public class Game {

    public static final int min = 2;
    public static final int max = 12;
    public static int diceValue = 0;

    public String numPeeps;

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

    //the row and column that the player wants to move to
    private int row;
    private int col;


    //input Scanner
    private static Scanner input = new Scanner(System.in);

    //TODO constructor logic

    /**
     * Constructor for the game
     */
    public Game(){

        //creates the board and prints it out
        this.board = new Board();
        this.board.readBoard();
        this.board.printBoard();

        List<Player> players = createPlayers();
        initialiseGame(players);
//    initialiseCards();
//    setMurderCircumstances();
//    setHandCards();
//    dealOutCards();

        while(this.currentState == GameState.ONGOING){

            printHandCards();
            askRoll();
            askMove();
            //this.board.printBoard();
            this.board.updateBoard();
            this.board.printBoard();
            break;
        }
    }

    /**
     * Asks the player to move, and also does the moving of the player
     */
    public void askMove(){
        boolean bool = false;
        while(!bool){
            System.out.println("---------------------------------------------------------------------");
            System.out.println("According to your dice value, type in your desired moves (U for up, D for down, L for left, R for right) " +
                    "\nFor example, if you rolled a 3, your input should look something like LLU, this would move you left, left, up.");

            String movesInput = this.input.next();

            for(int i = 0; i < movesInput.length(); i++){
                if(movesInput.length() > this.diceValue){
                    System.out.println("Input does mot match your number of moves (dice value). Try again :/");
                    //bool = false;
                    break;
                }
                if(movesInput.charAt(i) == ('R') || movesInput.charAt(i) == ('r')){
                    Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                    if(isValid(currentPlayerPos.getRow(), currentPlayerPos.getCol() + 1)){
                        this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow(), currentPlayerPos.getCol() + 1);
                        System.out.println("Moved right!");
                        bool = true;
                    }
                    else{
                        System.out.println("Invalid right :(");
                        bool = false;
                        break;
                    }
                }
                if(movesInput.charAt(i) == 'L' || movesInput.charAt(i) == 'l'){
                    Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                    if(isValid(currentPlayerPos.getRow(), currentPlayerPos.getCol() - 1)){
                        this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow(), currentPlayerPos.getCol() - 1);
                        System.out.println("Moved left!");
                        bool = true;
                    }
                    else{
                        System.out.println("Invalid left :(");
                        bool = false;
                        break;
                    }
                }
                if(movesInput.charAt(i) == ('U') || movesInput.charAt(i) == ('u')){
                    Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                    if(isValid(currentPlayerPos.getRow() - 1, currentPlayerPos.getCol())){
                        this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow() - 1, currentPlayerPos.getCol());
                        System.out.println(currentPlayerPos.getRow() + " " + currentPlayerPos.getCol());
                        System.out.println("Moved up!");
                        bool = true;
                    }
                    else{
                        System.out.println("Invalid up :(");
                        bool = false;
                        break;
                    }
                }
                if(movesInput.charAt(i) == ('D') || movesInput.charAt(i) == ('d')){
                    Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
                    if(isValid(currentPlayerPos.getRow() + 1, currentPlayerPos.getCol())){
                        this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow() + 1, currentPlayerPos.getCol());
                        System.out.println("Moved down!");
                        bool = true;
                    }
                    else{
                        System.out.println("Invalid down :(");
                        bool = false;
                        break;
                    }
                }

            }
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
        int num = (int) (Math.random()*(max - min + 1) + min);
        diceValue = num;
        System.out.println("You rolled the number: " + diceValue);
    }

    /**
     * Has the different actions the player in turn can do.
     * Check hand, make suggestion, make accusation etc.
     */
    public void playerAction(){

    }

    /**
     * Moves the player in the space where they want to be in
     */
    public void playerMove(int row, int column){
        //empty for now
        //moves the player to the desired location on the board
    }

    /**
     * Updates the game state based on the player who won
     * @param player    a player to check if they have won or not
     */
    public void updateGame(PlayableCharacters player){
        //empty for now
    }

    /**
     * Creates the players depending on how many players are stated
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
                // Valid Cases
                case "3":
                    //playersPlaying.clear();

                    Player scarlett = new Player(PlayableCharacters.SCARLETT, 's');
                    Player mustard = new Player(PlayableCharacters.MUSTARD, 'm');
                    Player white = new Player(PlayableCharacters.WHITE, 'w');

                    playersPlaying.add(scarlett);
                    playersPlaying.add(mustard);
                    playersPlaying.add(white);

                    bool = true;
                    break;

                case "4":
                    //playersPlaying.clear();

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

                case "5":
                    //playersPlaying.clear();

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

                case "6":
                    //playersPlaying.clear();

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

                default:
                    System.out.println("--------------------------------------------------");
                    System.out.println("String entered or invalid number of Players");
                    System.out.println("How many players are playing? (Min: 3, Max: 6) : ");
                    //createPlayers();

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
        else if(this.board.getArrayTiles()[r][c].getRoom().equals('X')){
            System.out.println("Moving into wall (X)");
            return false;
        }

        //if the location the player wants to move into is occupied by a player then it is invalid
        else if(this.board.getArrayTiles()[r][c].getRoom().equals('w') ||
                this.board.getArrayTiles()[r][c].getRoom().equals('g') ||
                this.board.getArrayTiles()[r][c].getRoom().equals('P') ||
                this.board.getArrayTiles()[r][c].getRoom().equals('p') ||
                this.board.getArrayTiles()[r][c].getRoom().equals('s') ||
                this.board.getArrayTiles()[r][c].getRoom().equals('m')){
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

    public Player getCurrentPlayer(){
        for(Player player : this.players){
            if(this.currentPlayer == player.getCharToken()){
                return player;
            }
        }
        return null; //maybe will f up
    }
    public static void main(String[] arguments){
//    	Board newBoard = new Board();
//  	    newBoard.readBoard();
//  	    newBoard.printBoard();
        new Game();
    }
}
