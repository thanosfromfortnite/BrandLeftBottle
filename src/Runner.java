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
        boolean gameRunning = true;
        int OVERWORLD = 0; int BATTLE = 1;
        int gameState;

        // The character creation process, based on Profile class. The only real input the user has is with their name.
        System.out.println("Welcome, enter your name.");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();


        int fieldLength = 11; int fieldWidth = 11;
        Profile you = new Profile(input, fieldLength/2, fieldLength/2);

        Room[][] field = randomGenerateField(fieldLength,fieldWidth);


        while (gameRunning) {
            int xLoc = you.returnLocation()[0];
            int yLoc = you.returnLocation()[1];
            boolean n, e, s, w;
            n = field[xLoc][yLoc-1].isAnActualRoom();
            e = field[xLoc+1][yLoc].isAnActualRoom();
            s = field[xLoc][yLoc+1].isAnActualRoom();
            w = field[xLoc-1][yLoc].isAnActualRoom();

            printGUI(n, e, s, w);
            in = new Scanner(System.in);
            input = in.nextLine().toLowerCase();

            if (input.equals("m")) {
                printMap(field, you);
            }
            if (input.equals("n")) {
                field[xLoc][yLoc-1].enterRoom(you);
                field[you.returnLocation()[0]][you.returnLocation()[1]].leaveRoom();
            }
            if (input.equals("e")) {
                field[xLoc+1][yLoc].enterRoom(you);
                field[you.returnLocation()[0]][you.returnLocation()[1]].leaveRoom();
            }
            if (input.equals("s")) {
                field[xLoc][yLoc+1].enterRoom(you);
                field[you.returnLocation()[0]][you.returnLocation()[1]].leaveRoom();
            }
            if (input.equals("w")) {
                field[xLoc-1][yLoc].enterRoom(you);
                field[you.returnLocation()[0]][you.returnLocation()[1]].leaveRoom();
            }

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
                dungeon[i][j] = new NotAPlaceYouCanReallyGoTo(i, j);
                // Fills the whole dungeon with walls, to create a clean slate to work on.
            }
        }
        dungeon[length/2][width/2] = new StartingRoom(length/2, width/2);
        // Creates the start room in the absolute middle of the dungeon.

        roomIQ = dungeon[5][4];
        // By setting "roomIQ" to a space in the dungeon, I'm essentially "selecting" this piece of the dungeon to work
        // on.
        for (int i = 0; i < length / 2 - 1; i ++) {
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            Room[] nesw = {
                    dungeon[roomIQ.xLoc][roomIQ.yLoc-1], // 1 space north of the room "In Question"
                    dungeon[roomIQ.xLoc+1][roomIQ.yLoc], // 1 space east of the room "In Question"
                    dungeon[roomIQ.xLoc][roomIQ.yLoc+1], // S.O.
                    dungeon[roomIQ.xLoc-1][roomIQ.yLoc]  // W
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
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            // Picks what type of room will be placed in this spot.
            if (roomIQ.deadEnd()) {
                break;
                // And if the room is considered a "dead end", that will be the edge of one side.
            }
        }
        // The other three copies of the code below are the exact same thing, in different directions.
        roomIQ = dungeon[6][5]; // Generate to the east.
        for (int i = 0; i < width / 2 - 1; i ++) {
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            Room[] nesw = {
                    dungeon[roomIQ.xLoc][roomIQ.yLoc-1], // 1 space north of the room "In Question"
                    dungeon[roomIQ.xLoc+1][roomIQ.yLoc], // 1 space east of the room "In Question"
                    dungeon[roomIQ.xLoc][roomIQ.yLoc+1], // S.O.
                    dungeon[roomIQ.xLoc-1][roomIQ.yLoc] // W
            };
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            if (roomIQ.deadEnd()) {
                break;
            }
        }

        roomIQ = dungeon[5][6]; // South
        for (int i = 0; i < length / 2 - 1; i ++) {
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            Room[] nesw = {
                    dungeon[roomIQ.xLoc][roomIQ.yLoc-1], // 1 space north of the room "In Question"
                    dungeon[roomIQ.xLoc+1][roomIQ.yLoc], // 1 space east of the room "In Question"
                    dungeon[roomIQ.xLoc][roomIQ.yLoc+1], // S.O.
                    dungeon[roomIQ.xLoc-1][roomIQ.yLoc] // W
            };
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            if (roomIQ.deadEnd()) {
                break;
            }
        }

        roomIQ = dungeon[4][5]; // West
        for (int i = 0; i < width / 2 - 1; i ++) {
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            Room[] nesw = {
                    dungeon[roomIQ.xLoc][roomIQ.yLoc-1], // 1 space north of the room "In Question"
                    dungeon[roomIQ.xLoc+1][roomIQ.yLoc], // 1 space east of the room "In Question"
                    dungeon[roomIQ.xLoc][roomIQ.yLoc+1], // S.O.
                    dungeon[roomIQ.xLoc-1][roomIQ.yLoc] // W
            };
            if (nesw[0].isAnActualRoom() && nesw[1].isAnActualRoom() && nesw[2].isAnActualRoom() && nesw[3].isAnActualRoom()) {
                break;
            }
            randomAdjacentRoom = generateRandomInteger(0,3);
            while (nesw[randomAdjacentRoom].isAnActualRoom()) {
                randomAdjacentRoom = generateRandomInteger(0,3);
            }
            roomIQ = nesw[randomAdjacentRoom];
            dungeon[roomIQ.xLoc][roomIQ.yLoc] = pickWhichRoomToPutIn(roomIQ.xLoc, roomIQ.yLoc);
            if (roomIQ.deadEnd()) {
                break;
            }
        }
        return dungeon;
    }

    public static int generateRandomInteger(int min, int max) {
        return (int) (Math.random() * ((max + 1) - min)) + min;
    }

    public static Room pickWhichRoomToPutIn(int x, int y) {
        int decider = generateRandomInteger(0,5);
        if (decider == 5) {
            return new ItemRoom(x, y);
        }
        else if (decider < 2) {
            return new EmptyRoom(x, y);
        }
        else {
            return new BattleRoom(x, y);
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
                    z += "║" + map[i][k].returnMapPortion()[j];
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
        System.out.println("You are located in " + profile.returnLocation()[0] + ", " + profile.returnLocation()[1]);
    }

    public static void printGUI(boolean n, boolean e, boolean s, boolean w) {
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
        if (!n) {
            GUI[0] = "                   ||";
            GUI[1] = "                   ||";
            overworld[1] = "";
        }
        if (!w && !e) {
            GUI[2] = "                   ||";
            overworld[2] = "";
            overworld[3] = "";
        }
        else if (!w) {
            GUI[2] = "         >   EAST  ||";
            overworld[3] = "";
        }
        else if (!e) {
            GUI[2] = "  WEST  <          ||";
            overworld[2] = "";
        }
        if (!s) {
            GUI[3] = "                   ||";
            GUI[4] = "                   ||";
            overworld[4] = "";
        }

        for (int i = 0; i < 6; i ++) {
            System.out.println(GUI[i] + overworld[i]);
        }

    }

}
