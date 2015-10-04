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
		this.primaryStage.setTitle("�������� �����");
		initRootLayout();
		showPersonOverview();
	}

	/**
	 * Initializes the root layout. �������������� �������� �����.
	 *
	 */
	public void initRootLayout() {
		try {
			// ��������� �������� ����� �� fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			// �������� ��������, ���������� �������� �����.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(primaryStage);
			alert.setHeaderText("������ ��� ��������.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout. ���������� ��������
	 * ������� ������ ��������� ������.
	 */
	public void showPersonOverview() {
		try {
			// �������� �������� �������
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// ��������� �������� ������� � �������� �����.
			rootLayout.setCenter(personOverview);

			// ���� ������ ����������� � ����� ������ (MainApp).
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(primaryStage);
			alert.setHeaderText("������ ��� ��������.");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	/**
	 * ��������� ���� �������������� ������� ��������. ���� ������������ �������
	 * �� ��������� ����������� � ������������ true.
	 *
	 * @param person
	 *            ���������� ������
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Person person) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("fxml/PersonEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// �������� �������.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("�������������� ����� ������.");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// ������������� ����������� � ������ ������������.
			PersonEditDialogController personEditDialogController = loader.getController();
			personEditDialogController.setDialogStage(dialogStage);
			personEditDialogController.setPerson(person);
			// �������� ���� � �����.
			dialogStage.showAndWait();

			return personEditDialogController.isOkClicked();

		} catch (IOException e) {
			ErrorAlert alert = new ErrorAlert();
			alert.initOwner(primaryStage);
			alert.setHeaderText("������ ��� ������ ���������.");
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
