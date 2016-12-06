package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.fissionworks.restalm.constants.entity.EntityFieldParameter;
import com.fissionworks.restalm.model.customization.EntityField;
import com.fissionworks.restalm.model.customization.EntityFieldCollection;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * A {@link HttpMessageConverter} for marshalling/unmarshalling
 * {@link EntityFieldCollection} objects.
 *
 * @since 1.0.0
 *
 */
public final class EntityFieldCollectionMarshaller implements HttpMessageConverter<EntityFieldCollection> {

	private static final String LABEL = "Label";
	private static final String NAME = "Name";

	@Override
	public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
		return clazz.equals(EntityFieldCollection.class);
	}

	@Override
	public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_XML);
	}

	@Override
	public EntityFieldCollection read(final Class<? extends EntityFieldCollection> clazz,
			final HttpInputMessage inputMessage) throws IOException {
		final HierarchicalStreamReader reader = MarshallingUtils.createReader(inputMessage);
		final EntityFieldCollection entityFields = new EntityFieldCollection();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			entityFields.addEntityField(createEntityField(reader));
			reader.moveUp();
		}
		return entityFields;
	}

	@Override
	public void write(final EntityFieldCollection t, final MediaType contentType, final HttpOutputMessage outputMessage)
			throws IOException {
		throw new UnsupportedOperationException("writing EntityFields back to ALM is not supported");

	}

	private EntityField createEntityField(final HierarchicalStreamReader reader) {
		final EntityField field = new EntityField(reader.getAttribute(NAME), reader.getAttribute(LABEL));
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			switch (EntityFieldParameter.fromParameterName(reader.getNodeName())) {
			case REQUIRED:
				field.setRequired(Boolean.valueOf(reader.getValue()));
				break;
			case SYSTEM:
				field.setSystem(Boolean.valueOf(reader.getValue()));
				break;
			case EDITABLE:
				field.setEditable(Boolean.valueOf(reader.getValue()));
				break;
			default:
				break;
			}
			reader.moveUp();
		}
		return field;
	}
}
