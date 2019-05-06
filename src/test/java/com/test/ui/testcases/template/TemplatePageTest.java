package com.test.ui.testcases.template;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.test.ui.testcases.HomePage;
import com.test.ui.testcases.LoginPage;

/**
 * Tests related to TemplateManagementPage
 * 
 * @author Weipeng Zheng
 *
 */
public class TemplatePageTest extends GuiTestCase {

	protected final static Logger logger = LogManager.getLogger(TemplatePageTest.class.getName());
	protected static LoginPage loginPage = null;
	protected static HomePage homePage = null;
	protected static TemplatePage templateManagementPage = null;

	public boolean navigateToTemplateManagementPage() {
		templateManagementPage = new TemplatePage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);
		return templateManagementPage.navigateTo();
	}

	public boolean clickAddNewConceptIcon() {
		return templateManagementPage.addNewConcept();
	}

	public boolean specifyNameToTheConcept(String conceptName) {
		return templateManagementPage.inputNewConceptName(conceptName);
	}

	public boolean clickVerticalSelector() {
		return templateManagementPage.selectVertical();
	}

	public boolean selectVerticalOption(String index) {
		if (index.equalsIgnoreCase("Restaurant"))
			return templateManagementPage.selectFirstVerticalOption();
		else if (index.equalsIgnoreCase("Hospitality"))
			return templateManagementPage.selectSecondVerticalOption();
		else if (index.equalsIgnoreCase("Health Care"))
			return templateManagementPage.selectThirdVerticalOption();
		else if (index.equalsIgnoreCase("Entertaiment"))
			return templateManagementPage.selectFourthVerticalOption();
		else
			return templateManagementPage.selectFirstVerticalOption();
	}

	public boolean applySelectedVerticalsToTheConcept() {
		return templateManagementPage.applyVerticalOption();
	}

	public boolean tryToSaveTheNewlyCreatedConcept() {
		return templateManagementPage.saveTheNewConcept();
	}

	public boolean tryToCancelTheNewlyCreatedConcept() {
		return templateManagementPage.cancelTheNewConcept();
	}

	public boolean hoverMouseOverConceptRow(int index) {
		return templateManagementPage.hoverMouseOverConceptRow(index);
	}

	public boolean hoverMouseOverConceptRowAndDelete(int index) {
		return templateManagementPage.trashConceptByHoveringMouseOver(index);
	}

	public boolean hoverMouseOverConceptRowAndEditName(int index) {
		return templateManagementPage.editConceptNameByHoveringMouseOver(index);
	}

	public boolean specifyNewConceptNameByHoveringMouseOver(int index, String newConceptName) {
		return templateManagementPage.specifyNewConceptNameByHoveringMouseOver(index, newConceptName);
	}

	public boolean checkPopupWindowConfirmationMessage(String expectedMessage) {
		return templateManagementPage.checkPopupMessage(expectedMessage);
	}

	public boolean clickPopupWindowConfirmationButtons(boolean isPositive) {
		if (isPositive)
			return templateManagementPage.selectPositive();
		return templateManagementPage.selectPositive();
	}

	@Test(description = "VOL-8888:[UI] Edit concept name")
	public void testEditingConceptName() {
		test = extent.startTest("Navigate to template page and then edit concept name");
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(navigateToTemplateManagementPage());
		softly.assertThat(specifyNewConceptNameByHoveringMouseOver(2, "AT" + System.currentTimeMillis()));

		softly.assertAll();
	}

	// @Test(description = "VOL-9999:[UI] Creating new concept")
	public void testCreatingNewConcept() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(navigateToTemplateManagementPage());
		softly.assertThat(hoverMouseOverConceptRow(1));
		softly.assertThat(hoverMouseOverConceptRowAndDelete(2));
		softly.assertThat(checkPopupWindowConfirmationMessage("Are you sure you want to delete this item?"));
		softly.assertThat(clickPopupWindowConfirmationButtons(true));
		softly.assertThat(hoverMouseOverConceptRowAndEditName(1));
		softly.assertThat(specifyNewConceptNameByHoveringMouseOver(2, "AT" + System.currentTimeMillis()));
		softly.assertThat(clickAddNewConceptIcon());
		softly.assertThat(specifyNameToTheConcept("AT" + System.currentTimeMillis()));
		softly.assertThat(clickVerticalSelector());
		softly.assertThat(selectVerticalOption("Restaurant"));
		softly.assertThat(selectVerticalOption("Hospitality"));
		softly.assertThat(selectVerticalOption("Health Care"));
		softly.assertThat(selectVerticalOption("I have no Idea"));
		softly.assertThat(applySelectedVerticalsToTheConcept());
		softly.assertThat(tryToSaveTheNewlyCreatedConcept());
		// softly.assertThat(tryToCancelTheNewlyCreatedConcept());

		softly.assertAll();
	}

	// @Test(description = "VOL-3214:[UI] View concept page")
	public void testViewingConcept() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(navigateToTemplateManagementPage());
		softly.assertThat(hoverMouseOverConceptRowAndEditName(1));
		softly.assertThat(clickVerticalSelector());
		softly.assertThat(hoverMouseOverConceptRowAndDelete(2));
		softly.assertThat(checkPopupWindowConfirmationMessage("Are you sure you want to delete this item?"));
		softly.assertThat(clickPopupWindowConfirmationButtons(true));

		softly.assertAll();
	}

	// @Test(description = "VOL-3215:[UI] Add new concept")
	public void testAddingNewConcept() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(navigateToTemplateManagementPage());
		softly.assertThat(clickAddNewConceptIcon());
		softly.assertThat(specifyNameToTheConcept("AT" + System.currentTimeMillis()));
		softly.assertThat(clickVerticalSelector());
		softly.assertThat(selectVerticalOption("Restaurant"));
		softly.assertThat(selectVerticalOption("Hospitality"));
		softly.assertThat(applySelectedVerticalsToTheConcept());
		softly.assertThat(tryToSaveTheNewlyCreatedConcept());

		softly.assertAll();
	}

	// @Test(description = "VOL-3217:[UI] Add new concept alternative flow")
	public void testAddingNewConceptAlternativeFlow() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(navigateToTemplateManagementPage());
		softly.assertThat(clickAddNewConceptIcon());
		softly.assertThat(specifyNameToTheConcept("AT" + System.currentTimeMillis()));
		softly.assertThat(clickVerticalSelector());
		softly.assertThat(selectVerticalOption("Restaurant"));
		softly.assertThat(selectVerticalOption("Hospitality"));
		softly.assertThat(applySelectedVerticalsToTheConcept());
		softly.assertThat(tryToCancelTheNewlyCreatedConcept());
		softly.assertThat(clickAddNewConceptIcon());
		softly.assertThat(specifyNameToTheConcept("AT" + System.currentTimeMillis()));
		softly.assertThat(clickVerticalSelector());
		softly.assertThat(selectVerticalOption("Restaurant"));
		softly.assertThat(selectVerticalOption("Hospitality"));
		softly.assertThat(applySelectedVerticalsToTheConcept());
		softly.assertThat(tryToSaveTheNewlyCreatedConcept());
		// ...

		softly.assertAll();
	}
}
