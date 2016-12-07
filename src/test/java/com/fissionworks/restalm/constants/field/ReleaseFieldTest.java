package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ReleaseFieldTest {

	@Test
	public void getName_forDefectDetectedReleaseDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forDefectDetectedReleaseEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_END_DATE.getName(), "end-date");
	}

	@Test
	public void getName_forDefectDetectedReleaseId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_ID.getName(), "id");
	}

	@Test
	public void getName_forDefectDetectedReleaseName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_NAME.getName(), "name");
	}

	@Test
	public void getName_forDefectDetectedReleaseParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forDefectDetectedReleaseStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_START_DATE.getName(), "start-date");
	}

	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.END_DATE.getName(), "end-date");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.START_DATE.getName(), "start-date");
	}

	@Test
	public void getQualifiedName_forDefectDetectedReleaseDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_DESCRIPTION.getQualifiedName(),
				"defect-to-detected-release-mirror.description");
	}

	@Test
	public void getQualifiedName_forDefectDetectedReleaseEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_END_DATE.getQualifiedName(),
				"defect-to-detected-release-mirror.end-date");
	}

	@Test
	public void getQualifiedName_forDefectDetectedReleaseId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_ID.getQualifiedName(),
				"defect-to-detected-release-mirror.id");
	}

	@Test
	public void getQualifiedName_forDefectDetectedReleaseName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_NAME.getQualifiedName(),
				"defect-to-detected-release-mirror.name");
	}

	@Test
	public void getQualifiedName_forDefectDetectedReleaseParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_PARENT_ID.getQualifiedName(),
				"defect-to-detected-release-mirror.parent-id");
	}

	@Test
	public void getQualifiedName_forDefectDetectedReleaseStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DEFECT_DETECTED_RELEASE_START_DATE.getQualifiedName(),
				"defect-to-detected-release-mirror.start-date");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.DESCRIPTION.getQualifiedName(), "release.description");
	}

	@Test
	public void getQualifiedName_forEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.END_DATE.getQualifiedName(), "release.end-date");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.ID.getQualifiedName(), "release.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.NAME.getQualifiedName(), "release.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.PARENT_ID.getQualifiedName(), "release.parent-id");
	}

	@Test
	public void getQualifiedName_forStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseField.START_DATE.getQualifiedName(), "release.start-date");
	}
}
