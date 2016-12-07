package com.fissionworks.restalm.constants;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ServiceUrlTest {

	@Test
	public void url_forAddEntityUrl_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.ADD_ENTITY_URL.url(),
				"{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}");
	}

	@Test
	public void url_forAuthenticate_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.AUTHENTICATE.url(), "{url}/qcbin/authentication-point/authenticate");
	}

	@Test
	public void url_forBulkDeleteUrl_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.BULK_DELETE_URL.url(),
				"{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}?ids-to-delete={ids}");
	}

	@Test
	public void url_forEntityById_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.ENTITY_BY_ID.url(),
				"{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}/{id}");
	}

	@Test
	public void url_forGetEntityCollection_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.GET_ENTITY_COLLECTION.url(),
				"{url}/qcbin/rest/domains/{domain}/projects/{projectName}/{collectionType}?fields={fields}&query={query}&page-size={pageSize}&start-index={startIndex}");
	}

	@Test
	public void url_forGetEntityFields_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.GET_ENTITY_FIELDS.url(),
				"{url}/qcbin/rest/domains/{domain}/projects/{projectName}/customization/entities/{entityType}/fields");
	}

	@Test
	public void url_forIsAuthenticated_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.IS_AUTHENTICATED.url(), "{url}/qcbin/rest/is-authenticated");
	}

	@Test
	public void url_forLogout_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.LOGOUT.url(), "{url}/qcbin/authentication-point/logout");
	}

	@Test
	public void url_getDomains_shouldReturnCorrectUrl() {
		Assert.assertEquals(ServiceUrl.GET_DOMAINS.url(), "{url}/qcbin/rest/domains?include-projects-info=y");
	}
}
