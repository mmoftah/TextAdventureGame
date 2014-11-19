import java.util.Scanner;
import java.io.*;
public class Player{
    private String characterName, Class, input;
    private int potions, food, water, weapons, people, vehicles, playerX, playerY;
    private Scanner scan = new Scanner(System.in);
    private Scanner scan2 = new Scanner(System.in);
	private static Scanner scan1;
	boolean autosave = false;
    /*
     * Constructor for objects of class Player
     */
    public Player(String name){
        // initialize instance variables
        characterName = name;
    }

    public static void startGame(){
    	File f = new File("SaveGame.sav");
        int i = 0;
        
        while (i==0){
            System.out.print("New game or load game? (new or load): ");
            if(f.exists() && !f.isDirectory())
            	System.out.print("(You have a saved game.): ");
            String inputs;
            scan1 = new Scanner(System.in);
            inputs = scan1.next().toLowerCase();

            if (inputs.equals("load")){
                if(f.exists() && !f.isDirectory()) { 
                    Player player1 = new Player("Loading Save...");
                    player1.loadGame();
                    i++;
                } else {
                    System.out.println("You dont have a save game. Creating new account.");
                    System.out.print("Please enter your character name: ");
                    inputs = scan1.next();
                    Player player1 = new Player(inputs);
                    player1.chooseClass();
                    i++;
                }
            } else if (inputs.equals("new")){
                System.out.print("Please enter your character's name: ");
                inputs = scan1.next();
                Player player1 = new Player(inputs);
                player1.chooseClass();
                i++;
            }
        }
    }

    /*
     * Player chooses the class they want and gives appropriate starter kit.
     */
    public void chooseClass(){
        int i = 0;

        while (i==0){
            System.out.print("Choose a class (Fighter, Leader, or Survivor): ");
            Class = scan2.next().toLowerCase();
            if (Class.equals("fighter") || Class.equals("leader") || Class.equals("survivor"))
                i = 1;
            else 
                i = 0;
        }
        playerX = 33;
        playerY = 33;
        System.out.print("Would you like to enable autosave?(y or n): ");
        input = scan2.next().toLowerCase();
        if (input.equals("y") || input.equals("yes"))
        	autosave = true;
        else
        	autosave = false;
        giveStarter();
    }

    public void giveStarter(){
        if (Class.equals("fighter")){
            potions = 15;
            food = 7;
            water = 7;
            weapons = 15;
            people = 5;
            vehicles = 1;
        } else if(Class.equals("leader")){
            potions = 5;
            food = 7;
            water = 7;
            weapons = 5;
            people = 21;
            vehicles = 5;
        } else if(Class.equals("survivor")){
            potions = 5;
            food = 17;
            water = 17;
            weapons = 5;
            people = 5;
            vehicles = 1; 
        }
        if (autosave)
        saveGame();
        enterCommand();
    }

    public void enterCommand(){
        System.out.print("What do you want to do " + characterName + "? Type 'help' for commands: ");
        input = scan.nextLine();
        switch (input.toLowerCase()){
            default:
            System.out.println("Unknown Command.");
            enterCommand();
            break;
            case "help":
            case "help ":
            case " help ":
            case " help":
            case "'help'":
            showCommands();
            break;
            case "inventory":
            case " inventory":
            case "inventory ":
            case " inventory ":
            case "'inventory'":
            inventory();
            break;
            case "save":
            case " save":
            case "save ":
            case " save ":
            case "'save'":
            saveGame();
            break;
            case "map":
            case " map":
            case "map ":
            case " map ":
            case "'map'":
            checkMap();
            break;
            case "move up":
            case " move up":
            case "move up ":
            case " move up ":
            case "'move up'":
            moveUp();	
            break;
            case "move down":
            case " move down":
            case "move down ":
            case " move down ":
            case "'move down'":
            moveDown();	
            break;
            case "move right":
            case " move right":
            case "move right ":
            case " move right ":
            case "'move right'":
            moveRight();	
            break;
            case "move left":
            case " move left":
            case "move left ":
            case " move left ":
            case "'move left'":
            moveLeft();	
            break;
        }
    }

