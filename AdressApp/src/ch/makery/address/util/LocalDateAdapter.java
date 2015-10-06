package ch.makery.address.util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Adapter (for JAXB) to convert between the ObjectProperty<LocalDate> and the ISO 8601
 * String representation of the date such as '2012-12-03'.
 *
 */
public class LocalDateAdapter extends XmlAdapter<String, ObjectProperty<LocalDate>> {

    @Override
    public ObjectProperty<LocalDate> unmarshal(String paramBoundType) throws Exception {
        return new SimpleObjectProperty<LocalDate>(LocalDate.parse(paramBoundType));
    }

    @Override
    public String marshal(ObjectProperty<LocalDate> paramValueType) throws Exception {
        return paramValueType.getValue().toString();
    }
}
