package bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AccountView extends VBox {

    private TextField accountField;
    private TextField amountField;

    private Button depositButton;
    private Button withdrawButton;
    private Button balanceButton;
    private Button historyButton;
    private Button registerButton;
    private Button openAccountButton;
    private Button interestButton;

    private Label balanceLabel;
    private Label messageLabel;

    public AccountView() {
        setSpacing(15);
        setPadding(new Insets(25));
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: #F2F6FC;");

        Label bankName = new Label("PulaTrust Bank");
        bankName.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #17365D;"
        );

        Label heading = new Label("Customer Banking Dashboard");
        heading.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #64748B;"
        );

        VBox accountPanel = new VBox(10);
        accountPanel.setPadding(new Insets(20));
        accountPanel.setMaxWidth(450);
        accountPanel.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;"
        );

        Label accountTitle = new Label("Account Details");
        accountTitle.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        accountField = new TextField();
        accountField.setPromptText("Enter account number");
        accountField.setPrefHeight(38);

        amountField = new TextField();
        amountField.setPromptText("Enter amount in Pula");
        amountField.setPrefHeight(38);

        accountPanel.getChildren().addAll(
                accountTitle,
                new Label("Account Number"),
                accountField,
                new Label("Amount (BWP)"),
                amountField
        );

        depositButton = createButton("Deposit");
        withdrawButton = createButton("Withdraw");
        balanceButton = createButton("View Balance");
        historyButton = createButton("View Transactions");
        registerButton = createButton("Register Customer");
        openAccountButton = createButton("Open Account");
        interestButton = createButton("Pay Interest");

        HBox row1 = createRow(depositButton, withdrawButton);
        HBox row2 = createRow(balanceButton, historyButton);
        HBox row3 = createRow(registerButton, openAccountButton);

        VBox resultsPanel = new VBox(10);
        resultsPanel.setPadding(new Insets(20));
        resultsPanel.setAlignment(Pos.CENTER);
        resultsPanel.setMaxWidth(450);
        resultsPanel.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;"
        );

        balanceLabel = new Label("Balance: --");
        balanceLabel.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #17365D;"
        );

        messageLabel = new Label("Welcome to PulaTrust Bank");
        messageLabel.setWrapText(true);
        resultsPanel.getChildren().addAll(balanceLabel, messageLabel);

        getChildren().addAll(
                bankName,
                heading,
                accountPanel,
                row1,
                row2,
                row3,
                interestButton,
                resultsPanel
        );
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(190);
        button.setPrefHeight(42);
        button.setStyle(
                "-fx-background-color: #24598D;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7;"
        );
        return button;
    }

    private HBox createRow(Button first, Button second) {
        HBox row = new HBox(10, first, second);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    public String getAccountNumber() {
        return accountField.getText();
    }

    public String getAmount() {
        return amountField.getText();
    }

    public Button getDepositButton() {
        return depositButton;
    }

    public Button getWithdrawButton() {
        return withdrawButton;
    }

    public Button getBalanceButton() {
        return balanceButton;
    }

    public Button getHistoryButton() {
        return historyButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public Button getOpenAccountButton() {
        return openAccountButton;
    }

    public Button getInterestButton() {
        return interestButton;
    }

    public void showBalance(String balance) {
        balanceLabel.setText("Balance: P" + balance);
    }

    public void showMessage(String message) {
        messageLabel.setText(message);
    }
}
