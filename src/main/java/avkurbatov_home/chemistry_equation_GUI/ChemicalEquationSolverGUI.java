package avkurbatov_home.chemistry_equation_GUI;

import avkurbatov_home.chemistry_equation.ChemicalEquationSolverMathPart;
import avkurbatov_home.chemistry_equation.ElementsAndSubstancesException;
import avkurbatov_home.chemistry_equation.ParsingEquationException;
import avkurbatov_home.chemistry_equation.Messages;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ChemicalEquationSolverGUI extends Application{

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

    public void start(Stage myStage) {
        myStage.setTitle( Messages.getMESSAGE_chemical_equation_solver() + " v.1.1" );
        BorderPane commonRoot = new BorderPane();

        Scene myScene = new Scene(commonRoot, MIN_WIDTH, MIN_HEIGHT);
        myStage.setScene(myScene);

        myStage.setMinWidth(MIN_WIDTH);
        myStage.setMinHeight(MIN_HEIGHT);

        VBox infoBox = new VBox();
        infoBox.setSpacing(20.0);

        labelGreeting = new Label(Messages.getMESSAGE_type_chemical_equation_here());

        textFieldWithEquation = new TextField();
        textFieldWithEquation.setPrefWidth(MIN_WIDTH);
        buttonCalculate = new Button(Messages.getMESSAGE_сalculate_coefficients());
        buttonCalculate.setPrefWidth(MIN_WIDTH);

        answerText = new Text();
        TextFlow answerTextFlow = new TextFlow(answerText);
        answerTextFlow.setPrefWidth(MIN_WIDTH);

        textFieldWithEquation.setOnAction(new EquationCalculator());
        buttonCalculate.setOnAction(new EquationCalculator());

        labelLanguage = new Label(Messages.getMESSAGE_choose_language());
        RadioButton rbEnglish = new RadioButton(RB_ENGLISH_NAME);
        RadioButton rbRussian = new RadioButton(RB_RUSSIAN_NAME);
        rbEnglish.setSelected(true); Messages.setLang(0);
        rbRussian.setSelected(false);
        ToggleGroup groupLang = new ToggleGroup();
        rbEnglish.setToggleGroup(groupLang);
        rbRussian.setToggleGroup(groupLang);
        HBox boxLang = new HBox();
        boxLang.getChildren().addAll(rbEnglish, rbRussian);

        infoBox.getChildren().addAll(labelLanguage,
                                    boxLang,
                                    labelGreeting,
                                    textFieldWithEquation,
                                    buttonCalculate,
                                    answerTextFlow);

        BorderPane tipBox = new BorderPane();

        tipText = new Text(Messages.getMESSAGE_tip() + Messages.getMessageNextTip());
        TextFlow tipTextFlow = new TextFlow(tipText);
        tipTextFlow.setPrefWidth(MIN_WIDTH);
        tipButton = new Button(Messages.getMESSAGE_next_tip());
        tipButton.setPrefWidth(TIP_BUTTON_WIDTH);

        tipBox.setTop(tipTextFlow);
        tipBox.setRight(tipButton);
        tipButton.setPrefHeight(tipTextFlow.getHeight());
        tipButton.setOnAction((ae)->{
            tipText.setText(Messages.getMESSAGE_tip() + Messages.getMessageNextTip());
        });

        myScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double sceneWidth = myScene.getWidth();
            buttonCalculate.setPrefWidth(sceneWidth);
            textFieldWithEquation.setPrefWidth(sceneWidth);
            answerTextFlow.setPrefWidth(sceneWidth);
            tipTextFlow.setPrefWidth(sceneWidth);
        });

        groupLang.selectedToggleProperty().addListener((ae)-> {
            RadioButton selRb = (RadioButton) groupLang.getSelectedToggle();
            switch (selRb.getText()){
                case RB_ENGLISH_NAME:
                    Messages.setLang(0);
                    break;
                case RB_RUSSIAN_NAME:
                    Messages.setLang(1);
                    break;
                default:
                    Messages.setLang(0);
            }
            myStage.setTitle( Messages.getMESSAGE_chemical_equation_solver() );
            labelGreeting.setText(Messages.getMESSAGE_type_chemical_equation_here());
            buttonCalculate.setText(Messages.getMESSAGE_сalculate_coefficients());
            labelLanguage.setText(Messages.getMESSAGE_choose_language());
            tipButton.setText(Messages.getMESSAGE_next_tip());
            if(answerText.getText().equals("")){
                tipText.setText(Messages.getMESSAGE_tip() + Messages.getMessageSameTip());
            }else{
                tipText.setText(Messages.getMESSAGE_tip() + Messages.getMessage_tip_answer_language());
            }
            }
        );

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
                generateAlert(Messages.getMESSAGE_error_in_equation(), e.toString());
            }catch (ElementsAndSubstancesException | ArrayIndexOutOfBoundsException e){
                generateAlert(Messages.getMESSAGE_сritical_error(), Messages.getMESSAGE_critical_error() );
            }catch(NoClassDefFoundError e){
                generateAlert(Messages.getMESSAGE_сritical_error(), String.format( Messages.getMESSAGE_error_class_missing(),  e.getMessage()) );
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