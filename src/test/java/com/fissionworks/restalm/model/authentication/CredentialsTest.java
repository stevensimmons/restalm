package com.fissionworks.restalm.model.authentication;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CredentialsTest {

	@Test
	public void getBasicAuthString_withOneLetterPassword_shouldReturnCorrectAuthString() {
		final Credentials creds = new Credentials("myUsername", "!");
		Assert.assertEquals(creds.getBasicAuthString(), "Basic bXlVc2VybmFtZToh");
	}

	@Test
	public void getBasicAuthString_withOneLetterUsername_shouldReturnCorrectAuthString() {
		final Credentials creds = new Credentials("a", "myPassword");
		Assert.assertEquals(creds.getBasicAuthString(), "Basic YTpteVBhc3N3b3Jk");
	}

	@Test
	public void getBasicAuthString_withSixtyLetterUsername_shouldReturnCorrectAuthString() {
		final Credentials creds = new Credentials("qwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiop",
				"myPassword");
		Assert.assertEquals(creds.getBasicAuthString(),
				"Basic cXdlcnR5dWlvcHF3ZXJ0eXVpb3Bxd2VydHl1aW9wcXdlcnR5dWlvcHF3ZXJ0eXVpb3Bxd2VydHl1aW9wOm15UGFzc3dvcmQ=");
	}

	@Test
	public void getBasicAuthString_withTwentyLetterPassword_shouldReturnCorrectAuthString() {
		final Credentials creds = new Credentials("myUsername", "qwertyuiopqwertyuiop");
		Assert.assertEquals(creds.getBasicAuthString(), "Basic bXlVc2VybmFtZTpxd2VydHl1aW9wcXdlcnR5dWlvcA==");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiating_withBlankUsername_shouldThrowException() {
		new Credentials("   ", "myPassword");
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instantiating_withNullPassword_shouldThrowException() {
		new Credentials("myUsername", null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void instantiating_withNullUsername_shouldThrowException() {
		new Credentials(null, "myPassword");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiating_withPasswordGreaterThan20Characters_shouldThrowException() {
		new Credentials("myUsername", "q@df^fvhasdfghjk;'[po");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiating_withUsernameContainingSpecialCharacters_shouldThrowException() {
		new Credentials("myUsern@me:", "myPassword");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void instantiating_withUsernameGreaterThan60Characters_shouldThrowException() {
		new Credentials("qwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopqwertyuiopq", "myPassword");
	}

	@Test
	public void toString_shouldReturnStringContainingUsernameAndPassword() {
		final Credentials creds = new Credentials("myUsername", "myPassword");
		Assert.assertTrue(StringUtils.contains(creds.toString(), "myUsername"),
				"toString() should return string with username in it");
		Assert.assertTrue(StringUtils.contains(creds.toString(), "myPassword"),
				"toString() should return string with password in it");
	}
}
