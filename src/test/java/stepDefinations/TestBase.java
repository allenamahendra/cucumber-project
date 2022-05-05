package stepDefinations;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import libraries.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBase {
    public static Map<String, String> world = new HashMap<>();
    public static Map<String, List<String>> worldList = new HashMap<>();
    public Logger logger = LogManager.getLogger(TestBase.class);
    public static WebDriver driver;
    public static int expWait;
    public static String browser;
    public static ExtentReports reports;
    public static ExtentTest test;
    public static ExtentHtmlReporter htmlReporter;
    public static String reportName = null;

    public static void refreshBrowser() {
        pause(1000);
        driver.navigate().refresh();
        pause(1000);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loader-style']")));
        pause(1000);
    }

    public static void waitForPageLoad() throws Exception {
        pause(1000);
        try {
            if (isElementcurrentlyDisplayed(By.xpath("//div[@class='loader-style']"))) {
                new WebDriverWait(driver, 60)
                        .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='loader-style']")));
                pause(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        protected void openDriverBrowser() {
            try {
                logger.info("Browser selected#" + browser);
                logger.info("Operating System#" + System.getProperty("os.name"));
                if (browser.equalsIgnoreCase("chrome")) {
                    LaunchChromeBrowser();
                } else if (browser.equalsIgnoreCase("edge")) {
                    LaunchEdge();
                }
                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
            } catch (Exception e) {
                logger.error("Browser failed to opened");
            }
        }

        protected void LaunchChromeBrowser() {
            try {
                System.setProperty("webdriver.chrome.silentOutput", "true");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920x1080");
                if (System.getProperty("os.name").equalsIgnoreCase("linux")) {
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver");
                    chromeOptions.addArguments("--headless");
                    chromeOptions.setBinary("/usr/bin/google-chrome");
                } else {
                    WebDriverManager.chromedriver().setup();  //chrome browser will launch
                }
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                driver = new ChromeDriver(chromeOptions);
                logger.info("Chrome Browser started");
            } catch (Exception e) {
                logger.info(e);
            }
        }

        public void LaunchEdge() {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            logger.info("Edge Browser started");
        }

        public boolean navigatetoURL() {
            try {
                driver.get(ConfigReader.getConfigValue("URL" ));
                logger.info("Loading:\t url");
            } catch (Exception e) {
                logger.error("Unable to navigate to URL");
            }
            return driver.getCurrentUrl().length() > 0;
        }

        public boolean sendKeys(By element, Keys text) {
            try {
                driver.findElement(element).sendKeys(text);
            } catch (Exception e) {
                logger.error("Element Not clicked " + e);
            }
            return true;
        }

        public boolean sendKeys(By element, String text) {
            try {
                driver.findElement(element).clear();
                toHighlight(element);
                logger.info(text + " entered in element: " + element);
                driver.findElement(element).sendKeys(text);
            } catch (Exception e) {
                logger.error("Element Not clicked " + e);
            }
            return true;
        }

        public boolean sendKeys(WebElement element, String text) {
            try {
                element.clear();
                logger.info(text + " entered in element: " + element);
                element.sendKeys(text);
            } catch (Exception e) {
                logger.error("Element Not clicked " + e);
            }
            return true;
        }

        public boolean sendKeys(WebElement element, char text) {
            try {
                element.clear();
                logger.info(text + " entered in element: " + element);
                String s = new StringBuilder().append(text).toString();
                element.sendKeys(s);
            } catch (Exception e) {
                logger.error("Element Not clicked " + e);
            }
            return true;
        }

        public boolean clearText(By element) {
            try {
                WebElement el = driver.findElement(element);
                if (el.getTagName().equalsIgnoreCase("input") && el.getAttribute("type").equalsIgnoreCase("text")) {
                    driver.findElement(element).clear();
                }

            } catch (Exception e) {
                logger.error(e);
            }
            return true;
        }

        protected boolean waitForStatus(By ele, String taSTatus) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 20);
                return wait.until(ExpectedConditions.textToBePresentInElementLocated(ele, taSTatus));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean actionClick(By element) {
            try {
                WebElement webElement = driver.findElement(element);
                Actions action = new Actions(driver);
                action.moveToElement(webElement).click().perform();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public void actionClick(WebElement element) {
            Actions action = new Actions(driver);
            action.moveToElement(element).click().build().perform();
        }

        public boolean click(By element) throws Exception {
            try {
                logger.info("Clicking object using By element : " + element);
                try {
                    waitUntilElementIsClickable(element, expWait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("Element is clickable with condition Implict wait");
                WebElement webElement = driver.findElement(element);
                if (webElement.isEnabled() && webElement.isDisplayed()) {
                    logger.info("Element is enabled or displayed in page");
                    toHighlight(element);
                    webElement.click();
                    return true;
                } else {
                    logger.error("Element is not enabled or displayed for click, will try javascript click next.");
                    return clickElementUsingJavaScript(element);
                }
            } catch (ElementNotInteractableException e) {
                logger.error("Element not interactable during click " + e.getMessage());

                return clickElementUsingJavaScript(element);
            } catch (WebDriverException e) {
                e.printStackTrace();
                logger.error("WebDriver exception during click " + e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL")) {
                    new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.elementToBeClickable(element)).click();
                    return true;
                } else
                    return clickElementUsingJavaScript(element);
            } catch (Exception e) {
                e.printStackTrace();
                return clickElementUsingJavaScript(element);
            }
        }

        public boolean moveToElement(By element) throws Exception {
            try {
                logger.info("Moving to element : " + element);
                try {
                    waitUntilElementIsClickable(element, expWait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                WebElement webElement = driver.findElement(element);
                if (webElement.isEnabled() && webElement.isDisplayed()) {
                    logger.info("Element is enabled or displayed in page");
                    toHighlight(element);
                    Actions action = new Actions(driver);
                    action.moveToElement(webElement).perform();

                    return true;
                }
            } catch (ElementNotInteractableException e) {
                logger.error("Element not interactable during move " + e.getMessage());
            }
            return false;
        }

        public boolean click(WebElement webElement) {
            try {
                logger.info("Clicking object using By element : " + webElement);
                try {
                    waitUntilElementIsClickable(webElement, expWait);
                } catch (Exception e) {

                }
                logger.info("Element is clickable with condition Implict wait");

                if (webElement.isEnabled() && webElement.isDisplayed()) {
                    logger.info("Element is enabled or displayed in page");
                    webElement.click();
                    return true;
                }
            } catch (WebDriverException e) {
                e.printStackTrace();
                logger.error("WebDriver exception during click " + e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL")) {
                    new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.elementToBeClickable(webElement)).click();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }

        public boolean waitUntilElementIsClickable(By locator, long seconds) {
            WebElement element = null;
            try {
                new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(locator));
            } catch (Exception e) {
                logger.info("Failed to wait for element to be clickable");
                throw e;
            }
            return true;
        }

        public boolean waitUntilElementIsInvisible(By locator, long seconds) {
            WebElement element = null;
            try {
                new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            } catch (Exception e) {
                logger.info("Failed to wait for element to be invisible");
                throw e;
            }
            return true;
        }

        public boolean waitUntilElementIsClickable(WebElement element, long seconds) throws Exception {
            try {
                new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(element));
            } catch (Exception e) {
                logger.info("Failed to wait for element to be clickable");
                throw e;
            }
            return true;
        }

        public boolean clickElementUsingJavaScript(By locator) throws Exception {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            try {
                try {
                    waitUntilElementIsClickable(locator, expWait);
                } catch (Exception e) {
                    // do nothing, continue to try and click element
                }
                jse.executeScript("arguments[0].click();", driver.findElement(locator));

                return true;
            } catch (TimeoutException e) {
                throw new Exception("Element " + locator.toString() + " was not found\n" + e.getMessage(), e);
            } catch (WebDriverException e) {
                if (e.getMessage().contains("JavaScript error")) {
                    logger.warn("Skipping exception with JavaScript error");
                } else if (!e.getMessage().contains("Missing Template ERR_CONNECT_FAIL")) {
                    logger.info("Failed to click: " + locator + " by javascript. Retrying..");
                    jse.executeScript("arguments[0].click();",
                            new WebDriverWait(driver, expWait)
                                    .until(ExpectedConditions.elementToBeClickable(locator)));
                    return true;
                } else
                    throw new Exception("Web driver exception clicking element with javascript " + locator.toString() + "\n"
                            + e.getMessage());
            } catch (Exception e) {
            }
            return false;
        }

        public boolean clickElementUsingJavaScript(WebElement locator) throws Exception {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            try {
                try {
                    waitUntilElementIsClickable(locator, 2);
                } catch (Exception e) {
                    // do nothing, continue to try and click element
                }
                jse.executeScript("arguments[0].click();", locator);

                return true;
            } catch (TimeoutException e) {
                throw new Exception("Element " + locator.toString() + " was not found\n" + e.getMessage(), e);
            } catch (WebDriverException e) {
                if (e.getMessage().contains("JavaScript error")) {
                    logger.warn("Skipping exception with JavaScript error");
                } else if (!e.getMessage().contains("Missing Template ERR_CONNECT_FAIL")) {
                    logger.info("Failed to click: " + locator + " by javascript. Retrying..");
                    jse.executeScript("arguments[0].click();",
                            new WebDriverWait(driver, 2)
                                    .until(ExpectedConditions.elementToBeClickable(locator)));
                    return true;
                } else
                    throw new Exception("Web driver exception clicking element with javascript " + locator.toString() + "\n"
                            + e.getMessage());
            } catch (Exception e) {
            }
            return false;
        }

        public boolean isElementCurrentlyDisplayed(By element) throws Exception {
            boolean isDisplayed = false;
            List<WebElement> elementList = driver.findElements(element);
            if (elementList.size() <= 0) {
                return false;
            } else if (elementList.size() > 1) {
                throw new Exception("Error: Found multiple elements");
            } else {
                WebElement foundElement = elementList.get(0);
                if (foundElement.isDisplayed()) {
                    isDisplayed = true;
                }
                return isDisplayed;
            }
        }

        public static boolean isElementcurrentlyDisplayed(By element) throws Exception {
            boolean isDisplayed = false;
            List<WebElement> elementList = driver.findElements(element);
            if (elementList.size() <= 0) {
                return false;
            } else if (elementList.size() > 1) {
                throw new Exception("Error: Found multiple elements");
            } else {
                WebElement foundElement = elementList.get(0);
                if (foundElement.isDisplayed()) {
                    isDisplayed = true;
                }
                return isDisplayed;
            }
        }

        public String getTextFromElement(By element) throws Exception {
            try {
                logger.info("Getting text from element : " + element + "");
                String innerText = new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.visibilityOfElementLocated(element)).getText().trim();
                logger.info("The Inner Text Of An Element is : " + innerText);
                return innerText;
            } catch (StaleElementReferenceException e) {
                return new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(element)))
                        .getText();
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL"))
                    return new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
                else
                    throw new Exception(e);
            }
        }

        public String getTextFromElements(WebElement element) throws Exception {
            try {
                logger.info("Getting text from element : " + element + "");
                String innerText = new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.visibilityOf(element)).getText().trim();
                logger.info("The Inner Text Of An Element is : " + innerText);
                return innerText;
            } catch (StaleElementReferenceException e) {
                return new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
                        .getText();
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL"))
                    return new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.visibilityOf(element)).getText();
                else
                    throw new Exception(e);
            }
        }

        public String getAttributeValueFromElement(By element, String attribute) throws Exception {
            try {
                logger.info("Getting text from element : " + element + "");
                String innerText = new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.visibilityOfElementLocated(element)).getAttribute(attribute).trim();
                logger.info("The attribute value of an Element is : " + innerText);
                return innerText;
            } catch (StaleElementReferenceException e) {
                return new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(element)))
                        .getAttribute(attribute);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL"))
                    return new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.visibilityOfElementLocated(element)).getAttribute(attribute);
                else
                    throw new Exception(e);
            }
        }

        public String getAttributeValueFromElement(WebElement element, String attribute) throws Exception {
            try {
                logger.info("Getting text from element : " + element + "");
                String innerText = new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.visibilityOf(element)).getAttribute(attribute).trim();
                logger.info("The attribute value of an Element is : " + innerText);
                return innerText;
            } catch (StaleElementReferenceException e) {
                return new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
                        .getAttribute(attribute);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL"))
                    return new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.visibilityOf(element)).getAttribute(attribute);
                else
                    throw new Exception(e);
            }
        }

        public String getAttributeValueFromElements(String attribute, WebElement element) throws Exception {
            try {
                logger.info("Getting text from element : " + element + "");
                String innerText = new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.visibilityOf(element)).getAttribute(attribute).trim();
                logger.info("The attribute value of an Element is : " + innerText);
                return innerText;
            } catch (StaleElementReferenceException e) {
                return new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)))
                        .getAttribute(attribute);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage());
                if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL"))
                    return new WebDriverWait(driver, expWait)
                            .until(ExpectedConditions.visibilityOf(element)).getAttribute(attribute);
                else
                    throw new Exception(e);
            }
        }

        public boolean isElementEnabled(By element) {
            WebElement ele = driver.findElement(element);
            if (ele.isEnabled()) {
                logger.info("Element enabled");
                return true;
            } else
                return false;
        }

        public boolean isElementSelected(By element) throws Exception {
            WebElement ele = driver.findElement(element);
            if (ele.isSelected()) {
                logger.info("Element enabled");
                return true;
            } else
                return false;
        }

        public boolean waitForElementToDisplay(By locator, long maxSecondsToWait) {
            for (int i = 0; i < maxSecondsToWait; i++) {
                try {
                    Thread.sleep(1000);
                    if (isElementCurrentlyDisplayed(locator)) {
                        return true;
                    }
                } catch (Exception e) {
                    // do nothing, let it keep looping to wait for object
                }
            }
            return false;
        }

        public boolean waitForElementToDisplay(WebElement locator, long maxSecondsToWait) {
            for (int i = 0; i < maxSecondsToWait; i++) {
                try {
                    Thread.sleep(1000);
                    if (locator.isDisplayed()) {
                        return true;
                    }
                } catch (Exception e) {
                    // do nothing, let it keep looping to wait for object
                }
            }
            return false;
        }

        public static void pause(long ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ie) {
            }
        }

        private void toHighlight(By element) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style','background:yellow;border: 2px solid red;');", driver.findElement(element));
        }

        private void toRemoveHighlight(By element) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].setAttribute('style','background:nill;border: nill;');", driver.findElement(element));
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
        }

        protected boolean selectByVisibleText(By element, String str) {
            try {
                waitForElementToDisplay(element,10);
                if(isElementCurrentlyDisplayed(element)) {
                    WebElement ele = driver.findElement(element);
                    Actions action = new Actions(driver);
                    action.moveToElement(ele).perform();
                    Select select = new Select(ele);
                    select.selectByVisibleText(str);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public static boolean isAlertPresent() {
            try {
                driver.switchTo().alert();
                return true;
            } catch (NoAlertPresentException ex) {
                return false;
            }
        }

        protected boolean isAttributePresent(WebElement element, String attribute) {
            Boolean result = false;
            try {
                String value = element.getAttribute(attribute);
                if (value != null) {
                    result = true;
                }
            } catch (Exception e) {
            }
            return result;
        }

        protected boolean isAttributePresent(By ele, String attribute) {
            Boolean result = false;
            try {
                WebElement element = driver.findElement(ele);
                String value = element.getAttribute(attribute);
                if (value != null) {
                    result = true;
                }
            } catch (Exception e) {
            }
            return result;
        }

        public void scrollIntoView(By ele){
            waitForElementToDisplay(ele, 5);
            WebElement element = driver.findElement(ele);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",element);
        }

        public void scrollIntoView(WebElement ele){
            waitForElementToDisplay(ele, 2);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",ele);
        }
    }