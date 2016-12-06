package com.fissionworks.restalm.constants;

/**
 * URL's for interaction with ALM's rest API.
 *
 * @since 1.0.0
 *
 */
public enum ServiceUrl {

	/**
	 * POST Method; URL for adding entities; URL value is "
	 * <code>{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}</code>
	 * ".
	 *
	 * @since 1.0.0
	 */
	ADD_ENTITY_URL("{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}"),

	/**
	 * GET Method, URL value is
	 * <code>{url}/qcbin/authentication-point/authenticate</code>.
	 *
	 * @since 1.0.0
	 */
	AUTHENTICATE("{url}/qcbin/authentication-point/authenticate"),

	/**
	 * DELETE method; URL value is
	 * <code>"{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}?ids-to-delete={ids}"</code>.
	 *
	 * @since 1.0.0
	 */
	BULK_DELETE_URL("{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}?ids-to-delete={ids}"),

	/**
	 * URL for interaction with specific entities by ID; URL value is "
	 * <code>{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}/{id}</code>".
	 *
	 * @since 1.0.0
	 */
	ENTITY_BY_ID("{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}/{id}"),

	/**
	 * GET Method, URL for getting a list of domains and associated projects;
	 * URL value is "
	 * <code>{url}/qcbin/rest/domains?include-projects-info=y</code> ".
	 *
	 * @since 1.0.0
	 */
	GET_DOMAINS("{url}/qcbin/rest/domains?include-projects-info=y"),

	/**
	 * URL for interaction with collections of entities; URL value is "
	 * <code>{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}?query={query}&fields={fields}&page-size={pageSize}&start-index={startIndex}</code>
	 * ".
	 *
	 * @since 1.0.0
	 */
	GET_ENTITY_COLLECTION("{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}?fields={fields}&query={query}&page-size={pageSize}&start-index={startIndex}"),

	/**
	 * GET Method, URL for getting the fields associated with a particular
	 * entity; URL value is "
	 * <code>{url}/qcbin/rest/domains/{domain}/projects/{projectName}/customization/entities/{entityType}/fields</code>
	 * ".
	 *
	 * @since 1.0.0
	 */
	GET_ENTITY_FIELDS("{url}/qcbin/rest/domains/{domain}/projects/{projectName}/customization/entities/{entityType}/fields"),

	/**
	 * GET Method to determine if connection is authenticated; url value is
	 * <code>"{url}/qcbin/rest/is-authenticated"</code>.
	 *
	 * @since 1.0.0
	 */
	IS_AUTHENTICATED("{url}/qcbin/rest/is-authenticated"),

	/**
	 * GET Method to logut; url value is
	 * <code>"{url}/qcbin/authentication-point/logout"</code>.
	 *
	 * @since 1.0.0
	 */
	LOGOUT("{url}/qcbin/authentication-point/logout");

	private final String url;

	private ServiceUrl(final String theUrl) {
		this.url = theUrl;
	}

	/**
	 * Retrieve URL string value for current enum constant.
	 *
	 * @return URL string value for current enum constant
	 * @since 1.0.0
	 */
	public final String url() {
		return this.url;
	}

}
