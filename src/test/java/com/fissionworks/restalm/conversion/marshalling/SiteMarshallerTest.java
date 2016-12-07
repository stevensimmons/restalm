package com.fissionworks.restalm.conversion.marshalling;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fissionworks.restalm.model.site.Domain;
import com.fissionworks.restalm.model.site.Project;
import com.fissionworks.restalm.model.site.Site;

@PrepareForTest(HttpInputMessage.class)
public class SiteMarshallerTest extends PowerMockTestCase {

	@Test
	public void canRead_withNonSiteClass_shouldReturnFalse() {
		final SiteMarshaller marshaller = new SiteMarshaller();
		Assert.assertFalse(marshaller.canRead(String.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canRead_withSiteClass_shouldReturnTrue() {
		final SiteMarshaller marshaller = new SiteMarshaller();
		Assert.assertTrue(marshaller.canRead(Site.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void canWrite_shouldReturnFalse() {
		final SiteMarshaller marshaller = new SiteMarshaller();
		Assert.assertFalse(marshaller.canWrite(Site.class, MediaType.APPLICATION_XML));
	}

	@Test
	public void getSupportedMediaTypes_shouldReturnArrayWithApplicationXml() {
		final List<MediaType> supportedTypes = new SiteMarshaller().getSupportedMediaTypes();
		Assert.assertEquals(supportedTypes.size(), 1);
		Assert.assertTrue(supportedTypes.contains(MediaType.APPLICATION_XML));
	}

	@Test
	public void read_withInputMessageWithMultipleDomains_shouldReturnSiteWithMutlipleDomains() throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final Site expectedSite = new Site();
		expectedSite.addDomain(createSingleProjectDomain());
		expectedSite.addDomain(createNoProjectDomain());
		expectedSite.addDomain(createMultiProjectDomain());

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createSiteXml(expectedSite))).when(inputMessage).getBody();
		final SiteMarshaller marshaller = new SiteMarshaller();
		final Site actualSite = marshaller.read(Site.class, inputMessage);
		Assert.assertEquals(actualSite, expectedSite);
	}

	@Test
	public void read_withInputMessageWithOneDomain_shouldReturnSiteWithOneDomain() throws IOException {
		final HttpInputMessage inputMessage = PowerMockito.mock(HttpInputMessage.class);
		final Site expectedSite = new Site();
		expectedSite.addDomain(createSingleProjectDomain());

		Mockito.doReturn(IOUtils.toInputStream(XmlUtils.createSiteXml(expectedSite))).when(inputMessage).getBody();
		final SiteMarshaller marshaller = new SiteMarshaller();
		final Site actualSite = marshaller.read(Site.class, inputMessage);
		Assert.assertEquals(actualSite, expectedSite);
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void write_shouldThrowException() throws IOException {
		new SiteMarshaller().write(new Site(), MediaType.APPLICATION_XML, null);
	}

	private Domain createMultiProjectDomain() {
		final Domain domain = new Domain("MULTI_PROJECT");
		domain.addProject(new Project("MULTI_PROJECT", "Project2"));
		domain.addProject(new Project("MULTI_PROJECT", "Project3"));
		domain.addProject(new Project("MULTI_PROJECT", "Project4"));
		return domain;
	}

	private Domain createNoProjectDomain() {
		final Domain domain = new Domain("NO_PROJECT");
		return domain;
	}

	private Domain createSingleProjectDomain() {
		final Domain domain = new Domain("SINGLE_PROJECT");
		domain.addProject(new Project("SINGLE_PROJECT", "Project1"));
		return domain;
	}
}
