import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;

/**
 * A Simple GUI that takes in details from the user and prints a University Card to the Terminal.
 *
 * <p>Purdue University -- CS18000 -- Fall 2022 -- Homework 11 -- Walkthrough</p>
 *
 * @author Purdue CS
 * @version June 13, 2022
 */
public class UniversityCardGUI {

    /**
     * String array listing universities
     */
    private static final String[] universityOptions = {"Purdue University", "Indiana University",
            "University of Illinois", "Ohio University"};

    /**
     * String array listing class standings
     */
    private static final String[] standingOptions = {"Freshman", "Sophomore", "Junior", "Senior"};


    public static void main(String[] args) {
        String name;
        String email;
        String university;
        String classStanding;
        int age;

        showWelcomeMessageDialog();
        name = showNameInputDialog();
        age = showAgeInputDialog();
        email = showEmailInputDialog();
        university = showUniversityInputDialog();
        classStanding = showClassStandingInputDialog();
        showPrintingDetailsDialog();

        System.out.printf("Here are your University details:\n" +
                "University: %s\n" +
                "Name: %s\n" +
                "Email: %s\n" +
                "Class Standing: %s\n" +
                "Age: %d\n", university, name, email, classStanding, age);
    } //main

    /**
     * Displays a Message Dialog with the following text: "Welcome to University Card generator"
     */
    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to University Card generator",
                "University Card", JOptionPane.INFORMATION_MESSAGE);
    } //showWelcomeMessageDialog

    /**
     * Displays an Input dialog asking for the full name of the user. If the given string is {@code null}
     * or empty, an error message is displayed and the dialog is called again.
     *
     * @return {@code String} full name of the user.
     */
    public static String showNameInputDialog() {
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "What is your full name?",
                    "University Card", JOptionPane.QUESTION_MESSAGE);
            if ((name == null) || (name.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!", "University Card",
                        JOptionPane.ERROR_MESSAGE);

            } //end if

        } while ((name == null) || (name.isEmpty()));

        return name;
    } //showNameInputDialog

    /**
     * Displays an Input dialog asking for the age of the user. If the given string is not an {@code int}, {@code null},
     * or empty, an error message is displayed and the dialog is called again.
     *
     * Location: null (put the dialog box in the center of the screen)
     * Message: "What is your age?"
     * Title: "University Card"
     * messageType: QUESTION_MESSAGE
     *
     * @return {@code int} age of the user.
     */
    public static int showAgeInputDialog() {
        int age = 0;
        boolean valid;
        do {
            try {
                do {
                    age = Integer.parseInt(JOptionPane.showInputDialog(null, "What is your age?",
                            "University Card", JOptionPane.QUESTION_MESSAGE));
                    if (age < 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number!", "University Card",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    valid = true;
                } while (age < 0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!", "University Card",
                        JOptionPane.ERROR_MESSAGE);
                valid = false;
            }
        } while (!valid);
        return age;
    } //showAgeInputDialog

    /**
     * Displays an Input dialog asking for the email of the user. If the given string is {@code null}
     * or empty, an error message is displayed and the dialog is called again.
     *
     * Location: null (put the dialog box in the center of the screen)
     * Message: "What is your email?"
     * Title: "University Card"
     * messageType: QUESTION_MESSAGE
     * @return {@code String} email of the user.
     */
    public static String showEmailInputDialog() {
        return JOptionPane.showInputDialog(null, "What is your email?",
                "University Card", JOptionPane.QUESTION_MESSAGE);
    } //showEmailInputDialog

    /**
     * Displays an Input dialog asking the user to select their university from a list. If the selected string is
     * {@code null} or empty, an error message is displayed and the dialog is called again.
     *
     * @return {@code String} university of the user.
     */
    public static String showUniversityInputDialog() {
        String university;
        do {
            university = (String) JOptionPane.showInputDialog(null, "Select your university",
                    "University Card", JOptionPane.QUESTION_MESSAGE, null, universityOptions,
                    universityOptions[0]);
            if (university == null) {
                JOptionPane.showMessageDialog(null, "Choice cannot be empty!", "University Card",
                        JOptionPane.ERROR_MESSAGE);

            } //end if

        } while (university == null);

        return university;
    } //showUniversityInputDialog

    /**
     * Displays an Input dialog asking the user to select their class standing from a list. If the selected string is
     * {@code null} or empty, an error message is displayed and the dialog is called again.
     *
     * Location: null (put the dialog box in the center of the screen)
     * Message: "Please select a valid class standing!"
     * Title: "University Card"
     * messageType: ERROR_MESSAGE
     *
     * @return {@code String} class standing of the user.
     */
    public static String showClassStandingInputDialog() {
        String classStanding;
        do {
            classStanding = (String) JOptionPane.showInputDialog(null,
                    "Select your class standing",  "University Card", JOptionPane.QUESTION_MESSAGE,
                    null, standingOptions, standingOptions[0]);
            if ((classStanding == null) || (classStanding.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Please select a valid class standing!",
                        "University Card", JOptionPane.ERROR_MESSAGE);

            } //end if

        } while ((classStanding == null) || (classStanding.isEmpty()));

        return classStanding;
    } //showClassStandingInputDialog

    /**
     * Displays a Message Dialog with the following text: "Printing Details to Terminal..."
     *
     * showMessageDialog
     *
     * Location: null (put the dialog box in the center of the screen)
     * Message: "Printing Details to Terminal..."
     * Title: "University Card"
     * messageType: INFORMATION_MESSAGE
     *
     */
    public static void showPrintingDetailsDialog() {
        JOptionPane.showMessageDialog(null, "Printing Details to Terminal...",
                "University Card", JOptionPane.INFORMATION_MESSAGE);
    } //showPrintingDetailsDialog
} //UniversityCard
