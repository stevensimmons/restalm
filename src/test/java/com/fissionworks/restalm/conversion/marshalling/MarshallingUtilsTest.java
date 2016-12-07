package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpInputMessage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppReader;

public class MarshallingUtilsTest extends PowerMockTestCase {

	@Test
	public void createEntity_withXmlWithoutRelatedEntity_shouldCreateEntity() {
		final Reader stringReader = new StringReader(
				XmlUtils.createEntityXml(new GenericEntity("test", createFieldListOne())));
		final HierarchicalStreamReader reader = createReader(stringReader);
		final GenericEntity actualEntity = MarshallingUtils.createEntity(reader);
		Assert.assertEquals(actualEntity, new GenericEntity("test", createFieldListOne()));
	}

	@Test
	public void createEntity_withXmlWithRelatedEntity_shouldCreateEntity() {
		final GenericEntity sourceEntity = new GenericEntity("test", createFieldListOne());
		sourceEntity.addRelatedEntity(new GenericEntity("related", createFieldListOne()));
		final Reader stringReader = new StringReader(XmlUtils.createEntityXml(sourceEntity));
		final HierarchicalStreamReader reader = createReader(stringReader);
		final GenericEntity actualEntity = MarshallingUtils.createEntity(reader);
		Assert.assertTrue(actualEntity.hasRelatedEntities());
		Assert.assertEquals(actualEntity.getRelatedEntities().size(), 1);
		Assert.assertEquals(actualEntity.getRelatedEntities().get(0),
				new GenericEntity("related", createFieldListOne()));
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void createReader_withInputMessageThatThrowsIOException_shoudThrowIllegalStateException()
			throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		Mockito.doThrow(IOException.class).when(inputMessage).getBody();
		MarshallingUtils.createReader(inputMessage);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void createReader_withInputMessageThatThrowsXmlPullParserException_shoudThrowIllegalStateException()
			throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		Mockito.doThrow(XmlPullParserException.class).when(inputMessage).getBody();
		MarshallingUtils.createReader(inputMessage);
	}

	@Test
	public void MarshallingUtils_shouldHaveInaccessibleConstructor() {
		final Constructor<?>[] constructors = MarshallingUtils.class.getDeclaredConstructors();
		final Constructor<?> constructor = constructors[0];
		Assert.assertFalse(constructor.isAccessible(), "Constructor should be inaccessible");
	}

	@Test
	public void MarshallingUtils_shouldHaveOneConstructor() {
		final Constructor<?>[] constructors = MarshallingUtils.class.getDeclaredConstructors();
		Assert.assertEquals(constructors.length, 1);
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void MarshallingUtilsInstantiationThroughReflection_shouldThrowException()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		final Constructor<?>[] constructors = MarshallingUtils.class.getDeclaredConstructors();
		final Constructor<?> constructor = constructors[0];
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	private List<Field> createFieldListOne() {
		final List<Field> fields = new ArrayList<>();
		fields.add(new Field("fieldOne", Arrays.asList("valueOne")));
		fields.add(new Field("fieldTwo", Arrays.asList("valueTwo")));
		fields.add(new Field("fieldThree", Arrays.asList("valueOne", "valueTwo", "valueThree")));
		return fields;
	}

	private HierarchicalStreamReader createReader(final Reader reader) {
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			return new XppReader(reader, factory.newPullParser(), new NoNameCoder());
		} catch (final XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new IllegalStateException("Unable to create HierarchicalStreamReader");
	}

}
