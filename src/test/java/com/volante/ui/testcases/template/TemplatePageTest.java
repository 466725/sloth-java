package com.volante.ui.testcases.template;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.framework.templates.GuiTestCase;
import com.volante.ui.testcases.HomePage;
import com.volante.ui.testcases.LoginPage;

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
	protected static TemplatePage templatePage = null;

	@Test(priority = 1)
	public void gotoTemplatePage() {
		SoftAssertions softly = new SoftAssertions();
		templatePage = new TemplatePage(driver, GuiTestCase.URL, GuiTestCase.userName, GuiTestCase.password);

		softly.assertThat(templatePage.navigateTo());
		softly.assertAll();
	}

	@Test(priority = 3)
	public void deleteConcept() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(templatePage.hoverAndClickTrashConceptIcon(1));
		softly.assertThat(templatePage.checkPopupMessageOnDeleteConceptPopup("Are you sure you want to delete this item?"));
		softly.assertThat(templatePage.selectPositiveOnDeleteConceptPopup());
		softly.assertAll();
	}

	@Test(priority = 9)
	public void editConcept() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(templatePage.specifyNewConceptNameByHoveringMouseOver(2, "Weipeng" + System.currentTimeMillis()));
		softly.assertAll();
	}

	@Test(priority = 99)
	public void addConcept() {
		SoftAssertions softly = new SoftAssertions();

		softly.assertThat(templatePage.clickAddNewConceptIcon());
		softly.assertThat(templatePage.inputNewConceptName("Yahtu" + System.currentTimeMillis()));
		softly.assertThat(templatePage.clickVerticalSelectDropdown());
		softly.assertThat(templatePage.selectFirstVerticalOption());
		softly.assertThat(templatePage.selectFourthVerticalOption());
		softly.assertThat(templatePage.applySelectedVerticalOptions());
		softly.assertThat(templatePage.clickSaveNewConceptIcon());
		softly.assertAll();
	}
}
