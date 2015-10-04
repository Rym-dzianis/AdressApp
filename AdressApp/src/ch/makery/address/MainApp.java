package ch.makery.address;

import java.io.IOException;

import ch.makery.address.alerts.ErrorAlert;
import ch.makery.address.model.Person;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	public MainApp() {
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Адресная книга");
		initRootLayout();
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
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);

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
			dialogStage.setTitle("Редактирование новой записи.");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
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
}
