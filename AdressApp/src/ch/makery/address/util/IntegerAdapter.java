package ch.makery.address.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Adapter (for JAXB) to convert between the IntegerProperty and Integer.
 *
 */
public class IntegerAdapter extends XmlAdapter<Integer, IntegerProperty> {

	@Override
	public Integer marshal(IntegerProperty paramBoundType) throws Exception {
		return paramBoundType.intValue();
	}

	@Override
	public IntegerProperty unmarshal(Integer paramValueType) throws Exception {
		return new SimpleIntegerProperty(paramValueType);
	}

}
