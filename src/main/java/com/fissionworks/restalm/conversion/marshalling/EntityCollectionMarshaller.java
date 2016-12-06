package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.fissionworks.restalm.model.entity.base.GenericEntity;
import com.fissionworks.restalm.model.entity.base.GenericEntityCollection;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Implementation of {@link HttpMessageConverter} that converts from a
 * collection of resource entities (tests, requirements, runs, etc) xml into a
 * {@link GenericEntityCollection} which can later be transformed into a
 * corresponding {@code AlmEntityCollection}.
 *
 * @since 1.0.0
 *
 */
public final class EntityCollectionMarshaller implements HttpMessageConverter<GenericEntityCollection> {

	private static final String TOTAL_RESULTS = "TotalResults";

	@Override
	public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
		return clazz.equals(GenericEntityCollection.class);
	}

	@Override
	public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
		return clazz.equals(GenericEntityCollection.class);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(MediaType.APPLICATION_XML);
	}

	@Override
	public GenericEntityCollection read(final Class<? extends GenericEntityCollection> clazz,
			final HttpInputMessage inputMessage) throws IOException {
		final HierarchicalStreamReader reader = MarshallingUtils.createReader(inputMessage);
		final int totalResults = Integer.valueOf(reader.getAttribute(TOTAL_RESULTS));
		final Set<GenericEntity> entities = new HashSet<>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			entities.add(MarshallingUtils.createEntity(reader));
			reader.moveUp();
		}
		return new GenericEntityCollection(totalResults, entities);
	}

	@Override
	public void write(final GenericEntityCollection t, final MediaType contentType,
			final HttpOutputMessage outputMessage) throws IOException {
		throw new UnsupportedOperationException("writing GenericEntityCollections not currently supported");
	}

}
