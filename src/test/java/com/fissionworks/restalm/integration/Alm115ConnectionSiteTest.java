package com.fissionworks.restalm.integration;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fissionworks.restalm.Alm115Connection;
import com.fissionworks.restalm.model.authentication.Credentials;
import com.fissionworks.restalm.model.site.Site;

public class Alm115ConnectionSiteTest {

	private Alm115Connection alm;

	private final String URL = "http://xxxx:xxx";

	private final Credentials validCreds = new Credentials("xx", "xx");

	@BeforeClass
	public void _setup() {
		alm = new Alm115Connection(URL);
		alm.authenticate(validCreds);
	}

	@Test
	public void getSite_withoutLoggingIn_shouldReturnSite() {
		final Site site = alm.getSite();
		Assert.assertNotNull(site);
		Assert.assertTrue(site.getDomainNames().size() >= 2, "ALM deployment should have at least 2 domains");
		Assert.assertTrue(site.getDomainNames().contains("DEFAULT"));
		Assert.assertTrue(site.getDomainNames().contains("SANDBOX"));
	}
}
