package com.fissionworks.restalm.constants.field;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ReleaseCycleFieldTest {

	@Test
	public void getName_forDefectDetectedCycleDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forDefectDetectedCycleEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_END_DATE.getName(), "end-date");
	}

	@Test
	public void getName_forDefectDetectedCycleId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_ID.getName(), "id");
	}

	@Test
	public void getName_forDefectDetectedCycleName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_NAME.getName(), "name");
	}

	@Test
	public void getName_forDefectDetectedCycleParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forDefectDetectedCycleStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_START_DATE.getName(), "start-date");
	}

	@Test
	public void getName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DESCRIPTION.getName(), "description");
	}

	@Test
	public void getName_forEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.END_DATE.getName(), "end-date");
	}

	@Test
	public void getName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.ID.getName(), "id");
	}

	@Test
	public void getName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.NAME.getName(), "name");
	}

	@Test
	public void getName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.PARENT_ID.getName(), "parent-id");
	}

	@Test
	public void getName_forStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.START_DATE.getName(), "start-date");
	}

	@Test
	public void getQualifiedName_forDefectDetectedCycleDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_DESCRIPTION.getQualifiedName(),
				"defect-to-detected-rcycl-mirror.description");
	}

	@Test
	public void getQualifiedName_forDefectDetectedCycleEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_END_DATE.getQualifiedName(),
				"defect-to-detected-rcycl-mirror.end-date");
	}

	@Test
	public void getQualifiedName_forDefectDetectedCycleId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_ID.getQualifiedName(),
				"defect-to-detected-rcycl-mirror.id");
	}

	@Test
	public void getQualifiedName_forDefectDetectedCycleName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_NAME.getQualifiedName(),
				"defect-to-detected-rcycl-mirror.name");
	}

	@Test
	public void getQualifiedName_forDefectDetectedCycleParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_PARENT_ID.getQualifiedName(),
				"defect-to-detected-rcycl-mirror.parent-id");
	}

	@Test
	public void getQualifiedName_forDefectDetectedCycleStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DEFECT_DETECTED_CYCLE_START_DATE.getQualifiedName(),
				"defect-to-detected-rcycl-mirror.start-date");
	}

	@Test
	public void getQualifiedName_forDescription_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.DESCRIPTION.getQualifiedName(), "release-cycle.description");
	}

	@Test
	public void getQualifiedName_forEndDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.END_DATE.getQualifiedName(), "release-cycle.end-date");
	}

	@Test
	public void getQualifiedName_forId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.ID.getQualifiedName(), "release-cycle.id");
	}

	@Test
	public void getQualifiedName_forName_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.NAME.getQualifiedName(), "release-cycle.name");
	}

	@Test
	public void getQualifiedName_forParentId_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.PARENT_ID.getQualifiedName(), "release-cycle.parent-id");
	}

	@Test
	public void getQualifiedName_forStartDate_shouldReturnCorrectName() {
		Assert.assertEquals(ReleaseCycleField.START_DATE.getQualifiedName(), "release-cycle.start-date");
	}
}
