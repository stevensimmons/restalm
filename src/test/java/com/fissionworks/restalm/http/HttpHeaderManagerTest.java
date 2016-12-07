package com.fissionworks.restalm.http;

import java.io.IOException;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.authentication.Credentials;

@PrepareForTest({ClientHttpRequestExecution.class, ClientHttpResponse.class, ClientHttpRequestExecution.class})
public class HttpHeaderManagerTest extends PowerMockTestCase {

	private static final String LWSSO = "LWSSO_COOKIE_KEY=0iOtRS_6jnOGSht3Yq8K4FhX67uA_P46BNrV-XK3nHW_WCQsiEDIyje1eGa3r64INZp0kimw_XYvZye9vc86TWEbTPDRiYF0s2o_RrfWgWZ0NxCkcUR2tidLilqycNrOuvdcZAX8AlEoaiKUG8LatQrFKKPBzrozLA_4EdhYF_4p7QbCErDe4kAzTDDOR6j_eqBeVr8mRP07n7wMgk5ezg..;Path=/";
	private static final String QC_SESSION = "QCSession=MjE3OTgzNDtXRURPbVBSNkw4QVRUZDRXM0swLXBRKio7UkVTVCBjbGllbnQ7IDsg;Path=/";

	@Test(expectedExceptions = NullPointerException.class)
	public void instantiate_withNullCredentials_shouldThrowException() {
		new HttpHeaderManager(null);
	}

	@Test
	public void intercept_withBlankLwssoCookie_shouldSetAuthorizationHeaderAndExtractSetCookie() throws IOException {
		final MockClientHttpRequest request = new MockClientHttpRequest();

		final HttpHeaderManager headerManager = new HttpHeaderManager(new Credentials("svc", ""));
		Assert.assertEquals(headerManager.getCurrentLwssoCookie(), "");
		Assert.assertEquals(headerManager.getCurrentQcSessionCookie(), "");
		performAuthorizationRequest(headerManager, request);

		Assert.assertEquals(request.getHeaders(), createExpectedAuthorizationHeaders());
		Assert.assertEquals(headerManager.getCurrentLwssoCookie(), LWSSO);

	}

	@Test
	public void intercept_withPreviouslyExtractedLwssoAndQcSessionCookie_shouldSetLwssoAndQcSessionHeader()
			throws IOException {
		final HttpHeaderManager headerManager = new HttpHeaderManager(new Credentials("svc", ""));
		performAuthorizationRequest(headerManager, new MockClientHttpRequest());
		performFirstPostAuthorizationRequest(headerManager, new MockClientHttpRequest());

		final MockClientHttpRequest request = new MockClientHttpRequest();
		performRequestWithSession(headerManager, request);

		Assert.assertEquals(request.getHeaders(), createExpectedLwssoAndQcSessionHeaders());
		Assert.assertEquals(headerManager.getCurrentLwssoCookie(), LWSSO);
		Assert.assertEquals(headerManager.getCurrentQcSessionCookie(), QC_SESSION);
	}

	@Test
	public void intercept_withPreviouslyExtractedLwssoCookie_shouldSetLwssoHeaderAndExtractSetCookie()
			throws IOException {
		final HttpHeaderManager headerManager = new HttpHeaderManager(new Credentials("svc", ""));
		performAuthorizationRequest(headerManager, new MockClientHttpRequest());
		final MockClientHttpRequest request = new MockClientHttpRequest();
		performFirstPostAuthorizationRequest(headerManager, request);

		Assert.assertEquals(request.getHeaders(), createExpectedLwssoOnlyHeaders());
		Assert.assertEquals(headerManager.getCurrentLwssoCookie(), LWSSO);
		Assert.assertEquals(headerManager.getCurrentQcSessionCookie(), QC_SESSION);
	}

	private HttpHeaders createAuthorizationResponseHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", LWSSO);
		return headers;
	}

	private byte[] createBody() {
		return new byte[1];
	}

	private HttpHeaders createExpectedAuthorizationHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic c3ZjOg==");
		headers.add("Content-Type", "application/xml");
		headers.add("Accept", "application/xml");
		return headers;
	}

	private HttpHeaders createExpectedLwssoAndQcSessionHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", LWSSO);
		headers.add("Cookie", QC_SESSION);
		headers.add("Content-Type", "application/xml");
		headers.add("Accept", "application/xml");
		return headers;
	}

	private HttpHeaders createExpectedLwssoOnlyHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", LWSSO);
		headers.add("Content-Type", "application/xml");
		headers.add("Accept", "application/xml");
		return headers;
	}

	private HttpHeaders createQcSessionResponseHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", QC_SESSION);
		return headers;
	}

	private void performAuthorizationRequest(final HttpHeaderManager headerManager, final HttpRequest request)
			throws IOException {
		final byte[] body = createBody();
		final ClientHttpResponse mockResponse = PowerMockito.mock(ClientHttpResponse.class);
		final ClientHttpRequestExecution mockExecution = PowerMockito.mock(ClientHttpRequestExecution.class);

		Mockito.doReturn(mockResponse).when(mockExecution).execute(request, body);
		Mockito.doReturn(createAuthorizationResponseHeaders()).when(mockResponse).getHeaders();
		headerManager.intercept(request, createBody(), mockExecution);
	}

	private void performFirstPostAuthorizationRequest(final HttpHeaderManager headerManager, final HttpRequest request)
			throws IOException {
		final byte[] body = createBody();
		final ClientHttpResponse mockResponse = PowerMockito.mock(ClientHttpResponse.class);
		final ClientHttpRequestExecution mockExecution = PowerMockito.mock(ClientHttpRequestExecution.class);

		Mockito.doReturn(mockResponse).when(mockExecution).execute(request, body);
		Mockito.doReturn(createQcSessionResponseHeaders()).when(mockResponse).getHeaders();
		headerManager.intercept(request, createBody(), mockExecution);
	}

	private void performRequestWithSession(final HttpHeaderManager headerManager, final HttpRequest request)
			throws IOException {
		final byte[] body = createBody();
		final ClientHttpResponse mockResponse = PowerMockito.mock(ClientHttpResponse.class);
		final ClientHttpRequestExecution mockExecution = PowerMockito.mock(ClientHttpRequestExecution.class);

		Mockito.doReturn(mockResponse).when(mockExecution).execute(request, body);
		Mockito.doReturn(new HttpHeaders()).when(mockResponse).getHeaders();
		headerManager.intercept(request, createBody(), mockExecution);
	}
}
