package ch.makery.address.repository;


import ch.makery.address.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonRepository {

	// наблюдаемый список
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    private static PersonRepository personRepository = new PersonRepository();

	private PersonRepository() {
	}

	public static PersonRepository getInstance(){
		return personRepository;
	}

	public ObservableList<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(ObservableList<Person> personList) {
		this.personList = personList;
	}
}
