package ch.makery.address.model;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty street;
	private IntegerProperty postalCode;
	private StringProperty city;
	private ObjectProperty<LocalDate> birthday;
	private StringProperty phone;

	/**
	 * Default constructor.
	 */
	public Person() {
		firstName = new SimpleStringProperty(null);
		lastName = new SimpleStringProperty(null);
		street = new SimpleStringProperty(null);
		postalCode = new SimpleIntegerProperty();
		city = new SimpleStringProperty(null);
		birthday = new SimpleObjectProperty<LocalDate>(null);
		phone = new SimpleStringProperty(null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);

		// Some initial dummy data, just for convenient testing.
		this.street = new SimpleStringProperty("some street");
		this.postalCode = new SimpleIntegerProperty(1234);
		this.city = new SimpleStringProperty("some city");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
		this.phone = new SimpleStringProperty("+375 ");
	}

	public StringProperty getFirstName() {
		return firstName;
	}

	public void setFirstName(StringProperty firstName) {
		this.firstName = firstName;
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public void setLastName(StringProperty lastName) {
		this.lastName = lastName;
	}

	public StringProperty getStreet() {
		return street;
	}

	public void setStreet(StringProperty street) {
		this.street = street;
	}

	public IntegerProperty getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(IntegerProperty postalCode) {
		this.postalCode = postalCode;
	}

	public StringProperty getCity() {
		return city;
	}

	public void setCity(StringProperty city) {
		this.city = city;
	}

	public ObjectProperty<LocalDate> getBirthday() {
		return birthday;
	}

	public void setBirthday(ObjectProperty<LocalDate> birthday) {
		this.birthday = birthday;
	}

	public StringProperty getPhone() {
		return phone;
	}

	public void setPhone(StringProperty phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Person other = (Person) obj;
		return (firstName == other.firstName || (firstName != null && firstName.equals(other.getFirstName())))
				&& (lastName == other.lastName || (lastName != null && lastName.equals(other.getLastName())))
				&& (street == other.street || (street != null && street.equals(other.getLastName())))
				&& (postalCode == other.postalCode || (postalCode != null && postalCode.equals(other.getLastName())))
				&& (city == other.city || (city != null && city.equals(other.getLastName())))
				&& (birthday == other.birthday || (birthday != null && birthday.equals(other.getLastName())))
				&& (phone == other.phone || (phone != null && phone.equals(other.getLastName())));
	}
}
