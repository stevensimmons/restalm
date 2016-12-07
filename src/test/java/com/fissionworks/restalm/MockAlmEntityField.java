package com.fissionworks.restalm;

import com.fissionworks.restalm.constants.field.FieldName;

public enum MockAlmEntityField implements FieldName {

	FIELD_ONE("fieldOne"),

	FIELD_THREE("fieldThree"),

	FIELD_TWO("fieldTwo");

	final private String name;

	private MockAlmEntityField(final String theName) {
		this.name = theName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getQualifiedName() {
		return String.format("%s.%s", "mockAlmEntity", name);
	}

}
