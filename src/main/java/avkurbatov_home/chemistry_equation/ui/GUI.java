package avkurbatov_home.chemistry_equation.ui;

import avkurbatov_home.chemistry_equation.enums.Language;
import avkurbatov_home.chemistry_equation.math.EquationSolver;
import avkurbatov_home.chemistry_equation.exceptions.ParsingEquationException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import static avkurbatov_home.chemistry_equation.ui.Messenger.MESSENGER;

/**
 * Primary enter point for application
 * */
public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 400;
    private static final int TIP_BUTTON_WIDTH = 70;
    private static final String RB_ENGLISH_NAME = "English";
    private static final String RB_RUSSIAN_NAME = "Русский язык";

    private TextField textFieldWithEquation;
    private Text answerText;

    private Label labelLanguage;
    private Label labelGreeting;
    private Button buttonCalculate;
    private Text tipText;
    private Button tipButton;

    public void start(final Stage myStage) {
        myStage.setTitle( MESSENGER.chemicalEquationSolverTitle() );
        final BorderPane commonRoot = new BorderPane();

        final Scene myScene = new Scene(commonRoot, MIN_WIDTH, MIN_HEIGHT);
        myStage.setScene(myScene);

        myStage.setMinWidth(MIN_WIDTH);
        myStage.setMinHeight(MIN_HEIGHT);

        final VBox infoBox = new VBox();
        infoBox.setSpacing(20.0);

        labelGreeting = new Label(MESSENGER.typeChemicalEquationHere());

        // region Equation text Field
        textFieldWithEquation = new TextField();
        textFieldWithEquation.setPrefWidth(MIN_WIDTH);
        buttonCalculate = new Button(MESSENGER.calculateCoefficients());
        buttonCalculate.setPrefWidth(MIN_WIDTH);

        answerText = new Text();
        TextFlow answerTextFlow = new TextFlow(answerText);
        answerTextFlow.setPrefWidth(MIN_WIDTH);

        textFieldWithEquation.setOnAction(new EquationCalculator());
        buttonCalculate.setOnAction(new EquationCalculator());
        // endregion

        // region Language
        labelLanguage = new Label(MESSENGER.chooseLanguage());
        final RadioButton rbEnglish = new RadioButton(RB_ENGLISH_NAME);
        final RadioButton rbRussian = new RadioButton(RB_RUSSIAN_NAME);
        rbEnglish.setSelected(true);
        rbRussian.setSelected(false);
        final ToggleGroup groupLang = new ToggleGroup();
        rbEnglish.setToggleGroup(groupLang);
        rbRussian.setToggleGroup(groupLang);
        final HBox boxLang = new HBox();
        boxLang.getChildren().addAll(rbEnglish, rbRussian);

        infoBox.getChildren().addAll(labelLanguage,
                                    boxLang,
                                    labelGreeting,
                                    textFieldWithEquation,
                                    buttonCalculate,
                                    answerTextFlow);
        // endregion

        // region Tips
        final BorderPane tipBox = new BorderPane();

        tipText = new Text(MESSENGER.tip() + MESSENGER.getNextTip());
        final TextFlow tipTextFlow = new TextFlow(tipText);
        tipTextFlow.setPrefWidth(MIN_WIDTH);
        tipButton = new Button(MESSENGER.nextTip());
        tipButton.setPrefWidth(TIP_BUTTON_WIDTH);

        tipBox.setTop(tipTextFlow);
        tipBox.setRight(tipButton);
        tipButton.setPrefHeight(tipTextFlow.getHeight());
        tipButton.setOnAction((ae)->{
            tipText.setText(MESSENGER.tip() + MESSENGER.getNextTip());
        });
        // endregion

        myScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double sceneWidth = myScene.getWidth();
            buttonCalculate.setPrefWidth(sceneWidth);
            textFieldWithEquation.setPrefWidth(sceneWidth);
            answerTextFlow.setPrefWidth(sceneWidth);
            tipTextFlow.setPrefWidth(sceneWidth);
        });

        groupLang.selectedToggleProperty().addListener((ae)-> {
            final RadioButton selRb = (RadioButton) groupLang.getSelectedToggle();
            switch (selRb.getText()){
                case RB_ENGLISH_NAME:
                    MESSENGER.setLanguage(Language.ENGLISH);
                    break;
                case RB_RUSSIAN_NAME:
                    MESSENGER.setLanguage(Language.RUSSIAN);
                    break;
            }
            myStage.setTitle( MESSENGER.chemicalEquationSolverTitle() );
            labelGreeting.setText(MESSENGER.typeChemicalEquationHere());
            buttonCalculate.setText(MESSENGER.calculateCoefficients());
            labelLanguage.setText(MESSENGER.chooseLanguage());
            tipButton.setText(MESSENGER.nextTip());
            if (answerText.getText().isEmpty()) {
                tipText.setText(MESSENGER.tip() + MESSENGER.getCurrentTip());
            } else {
                tipText.setText(MESSENGER.tip() + MESSENGER.tipAnswerLanguage());
            }
        });

        commonRoot.setTop(infoBox);
        commonRoot.setBottom(tipBox);
        myStage.show();
    }

    private class EquationCalculator implements EventHandler<ActionEvent> {
        final EquationSolver equationSolver = new EquationSolver();

        public void handle(ActionEvent ae){
            final String equation = textFieldWithEquation.getText();
            String answer = "";
            try {
                answer = equationSolver.solveChemistryEquation(equation);
            } catch (ParsingEquationException e) {
                generateAlert(MESSENGER.errorInEquation(), e.toString());
            } catch (Exception e) {
                generateAlert(MESSENGER.criticalError(), MESSENGER.criticalError());
            }
            answerText.setText(answer);
        }

        private void generateAlert(String title, String message){
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
}