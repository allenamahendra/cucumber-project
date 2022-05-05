package runners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import libraries.ConfigReader;
import org.testng.annotations.*;
import stepDefinations.TestBase;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@CucumberOptions
        (features = "src/test/resources",
                plugin = {"pretty", "html:target/cucumber-html-report.html"
                        ,"html:target/cucumber_reports/cucumber_pretty.html"
                        ,"json:target/cucumber_reports/cucumberTestReport.json"
//                        ,"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter"
                },
                glue = {"stepDefinations"},
                tags = "@RUN")

public class TestRunner extends AbstractTestNGCucumberTests {
    String tags = "";
    static SimpleDateFormat format = null;
    static Calendar cal = null;
    String reportTime;
    public Object[][] data;

    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
    }

//    @AfterMethod
    public void closeBrowser() throws SQLException {
        if (TestBase.driver == null) {
        } else {
            TestBase.driver.quit();
        }
    }

    @BeforeSuite
    public void setUp() throws IOException {
        String tempBrowser = System.getProperty("Browser");
        TestBase.browser = ConfigReader.getConfigValue("Browser");
        TestBase.expWait = Integer.parseInt(ConfigReader.getConfigValue("DefaultWaitTime"));
        if(tempBrowser != null && !tempBrowser.isEmpty()){
            TestBase.browser = tempBrowser;
        }
        System.out.println("Browser#"+TestBase.browser);

        setUpExtentReports();
    }

    private void setUpExtentReports() {
        cal = Calendar.getInstance();
        format = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss");
        TestBase.reports = new ExtentReports();
        SimpleDateFormat formatt = new SimpleDateFormat("ddMMMyyyy_HH-mm");
        reportTime = formatt.format(cal.getTime());
        TestBase.reportName = System.getProperty("user.dir") + "/Reports/ExtentReport.html";
        TestBase.htmlReporter = new ExtentHtmlReporter(new File(TestBase.reportName));
        TestBase.htmlReporter.loadXMLConfig(String.valueOf(new File(System.getProperty("user.dir") + "/src/test/resources/extent-config.xml")));
        TestBase.reports.setSystemInfo("Browser",   TestBase.browser);
        TestBase.reports.setSystemInfo("Author", "Mahesh");
        TestBase.reports.setSystemInfo("Executed By", System.getProperty("user.name"));
        TestBase.reports.setSystemInfo("Operating System", System.getProperty("os.name"));
        TestBase.reports.attachReporter( TestBase.htmlReporter);
    }
}