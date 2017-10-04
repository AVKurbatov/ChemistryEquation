package chemistry_equation;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Created by Александр on 03.10.2017.
 */
public class ChemicalEquationSolverGUI extends Application implements ChemistryEquationMessages{

    public static void main(String[] args) {
        launch(args);
    }

    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 400;
    private static final int TIP_BUTTON_WIDTH = 70;
    private static final String[] MESSAGE_tips = {MESSAGE_tip_0, MESSAGE_tip_1, MESSAGE_tip_2, MESSAGE_tip_3, MESSAGE_tip_4};
    private TextField textFieldWithEquation;
    private Text answerText;


    private int tipNumber = 0;

    public void start(Stage myStage) {
        myStage.setTitle("Chemical equation solver");
        BorderPane commonRoot = new BorderPane();

        Scene myScene = new Scene(commonRoot, MIN_WIDTH, MIN_HEIGHT);
        myStage.setScene(myScene);

        myStage.setMinWidth(MIN_WIDTH);
        myStage.setMinHeight(MIN_HEIGHT);

        VBox infoBox = new VBox();
        infoBox.setSpacing(20.0);

        Label labelGreeting = new Label("Type chemical equation here:");

        textFieldWithEquation = new TextField();
        textFieldWithEquation.setPrefWidth(MIN_WIDTH);
        Button buttonCalculate = new Button("Calculate coefficients");
        buttonCalculate.setPrefWidth(MIN_WIDTH);

        answerText = new Text();
        TextFlow answerTextFlow = new TextFlow(answerText);
        answerTextFlow.setPrefWidth(MIN_WIDTH);

        textFieldWithEquation.setOnAction(new EquationCalculator());
        buttonCalculate.setOnAction(new EquationCalculator());

        infoBox.getChildren().addAll(labelGreeting,
                textFieldWithEquation,
                buttonCalculate,
                answerTextFlow);

        BorderPane tipBox = new BorderPane();

        Text tipText = new Text("Tip: " + MESSAGE_tips[tipNumber]);
        TextFlow tipTextFlow = new TextFlow(tipText);
        tipTextFlow.setPrefWidth(MIN_WIDTH);
        Button tipButton = new Button("Next tip");
        tipButton.setPrefWidth(TIP_BUTTON_WIDTH);

        tipBox.setTop(tipTextFlow);
        tipBox.setRight(tipButton);
        tipButton.setPrefHeight(tipTextFlow.getHeight());
        tipButton.setOnAction((ae)->{
            tipNumber = (tipNumber + 1) % MESSAGE_tips.length;
            tipText.setText("Tip: " + MESSAGE_tips[tipNumber]);
        });

        myScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double sceneWidth = myScene.getWidth();
            buttonCalculate.setPrefWidth(sceneWidth);
            textFieldWithEquation.setPrefWidth(sceneWidth);
            answerTextFlow.setPrefWidth(sceneWidth);
            tipTextFlow.setPrefWidth(sceneWidth);
        });

        commonRoot.setTop(infoBox);
        commonRoot.setBottom(tipBox);
        myStage.show();
    }

    class EquationCalculator implements EventHandler<ActionEvent> {
        public void handle(ActionEvent ae){
            String equation = textFieldWithEquation.getText();
            String answer = "";
            try {
                answer = ChemicalEquationSolverMathPart.solveChemistryEquation(equation);
            }catch (ParsingEquationException e){
                generateAlert("Error in equation", e.toString());
            }catch (ElementsAndSubstancesException | ArrayIndexOutOfBoundsException e){
                generateAlert("Critical error", MESSAGE_critical_error);
            }
            answerText.setText(answer);
        }
        private void generateAlert(String title, String message){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}