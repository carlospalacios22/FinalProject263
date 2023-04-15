// ExceptionErrors.java

import javax.swing.JOptionPane;

public class ExceptionErrors {
    public static void checkNumberOfPlayers(String input) throws EmptyInputException {
        if (input.isEmpty()) {
            throw new EmptyInputException("Number of players cannot be empty!");
        }
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

