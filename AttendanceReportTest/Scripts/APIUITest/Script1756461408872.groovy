import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys


// Load the Excel Data File 

def testData = findTestData('Data Files/newEmployeeAPI')

for (def i = 1; i <= testData.getRowNumbers(); i++) {
	String name = testData.getValue('Name', i)
	String assignment = testData.getValue('Assignment', i)
	String checkin = testData.getValue('Checkin', i)
	String lastcheckin = testData.getValue('LastCheckin', i)
	
	println(name)
	println(assignment)
	println(checkin)
	println(lastcheckin)
	

	// Send API request
	def response = WS.sendRequest(findTestObject('Object Repository/PostRequest', [
		('name') : name,
		('assignment') : assignment,
		('checkin') : checkin,
		('lastcheckin') : lastcheckin
	]))

	println("Response Status: " + response.getStatusCode())
	println("Response Body: " + response.getResponseBodyContent())

	WS.verifyResponseStatusCode(response, 200)

	// UI verification
	WebUI.openBrowser('https://v0-attendance-app-with-api.vercel.app/')
	
	WebUI.click(findTestObject('APIUI Validation/selectEmployee'))
	WebUI.setText(findTestObject('APIUI Validation/setSearch'), name+' -'+ assignment)

	// Variable string for the employee name
	String employeeName = name + ' - ' + assignment

	// Build the dynamic XPath with the variable
	String dynamicXpath = "//div[@data-slot='command-item' and @role='option' and normalize-space()='" + employeeName + "']"

	// Create a TestObject with this XPath
	TestObject optionObj = new TestObject("dynamicOption")
	optionObj.addProperty("xpath", ConditionType.EQUALS, dynamicXpath)

	//Verify new employee added
	WebUI.verifyElementText(optionObj, employeeName)
		
		WebUI.closeBrowser()
}