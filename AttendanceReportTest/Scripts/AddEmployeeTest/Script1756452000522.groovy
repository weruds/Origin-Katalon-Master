import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


//open browser aand navigate to the url
WebUI.openBrowser('')
WebUI.navigateToUrl('https://v0-attendance-app-with-api.vercel.app/')
WebUI.maximizeWindow()

// Verify Employee Attendance Page
boolean HasPage = WebUI.verifyElementText(findTestObject('AppInterface/EmployeePage'), 'Employee Attendance', FailureHandling.OPTIONAL)
WebUI.comment("Page Exists is " + HasPage)

if (HasPage) {
    // If element is found
    WebUI.comment("Employee Attendance page is ready")



    // Load Excel data
    def excelData = TestDataFactory.findTestData('Data Files/newEmployeeRecord')

    // Loop through rows and use values
    for (int row = 1; row <= excelData.getRowNumbers(); row++) {
        def employee = excelData.getValue(1, row)   // Column 1
        def role = excelData.getValue(2, row)       // Column 2
		
		WebUI.click(findTestObject('AppInterface/AddEmp'))
		WebUI.comment("Create Button Clicked")
	
		WebUI.verifyElementText(findTestObject('AppInterface/newEmployeeText'), 'Add New Employee')
		WebUI.comment("New Employee form opened")

        WebUI.comment("Processing record: " + employee + " - " + role)

        WebUI.setText(findTestObject('AppInterface/EmployeeTxt'), employee)
		WebUI.sendKeys(findTestObject('AppInterface/EmployeeTxt'), Keys.chord(Keys.TAB))
        WebUI.setText(findTestObject('AppInterface/RoleTxt'), role)
        WebUI.click(findTestObject('AppInterface/CreateBtn'))

        WebUI.delay(2)
    }

    // ✅ Close browser AFTER processing all rows
    WebUI.closeBrowser()

} else {
    WebUI.comment("Employee Page not found")
    WebUI.closeBrowser()
}



//WebUI.click(findTestObject('Object Repository/AppInterface/h1_Add New Employee_text-3xl font-bold text_011254'))

//WebUI.click(findTestObject('Object Repository/AppInterface/button_Employee Attendance_inline-flex item_768b4f'))

//WebUI.setText(findTestObject('Object Repository/AppInterface/input_Employee Name_employee-name'), 'Wilson')

//WebUI.setText(findTestObject('Object Repository/AppInterface/input_Assignment_employee-assignment'), 'QA')

//WebUI.setText(findTestObject('Object Repository/AppInterface/input_Employee Name_employee-name'), 'Wilson1')
