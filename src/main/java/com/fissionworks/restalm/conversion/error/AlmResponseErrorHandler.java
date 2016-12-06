package com.fissionworks.restalm.conversion.error;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import com.fissionworks.restalm.conversion.marshalling.MarshallingUtils;
import com.fissionworks.restalm.exceptions.AlmRestException;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Custom {@link ResponseErrorHandler} used to manage error responses from the
 * ALM rest API.
 *
 * @since 1.0.0
 *
 */
public final class AlmResponseErrorHandler implements ResponseErrorHandler {
	@Override
	public void handleError(final ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
			throw new AlmRestException(
					"(401) Unauthorized response returned; verify connection has been authenticated and session has not expired");
		}
		throw createAlmRestException(response);
	}

	/**
	 * {@inheritDoc} For the ALM rest api, a response status of anything other
	 * then 200 or 201 returns true.
	 */
	@Override
	public boolean hasError(final ClientHttpResponse response) throws IOException {
		return !(response.getStatusCode().equals(HttpStatus.OK) || response.getStatusCode().equals(HttpStatus.CREATED));
	}

	private AlmRestException createAlmRestException(final HttpInputMessage inputMessage) {
		final HierarchicalStreamReader reader = MarshallingUtils.createReader(inputMessage);
		String message = "";
		String id = "";
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("Id")) {
				id = reader.getValue();
			} else if (reader.getNodeName().equals("Title")) {
				message = reader.getValue();
			}
			reader.moveUp();
		}
		return new AlmRestException(message, id);
	}

}
