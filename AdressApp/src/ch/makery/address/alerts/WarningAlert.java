package ch.makery.address.alerts;

public class WarningAlert extends AdressAppAlert {
	public WarningAlert() {
		super(AlertType.WARNING);
		setTitle("Предупреждение!!!");
	}
}
