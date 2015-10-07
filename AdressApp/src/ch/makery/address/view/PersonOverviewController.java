package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;

import ch.makery.address.MainApp;
import ch.makery.address.alerts.NotSelectedAlert;
import ch.makery.address.model.Person;
import ch.makery.address.repository.PersonRepository;
import ch.makery.address.util.DateUtil;

public class PersonOverviewController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;

	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label birthdayLabel;
	@FXML
	private Label phoneLabel;

	// Reference to the main application.
	private MainApp mainApp;
    private PersonRepository personRepository = PersonRepository.getInstance();

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 *
	 * Инициализация контроллера. Этот метод вызывается автоматически после
	 * загрузки fxml.
	 *
	 */
	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastName());

		// Clear person details.
		showPersonDetails(null);

		// Listen for selection changes and show the person details when
		// changed.
		// берем модель таблицы и выбранное значение, добавляем лисенер и
		// скармливаем интерфейсу лисенера метод showPersonDetails
		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));


	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainApp
	 */
	public void setMainApp(MainApp main) {
		this.mainApp = main;
		// Add observable list data to the table
		personTable.setItems(personRepository.getPersonList());
	}

	/**
	 * Заполнение полей персоны. Если передан null все поля будут стерты.
	 *
	 * @param person
	 *            the person or null
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			firstNameLabel.setText(person.getFirstName().getValue());
			lastNameLabel.setText(person.getLastName().getValue());
			streetLabel.setText(person.getStreet().getValue());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode().getValue()));
			cityLabel.setText(person.getCity().getValue());
			birthdayLabel.setText(DateUtil.format(person.getBirthday().getValue()));
			phoneLabel.setText(person.getPhone().getValue());
		} else {
			// Person is null, remove all the text.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
			phoneLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button. Вызывается когда
	 * пользователь кликает кнопку delete.
	 */
	@FXML
	public void handleDeletePerson() {
		TableViewSelectionModel<Person> selectionModel = personTable.getSelectionModel();
		// удаление не по индексу а по объекту !!!
		boolean deletingResult = personTable.getItems().remove(selectionModel.getSelectedItem());
		if (!deletingResult) {
			// Nothing selected.
			NotSelectedAlert alert = new NotSelectedAlert();
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setContentText("Пожалуйста, веберите объект для удаления.");
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 * Вызывается при клике. Открывает окно редактирования.
	 */
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
		if (okClicked) {
			personRepository.getPersonList().add(tempPerson);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 * Вызывается при клике. Открывает окно редактирования.
	 */
	@FXML
	public void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
			if (okClicked) {
				showPersonDetails(selectedPerson);
			}

		} else {
			// Nothing selected.
			NotSelectedAlert alert = new NotSelectedAlert();
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setContentText("Пожалуйста, веберите объект для редактирования.");
			alert.showAndWait();
		}
	}
}
