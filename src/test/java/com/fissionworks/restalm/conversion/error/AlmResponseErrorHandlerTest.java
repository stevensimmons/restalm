package com.fissionworks.restalm.conversion.error;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.exceptions.AlmRestException;

@PrepareForTest(ClientHttpResponse.class)
public class AlmResponseErrorHandlerTest extends PowerMockTestCase {

	@Test
	public void handleError_withNonUnauthorizedError_shouldCreateAlmRestException() throws IOException {
		final AlmResponseErrorHandler errorHandler = new AlmResponseErrorHandler();
		final ClientHttpResponse response = PowerMockito.mock(ClientHttpResponse.class);
		final String id = "the Id";
		final String title = "the title";
		boolean exceptionCaught = false;
		Mockito.doReturn(HttpStatus.BAD_REQUEST).when(response).getStatusCode();
		Mockito.doReturn(getExceptionStream(id, title)).when(response).getBody();
		try {
			errorHandler.handleError(response);
		} catch (final AlmRestException exception) {
			exceptionCaught = true;
			Assert.assertEquals(exception.getMessage(), "the Id: the title");
		}
		Assert.assertTrue(exceptionCaught);
	}

	@Test(expectedExceptions = AlmRestException.class, expectedExceptionsMessageRegExp = "AlmRestException: \\(401\\) Unauthorized response returned; verify connection has been authenticated and session has not expired")
	public void handleError_withUnauthorizedResponse_shouldThrowUnauthorizedAlmRestException() throws IOException {
		final AlmResponseErrorHandler errorHandler = new AlmResponseErrorHandler();
		final ClientHttpResponse response = PowerMockito.mock(ClientHttpResponse.class);
		Mockito.doReturn(HttpStatus.UNAUTHORIZED).when(response).getStatusCode();
		errorHandler.handleError(response);
	}

	@Test
	public void hasError_withCreatedHttpStatus_shouldReturnFalse() throws IOException {
		final AlmResponseErrorHandler errorHandler = new AlmResponseErrorHandler();
		final ClientHttpResponse response = PowerMockito.mock(ClientHttpResponse.class);
		Mockito.doReturn(HttpStatus.CREATED).when(response).getStatusCode();
		Assert.assertFalse(errorHandler.hasError(response));
	}

	@Test
	public void hasError_withNonOKOrCreatedStatus_shouldReturnTrue() throws IOException {
		final AlmResponseErrorHandler errorHandler = new AlmResponseErrorHandler();
		final ClientHttpResponse response = PowerMockito.mock(ClientHttpResponse.class);
		Mockito.doReturn(HttpStatus.BAD_REQUEST).when(response).getStatusCode();
		Assert.assertTrue(errorHandler.hasError(response));
	}

	@Test
	public void hasError_withOkHttpStatus_shouldReturnFalse() throws IOException {
		final AlmResponseErrorHandler errorHandler = new AlmResponseErrorHandler();
		final ClientHttpResponse response = PowerMockito.mock(ClientHttpResponse.class);
		Mockito.doReturn(HttpStatus.OK).when(response).getStatusCode();
		Assert.assertFalse(errorHandler.hasError(response));
	}

	private InputStream getExceptionStream(final String id, final String title) {
		return IOUtils.toInputStream(String.format(
				"<QCRestException><Id>%s</Id><Title>%s</Title><ExceptionProperties/></QCRestException>", id, title));
	}
}
