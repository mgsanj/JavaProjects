import java.io.*;
import java.util.ArrayList;
/**
 * Project 4 -- Account
 *
 * This class holds emails and passwords for its two
 * children, Seller and Customer.
 *
 * @author Ashish Chenna, 
 *         Sanjana Gadaginmath,
 *         Ian Lam,
 *         Gunyoung Park, lab sec 12
 *
 * @version November 13, 2023
 *
 */
public class Account {
    private String email;
    private String password;
    //create is basically just the same as the constructor
    //for now i've left the method out but we can discuss later
    public Account() { }
    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
    //accounts must be formatted in type,email,password

    public boolean emailInvalid(String email) {
        if ((email == null) || (email.isEmpty())) {
            return true;
        }
        String[] splitted = email.split("@");
        if (splitted.length == 2) {
            String[] afterAt = splitted[1].split("\\.");
            if (afterAt.length <= 1) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
    
    public boolean accountExists(String email) {
        ArrayList<String> emails = new ArrayList<>();
        boolean check = false;
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.csv"))) {
            String  line = br.readLine();
            while (line != null) {
                String[] splitted = line.split(",");
                emails.add(splitted[1]);
                line = br.readLine();
            }
            if (emails.isEmpty()) {
                return false;
            }
            if (emails.contains(email)) {
                check = true;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean verifyAccount(String email, String pass) {
        ArrayList<String> account = new ArrayList<>();
        boolean check = false;
        try (BufferedReader br = new BufferedReader(new FileReader("accounts.csv"))) {
            String line = br.readLine();
            while (line != null) {
                account.add(line);
                line = br.readLine();
            }
 
            for (int i = 0; i < account.size(); i++) {
                String[] splitted = account.get(i).split(",");
                if (email.equals(splitted[1]) && pass.equals(splitted[2])){
                    check = true;
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }

    public String getEmail() {
        return email;
    }
}
