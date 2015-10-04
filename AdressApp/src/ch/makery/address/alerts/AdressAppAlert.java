package ch.makery.address.alerts;

import javafx.scene.control.Alert;

public abstract class AdressAppAlert extends Alert{

	public AdressAppAlert(AlertType alertType) {
		super(alertType);
	}
}
