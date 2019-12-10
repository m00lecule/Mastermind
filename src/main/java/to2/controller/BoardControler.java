package to2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import to2.BoardElements.Color;
import to2.BoardElements.Row;
import to2.model.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Mastermind board controller - manipulates view adding more rows to the board.
 */
public class BoardControler {

    //mocked data model
    private int rowsNumber = 8;

    private int colorsNumer = 7;

    private Game game = new Game(4, rowsNumber, colorsNumer);

    @FXML
    private VBox vboxCentral;

    @FXML
    private Button nextStep;

    @FXML
    private Button resetButton;

    private List<Row> rowsList = new LinkedList<>();

    private ListIterator<Row> it;
    private Row currentRow;

    private void showPopup(String content, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); //TODO: custom graphic (setGraphic)
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.show();
    }

    @FXML
    private void handleNextStepAction(ActionEvent event) {
        currentRow.setDisable(true);

        List<Color> guesses = currentRow.getGuesses();

        List<Color> result = game.compareSequence(guesses);

        currentRow.updateCircles(result);

        if (game.wonGame()) {
            showPopup("Your score: " + game.getScore(), "You won!", "Congratulations!");
        } else {
            if (it.hasPrevious()) {
                currentRow = it.previous();
                currentRow.setDisable(false);
            } else {
                System.out.println(game.getScore());
                showPopup("Your score: 0", "You lost", "Not congratulations.");
            }
        }
    }

    @FXML
    private void handleResetAction(ActionEvent event) {
        this.game.reset();
        vboxCentral.getChildren().clear();
        rowsList.clear();
        initialize();
    }

    @FXML
    private void initialize() {

        for (int rows = 0; rows < rowsNumber; rows++) {
            Row r = new Row(true);
            rowsList.add(r);
            vboxCentral.getChildren().add(r);
        }

        it = rowsList.listIterator(rowsList.size());
        currentRow = it.previous();
        currentRow.setDisable(false);
    }

    public int getData() {
        return rowsNumber;
    }

    public void setData(int rowsNumber) {
        this.rowsNumber = rowsNumber;
    }
}



