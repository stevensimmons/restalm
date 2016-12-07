package com.fissionworks.restalm.integration;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.site.Project;

public class Alm115ConnectionAuthenticationTest {

	private final String URL = "http://xxxx:xxx";

	private final Credentials validCreds = new Credentials("xxx", "xxx");

	@Test(expectedExceptions = IllegalStateException.class)
	public void authenticate_withAlreadyAuthenticatedAlm_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		alm.authenticate(validCreds);
	}

	@Test
	public void authenticateAndIsAuthenticated_withValidCredentials_shouldAuthenticateAndReturnTrue() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		Assert.assertTrue(alm.isAuthenticated(),
				"Is authenticated should return true with authenticated ALM connection");
	}

	@Test
	public void isAuthenticated_withUnauthenticatedAlm_shouldReturnFalse() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.isAuthenticated();
	}

	@Test
	public void isLoggedIn_withNonLoggedInAlm_shouldReturnFalse() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		Assert.assertTrue(alm.isAuthenticated());
		Assert.assertFalse(alm.isLoggedIn());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void login_withNonExistentProject_shouldThrowException() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		alm.login(new Project("DEFAULT", "Not_A_Project"));
	}

	@Test
	public void login_withValidProject_shouldLoginAndIsLoggedInShouldReturnTrue() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		alm.login(new Project("SANDBOX", "Test_Restful"));
		Assert.assertTrue(alm.isLoggedIn());
	}

	@Test
	public void logout_withAuthenticatedAndLoggedInAlm_shouldLogout() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		alm.login(new Project("SANDBOX", "Test_Restful"));
		Assert.assertTrue(alm.isAuthenticated());
		Assert.assertTrue(alm.isLoggedIn());
		alm.logout();
		Assert.assertFalse(alm.isAuthenticated());
		Assert.assertFalse(alm.isLoggedIn());
	}

	@Test
	public void logout_withAuthenticatedButNotLoggedInAlm_shouldLogout() {
		final Alm115Connection alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
		Assert.assertTrue(alm.isAuthenticated());
		Assert.assertFalse(alm.isLoggedIn());
		alm.logout();
		Assert.assertFalse(alm.isAuthenticated());
		Assert.assertFalse(alm.isLoggedIn());
	}

	@Test
	public void logout_withUnauthenticatedAlm_shouldBeAllowed() {
		final Alm115Connection alm = new Alm115Connection(URL);
		Assert.assertFalse(alm.isAuthenticated());
		Assert.assertFalse(alm.isLoggedIn());
		alm.logout();
		Assert.assertFalse(alm.isAuthenticated());
		Assert.assertFalse(alm.isLoggedIn());
	}

}
