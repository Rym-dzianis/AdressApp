package ch.makery.address.alerts;

public class ErrorAlert extends AdressAppAlert  {
	public ErrorAlert() {
		super(AlertType.ERROR);
		setTitle("Ошибка!!!");
	}
}
