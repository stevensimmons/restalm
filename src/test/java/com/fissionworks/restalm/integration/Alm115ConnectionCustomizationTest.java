package com.fissionworks.restalm.integration;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;
import com.fissionworks.restalm.model.entity.testplan.AlmTest;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionCustomizationTest {

	private static final String DOMAIN = "SANDBOX";

	private static final String PASSWORD = "xxxx";

	private static final String PROJECT_NAME = "ALM_Utils_Testing";

	private static final String URL = "http://xxxxx:xxxx";

	private static final String USERNAME = "xxxx";

	private Alm115Connection alm;

	@BeforeClass
	public void _setup() {
		alm = new Alm115Connection(URL);
		alm.authenticate(new Credentials(USERNAME, PASSWORD));
		alm.login(new Project(DOMAIN, PROJECT_NAME));
	}

	@AfterClass
	public void _teardown() {
		alm.logout();
	}

	@Test
	public void getEntityFields_withValidEntityClass_shouldReturnFields() {
		final EntityFieldCollection entityFieldCollection = alm.getEnityFields(AlmTest.class);
		final Set<EntityField> entityFields = entityFieldCollection.getFields();
		Assert.assertTrue(entityFields.size() > 0);
		// check for a couple key fields
		Assert.assertTrue(entityFields.contains(getEntityField("name", "Test Name", true, true, true)));
		Assert.assertTrue(entityFields.contains(getEntityField("id", "Test ID", false, true, false)));
		Assert.assertTrue(entityFields.contains(getEntityField("subtype-id", "Type", true, true, true)));

	}

	private EntityField getEntityField(final String name, final String label, final boolean editable,
			final boolean system, final boolean required) {
		final EntityField field = new EntityField(name, label);
		field.setEditable(editable);
		field.setSystem(system);
		field.setRequired(required);
		return field;
	}
}
