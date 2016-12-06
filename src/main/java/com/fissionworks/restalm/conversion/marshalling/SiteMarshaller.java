package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.fissionworks.restalm.model.site.Domain;
import com.fissionworks.restalm.model.site.Project;
import com.fissionworks.restalm.model.site.Site;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * A {@link HttpMessageConverter} for marshalling/unmarshalling {@link Site}
 * objects.
 *
 * @since 1.0.0
 *
 */
public final class SiteMarshaller implements HttpMessageConverter<Site> {

	@Override
	public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
		return clazz.equals(Site.class);
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
	public Site read(final Class<? extends Site> clazz, final HttpInputMessage inputMessage) throws IOException {

		final Site siteStructure = new Site();

		final HierarchicalStreamReader reader = MarshallingUtils.createReader(inputMessage);
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			final Domain domain = new Domain(reader.getAttribute("Name"));

			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				domain.addProject(new Project(domain.getName(), reader.getAttribute("Name")));
				reader.moveUp();
			}
			reader.moveUp();

			siteStructure.addDomain(domain);
			reader.moveUp();
		}

		return siteStructure;
	}

	@Override
	public void write(final Site t, final MediaType contentType, final HttpOutputMessage outputMessage)
			throws IOException {
		throw new UnsupportedOperationException("Site objects cannot be written back to ALM.");
	}
}
