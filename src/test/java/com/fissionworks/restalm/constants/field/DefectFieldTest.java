package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DefectFieldTest {

	@Test
	public void getName_forAssignedTo_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.ASSIGNED_TO.getName(), "owner");
	}

	@Test
	public void getName_forClosingDate_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.CLOSING_DATE.getName(), "closing-date");
	}

	@Test
	public void getName_forComments_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.COMMENTS.getName(), "dev-comments");
	}

	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forDetectedBy_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_BY.getName(), "detected-by");
	}

	@Test
	public void getName_forDetectedInCycle_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_IN_CYCLE_ID.getName(), "detected-in-rcyc");
	}

	@Test
	public void getName_forDetectedInRelease_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_IN_RELEASE_ID.getName(), "detected-in-rel");
	}

	@Test
	public void getName_forDetectedOnDate_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_ON_DATE.getName(), "creation-time");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.ID.getName(), "id");
	}

	@Test
	public void getName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.MODIFIED.getName(), "last-modified");
	}

	@Test
	public void getName_forSeverity_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.SEVERITY.getName(), "severity");
	}

	@Test
	public void getName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.STATUS.getName(), "status");
	}

	@Test
	public void getName_forSummary_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.SUMMARY.getName(), "name");
	}

	@Test
	public void getQualifiedName_forAssignedTo_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.ASSIGNED_TO.getQualifiedName(), "defect.owner");
	}

	@Test
	public void getQualifiedName_forClosingDate_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.CLOSING_DATE.getQualifiedName(), "defect.closing-date");
	}

	@Test
	public void getQualifiedName_forComments_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.COMMENTS.getQualifiedName(), "defect.dev-comments");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DESCRIPTION.getQualifiedName(), "defect.description");
	}

	@Test
	public void getQualifiedName_forDetectedBy_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_BY.getQualifiedName(), "defect.detected-by");
	}

	@Test
	public void getQualifiedName_forDetectedInCycle_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_IN_CYCLE_ID.getQualifiedName(), "defect.detected-in-rcyc");
	}

	@Test
	public void getQualifiedName_forDetectedInRelease_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_IN_RELEASE_ID.getQualifiedName(), "defect.detected-in-rel");
	}

	@Test
	public void getQualifiedName_forDetectedOnDate_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.DETECTED_ON_DATE.getQualifiedName(), "defect.creation-time");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.ID.getQualifiedName(), "defect.id");
	}

	@Test
	public void getQualifiedName_forLastModified_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.MODIFIED.getQualifiedName(), "defect.last-modified");
	}

	@Test
	public void getQualifiedName_forSeverity_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.SEVERITY.getQualifiedName(), "defect.severity");
	}

	@Test
	public void getQualifiedName_forStatus_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.STATUS.getQualifiedName(), "defect.status");
	}

	@Test
	public void getQualifiedName_forSummary_shouldReturnCorrectName() {
		Assert.assertEquals(DefectField.SUMMARY.getQualifiedName(), "defect.name");
	}

}
