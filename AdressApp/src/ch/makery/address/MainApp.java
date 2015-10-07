package ch.makery.address;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.makery.address.alerts.ErrorAlert;
import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.repository.PersonRepository;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private PersonRepository personRepository = PersonRepository.getInstance();

	private PersonOverviewController personOverviewController;

	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Адресная книга");

		// Иконка окна.
		this.primaryStage.getIcons().add(new Image("file:resources/images/Address_Book_Alt_red.png"));

		initRootLayout();

		this.primaryStage.setOnCloseRequest(e -> exit());

		showPersonOverview();
	}

	/**
	 * Initializes the root layout. Инициализирует корневой макет.
	 *
	 */
	public void initRootLayout() {
		try {
			// Загружает корневой макет из fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Показать картинку, содержащую корневой макет.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();

		} catch (IOException e) {
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(primaryStage);
			alert.setHeaderText("Ошибка при открытии.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout. Показывает описание
	 * персоны внутри корневого макета.
	 */
	public void showPersonOverview() {
		try {
			// Загрузка описание персоны
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// Установка описание персоны в корневой макет.
			rootLayout.setCenter(personOverview);

			// Дать доступ контроллеру к этому классу (MainApp).
//			PersonOverviewController controller = loader.getController();
			personOverviewController = loader.getController();
			personOverviewController.setMainApp(this);

			File file = getPersonFilePath();
			loadPersonDataFromFile(file);

		} catch (IOException e) {
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(primaryStage);
			alert.setHeaderText("Ошибка при открытии.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * Открывает окно редактирования деталей контакта. Если пользователь кликает
	 * ОК изменения сохраняются и возвращается true.
	 *
	 * @param person
	 *            изменяемый объект
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Создание диалога.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Редактирование записи.");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Иконка окна.
			dialogStage.getIcons().add(new Image("file:resources/images/Address_Book_Alt_red.png"));

			// Инициализация контроллера и запись позьзователя.
			PersonEditDialogController personEditDialogController = loader.getController();
			personEditDialogController.setDialogStage(dialogStage);
			personEditDialogController.setPerson(person);

			// Показать окно и ждать.
			dialogStage.showAndWait();

			return personEditDialogController.isOkClicked();

		} catch (IOException e) {
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(primaryStage);
			alert.setHeaderText("Ошибка при вызове редактора.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();

			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to show birthday statistics.
	 */
	public void showBirthdayStatistics() {
	    try {
	        // Load the fxml file and create a new stage for the popup.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("fxml/BirthdayStatistics.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Birthday Statistics");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the persons into the controller.
	        BirthdayStatisticsController controller = loader.getController();
	        controller.setPersonData(personRepository.getPersonList());

	        dialogStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public PersonOverviewController getPersonOverviewController() {
		return personOverviewController;
	}

	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		System.out.println("Started !!!");
		launch(args);
	}

	// -----------------------------------------------------

	/**
	 * Returns the person file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 *
	 * @return
	 */
	public File getPersonFilePath() {
		System.out.println("Взять путь к файлу из контекста");
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 *
	 * @param file
	 *            the file or null to remove the path
	 */
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());

			// Update the stage title.
			primaryStage.setTitle("AddressApp - " + file.getName());
		} else {
			prefs.remove("filePath");

			// Update the stage title.
			primaryStage.setTitle("AddressApp");
		}
	}

	// ------------------------------------------------------- запись в файл

	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 *
	 * @param file
	 */
	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

			personRepository.getPersonList().clear();
			personRepository.getPersonList().addAll(wrapper.getPersons());

			// Save the file path to the registry.
			setPersonFilePath(file);

		} catch (Exception e) { // catches ANY exception
			// Show the error message.
			ErrorAlert alert = new ErrorAlert();
			alert.setHeaderText("Не возможно прочитать данные из файла:\n" + file.getPath());
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	/**
	 * Saves the current person data to the specified file.
	 *
	 * @param file
	 */
	public void savePersonDataToFile(File file) {
		try {
			System.out.println("Сохранение в файл");
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Wrapping our person data.
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(PersonRepository.getInstance().getPersonList());

			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);

			// Save the file path to the registry.
			setPersonFilePath(file);
		} catch (Exception e) { // catches ANY exception
			// Show the error message.
			ErrorAlert alert = new ErrorAlert();
			alert.setHeaderText("Не возможно сохранить данные в файл:\n" + file.getPath());
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	// ------------------------
	protected void exit() {
		System.out.println("Exit");
		// диалог о закрытии
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Закрытие программы.");
		alert.setHeaderText("Закрытие программы и сохранение в файл.");
		alert.setContentText("Вы уверены?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			savePersonDataToFile(getPersonFilePath());
		} else {

		}

	}
}
