package bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginView extends VBox {

    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button loginButton;

    public LoginView() {
        setSpacing(20);
        setPadding(new Insets(35));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #F2F6FC;");

        Label bankName = new Label("PulaTrust Bank");
        bankName.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #17365D;");

        Label slogan = new Label("Simple Banking | Clear Control");
        slogan.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748B;");

        VBox form = new VBox(12);
        form.setPadding(new Insets(25));
        form.setMaxWidth(360);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setStyle(
                "-fx-background-color: white; -fx-background-radius: 12; " +
                "-fx-border-color: #E2E8F0; -fx-border-radius: 12;"
        );

        Label heading = new Label("Customer Login");
        heading.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #17365D;");

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefHeight(38);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefHeight(38);

        loginButton = new Button("LOGIN");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setPrefHeight(42);
        loginButton.setDefaultButton(true);
        loginButton.setStyle(
                "-fx-background-color: #24598D; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 7;"
        );

        form.getChildren().addAll(
                heading,
                new Label("Username"), usernameField,
                new Label("Password"), passwordField,
                loginButton
        );

        Label footer = new Label("PulaTrust Bank | Banking System Project");
        footer.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");
        getChildren().addAll(bankName, slogan, form, footer);
    }

    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return passwordField.getText(); }
    public Button getLoginButton() { return loginButton; }
}
