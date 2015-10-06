package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import ch.makery.address.MainApp;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
//        mainApp.getPersonData().clear();
//        mainApp.setPersonFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
//        FileChooser fileChooser = new FileChooser();
//
//        // Set extension filter
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                "XML files (*.xml)", "*.xml");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        // Show save file dialog
//        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
//
//        if (file != null) {
//            mainApp.loadPersonDataFromFile(file);
//        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
//        File personFile = mainApp.getPersonFilePath();
//        if (personFile != null) {
//            mainApp.savePersonDataToFile(personFile);
//        } else {
//            handleSaveAs();
//        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
//        FileChooser fileChooser = new FileChooser();
//
//        // Set extension filter
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
//                "XML files (*.xml)", "*.xml");
//        fileChooser.getExtensionFilters().add(extFilter);
//
//        // Show save file dialog
//        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
//
//        if (file != null) {
//            // Make sure it has the correct extension
//            if (!file.getPath().endsWith(".xml")) {
//                file = new File(file.getPath() + ".xml");
//            }
//            mainApp.savePersonDataToFile(file);
//        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	System.out.println("About");
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("О программе.");
    	alert.setHeaderText("Информация о программе:");
    	alert.setContentText("Версия:    1.1\n\n\nАвтор:   Римашевский Д М\n\n\nСайт:    http://");
    	alert.showAndWait();
    }

    /**
     * Инструкция.
     */
    @FXML
    private void handleFAQ() {
    	System.out.println("FAQ");
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Инструкция.");
    	alert.setHeaderText("Инструкция:");
    	alert.setContentText("1.\n\n\n2.\n\n\n3.\n\n\n4.\n\n\n5.\n\n\n6.\n\n\n7.\n\n\n8.");
    	alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
