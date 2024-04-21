import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Midterm 2: VideoGameProfilers
 * In this class, we are reading profile data from a text file, taking input from the user for certain filtering
 * criteria, and then writing the filtered list of profile data to another file.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 30, 2023
 */
public class VideoGameProfiler {
    public static final String INPUT_PROMPT = "What is the name of the file you would like to read from?";
    public static final String INPUT_ERROR = "The file doesn't exist!";
    public static final String OUTPUT_PROMPT = "What is the name of the output file?";

    public static final String THRESHOLD_PROMPT = "Enter the ratings threshold filter:";

    public static final String OUTPUT_SUCCESS = "The file was written to!";

    public static final String OUTPUT_ERROR = "There was an error writing to the file.";
    public static String[] readFile(String filename) {
        try {
            ArrayList<String> data = new ArrayList<String>();
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                data.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            String[] arrLines = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                arrLines[i] = data.get(i);
            }
            return arrLines;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeFile(String[] dataset, double threshold, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < dataset.length; i++) {
                double rating = Double.valueOf(dataset[i].split(", ")[3]);
                if (rating >= threshold) {
                    writer.println(dataset[i]);
                }
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(INPUT_PROMPT);
        String inFileName = scan.nextLine();
        try {
            File checkFile = new File(inFileName);
            if (!checkFile.isFile()) {
                System.out.println(INPUT_ERROR);
                String[] data = readFile(inFileName);
            } else {
                String[] data = readFile(inFileName);
                System.out.println(THRESHOLD_PROMPT);
                double threshold = scan.nextDouble();
                scan.nextLine();
                System.out.println(OUTPUT_PROMPT);
                String outFileName = scan.nextLine();
                boolean fileWritten = writeFile(data, threshold, outFileName);
                if (fileWritten) {
                    System.out.println(OUTPUT_SUCCESS);
                } else {
                    System.out.println(OUTPUT_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
