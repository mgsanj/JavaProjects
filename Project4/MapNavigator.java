import java.io.BufferedReader;
import java.io.FileReader;
/**
 * A class that processes a file input to navigate a simulated game map for users.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version November 1, 2023
 */
public class MapNavigator extends Thread {
    private static int currentRow = 4;
    private static int currentColumn = 4;
    private static int moveNumber = 1;
    private static boolean started = false;
    private static char[][] map = new char[10][10];
    private int playerNumber;
    private String fileName;

    public static Object SYNC = new Object();

    public MapNavigator(int playerNumber, String fileName) {
        this.playerNumber = playerNumber;
        this.fileName = fileName;
    }

    public void run() {
        try {
            synchronized (SYNC) {
                if (!started) {
                    System.out.println("Welcome! Initial Map:");
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            map[i][j] = ' ';
                        }
                    }
                    map[4][4] = 'X';
                    started = true;
                    printMap();
                }
            }
            readFile();
        } catch (Exception e) {
            System.out.println("Error, invalid input!");
            e.printStackTrace();
        }
    }

    public void readFile() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                int move = Integer.parseInt(line);
                move(move);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            System.out.println("Error, invalid input!");
            e.printStackTrace();
        }
    }

    public void move(int move) {
        synchronized (SYNC) {
            switch (move) {
                case 1:
                    map[currentRow][currentColumn] = ' ';
                    if (currentColumn == 0) {
                        currentColumn = 9;
                    } else {
                        currentColumn--;
                    }
                    map[currentRow][currentColumn] = 'X';
                    System.out.println("Move number: " + moveNumber);
                    System.out.println("Player: " + playerNumber);
                    System.out.println("Move: Left");
                    moveNumber++;
                    printMap();
                    break;
                case 2:
                    map[currentRow][currentColumn] = ' ';
                    if (currentColumn == 9) {
                        currentColumn = 0;
                    } else {
                        currentColumn++;
                    }
                    map[currentRow][currentColumn] = 'X';
                    System.out.println("Move number: " + moveNumber);
                    System.out.println("Player: " + playerNumber);
                    System.out.println("Move: Right");
                    moveNumber++;
                    printMap();
                    break;
                case 3:
                    map[currentRow][currentColumn] = ' ';
                    if (currentRow == 0) {
                        currentRow = 9;
                    } else {
                        currentRow--;
                    }
                    map[currentRow][currentColumn] = 'X';
                    System.out.println("Move number: " + moveNumber);
                    System.out.println("Player: " + playerNumber);
                    System.out.println("Move: Up");
                    moveNumber++;
                    printMap();
                    break;
                case 4:
                    map[currentRow][currentColumn] = ' ';
                    if (currentRow == 9) {
                        currentRow = 0;
                    } else {
                        currentRow++;
                    }
                    map[currentRow][currentColumn] = 'X';
                    System.out.println("Move number: " + moveNumber);
                    System.out.println("Player: " + playerNumber);
                    System.out.println("Move: Down");
                    moveNumber++;
                    printMap();
                    break;
                default:
                    System.out.println("Error, invalid input!");
            }
        }
    }

    public void printMap() {
        for (int i = 0; i < 10; i++) {
            System.out.print("[");
            for (int j = 0; j < 10; j++) {
                if (j == 9) {
                    System.out.print(map[i][j]);
                } else {
                    System.out.print(map[i][j] + "|");
                }
            }
            System.out.print("]\n");
            System.out.println("---------------------");
        }
    }

    public static void main(String[] args) {
        try {
            MapNavigator[] mapNavigators = {new MapNavigator(1 , "PlayerOneMoves.txt"),
                    new MapNavigator(2 , "PlayerTwoMoves.txt"),
                    new MapNavigator(3 , "PlayerThreeMoves.txt"),
                    new MapNavigator(4 , "PlayerFourMoves.txt")};

            for (int i = 0; i < mapNavigators.length; i++) {
                mapNavigators[i].start();
            }
            for (int i = 0; i < mapNavigators.length; i++) {
                mapNavigators[i].join();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }
}
