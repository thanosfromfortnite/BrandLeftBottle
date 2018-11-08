/**
 * JESSE HAN
 * AP JAVA PERIOD 4, 5
 * MR FOLWELL'S CLASS
 * I MADE THIS
 * IT TOOK A WHILE
 **/

/*
* OK, so this is the Runner class. I'm gonna take you through how it works.
* First, you run the program (good job), and a Scanner asks the user for their name.
* That name is the only controllable input into creating their profile courtesy of Profile.java
* A completely random dungeon is created. You can find a more detailed process by looking at the comments on
* randomGenerateField();.
* The game starts. More details will follow.
*/

import java.util.Scanner;
public class Runner {
    public static void main(String[] args) {
        boolean gameEnd = false; boolean walkIntoWall = false;

        // The character creation process, based on Profile class. The only real input the user has is with their name.
        System.out.println("WARNING: The map only works right if you have the right font. In IntelliJ, go to File>Settings>Editor>Font>Change Font to Consolas");
        System.out.println("Welcome, enter your name.");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        String[] tutorial = {
                "Welcome to Fungeon, a randomly generated turn-based dungeon game where you must explore the dungeon room by room to reach the boss room.",
                "Every room will start off hidden until you explore it. The boss room is visible on the map at all times, which is your goal.",
                "You navigate the world using n, e, s, and w, to explore north, east, west, and south. Your compass on the left will tell you your options, in addition to the main screen.",
                "If there is a wall in any direction, you would not be able to travel through. You gain a bit of health over time as you travel between rooms.",
                "There are five different types of rooms total in Fungeon.",
                "You start off in the Starting Room. There is nothing here, but if you return to this room, all of your health will be restored.",
                "An item room will give you the option to take or ignore a random item of a certain rarity. They spawn less frequently than battle rooms. Since this is randomly generated, you might not even be able to encounter one.",
                "An empty room is empty.",
                "A battle room will give you an encounter with a random enemy.",
                "During a battle, all of your stats will be factored into different aspects of the battle. Your strength will scale with the damage that you or your enemies deal, while defence of an opponent inversely scales with it.",
                "Your speed will determine your chance of successfully fleeing from a battle. It is compared to an enemy's speed.",
                "During a battle, you may choose to attack with a, defend and restore some health with d, inspect the enemy and view their stats with i, view your own stats with t, or attempt to flee with r.",
                "All of these actions will count as a turn EXCEPT for viewing your own stats. If you fail to flee, you will take extra damage if the enemy attacks.",
                "If you defeat an enemy in battle, the room will become safe for the remainder of the game and your stats may increase. If you flee, only the room will be safe.",
                "The more enemies you defeat, the stronger they get, so choose your fights wisely.",
                "The final room that has yet to be mentioned is the boss room. In here, you will fight an enemy with increased health and other stats. You will not be able to flee from this battle.",
                "Do NOT try to flee from a boss fight.",
                "The boss will have the ability to use special attacks.",
                "If you get lost while exploring, you can always use m to check the map, or t to check your own stats.",
                "Battle rooms will be marked with a B, Item rooms are marked with a T (as in treasure), the starting room will be marked with an S, and X marks the boss room.",
                "Good luck."
        };
        boolean tutorialEnded = false;
        while (!tutorialEnded) {
            System.out.println("Would you like to take the tutorial? (y/n)");
            in = new Scanner(System.in);
            input = in.nextLine();
            input.toLowerCase().trim();
            if (input.equals("y")) {
                tutorialEnded = true;
                System.out.println("Press enter after each line to continue.");
                for (int i = 0; i < tutorial.length; i++) {
                    System.out.println(tutorial[i]);
                    in = new Scanner(System.in);
                    input = in.nextLine();
                }
            }
            if (input.equals("n")) {
                tutorialEnded = true;
            }
        }

        int fieldLength = 11; int fieldWidth = 11;
        Profile you = new Profile(input, fieldLength/2, fieldLength/2, 100);
        Room[][] field = randomGenerateField(fieldLength,fieldWidth);


        while (!gameEnd) {
            walkIntoWall = false;
            int xLoc = you.returnLocation()[1];
            int yLoc = you.returnLocation()[0];
            boolean enterBossRoom;
            Room n, e, s, w;
            n = field[xLoc][yLoc-1];
            e = field[xLoc+1][yLoc];
            s = field[xLoc][yLoc+1];
            w = field[xLoc-1][yLoc];
            printGUI(n, e, s, w);


            in = new Scanner(System.in);
            input = in.nextLine().toLowerCase();

            if (input.equals("m")) {
                printMap(field, you);
            }
            if (input.equals("n")) {
                if (n.isAnActualRoom()) {
                    if (n.bossRoom()) {
                        enterBossRoom = warnPlayerOfImpedingDoom();
                        if (enterBossRoom) {
                            field[xLoc][yLoc].leaveRoom();
                            you.changeHealth((int) (you.health * 0.2));
                            gameEnd = n.enterRoom(you);
                        }
                    }
                    else {
                        field[xLoc][yLoc].leaveRoom();
                        you.changeHealth((int) (you.health * 0.2));
                        gameEnd = n.enterRoom(you);
                    }
                }
                else {walkIntoWall = true;}
            }
            if (input.equals("e")) {
                if (e.isAnActualRoom()) {
                    if (e.bossRoom()) {
                        enterBossRoom = warnPlayerOfImpedingDoom();
                        if (enterBossRoom) {
                            field[xLoc][yLoc].leaveRoom();
                            you.changeHealth((int) (you.health * 0.2));
                            gameEnd = e.enterRoom(you);
                        }
                    }
                    else {
                        field[xLoc][yLoc].leaveRoom();
                        you.changeHealth((int) (you.health * 0.2));
                        gameEnd = e.enterRoom(you);
                    }
                }
                else {walkIntoWall = true;}
            }
            if (input.equals("s")) {
                if (s.isAnActualRoom()) {
                    if (s.bossRoom()) {
                        enterBossRoom = warnPlayerOfImpedingDoom();
                        if (enterBossRoom) {
                            field[xLoc][yLoc].leaveRoom();
                            you.changeHealth((int) (you.health * 0.2));
                            gameEnd = s.enterRoom(you);
                        }
                    }
                    else {
                        field[xLoc][yLoc].leaveRoom();
                        you.changeHealth((int) (you.health * 0.2));
                        gameEnd = s.enterRoom(you);
                    }
                }
                else {walkIntoWall = true;}
            }
            if (input.equals("w")) {
                if (w.isAnActualRoom()) {
                    if (w.bossRoom()) {
                        enterBossRoom = warnPlayerOfImpedingDoom();
                        if (enterBossRoom) {
                            field[xLoc][yLoc].leaveRoom();
                            you.changeHealth((int) (you.health * 0.2));
                            gameEnd = w.enterRoom(you);
                        }
                    }
                    else {
                        field[xLoc][yLoc].leaveRoom();
                        you.changeHealth((int) (you.health * 0.2));
                        gameEnd = w.enterRoom(you);
                    }
                }
                else {walkIntoWall = true;}
            }
            if (input.equals("t")) {
                System.out.println("Name: " + you.returnName());
                System.out.println("Health: " + you.health + " / " + you.maxHealth);
                System.out.println("Strength: " + you.returnStat(1) + " (" + you.returnItem().strengthBonus + ")");
                System.out.println("Defence: " + you.returnStat(2) + " (" + you.returnItem().defenceBonus + ")");
                System.out.println("Speed: " + you.returnStat(0) + " (" + you.returnItem().speedBonus + ")");
                System.out.println("Item: " + you.returnItem().name);
            }
            // You may have noticed that the y, x format (length, width) has been switched to x, y in this particular case.
            // I don't know why, but for some crazy fucking reason y, x doesn't seem to work, and x,y does.
            // So I'm keeping it like this.
            if (walkIntoWall) {
                System.out.println("You stare down the cave wall, then walk straight into it. You take 1 damage.");
                you.changeHealth(-1);
                System.out.println("Type anything to continue.");
                in = new Scanner(System.in);
                input = in.nextLine();
            }
            if (you.returnDeathStatus()) {
                gameEnd = true;
            }
        }
        if (you.returnDeathStatus()) {
            System.out.println("  #####      #     #     #  ######               #####    #     #  #######  ######  ");
            System.out.println(" #     #    # #    ##   ##  #                   #     #   #     #  #        #     # ");
            System.out.println(" #         #   #   # # # #  #                   #     #   #     #  #        #     # ");
            System.out.println(" #        #     #  #  #  #  #####               #     #   #     #  #####    ######  ");
            System.out.println(" #   ###  #######  #     #  #                   #     #    #   #   #        #  #    ");
            System.out.println(" #     #  #     #  #     #  #                   #     #     # #    #        #   #   ");
            System.out.println("  #####   #     #  #     #  ######               #####       #     #######  #    #  ");
        }
        else {
            System.out.println(" #     #   #####   #     #          #       #   #####  #     # ");
            System.out.println("  #   #   #     #  #     #          #       #     #    ##    # ");
            System.out.println("   # #    #     #  #     #          #       #     #    # #   # ");
            System.out.println("    #     #     #  #     #          #       #     #    #  #  # ");
            System.out.println("    #     #     #  #     #          #   #   #     #    #   # # ");
            System.out.println("    #     #     #  #     #           # # # #      #    #    ## ");
            System.out.println("    #      #####    #####             #   #     #####  #     # ");
        }


    }
    /*
    * I like this method a lot. I thought of it in class while the internet was down and it's an incredible success.
    * An "epic win" if you will.
    * If you go through this code from top to bottom, I promise you'll at least maybe somewhat kind of hopefully
    * understand it by reading the comments. I didn't spend all night typing these up for nothing!
    */
    public static Room[][] randomGenerateField(int length, int width) {
        // So you're probably wondering why the length and the width of the space are inputs. And to tell you the truth,
        // I don't really know exactly why this happens, but this method doesn't recognize Room[][] field (above) as a
        // real variable, so to fix that I just decided to make another room[][] array in here and set it to field[][].
        Room[][] dungeon = new Room[length][width];
        Room roomIQ; int randomAdjacentRoom;
        // roomIQ stands for "Room In Question". It's very important in this method, you'll see why.

        for (int i = 0; i < dungeon.length; i ++) {
            for (int j = 0; j < dungeon[i].length; j ++) {
                dungeon[i][j] = new NotAPlaceYouCanReallyGoTo(j, i, false);
                // Fills the whole dungeon with walls, to create a clean slate to work on.
            }
        }
        dungeon[length/2][width/2] = new StartingRoom(length/2, width/2, false);
        // Creates the start room in the absolute middle of the dungeon.

        roomIQ = dungeon[4][5];
        roomIQ = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
        // By setting "roomIQ" to a space in the dungeon, I'm essentially "selecting" this piece of the dungeon to work
        // on.
        for (int i = 0; i < length / 2 - 1; i ++) {
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
            Room[] nesw = {
                    dungeon[roomIQ.yLoc-1][roomIQ.xLoc], // 1 space north of the room "In Question"
                    dungeon[roomIQ.yLoc][roomIQ.xLoc+1], // 1 space east of the room "In Question"
                    dungeon[roomIQ.yLoc+1][roomIQ.xLoc], // S.O.
                    dungeon[roomIQ.yLoc][roomIQ.xLoc-1]  // W
            };
            // nesw[] refers to every room that is directly N, E, S, and W of the room in question. This is to find
            // out which spaces are unused and therefore can have something generated on it.
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
                // And if all surrounding rooms are used, just end it and move on to another direction.
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                // So this "while" statement makes the program re-choose an adjacent room until it finds one that is
                // unused, if the one selected happens to be used.
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
            // Picks what type of room will be placed in this spot.
        }
        // The other three copies of the code below are the exact same thing, in different directions.
        roomIQ = dungeon[5][6]; // Generate to the east.
        roomIQ = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
        for (int i = 0; i < width / 2 - 1; i ++) {
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
            Room[] nesw = {
                    dungeon[roomIQ.yLoc-1][roomIQ.xLoc], // 1 space north of the room "In Question"
                    dungeon[roomIQ.yLoc][roomIQ.xLoc+1], // 1 space east of the room "In Question"
                    dungeon[roomIQ.yLoc+1][roomIQ.xLoc], // S.O.
                    dungeon[roomIQ.yLoc][roomIQ.xLoc-1]  // W
            };
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
        }

        roomIQ = dungeon[6][5]; // South
        roomIQ = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
        for (int i = 0; i < length / 2 - 1; i ++) {
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
            Room[] nesw = {
                    dungeon[roomIQ.yLoc-1][roomIQ.xLoc], // 1 space north of the room "In Question"
                    dungeon[roomIQ.yLoc][roomIQ.xLoc+1], // 1 space east of the room "In Question"
                    dungeon[roomIQ.yLoc+1][roomIQ.xLoc], // S.O.
                    dungeon[roomIQ.yLoc][roomIQ.xLoc-1]  // W
            };
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
        }

        roomIQ = dungeon[5][4]; // West
        roomIQ = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
        for (int i = 0; i < width / 2 - 1; i ++) {
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);
            Room[] nesw = {
                    dungeon[roomIQ.yLoc-1][roomIQ.xLoc], // 1 space north of the room "In Question"
                    dungeon[roomIQ.yLoc][roomIQ.xLoc+1], // 1 space east of the room "In Question"
                    dungeon[roomIQ.yLoc+1][roomIQ.xLoc], // S.O.
                    dungeon[roomIQ.yLoc][roomIQ.xLoc-1]  // W
            };
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.yLoc][roomIQ.xLoc] = pickWhichRoomToPutIn(roomIQ.yLoc, roomIQ.xLoc);

        }
        for (int i = 0; i < length; i ++) {
            if (dungeon[0][i].isAnActualRoom()) {
                dungeon[0][i] = new NotAPlaceYouCanReallyGoTo(0, i, true);
            }
            if (dungeon[i][0].isAnActualRoom()) {
                dungeon[i][0] = new NotAPlaceYouCanReallyGoTo(i, 0, true);
            }
        }
        int adjacentRooms = 0;
        while (adjacentRooms == 0) {
            roomIQ = dungeon[generateRandomInteger(2,dungeon.length-2)][generateRandomInteger(2,dungeon[0].length-2)];
            boolean[] neswBoolean = {
                    dungeon[roomIQ.yLoc-1][roomIQ.xLoc].isAnActualRoom(), // 1 space north of the room "In Question"
                    dungeon[roomIQ.yLoc][roomIQ.xLoc+1].isAnActualRoom(), // 1 space east of the room "In Question"
                    dungeon[roomIQ.yLoc+1][roomIQ.xLoc].isAnActualRoom(), // S.O.
                    dungeon[roomIQ.yLoc][roomIQ.xLoc-1].isAnActualRoom()
            };
            adjacentRooms = 0;
            for (int i = 1; i < neswBoolean.length; i ++) {
                if (neswBoolean[i]) {
                    adjacentRooms ++;
                }
            }
        }
        dungeon[roomIQ.yLoc][roomIQ.xLoc] = new BossRoom(roomIQ.yLoc, roomIQ.xLoc,false);
        return dungeon;
    }

    public static int generateRandomInteger(int min, int max) {
        return (int) (Math.random() * ((max + 1) - min)) + min;
    }

    public static Room pickWhichRoomToPutIn(int y, int x) {
        int decider = generateRandomInteger(0,9);
        if (decider < 2) {
            return new ItemRoom(y, x, true);
        }
        else if (decider < 4) {
            return new EmptyRoom(y, x, true);
        }
        else {
            return new BattleRoom(y, x, true);
        }
    }

    public static void printMap(Room[][] map, Profile profile) {
        String x = "  ";
        for (int i = 0; i < map.length; i ++) {
            x += i + "   ";
        }
        System.out.println(x);
        
        String y = "╔";
        for (int i = 0; i < map.length; i ++) {
            if (i != map.length-1) {y += "═══╦";}
            else {y += "═══╗";}
        }
        System.out.println(y);
        
        for (int i = 0; i < map.length; i ++) {
            for (int j = 0; j < 3; j ++) {
                String z = "";
                for (int k = 0; k < map[i].length; k ++) {
                    z += "║" + map[k][i].returnMapPortion()[j];
                }
                z += "║";
                if (j == 1) {
                    z += "  " + i;
                }
                System.out.println(z);
            }
            if (i != map.length - 1) {
                String a = "╠";
                for (int j = 0; j < map.length; j ++) {
                    if (j != map.length - 1) {
                        a += "═══╬";
                    }
                    else {
                        a += "═══╣";
                    }
                }
                System.out.println(a);
            }
        }
        String b = "╚";
        for (int i = 0; i < map[0].length; i ++) {
                    if (i != map.length - 1) {
                        b += "═══╩";
                    }
                    else {
                        b += "═══╝";
                    }
                }
        System.out.println(b);
        System.out.println("You are located in " + profile.returnLocation()[1] + ", " + profile.returnLocation()[0]);
    }

    public static void printGUI(Room n, Room e, Room s, Room w) {
        String[] GUI = {
                "       NORTH       ||", // 21 characters long
                "         ^         ||",
                "  WEST  < >  EAST  ||",
                "         V         ||",
                "       SOUTH       ||",
                "                   ||"
        };
        String[] overworld = {
                "  Your options:",
                "  (N)orth",
                "  (E)ast",
                "  (S)outh",
                "  (W)est",
                "  You can also check the (M)ap, or your S(T)ats."
        };
        if (!n.isAnActualRoom()) {
            GUI[0] = "                   ||";
            GUI[1] = "                   ||";
            overworld[1] = "";
        }
        if (!w.isAnActualRoom() && !e.isAnActualRoom()) {
            GUI[2] = "                   ||";
            overworld[2] = "";
            overworld[4] = "";
        }
        if (!w.isAnActualRoom() && e.isAnActualRoom()) {
            GUI[2] = "          >  EAST  ||";
            overworld[4] = "";
        }
        if (!e.isAnActualRoom() && w.isAnActualRoom()) {
            GUI[2] = "  WEST  <          ||";
            overworld[2] = "";
        }
        if (!s.isAnActualRoom()) {
            GUI[3] = "                   ||";
            GUI[4] = "                   ||";
            overworld[3] = "";
        }

        for (int i = 0; i < 6; i ++) {
            System.out.println(GUI[i] + overworld[i]);
        }

    }
    public static boolean warnPlayerOfImpedingDoom() {
        System.out.println("WARNING: The room you are about to enter is a Boss Room. Do you wish to enter? (y/n)");
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        in.toLowerCase().trim();
        while (!in.equals("y") && !in.equals("n")) {
            System.out.println("WARNING: The room you are about to enter is a Boss Room. Do you wish to enter? (y/n)");
            input = new Scanner(System.in);
            in = input.nextLine();
            in.toLowerCase().trim();
        }
        if (in.equals("y")) {
            return true;
        }
        else {
            System.out.println("You hesitate, and end up staying in your current room.");
            return false;
        }
    }

}
