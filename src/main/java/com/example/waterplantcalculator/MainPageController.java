package com.example.waterplantcalculator;

import Classes.Measurement;
import Classes.WaterFilledRectangle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    //Intake parameters
    Double inputMinimum = 2.0;
    Double inputVolumeCurrent = 0.0;
    Double inputMaximum = 7.0;
    //Clearwell parameters
    Double clearWellMinimum = 14.0;
    Double clearWellCurrent = 0.0;
    Double clearWellMaximum = 18.1;
    Double clearWellVolume = 7.0;
    Double clearWellPerFoot = (clearWellVolume / clearWellMaximum);
    //West highlifts
    Double pumpTimeLeftInMinutes = 0.0;
    Double westOutputMin = 0.0;
    Double westVolumeCurrent = 0.0;
    Double westOutputMax = 9.9;
    //East highlifts
    Double eastOutputMin = 0.0;
    Double eastVolumeCurrent = 0.0;
    Double eastOutputMax = 9.9;
    //Tower 1
    Double tower1SecondsTillDone = 0.0;
    Double tower1Maximum = 29.0;
    Double tower1Current = 0.0;
    Double tower1Minimum = 15.0;
    //Tower 2
    Double tower2SecondsTillDone = 0.0;
    Double tower2Maximum = 29.5;
    Double tower2Current = 0.0;
    Double tower2Minimum = 15.0;
    //Tower 3
    Double tower3SecondsTillDone = 0.0;
    Double tower3Maximum = 29.5;
    Double tower3Current = 0.0;
    Double tower3Minimum = 14.0;

    WaterFilledRectangle inputPipe;
    WaterFilledRectangle clearWell;
    WaterFilledRectangle tower1;
    WaterFilledRectangle tower2;
    WaterFilledRectangle tower3;

    @FXML
    private TextField clearWellHeightTxtBox;

    @FXML
    private TextField inputVolumeTxtBox;

    @FXML
    private TextField westOutputTxt;

    @FXML
    private TextField eastOutputTxt;
    @FXML
    private TextField tower1HeightTxt;

    @FXML
    private TextField tower2HeightTxt;

    @FXML
    private TextField tower3HeightTxt;
    @FXML
    private TextField pumpTimeLeftTxt;
    @FXML
    private Rectangle clearWellAir;

    @FXML
    private Rectangle clearWellWater;

    @FXML
    private Rectangle inputPipeAir;

    @FXML
    private Rectangle inputPipeWater;

    @FXML
    private Rectangle tower1Air;

    @FXML
    private Rectangle tower1Water;

    @FXML
    private Rectangle tower2Air;

    @FXML
    private Rectangle tower2Water;

    @FXML
    private Rectangle tower3Air;

    @FXML
    private Rectangle tower3Water;

    @FXML
    private Label clearWellMinLbl;

    @FXML
    private Label clearWellMaxLbl;

    @FXML
    private Label inputMinLbl;

    @FXML
    private Label inputMaxLbl;

    @FXML
    private Label tower1MinLbl;

    @FXML
    private Label tower1MaxLbl;

    @FXML
    private Label tower2MinLbl;

    @FXML
    private Label tower2MaxLbl;

    @FXML
    private Label tower3MinLbl;

    @FXML
    private Label tower3MaxLbl;

    @FXML
    private Label westOutputMaxLbl;

    @FXML
    private Label westOutputMinLbl;
    @FXML
    private Label eastOutputMaxLbl;
    @FXML
    private Label eastOutputMinLbl;
    @FXML
    private Label clearWellTimeLeftTxt;
    @FXML
    private Label tower1TimeToDoneLbl;
    @FXML
    private Label tower2TimeToDoneLbl;
    @FXML
    private Label tower3TimeToDoneLbl;
    @FXML
    private ChoiceBox<Measurement> choiceTower1Measurements;
    @FXML
    private ChoiceBox<Measurement> choiceTower2Measurements;
    @FXML
    private ChoiceBox<Measurement> choiceTower3Measurements;
    @FXML
    protected void onActionCalculateClearWell(){
        checkClearWellHeight();
        checkInputVolume();
        checkEastOutput();
        checkWestOutput();
        checkPumpTimeLeft();
        clearWellTimeLeftTxt.setText("Time Until Full\n??? Minutes");
        if (checkWestOutput() && checkEastOutput() && checkInputVolume() && checkClearWellHeight() && checkPumpTimeLeft()){
            Double netFlow =((inputVolumeCurrent - (westVolumeCurrent + eastVolumeCurrent)) / 1440);
            if (pumpTimeLeftInMinutes < 1) {
                System.out.println("Pump time less than 1");
                if (netFlow >= 0){
                    System.out.println("netflow greater equal 0");
                    int timeUntilFull = (int)(((clearWellMaximum - clearWellCurrent) * clearWellPerFoot) / (netFlow));
                    clearWellTimeLeftTxt.setText("Time Until Full\n " + timeUntilFull + " Minutes");
                } else {
                    System.out.println("netflow less than 0");
                    int timeUntilEmpty = (int)(((clearWellCurrent - clearWellMinimum) * clearWellPerFoot) / -(netFlow));
                    clearWellTimeLeftTxt.setText("Time Until Empty\n " + timeUntilEmpty + " Minutes");
                }
            } else {
                System.out.println("Pump time greater than 1");
                if (netFlow >= 0) {
                    System.out.println("netflow greater equal 0");
                    if ((netFlow * pumpTimeLeftInMinutes / 1440) > ((clearWellCurrent - clearWellMinimum) * clearWellPerFoot)){
                        System.out.println("full before pump done");
                        int timeUntilFull = (int)(((clearWellCurrent - clearWellMinimum) * clearWellPerFoot) / netFlow);
                        clearWellTimeLeftTxt.setText("Time Until Full\n " + timeUntilFull + " Minutes");
                    } else {
                        System.out.println("Not full before pump done");
                        int timeUntilFull = (int)((((clearWellMaximum - clearWellCurrent) * clearWellPerFoot) - (netFlow * pumpTimeLeftInMinutes)) / (inputVolumeCurrent / 1440));
                        timeUntilFull = timeUntilFull + (int)Math.round(pumpTimeLeftInMinutes);
                        clearWellTimeLeftTxt.setText("Time Until Full\n " + timeUntilFull + " Minutes");
                    }
                } else {
                    System.out.println("netflow less than 0");
                    int timeUntilFull = (int)(((clearWellMaximum - clearWellCurrent) * clearWellPerFoot) / (inputVolumeCurrent / 1440));
                    timeUntilFull = timeUntilFull + (int)(((westVolumeCurrent + eastVolumeCurrent) * pumpTimeLeftInMinutes) / inputVolumeCurrent);
                    clearWellTimeLeftTxt.setText("Time Until Full\n " + timeUntilFull + " Minutes");
                }
            }
        }
    }
    private boolean checkClearWellHeight() {
        Double inputHeight = null;
        if (!clearWellHeightTxtBox.getText().isEmpty()){
            try {
                inputHeight = Double.parseDouble(clearWellHeightTxtBox.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(inputHeight != null) {

                if (inputHeight > clearWellMaximum || inputHeight < clearWellMinimum){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Clearwell too high/low!");
                    alert.setHeaderText("Clearwell must be set between " + clearWellMinimum + " and " + clearWellMaximum);
                    alert.showAndWait();
                    clearWellHeightTxtBox.setText("");
                    return false;
                }
                clearWellHeightTxtBox.setText(inputHeight.toString());
                clearWellCurrent = inputHeight;
                clearWell.setPercent((inputHeight - clearWellMinimum)/(clearWellMaximum - clearWellMinimum));
                return true;
            }
        }
        clearWell.setPercent(0.0);
        clearWellHeightTxtBox.setText("");
        return false;
    }
    private boolean checkInputVolume() {
        Double inputVolume = null;
        if (!inputVolumeTxtBox.getText().isEmpty()){
            try {
                inputVolume = Double.parseDouble(inputVolumeTxtBox.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(inputVolume != null) {

                if (inputVolume > inputMaximum || inputVolume < inputMinimum){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Input flow to0 high/low");
                    alert.setHeaderText("Input must be set between " + inputMinimum + " and " + inputMaximum);
                    alert.showAndWait();
                    inputVolumeTxtBox.setText("");
                    return false;
                }
                inputVolumeTxtBox.setText(inputVolume.toString());
                inputVolumeCurrent = inputVolume;
                inputPipe.setPercent((inputVolume - inputMinimum) / (inputMaximum - inputMinimum));
                return true;
            }
        }
        inputPipe.setPercent(0.0);
        inputVolumeTxtBox.setText("");
        return false;
    }
    private boolean checkEastOutput(){
        Double eastVolume = null;
        if (!eastOutputTxt.getText().isEmpty()){
            try {
                eastVolume = Double.parseDouble(eastOutputTxt.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(eastVolume != null) {
                if (eastVolume> eastOutputMax || eastVolume < eastOutputMin){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("East flow to high/low");
                    alert.setHeaderText("East flow must be set between " + eastOutputMin + " and " + eastOutputMax);
                    alert.showAndWait();
                    eastOutputTxt.setText("0.0");
                    return false;
                }
                eastOutputTxt.setText(eastVolume.toString());
                eastVolumeCurrent = eastVolume;
                return true;
            }
        }
        eastOutputTxt.setText("0.0");
        return false;
    }
    private boolean checkWestOutput() {
        Double westVolume = null;
        if (!westOutputTxt.getText().isEmpty()){
            try {
                westVolume = Double.parseDouble(westOutputTxt.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(westVolume != null) {
                if (westVolume> westOutputMax || westVolume < westOutputMin){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("West flow to high/low");
                    alert.setHeaderText("West flow must be set between " + westOutputMin + " and " + westOutputMax);
                    alert.showAndWait();
                    westOutputTxt.setText("0.0");
                    return false;
                }
                westOutputTxt.setText(westVolume.toString());
                westVolumeCurrent = westVolume;
                return true;
            }
        }
        westOutputTxt.setText("0.0");
        return false;
    }
    private boolean checkPumpTimeLeft(){
        if (pumpTimeLeftTxt.getText().isEmpty()) {
            pumpTimeLeftTxt.setText("0.0");
            pumpTimeLeftInMinutes = 0.0;
            return true;
        }
        Double timeLeft = 0.0;
        try {
            timeLeft = Double.parseDouble(pumpTimeLeftTxt.getText());
        } catch (NumberFormatException e) {
        } catch (NullPointerException e) {
        }
        if (timeLeft < 0.0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Time left on pumps cannot be negative.");
            alert.setHeaderText("Approximate pump time left in minutes must be set to a positive number");
            alert.showAndWait();
            pumpTimeLeftTxt.setText("0.0");
            return false;
        } else {
            pumpTimeLeftTxt.setText(timeLeft.toString());
            pumpTimeLeftInMinutes = timeLeft;
            return true;
        }

    }
    @FXML
    protected boolean onActionAcceptTower1(){
        Double inputHeight = null;
        if (!tower1HeightTxt.getText().isBlank()){
            try {
                inputHeight = Double.parseDouble(tower1HeightTxt.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(inputHeight != null) {

                if (inputHeight > tower1Maximum || inputHeight < tower1Minimum){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tower 1 too high/low!");
                    alert.setHeaderText("Tower 1 must be set between " + tower1Minimum + " and " + tower1Maximum);
                    alert.showAndWait();
                    tower1HeightTxt.setText("");
                    return false;
                }
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Tower 1 Accept Reading");
                alert.setHeaderText("Please Confirm Tower 1 is currently reading " + inputHeight + " ft");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    tower1HeightTxt.setText(inputHeight.toString());
                    tower1Current = inputHeight;
                    Double towerpercent = ((inputHeight - tower1Minimum)/(tower1Maximum - tower1Minimum));
                    tower1.setPercent(towerpercent);
                    choiceTower1Measurements.getItems().add(new Measurement(tower1Current, towerpercent, LocalDateTime.now()));
                    predictTower1();
                    return true;
                }
                return false;
            }
        }
        tower1.setPercent(0.0);
        tower1HeightTxt.setText("");
        return false;
    }
    @FXML
    protected boolean onActionAcceptTower2(){
        Double inputHeight = null;
        if (!tower2HeightTxt.getText().isBlank()){
            try {
                inputHeight = Double.parseDouble(tower2HeightTxt.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(inputHeight != null) {
                if (inputHeight > tower2Maximum || inputHeight < tower2Minimum){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tower 1 too high/low!");
                    alert.setHeaderText("Tower 1 must be set between " + tower2Minimum + " and " + tower2Maximum);
                    alert.showAndWait();
                    tower2HeightTxt.setText("");
                    return false;
                }
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Tower 1 Accept Reading");
                alert.setHeaderText("Please Confirm Tower 1 is currently reading " + inputHeight + " ft");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    tower2HeightTxt.setText(inputHeight.toString());
                    tower2Current = inputHeight;
                    Double towerpercent = ((inputHeight - tower2Minimum)/(tower2Maximum - tower2Minimum));
                    tower2.setPercent(towerpercent);
                    choiceTower2Measurements.getItems().add(new Measurement(tower2Current, towerpercent, LocalDateTime.now()));
                    predictTower2();
                    return true;
                }
                return false;
            }
        }
        tower2.setPercent(0.0);
        tower2HeightTxt.setText("");
        return false;
    }
    @FXML
    protected boolean onActionAcceptTower3(){
        Double inputHeight = null;
        if (!tower3HeightTxt.getText().isBlank()){
            try {
                inputHeight = Double.parseDouble(tower3HeightTxt.getText());
            } catch (NumberFormatException e) {
            } catch (NullPointerException e) {
            }
            if(inputHeight != null) {

                if (inputHeight > tower3Maximum || inputHeight < tower3Minimum){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tower 1 too high/low!");
                    alert.setHeaderText("Tower 1 must be set between " + tower3Minimum + " and " + tower3Maximum);
                    alert.showAndWait();
                    tower3HeightTxt.setText("");
                    return false;
                }
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Tower 1 Accept Reading");
                alert.setHeaderText("Please Confirm Tower 1 is currently reading " + inputHeight + " ft");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    tower3HeightTxt.setText(inputHeight.toString());
                    tower3Current = inputHeight;
                    Double towerpercent = ((inputHeight - tower3Minimum)/(tower3Maximum - tower3Minimum));
                    tower3.setPercent(towerpercent);
                    choiceTower3Measurements.getItems().add(new Measurement(tower3Current, towerpercent, LocalDateTime.now()));
                    predictTower3();
                    return true;
                }

                return false;
            }
        }
        tower3.setPercent(0.0);
        tower3HeightTxt.setText("");
        return false;
    }
    public void predictTower1(){
        int index = choiceTower1Measurements.getItems().size();
        if (index > 1){
            Measurement newest = choiceTower1Measurements.getItems().get(index - 1);
            Measurement older = choiceTower1Measurements.getItems().get(index - 2);
            if (newest.getHeight() >= older.getHeight()){
                Double oldVol = volumeOfSphereAtHeight(15, tower1Maximum - older.getHeight());
                Double newVol = volumeOfSphereAtHeight(15, tower1Maximum - tower1Current);
                Double unitsPerSecond = (oldVol - newVol) / (older.getTime().until(newest.getTime(), ChronoUnit.SECONDS));
                Double secondsToGo = newVol / unitsPerSecond;
                tower1SecondsTillDone = secondsToGo;
                tower1TimeToDoneLbl.setText("Minutes Until Full: " + (int)(tower1SecondsTillDone / 60));
            } else {
                Double oldVol = volumeOfSphereAtHeight(15, tower1Maximum - older.getHeight());
                Double newVol = volumeOfSphereAtHeight(15, tower1Maximum - tower1Current);
                Double unitsPerSecond = (newVol - oldVol) / (older.getTime().until(newest.getTime(), ChronoUnit.SECONDS));
                Double secondsToGo = (volumeOfSphereAtHeight(15, 15) - newVol) / unitsPerSecond;
                tower1SecondsTillDone = null;

                tower1TimeToDoneLbl.setText("Minutes Until Empty: " + (int)(secondsToGo / 60));
            }
        }
    }
    public void predictTower2(){
        int index = choiceTower2Measurements.getItems().size();
        if (index > 1){
            Measurement newest = choiceTower2Measurements.getItems().get(index - 1);
            Measurement older = choiceTower2Measurements.getItems().get(index - 2);
            if (newest.getHeight() >= older.getHeight()){
                Double oldVol = volumeOfSphereAtHeight(15, tower2Maximum - older.getHeight());
                Double newVol = volumeOfSphereAtHeight(15, tower2Maximum - tower2Current);
                Double unitsPerSecond = (oldVol - newVol) / (older.getTime().until(newest.getTime(), ChronoUnit.SECONDS));
                Double secondsToGo = newVol / unitsPerSecond;
                tower2SecondsTillDone = secondsToGo;
                tower2TimeToDoneLbl.setText("Minutes Until Full: " + (int)(tower2SecondsTillDone / 60));
            } else {
                Double oldVol = volumeOfSphereAtHeight(15, tower2Maximum - older.getHeight());
                Double newVol = volumeOfSphereAtHeight(15, tower2Maximum - tower2Current);
                Double unitsPerSecond = (newVol - oldVol) / (older.getTime().until(newest.getTime(), ChronoUnit.SECONDS));
                Double secondsToGo = (volumeOfSphereAtHeight(15, 15) - newVol) / unitsPerSecond;
                tower2SecondsTillDone = null;
                tower2TimeToDoneLbl.setText("Minutes Until Empty: " + (int)(secondsToGo / 60));
            }
        }
    }
    public void predictTower3(){
        int index = choiceTower3Measurements.getItems().size();
        if (index > 1){
            Measurement newest = choiceTower3Measurements.getItems().get(index - 1);
            Measurement older = choiceTower3Measurements.getItems().get(index - 2);
            if (newest.getHeight() >= older.getHeight()){
                Double oldVol = volumeOfOddSphereAtHeight(15, tower3Maximum - older.getHeight());
                Double newVol = volumeOfOddSphereAtHeight(15, tower3Maximum - tower3Current);
                Double unitsPerSecond = (oldVol - newVol) / (older.getTime().until(newest.getTime(), ChronoUnit.SECONDS));
                Double secondsToGo = newVol / unitsPerSecond;
                tower3SecondsTillDone = secondsToGo;
                tower3TimeToDoneLbl.setText("Minutes Until Full: " + (int)(tower3SecondsTillDone / 60));
            } else {
                Double oldVol = volumeOfOddSphereAtHeight(15, tower3Maximum - older.getHeight());
                Double newVol = volumeOfOddSphereAtHeight(15, tower3Maximum - tower3Current);
                Double unitsPerSecond = (newVol - oldVol) / (older.getTime().until(newest.getTime(), ChronoUnit.SECONDS));
                Double secondsToGo = (volumeOfOddSphereAtHeight(15, 15) - newVol) / unitsPerSecond;
                tower3SecondsTillDone = null;
                tower3TimeToDoneLbl.setText("Minutes Until Empty: " + (int)(secondsToGo / 60));
            }
        }
    }

    public Double volumeOfSphereAtHeight(int rad, double height){
        Double top = (Math.PI/3) * height * height * ((3 * rad) - height);
        System.out.println(top);
        return top;
    }
    public Double volumeOfOddSphereAtHeight(int rad, double height){
        Double top = ((Math.PI/3) * height * height * ((3 * rad) - height)) + (Math.PI * rad * height);
        System.out.println(top);
        return top;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputPipe = new WaterFilledRectangle(inputPipeAir, inputPipeWater);
        clearWell = new WaterFilledRectangle(clearWellAir, clearWellWater);
        tower1 = new WaterFilledRectangle(tower1Air, tower1Water);
        tower2 = new WaterFilledRectangle(tower2Air, tower2Water);
        tower3 = new WaterFilledRectangle(tower3Air, tower3Water);
        clearWellMinLbl.setText(clearWellMinimum.toString() + " ft");
        clearWellMaxLbl.setText(clearWellMaximum.toString() + " ft");
        inputMinLbl.setText(inputMinimum.toString() + " MGD");
        inputMaxLbl.setText(inputMaximum.toString() + " MGD");
        tower1MaxLbl.setText(tower1Maximum.toString() + " ft");
        tower1MinLbl.setText(tower1Minimum.toString() + " ft");
        tower2MaxLbl.setText(tower2Maximum.toString() + " ft");
        tower2MinLbl.setText(tower2Minimum.toString() + " ft");
        tower3MaxLbl.setText(tower3Maximum.toString() + " ft");
        tower3MinLbl.setText(tower3Minimum.toString() + " ft");
        westOutputMaxLbl.setText(westOutputMax.toString() + " MGD");
        westOutputMinLbl.setText(westOutputMin.toString() + " MGD");
        eastOutputMaxLbl.setText(eastOutputMax.toString() + " MGD");
        eastOutputMinLbl.setText(eastOutputMin.toString() + " MGD");
        onActionCalculateClearWell();
        onActionAcceptTower1();
        ObservableList<Measurement> tower1Measurements = FXCollections.observableArrayList();
        choiceTower1Measurements.setItems(tower1Measurements);
        onActionAcceptTower2();
        onActionAcceptTower3();


    }







}