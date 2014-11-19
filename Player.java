
/*
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Scanner;
import java.io.*;
public class Player{
    private String characterName, Class, input;
    private int potions, food, water, weapons, people, vehicles, playerX = 4, playerY = 4;
    private int rows = 9, columns = 9;
    private Scanner scan = new Scanner(System.in);
    /*
     * Constructor for objects of class Player
     */
    public Player(String name){
        // initialise instance variables
        characterName = name;
    }

    public static void startGame(){
        int i = 0;
        while (i==0){
            System.out.print("New game or load game? (new or load): ");
            String inputs;
            Scanner scan1 = new Scanner(System.in);
            inputs = scan1.next().toLowerCase();

            if (inputs.equals("load")){
                File f = new File("SaveGame.sav");
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
                System.out.print("Please enter your character name: ");
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
            Class = scan.next().toLowerCase();
            if (Class.equals("fighter") || Class.equals("leader") || Class.equals("survivor"))
                i = 1;
            else 
                i = 0;
        }
        saveGame();
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
        enterCommand();
    }

    public void enterCommand(){
        System.out.print("What do you want to do " + characterName + "? Type 'help' for commands: ");
        input = scan.next();
        switch (input.toLowerCase()){
            default:
            enterCommand();
            break;
            case "help":
            showCommands();
            break;
            case "inventory":
            inventory();
            break;
            case "save":
            saveGame();
            break;
            case "map":
            checkMap();
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
        System.out.println("Commands: help   inventory   save   map");
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
            System.out.println("Game Saved!");
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
        char[][] map = new char[][]{
                { '#', '#', '#', '#', '#', '#', '#', '#', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '-', '-', '-', '-', '-', '-', '-', '#' },
                { '#', '#', '#', '#', '#', '#', '#', '#', '#' }
            };

        map[playerX][playerY] = 'P';

        for (int i=0; i < rows; i++){
            for (int j=0; j < columns; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        enterCommand();
    }
}
