package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;

@PrepareForTest(HttpInputMessage.class)
public class EnitityFieldCollectionMarshallerTest extends PowerMockTestCase {

	@Test
	public void canRead_withEntityFieldCollectionClass_shouldReturnTrue() {
		Assert.assertTrue(
				new EntityFieldCollectionMarshaller().canRead(EntityFieldCollection.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canRead_withNonEntityFieldCollectionClass_shouldReturnTrue() {
		Assert.assertFalse(new EntityFieldCollectionMarshaller().canRead(String.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canWrite_shouldReturnFalse() {
		Assert.assertFalse(
				new EntityFieldCollectionMarshaller().canWrite(EntityFieldCollection.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void getSupportedMediaTypes_shouldReturnListWithApplicationXmlType() {
		Assert.assertEquals(new EntityFieldCollectionMarshaller().getSupportedMediaTypes(),
				Arrays.asList(MediaType.APPLICATION_XML));
	}

	@Test
	public void read_withInputMessageWithMultipleEntities_shouldReturnCollectionWithMultipleEntities()
			throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final EntityFieldCollection expectedCollection = new EntityFieldCollection();
		expectedCollection.addEntityField(createEntityFieldOne());
		expectedCollection.addEntityField(createEntityFieldTwo());

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createEntityFieldCollectionXml(expectedCollection)))
				.when(inputMessage).getBody();
		final EntityFieldCollectionMarshaller marshaller = new EntityFieldCollectionMarshaller();
		final EntityFieldCollection actualCollection = marshaller.read(EntityFieldCollection.class, inputMessage);
		Assert.assertTrue(expectedCollection.equals(actualCollection));
	}

	@Test
	public void read_withInputMessageWithOneEntity_shouldReturnCollectionWithOneEntity() throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final EntityFieldCollection expectedCollection = new EntityFieldCollection();
		expectedCollection.addEntityField(createEntityFieldOne());

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createEntityFieldCollectionXml(expectedCollection)))
				.when(inputMessage).getBody();
		final EntityFieldCollectionMarshaller marshaller = new EntityFieldCollectionMarshaller();
		final EntityFieldCollection actualCollection = marshaller.read(EntityFieldCollection.class, inputMessage);
		Assert.assertTrue(expectedCollection.equals(actualCollection));
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void write_shouldThrowException() throws IOException {
		new EntityFieldCollectionMarshaller().write(null, null, null);
	}

	private EntityField createEntityFieldOne() {
		final EntityField field = new EntityField("field-one", "Field One");
		field.setEditable(true);
		field.setSystem(true);
		field.setRequired(true);
		return field;
	}

	private EntityField createEntityFieldTwo() {
		final EntityField field = new EntityField("field-two", "Field Two");
		field.setEditable(false);
		field.setSystem(false);
		field.setRequired(false);
		return field;
	}
}
