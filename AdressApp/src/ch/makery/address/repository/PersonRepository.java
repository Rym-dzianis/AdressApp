package ch.makery.address.repository;

import java.util.function.Predicate;

import ch.makery.address.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class PersonRepository {

	// наблюдаемый список
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    private static PersonRepository personRepository = new PersonRepository();

	private PersonRepository() {
		// Add some sample data
        personList.add(new Person("Денис", "Римашевский"));
        personList.add(new Person("Виталий", "Филатенко"));
        personList.add(new Person("Виталий", "Усеня"));
        personList.add(new Person("Андрей", "Мусский"));
        personList.add(new Person("Вячеслав", "Шаплыко"));
        personList.add(new Person("Саша", "Грэй"));
        personList.add(new Person("Анджелина", "Джоли"));
        personList.add(new Person("Александр", "Юревич"));
        personList.add(new Person("Димон", "Варг"));
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

	public void addPerson(Person person){
		personList.add(person);
	}

	public boolean deletePerson(Person person){
		return personList.removeIf(p -> p.equals(person));
	}

	public boolean updatePerson(Person person){
		int index = personList.indexOf(person);
		personList.set(index, person);
		return false;
	}
}