    public void inventory(){
        System.out.println("Inventory:");
        System.out.println("Potions: " + potions);
        System.out.println("Food: " + food);
        System.out.println("Water: " + water);
        System.out.println("Weapons: " + weapons);
        System.out.println("People: " + people);
        System.out.println("Vehicles: " + vehicles);
        enterCommand();
    }

    public void showCommands(){

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream("Commands");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine;
        int arraySize = 10;
        String array[][] = new String[arraySize][];
        int index = 0;
        try {
            while ((strLine = br.readLine()) != null) {

                if (index >= arraySize - 1) {
                    System.out.println("Error : Increase array size !");
                    break;
                }
                array[index] = strLine.split(" ");
                index++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j] + " ");
                }
                System.out.println(" ");
            }
        }

        if (autosave)
            saveGame();
        enterCommand();
    }

    public void saveGame(){
        try{  // Catch errors in I/O if necessary.
            // Open a file to write to, named SaveGame.sav.
            FileOutputStream saveFile=new FileOutputStream("SaveGame.sav");

            // Create an ObjectOutputStream to put objects into save file.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            // Now we do the save.
            save.writeObject(characterName);
            save.writeObject(Class);
            save.writeObject(potions);
            save.writeObject(food);
            save.writeObject(water);
            save.writeObject(weapons);
            save.writeObject(people);
            save.writeObject(vehicles);
            save.writeObject(playerX);
            save.writeObject(playerY);
            // Close the file.
            save.close(); // This also closes saveFile.
            System.out.print("(Game Saved!) ");
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
        enterCommand();
    }

    public void loadGame(){
        try{
            // Open file to read from, named SaveGame.sav.
            FileInputStream saveFile = new FileInputStream("SaveGame.sav");

            // Create an ObjectInputStream to get objects from save file.
            ObjectInputStream save = new ObjectInputStream(saveFile);

            // Now we do the restore.
            // readObject() returns a generic Object, we cast those back
            // into their original class type.
            // For primitive types, use the corresponding reference class.
            characterName = (String) save.readObject();
            Class = (String) save.readObject();
            potions = (Integer) save.readObject();
            food = (Integer) save.readObject();
            water = (Integer) save.readObject();
            weapons = (Integer) save.readObject();
            people = (Integer) save.readObject();
            vehicles = (Integer) save.readObject();
            playerX = (Integer) save.readObject();
            playerY = (Integer) save.readObject();

            // Close the file.
            save.close(); // This also closes saveFile.
            System.out.println("Game Loaded!");
        }
        catch(Exception exc){
            exc.printStackTrace(); // If there was an error, print the info.
        }
        enterCommand();
    }

    public void checkMap(){
        FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("Map");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine;
        int arraySize = 65;
        String array[][] = new String[arraySize][];
        int index = 0;
        try {
			while ((strLine = br.readLine()) != null) {
			    if (index > arraySize - 1) {
			        System.out.println("Error : Increase array size !");
			        break;
			    }
			    array[index] = strLine.split(" ");
			    index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        for (int i = playerX - 4; i <= playerX + 4; i++) {
            if (array[i] != null) {
                for (int j = playerY - 4; j <= playerY + 4; j++) {
                	if (i == playerX && j == playerY){
                		System.out.print("P ");
                	} else {
                        System.out.print(array[i][j] + " ");
                	}
                }
                System.out.println(" ");
            }
        }

        if (autosave)
            saveGame();
        enterCommand();
    }
    
    public void moveUp(){
    	playerY-=1;
    	checkMap();
    }
    
    public void moveDown(){
    	playerY+=1;
    	checkMap();
    }
    
    public void moveLeft(){
    	playerX-=1;
    	checkMap();
    }
    
    public void moveRight(){
    	playerX+=1;
    	checkMap();
    }
}
