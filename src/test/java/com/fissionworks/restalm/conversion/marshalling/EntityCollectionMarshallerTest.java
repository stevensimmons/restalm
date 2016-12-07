package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntityCollection;

@PrepareForTest(HttpInputMessage.class)
public class EntityCollectionMarshallerTest extends PowerMockTestCase {

	@Test
	public void canRead_withGenericEntityCollectionClass_shouldReturnTrue() {
		final EntityCollectionMarshaller marshaller = new EntityCollectionMarshaller();
		Assert.assertTrue(marshaller.canRead(GenericEntityCollection.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canRead_withNonGenericEntityCollectionClass_shouldReturnFalse() {
		final EntityCollectionMarshaller marshaller = new EntityCollectionMarshaller();
		Assert.assertFalse(marshaller.canRead(String.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canWrite_withGenericEntityCollectionClass_shouldReturnTrue() {
		final EntityCollectionMarshaller marshaller = new EntityCollectionMarshaller();
		Assert.assertTrue(marshaller.canWrite(GenericEntityCollection.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canWrite_withNonGenericEntityCollectionClass_shouldReturnFalse() {
		final EntityCollectionMarshaller marshaller = new EntityCollectionMarshaller();
		Assert.assertFalse(marshaller.canWrite(String.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void getSupportedMediaTypes_shouldReturnArrayWithApplicationXml() {
		final List<MediaType> supportedTypes = new EntityCollectionMarshaller().getSupportedMediaTypes();
		Assert.assertEquals(supportedTypes.size(), 1);
		Assert.assertTrue(supportedTypes.contains(MediaType.APPLICATION_XML));
	}

	@Test
	public void read_withInputMessageWithMultipleEntities_shouldReturnCollectionWithMultipleEntities()
			throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final Set<GenericEntity> expectedEntities = new HashSet<>();
		expectedEntities.add(createGenericEntityOne());
		expectedEntities.add(createGenericEntityTwo());
		final GenericEntityCollection expectedCollection = new GenericEntityCollection(1337, expectedEntities);

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createEntityCollectionXml(expectedCollection)))
				.when(inputMessage).getBody();
		final EntityCollectionMarshaller marshaller = new EntityCollectionMarshaller();
		final GenericEntityCollection actualCollection = marshaller.read(GenericEntityCollection.class, inputMessage);
		Assert.assertTrue(expectedCollection.equals(actualCollection));
	}

	@Test
	public void read_withInputMessageWithOneEntity_shouldReturnCollectionWithOneEntity() throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final Set<GenericEntity> expectedEntities = new HashSet<>();
		expectedEntities.add(createGenericEntityOne());
		final GenericEntityCollection expectedCollection = new GenericEntityCollection(1337, expectedEntities);

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createEntityCollectionXml(expectedCollection)))
				.when(inputMessage).getBody();
		final EntityCollectionMarshaller marshaller = new EntityCollectionMarshaller();
		final GenericEntityCollection actualCollection = marshaller.read(GenericEntityCollection.class, inputMessage);
		Assert.assertTrue(expectedCollection.equals(actualCollection));
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void write_shouldThrowException() throws IOException {
		new EntityCollectionMarshaller().write(null, null, null);
	}

	private GenericEntity createGenericEntityOne() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("no value field one", new ArrayList<String>()));
		fields.add(new Field("single value field one", Arrays.asList("single value one")));
		fields.add(new Field("multi value field one", Arrays.asList("multi value one", "multi value two")));
		return new GenericEntity("test", fields);
	}

	private GenericEntity createGenericEntityTwo() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("no value field two", new ArrayList<String>()));
		fields.add(new Field("single value field two", Arrays.asList("single value two")));
		fields.add(new Field("multi value field two", Arrays.asList("multi value one", "multi value two")));
		return new GenericEntity("test", fields);
	}

}
