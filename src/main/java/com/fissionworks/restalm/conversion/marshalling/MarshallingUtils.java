package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.path.PathTracker;
import com.thoughtworks.xstream.io.path.PathTrackingReader;
import com.thoughtworks.xstream.io.xml.XppReader;

/**
 * Utility class for performing common methods needed for implementing
 * {@code HttpMessageConverter}.
 *
 * @since 1.0.0
 *
 */
public final class MarshallingUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarshallingUtils.class);

	private static final String NAME = "Name";

	private static final String TYPE = "Type";

	private MarshallingUtils() {
		throw new UnsupportedOperationException("MarshallingUtils should not be instantiated");
	}

	/**
	 * Creates a {@link GenericEntity} by navigating xml with the provided
	 * reader. This method assumes the reader is currently positioned on the
	 * {code Entity} node.
	 *
	 * @param reader
	 *            The reader to use, which should be positioned on the {code
	 *            Entity} node.
	 * @return The {@link GenericEntity} created from the XML the reader is
	 *         parsing.
	 * @since 1.0.0
	 */
	public static GenericEntity createEntity(final HierarchicalStreamReader reader) {
		final GenericEntity entity = new GenericEntity(reader.getAttribute(TYPE), getFields(reader));
		reader.moveUp();
		reader.moveDown();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			reader.moveDown();
			entity.addRelatedEntity(new GenericEntity(reader.getAttribute(TYPE), getFields(reader)));
			reader.moveUp();
			reader.moveUp();
		}
		reader.moveUp();
		return entity;
	}

	/**
	 * Creates a {@link HierarchicalStreamReader} using the given inputMessage.
	 *
	 * @param inputMessage
	 *            The {@link HttpInputMessage} to create the reader from.
	 * @return A Fully constructed {@link HierarchicalStreamReader}.
	 * @throws IllegalStateException
	 *             Thrown if errors occur during the creation of the
	 *             {@link HierarchicalStreamReader}.
	 * @since 1.0.0
	 */
	public static HierarchicalStreamReader createReader(final HttpInputMessage inputMessage) {
		XmlPullParserFactory factory;
		try {
			factory = XmlPullParserFactory.newInstance();
			final XppReader reader = new XppReader(new InputStreamReader(inputMessage.getBody()),
					factory.newPullParser(), new NoNameCoder());
			return new PathTrackingReader(reader, new PathTracker());
		} catch (XmlPullParserException | IOException e) {
			LOGGER.error("Marshalling failure; unable to create stream reader to process HTTP response");
		}
		throw new IllegalStateException("Unable to create HierarchicalStreamReader");
	}

	private static List<Field> getFields(final HierarchicalStreamReader reader) {
		final List<Field> fields = new ArrayList<>();
		reader.moveDown();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			fields.add(new Field(reader.getAttribute(NAME), getValues(reader)));
			reader.moveUp();
		}
		return fields;
	}

	private static List<String> getValues(final HierarchicalStreamReader reader) {
		final List<String> values = new ArrayList<>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (StringUtils.isNotEmpty(reader.getValue())) {
				values.add(reader.getValue());
			}
			reader.moveUp();
		}
		return values;
	}

}
