package snorax.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Represents a dialog box in the chat interface.
 * User messages are shown on the right, bot messages on the left.
 */
public class DialogBox extends HBox {
    private static final String USER_BUBBLE_STYLE = "-fx-background-color: #89b4fa;"
            + "-fx-background-radius: 18 18 4 18;"
            + "-fx-padding: 10 14 10 14;"
            + "-fx-max-width: 320;";

    private static final String BOT_BUBBLE_STYLE = "-fx-background-color: #313244;"
            + "-fx-background-radius: 18 18 18 4;"
            + "-fx-padding: 10 14 10 14;"
            + "-fx-max-width: 320;";

    private static final String ERROR_BUBBLE_STYLE = "-fx-background-color: #f38ba8;"
            + "-fx-background-radius: 18 18 18 4;"
            + "-fx-padding: 10 14 10 14;"
            + "-fx-max-width: 320;";

    private static final String USER_TEXT_STYLE = "-fx-text-fill: #1e1e2e;"
            + "-fx-font-size: 13px;"
            + "-fx-wrap-text: true;";

    private static final String BOT_TEXT_STYLE = "-fx-text-fill: #cdd6f4;"
            + "-fx-font-size: 13px;"
            + "-fx-wrap-text: true;";

    private static final String ERROR_TEXT_STYLE = "-fx-text-fill: #1e1e2e;"
            + "-fx-font-size: 13px;"
            + "-fx-font-weight: bold;"
            + "-fx-wrap-text: true;";

    private static final String BOT_LABEL_STYLE = "-fx-text-fill: #89b4fa;"
            + "-fx-font-size: 10px;"
            + "-fx-font-weight: bold;";

    private static final String ERROR_LABEL_STYLE = "-fx-text-fill: #f38ba8;"
            + "-fx-font-size: 10px;"
            + "-fx-font-weight: bold;";

    private DialogBox(String message, boolean isUser, boolean isError) {
        Label bubble = createBubble(message, isUser, isError);

        setSpacing(8);
        setPadding(new Insets(2, 0, 2, 0));

        if (isUser) {
            buildUserLayout(bubble);
        } else {
            buildBotLayout(bubble, isError);
        }
    }

    private Label createBubble(String message, boolean isUser, boolean isError) {
        Label bubble = new Label(message);
        if (isUser) {
            bubble.setStyle(USER_BUBBLE_STYLE + USER_TEXT_STYLE);
        } else if (isError) {
            bubble.setStyle(ERROR_BUBBLE_STYLE + ERROR_TEXT_STYLE);
        } else {
            bubble.setStyle(BOT_BUBBLE_STYLE + BOT_TEXT_STYLE);
        }
        bubble.setWrapText(true);
        bubble.setMaxWidth(320);
        return bubble;
    }

    private void buildUserLayout(Label bubble) {
        setAlignment(Pos.CENTER_RIGHT);
        getChildren().add(bubble);
    }

    private void buildBotLayout(Label bubble, boolean isError) {
        String senderLabel = isError ? "⚠ Snorax" : "Snorax";
        String labelStyle = isError ? ERROR_LABEL_STYLE : BOT_LABEL_STYLE;

        Label nameLabel = new Label(senderLabel);
        nameLabel.setStyle(labelStyle);

        VBox botBox = new VBox(2, nameLabel, bubble);
        botBox.setAlignment(Pos.TOP_LEFT);

        setAlignment(Pos.CENTER_LEFT);
        getChildren().add(botBox);
    }

    /**
     * Creates a dialog box for user messages.
     *
     * @param message The user's message.
     * @return A DialogBox for the user.
     */
    public static DialogBox getUserDialog(String message) {
        return new DialogBox(message, true, false);
    }

    /**
     * Creates a dialog box for bot messages.
     *
     * @param message The bot's response.
     * @return A DialogBox for the bot.
     */
    public static DialogBox getSnoraxDialog(String message) {
        boolean isError = message.toLowerCase().contains("error")
                || message.toLowerCase().contains("invalid")
                || message.toLowerCase().contains("don't understand")
                || message.toLowerCase().contains("cannot")
                || message.toLowerCase().contains("please");
        return new DialogBox(message, false, isError);
    }
}
