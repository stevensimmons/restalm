package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.fissionworks.restalm.model.entity.base.Field;
import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.CompactWriter;

/**
 * Implementation of {@link HttpMessageConverter} that converts from the
 * resource entity (tests, requirements, runs, etc) xml into a
 * {@link GenericEntity} which can later be transformed into a corresponding
 * alm-utils class.
 *
 * @since 1.0.0
 *
 */
public final class EntityMarshaller implements HttpMessageConverter<GenericEntity> {

	@Override
	public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
		return clazz.equals(GenericEntity.class);
	}

	@Override
	public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
		return clazz.equals(GenericEntity.class);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_XML);
	}

	@Override
	public GenericEntity read(final Class<? extends GenericEntity> clazz, final HttpInputMessage inputMessage)
			throws IOException {
		final HierarchicalStreamReader reader = MarshallingUtils.createReader(inputMessage);
		return MarshallingUtils.createEntity(reader);
	}

	@Override
	public void write(final GenericEntity entity, final MediaType contentType, final HttpOutputMessage outputMessage)
			throws IOException {
		final HierarchicalStreamWriter writer = new CompactWriter(new OutputStreamWriter(outputMessage.getBody()));
		writer.startNode("Entity");
		writer.addAttribute("Type", entity.getType());
		writer.startNode("Fields");
		for (final Field field : entity.getFields()) {
			writer.startNode("Field");
			writer.addAttribute("Name", field.getName());
			if (field.getValues().size() == 0) {
				writer.startNode("Value");
				writer.endNode();
			} else {
				for (final String value : field.getValues()) {
					writer.startNode("Value");
					writer.setValue(value);
					writer.endNode();
				}
			}
			writer.endNode();
		}
		writer.endNode();
		writer.endNode();

	}

}
