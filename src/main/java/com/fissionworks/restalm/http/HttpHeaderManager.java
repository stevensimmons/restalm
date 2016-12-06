package com.fissionworks.restalm.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.fissionworks.restalm.model.authentication.Credentials;

/**
 * The HttpHeaderManager implements Spring RestTemplate's
 * ClienHttpRequestInterceptor in order to manage the headers needed to
 * communicate to ALM using the Rest API. The initial request made through using
 * the {@code RestTemplate} instance will include the Authorization header
 * constructed from the provided credentials, and subsequent requests will
 * include the LWSSO and QCSession cookies. Responses from ALM will be examined,
 * and if new LWSSO/QCSession cookies are returned they will be used on future
 * calls to maintain an active session.
 *
 * @since 1.0.0
 *
 */
public final class HttpHeaderManager implements ClientHttpRequestInterceptor {

	private static final String ACCEPT = "Accept";

	private static final String APPLICATION_XML = "application/xml";

	private static final String AUTHORIZATION = "Authorization";

	private static final String COOKIE = "Cookie";

	private static final String LWSSO_KEY = "LWSSO_COOKIE_KEY";

	private static final String QC_SESSION_KEY = "QCSession";

	private static final String SET_COOKIE = "Set-Cookie";

	private final String authorizationHeader;

	private String lwssoCookie;

	private String qcSessionCookie;

	/**
	 * Constructor requires a valid Credentials object to build the
	 * Authorization header from.
	 *
	 * @param credentials
	 *            A valid credentials object.
	 * @since 1.0.0
	 */
	public HttpHeaderManager(final Credentials credentials) {
		Validate.notNull(credentials, "credentials cannot be null");
		this.authorizationHeader = credentials.getBasicAuthString();
	}

	/**
	 * Get the LWSSO cookie currently being included in all HTTP requests.
	 *
	 * @return The LWSSO cookie.
	 * @since 1.0.0
	 */
	public String getCurrentLwssoCookie() {
		return lwssoCookie == null ? "" : lwssoCookie;
	}

	/**
	 * Get the QC session cookie currently being included in all HTTP requests.
	 *
	 * @return The QC Session cookie.
	 * @since 1.0.0
	 */
	public String getCurrentQcSessionCookie() {
		return qcSessionCookie == null ? "" : qcSessionCookie;
	}

	/**
	 * Intercepts HTTP request and responses made to ALM to set LWSSO/QCSession
	 * cookies needed for ALM communications.
	 *
	 * @since 1.0.0
	 */
	@Override
	public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
			final ClientHttpRequestExecution execution) throws IOException {
		request.getHeaders().putAll(getCurrentHeaders());
		final ClientHttpResponse response = execution.execute(request, body);
		extractCookies(response);
		return response;
	}

	private void extractCookies(final ClientHttpResponse response) {
		final List<String> setCookies = response.getHeaders().get(SET_COOKIE);
		if (setCookies != null) {
			for (final String cookie : setCookies) {
				if (StringUtils.contains(cookie, LWSSO_KEY)) {
					lwssoCookie = cookie;
				}
				if (StringUtils.contains(cookie, QC_SESSION_KEY)) {
					qcSessionCookie = cookie;
				}
			}
		}
	}

	private Map<String, List<String>> getCurrentHeaders() {
		final Map<String, List<String>> headerEntries = new HashMap<>();
		headerEntries.put(ACCEPT, Arrays.asList(APPLICATION_XML));
		headerEntries.put("Content-Type", Arrays.asList(APPLICATION_XML));
		if (StringUtils.isBlank(lwssoCookie)) {
			headerEntries.put(AUTHORIZATION, Arrays.asList(authorizationHeader));
		} else {
			final List<String> cookies = new ArrayList<>();
			cookies.add(lwssoCookie);
			if (qcSessionCookie != null) {
				cookies.add(qcSessionCookie);
			}
			headerEntries.put(COOKIE, cookies);
		}
		return headerEntries;
	}

}
