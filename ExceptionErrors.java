import javax.swing.JOptionPane;

public class ExceptionErrors {

    // This method checks if the input string for the number of players is empty
    // and throws an EmptyInputException if it is.
    public static void checkNumberOfPlayers(String input) throws EmptyInputException {
        if (input.isEmpty()) {
            throw new EmptyInputException("Number of players cannot be empty!");
        }
    }

    // This method displays a message dialog with the given message and an error icon.
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
