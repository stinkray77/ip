package snorax.ui;

import snorax.Snorax;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/snorax.png"));
    private Image pigImage = new Image(this.getClass().getResourceAsStream("/images/pig.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setSnorax(Snorax s) {
        snorax = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = snorax.getResponse(input);
        addDialogBoxes(
            DialogBox.getUserDialog(input, userImage);
            DialogBox.getSnoraxDialog(response, pigImage));
        userInput.clear();
    }

    /**
     * Adds dialog boxes to the dialog container using varargs.
     *
     * @param dialogBoxes The dialog boxes to add.
     */
    private void addDialogBoxes(Node... dialogBoxes) {
        dialogContainer.getChildren().addAll(dialogBoxes);
    }
}
