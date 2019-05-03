package com.test.ui.testcases.template;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.test.ui.testcases.HomePage;
import com.test.ui.testcases.LoginPage;

import io.qameta.allure.Link;
import io.qameta.allure.Step;

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

	@Step("Step of navigating to TemplateManagementPage")
	public boolean navigateToTemplateManagementPage() {
		templateManagementPage = new TemplatePage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);
		return templateManagementPage.navigateTo();
	}

	@Step("Step of adding new concept by clicking on the '+' icon")
	public boolean clickAddNewConceptIcon() {
		return templateManagementPage.addNewConcept();
	}

	@Step("Specify a name to the new concept")
	public boolean specifyNameToTheConcept(String conceptName) {
		return templateManagementPage.inputNewConceptName(conceptName);
	}

	@Step("Click vertical button to show drop down options")
	public boolean clickVerticalSelector() {
		return templateManagementPage.selectVertical();
	}

	@Step("Select a vertical option for the new concept")
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

	@Step("Apply selected verticals to the new concept")
	public boolean applySelectedVerticalsToTheConcept() {
		return templateManagementPage.applyVerticalOption();
	}

	@Step("Try to save the newly created concept")
	public boolean tryToSaveTheNewlyCreatedConcept() {
		return templateManagementPage.saveTheNewConcept();
	}

	@Step("Try to cacel the newly created concept")
	public boolean tryToCancelTheNewlyCreatedConcept() {
		return templateManagementPage.cancelTheNewConcept();
	}

	@Step("Hover mouse over a concept row")
	public boolean hoverMouseOverConceptRow(int index) {
		return templateManagementPage.hoverMouseOverConceptRow(index);
	}

	@Step("Hover mouse over a concept row, and then click trash icon")
	public boolean hoverMouseOverConceptRowAndDelete(int index) {
		return templateManagementPage.trashConceptByHoveringMouseOver(index);
	}

	@Step("Hover mouse over a concept row, and then click edit concept name icon")
	public boolean hoverMouseOverConceptRowAndEditName(int index) {
		return templateManagementPage.editConceptNameByHoveringMouseOver(index);
	}

	@Step("Hover mouse over a concept row, and then specify a new concept name")
	public boolean specifyNewConceptNameByHoveringMouseOver(int index, String newConceptName) {
		return templateManagementPage.specifyNewConceptNameByHoveringMouseOver(index, newConceptName);
	}

	@Step("Check the message on the popup confirmation window")
	public boolean checkPopupWindowConfirmationMessage(String expectedMessage) {
		return templateManagementPage.checkPopupMessage(expectedMessage);
	}

	@Step("Click either positive or negative button on the popup window")
	public boolean clickPopupWindowConfirmationButtons(boolean isPositive) {
		if (isPositive)
			return templateManagementPage.selectPositive();
		return templateManagementPage.selectPositive();
	}

	@Test(description = "VOL-8888:[UI] Edit concept name")
	@Link(name = "VOL-8888", url = "http://volantedocs.com/testlink/linkto.php?tprojectPrefix=VOL&item=testcase&id=VOL-8888")
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
