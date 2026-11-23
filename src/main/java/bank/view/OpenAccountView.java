package bank.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OpenAccountView extends VBox {

    private final TextField customerIdField;
    private final TextField branchField;
    private final ComboBox<String> accountTypeBox;
    private final TextField openingAmountField;
    private final TextField employerNameField;
    private final TextField employerAddressField;
    private final Button openButton;

    public OpenAccountView() {
        setSpacing(15);
        setPadding(new Insets(18));
        setAlignment(Pos.TOP_CENTER);
        setStyle("-fx-background-color: #F2F6FC;");

        Label bankName = new Label("PulaTrust Bank");
        bankName.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #17365D;");

        Label heading = new Label("Open a New Bank Account");
        heading.setStyle("-fx-font-size: 15px; -fx-text-fill: #64748B;");

        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        customerIdField = createField("Enter customer ID");
        branchField = createField("Enter branch");

        accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Savings", "Investment", "Cheque");
        accountTypeBox.setValue("Savings");
        accountTypeBox.setMaxWidth(Double.MAX_VALUE);

        openingAmountField = createField("Opening amount in Pula");
        openingAmountField.setText("0");

        employerNameField = createField("Enter employer name");
        employerAddressField = createField("Enter employer address");
        employerNameField.setDisable(true);
        employerAddressField.setDisable(true);

        accountTypeBox.setOnAction(e -> {
            boolean cheque = "Cheque".equals(accountTypeBox.getValue());
            employerNameField.setDisable(!cheque);
            employerAddressField.setDisable(!cheque);
        });

        Label note = new Label("Employer details are required for Cheque accounts.");
        note.setWrapText(true);
        note.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748B;");

        openButton = new Button("OPEN BANK ACCOUNT");
        openButton.setMaxWidth(Double.MAX_VALUE);
        openButton.setPrefHeight(42);
        openButton.setStyle(
                "-fx-background-color: #24598D; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-background-radius: 7;"
        );

        form.getChildren().addAll(
                new Label("Customer ID"), customerIdField,
                new Label("Branch"), branchField,
                new Label("Account Type"), accountTypeBox,
                new Label("Opening Amount (BWP)"), openingAmountField,
                new Label("Employer Name"), employerNameField,
                new Label("Employer Address"), employerAddressField,
                note, openButton
        );

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        getChildren().addAll(bankName, heading, scrollPane);
    }

    private TextField createField(String hint) {
        TextField field = new TextField();
        field.setPromptText(hint);
        field.setPrefHeight(36);
        return field;
    }

    public String getCustomerId() { return customerIdField.getText(); }
    public String getBranch() { return branchField.getText(); }
    public String getAccountType() { return accountTypeBox.getValue(); }
    public String getOpeningAmount() { return openingAmountField.getText(); }
    public String getEmployerName() { return employerNameField.getText(); }
    public String getEmployerAddress() { return employerAddressField.getText(); }
    public Button getOpenButton() { return openButton; }
}
