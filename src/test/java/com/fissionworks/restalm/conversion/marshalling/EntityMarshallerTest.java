package com.fissionworks.restalm.conversion.marshalling;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;

public class EntityMarshallerTest extends PowerMockTestCase {

	private class MockOutputMessage implements HttpOutputMessage {

		private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		@Override
		public OutputStream getBody() throws IOException {
			return outputStream;
		}

		@Override
		public HttpHeaders getHeaders() {
			return null;
		}

		@Override
		public String toString() {
			return outputStream.toString();
		}

	}

	@Test
	public void canRead_withGenericEntityClass_shouldReturnTrue() {
		final EntityMarshaller marshaller = new EntityMarshaller();
		Assert.assertTrue(marshaller.canRead(GenericEntity.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canRead_withNonGenericEntityClass_shouldReturnFalse() {
		final EntityMarshaller marshaller = new EntityMarshaller();
		Assert.assertFalse(marshaller.canRead(String.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canWrite_withGenericEntityClass_shouldReturnTrue() {
		final EntityMarshaller marshaller = new EntityMarshaller();
		Assert.assertTrue(marshaller.canWrite(GenericEntity.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canWrite_withNonGenericEntityClass_shouldReturnTrue() {
		final EntityMarshaller marshaller = new EntityMarshaller();
		Assert.assertFalse(marshaller.canWrite(String.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void getSupportedMediaTypes_shouldReturnArrayWithApplicationXml() {
		final List<MediaType> supportedTypes = new EntityMarshaller().getSupportedMediaTypes();
		Assert.assertEquals(supportedTypes.size(), 1);
		Assert.assertTrue(supportedTypes.contains(MediaType.APPLICATION_XML));
	}

	@Test
	public void read_withValidInputMessage_shouldReturnEntity() throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final GenericEntity expectedEntity = createGenericEntityOne();

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createEntityXml(expectedEntity))).when(inputMessage).getBody();
		final EntityMarshaller marshaller = new EntityMarshaller();
		final GenericEntity actualEntity = marshaller.read(GenericEntity.class, inputMessage);
		Assert.assertEquals(actualEntity, expectedEntity);
	}

	@Test
	public void write_withValidEntity_shouldWriteEntity() throws IOException {
		final GenericEntity expectedEntity = createGenericEntityOne();
		final EntityMarshaller marshaller = new EntityMarshaller();
		final MockOutputMessage output = new MockOutputMessage();

		marshaller.write(expectedEntity, MediaType.APPLICATION_XML, output);
		Assert.assertEquals(output.toString(),
				StringUtils.remove(XmlUtils.createEntityXml(expectedEntity), "<RelatedEntities/>"));

	}

	private GenericEntity createGenericEntityOne() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("no value field one", new ArrayList<String>()));
		fields.add(new Field("single value field one", Arrays.asList("single value one")));
		fields.add(new Field("multi value field one", Arrays.asList("multi value one", "multi value two")));
		return new GenericEntity("test", fields);
	}
}
