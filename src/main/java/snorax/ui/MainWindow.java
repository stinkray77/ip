package snorax.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import snorax.Snorax;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Snorax snorax;

    /**
     * Initializes the MainWindow.
     */
    @FXML
    public void initialize() {
        assert scrollPane != null : "ScrollPane should be injected by FXML";
        assert dialogContainer != null : "DialogContainer should be injected by FXML";
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Snorax instance.
     *
     * @param s The Snorax instance to set.
     */
    public void setSnorax(Snorax s) {
        assert s != null : "Snorax instance cannot be null";
        snorax = s;
        showWelcomeMessage();
    }

    private void showWelcomeMessage() {
        String welcome = "Hello! I'm Snorax \nHow can I help you today?";
        dialogContainer.getChildren().add(DialogBox.getSnoraxDialog(welcome));
    }

    /**
     * Handles user input from the text field.
     */
    @FXML
    private void handleUserInput() {
        assert snorax != null : "Snorax must be set before handling user input";

        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response = snorax.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getSnoraxDialog(response));

        userInput.clear();
    }
}
