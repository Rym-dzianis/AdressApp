package ch.makery.address.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.time.LocalDate;

import ch.makery.address.alerts.ErrorAlert;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

/**
 * Dialog to edit details of a person.
 */
public class PersonEditDialogController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField birthdayField;
	@FXML
	private TextField phoneField;

	private Stage dialogStage;
	private Person person;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 *
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 *
	 * @param person
	 */
	public void setPerson(Person person) {
		this.person = person;

		firstNameField.setText(person.getFirstName().getValue());
		lastNameField.setText(person.getLastName().getValue());
		streetField.setText(person.getStreet().getValue());
		postalCodeField.setText(Integer.toString(person.getPostalCode().getValue()));
		cityField.setText(person.getCity().getValue());
		birthdayField.setText(DateUtil.format(person.getBirthday().getValue()));
		birthdayField.setPromptText("dd.mm.yyyy");
		phoneField.setText(person.getPhone().getValue());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.textProperty());
			person.setLastName(lastNameField.textProperty());
			person.setStreet(streetField.textProperty());
			// переделано
			person.setPostalCode(new SimpleIntegerProperty(Integer.parseInt(postalCodeField.getText())));
			person.setCity(cityField.textProperty());
			// переделано
			person.setBirthday(new SimpleObjectProperty<LocalDate>(DateUtil.parse(birthdayField.getText())));
			person.setPhone(phoneField.textProperty());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "Введите Имя!\n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "Введите Фамилия!\n";
		}
		if (streetField.getText() == null || streetField.getText().length() == 0) {
			errorMessage += "Введите улицу!\n";
		}

		if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
			errorMessage += "Введите индекс!\n";
		} else {
			// try to parse the postal code into an int.
			try {
				Integer.parseInt(postalCodeField.getText());
			} catch (NumberFormatException e) {
				errorMessage += "No valid postal code (must be an integer)!\n";
			}
		}

		if (cityField.getText() == null || cityField.getText().length() == 0) {
			errorMessage += "Введите город!\n";
		}

		if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
			errorMessage += "Введите дату рождения!\n";
		} else {
			if (!DateUtil.validDate(birthdayField.getText())) {
				errorMessage += "Не правильная дата рождения. Используйте формат dd.mm.yyyy!\n";
			}
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}
}
