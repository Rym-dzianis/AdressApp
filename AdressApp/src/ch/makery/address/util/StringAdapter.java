package ch.makery.address.util;


import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Adapter (for JAXB) to convert between the StringProperty and the ISO 8601 String.
 *
 */
public class StringAdapter extends XmlAdapter<String, StringProperty>{

	@Override
	public String marshal(StringProperty paramBoundType) throws Exception {
		return paramBoundType.getValue();
	}

	@Override
	public StringProperty unmarshal(String paramValueType) throws Exception {
		return new SimpleStringProperty(paramValueType);
	}

}
