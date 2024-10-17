package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.text.DecimalFormat;
public class HelloController {

    @FXML
    private Label BMILabel;
    @FXML
    private Button ButtonCalculate;
    @FXML
    private Button ButtonChange;
    @FXML
    private TextField TextFieldHeight;
    @FXML
    private TextField TextFieldWeight;
    private DecimalFormat df = new DecimalFormat("#.##");
    private boolean isMetric = true;

    @FXML
    void Calculate(ActionEvent event) {
        calculateBMI();
    }

    @FXML
    void Change(ActionEvent event) {
        if (isMetric) {
            try {
                double heightCm = Double.parseDouble(TextFieldHeight.getText());
                double weightKg = Double.parseDouble(TextFieldWeight.getText());

                // Convert height from cm to inches
                double heightInches = heightCm * 0.393701; // cm to inches
                // Convert weight from kg to pounds
                double weightLbs = weightKg * 2.20462; // kg to lbs

                TextFieldHeight.setText(df.format(heightInches));
                TextFieldWeight.setText(df.format(weightLbs));
            } catch (NumberFormatException e) {
                BMILabel.setText("Please enter valid numbers.");
                return;
            }
        } else {
            try {
                double heightInches = Double.parseDouble(TextFieldHeight.getText());
                double weightLbs = Double.parseDouble(TextFieldWeight.getText());
                double heightCm = heightInches / 0.393701;
                double weightKg = weightLbs / 2.20462;

                TextFieldHeight.setText(df.format(heightCm));
                TextFieldWeight.setText(df.format(weightKg));
            } catch (NumberFormatException e) {
                BMILabel.setText("Please enter valid numbers.");
                return;
            }
        }
        isMetric = !isMetric;
        calculateBMI();
    }
    private void calculateBMI() {
        try {
            double height;
            double weight;
            if (isMetric) {
                height = Double.parseDouble(TextFieldHeight.getText()) / 100;
                weight = Double.parseDouble(TextFieldWeight.getText());
            } else {
                double heightInches = Double.parseDouble(TextFieldHeight.getText());
                height = heightInches * 0.0254;
                double weightLbs = Double.parseDouble(TextFieldWeight.getText());
                weight = weightLbs * 0.453592;
            }
            double bmiMetric = weight / (height * height);
            String category = determineBMICategory(bmiMetric);
            BMILabel.setText(df.format(bmiMetric) + " - " + category);
        } catch (NumberFormatException e) {
            BMILabel.setText("Please enter valid numbers.");
        }
    }private String determineBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 24.9) return "Normal";
        if (bmi < 29.9) return "Overweight";
        return "Obese";
    }
    @FXML
    void clearFields() {
        TextFieldHeight.clear();
        TextFieldWeight.clear();
        BMILabel.setText("BMI: ");
    }
    @FXML
    void exitApplication() {
        javafx.application.Platform.exit();
    }
    @FXML
    void showHelp() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("How to use the BMI Calculator");
        alert.setContentText("Enter your height in centimeters or feet/inches and weight in kilograms or pounds, then click 'Calculate BMI' to see your BMI and status.");
        alert.showAndWait();
    }
}

