
package bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RegistrationView extends VBox {

    private TextField firstNameField;
    private TextField surnameField;
    private TextField idNumberField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;

    // New login fields
    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    private CheckBox employedCheckBox;
    private Button registerButton;

    public RegistrationView() {

        setSpacing(15);
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: #F2F6FC;");

        Label bankName = new Label("PulaTrust Bank");
        bankName.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #17365D;"
        );

        Label heading = new Label("Customer Registration");
        heading.setStyle("-fx-font-size: 16px;");

        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;"
        );

        // Personal information
        firstNameField = createField("Enter first name");
        surnameField = createField("Enter surname");
        idNumberField = createField("Enter ID number");
        addressField = createField("Enter address");
        phoneField = createField("Enter phone number");
        emailField = createField("Enter email address");

        employedCheckBox = new CheckBox("Customer is employed");

        // Login information
        usernameField = createField("Create username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Create password");

        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");

        registerButton = new Button("REGISTER CUSTOMER");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.setPrefHeight(42);
        registerButton.setStyle(
                "-fx-background-color: #24598D;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 7;"
        );

        form.getChildren().addAll(
                new Label("First Name"),
                firstNameField,

                new Label("Surname"),
                surnameField,

                new Label("ID Number"),
                idNumberField,

                new Label("Address"),
                addressField,

                new Label("Phone Number"),
                phoneField,

                new Label("Email Address"),
                emailField,

                employedCheckBox,

                new Label("Login Details"),
                new Label("Username"),
                usernameField,

                new Label("Password"),
                passwordField,

                new Label("Confirm Password"),
                confirmPasswordField,

                registerButton
        );

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(
                bankName,
                heading,
                scrollPane
        );
    }

    private TextField createField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefHeight(36);
        return field;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getSurname() {
        return surnameField.getText();
    }

    public String getIdNumber() {
        return idNumberField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public boolean isEmployed() {
        return employedCheckBox.isSelected();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {

        return passwordField.getText();
    }

    public String getConfirmPassword() {
        return confirmPasswordField.getText();
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}
