import java.io.File;
import java.io.FileNotFoundException;
/**
 * Project 3: Validator
 * This class records data associated with the generated reports.
 *
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @author Sanjana Gadaginmath
 * @version October 27, 2023
 */
public class Validator {
    public static int checkPrice(int price) throws InvalidPriceException {
        //If the price is greater than or equal to 0, return the price. Otherwise, throw an InvalidPriceException with
        // message "Invalid Price Format: [price]".
        if (price >= 0) {
            return price;
        } else {
            throw new InvalidPriceException("Invalid Price Format: " + price);
        }
    }

    public static int checkValueFormat(String line, String valueType) throws WrongFormatException {
        //Check the given line to ensure it meets the standards of the input file. The valueType parameter refers to
        // one of three options: "MaxValue", "MinValue", "CompanyNumberValue".
        //If the line does not meet the standard set in the template for the given valueType, throw a
        // "WrongFormatException" with the message "Invalid [valueType] error".
        // If the line is correct, return the processed value. For example, given the line "Max:1000" and "MaxValue" as
        // the valueType, return 1000.
        int value = 0;
        switch (valueType){
            case "MaxValue":
                try {
                    value =  Integer.parseInt(line.split(":")[1]);
                } catch (Exception e) {
                    throw new WrongFormatException("Invalid MaxValue error");
                }
                break;
            case "MinValue":
                try {
                    value = Integer.parseInt(line.split(":")[1]);
                } catch (Exception e) {
                    throw new WrongFormatException("Invalid MinValue error");
                }
                break;
            case "CompanyNumberValue":
                try {
                    value = Integer.parseInt(line.split(":")[1]);
                } catch (Exception e) {
                    throw new WrongFormatException("Invalid CompanyNumberValue error");
                }
                break;
        }
        return value;
    }

    public static void checkFile(String fileName) throws FileNotFoundException {
        //If the given file does not exist, then throw a FileNotFoundException with the message "File: [fileName] is
        // invalid".
        try {
            File file = new File(fileName);
            if (!file.isFile()) {
                throw new FileNotFoundException("File: " + fileName + " is invalid");
            }
        } catch (Exception e) {
            throw new FileNotFoundException("File: " + fileName + " is invalid");
        }

    }
}
