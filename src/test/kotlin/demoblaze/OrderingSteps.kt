package demoblaze

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.cucumber.java8.En
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.cucumber.java8.HookNoArgsBody
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.logging.Logger


class OrderingSteps: En {


    private lateinit var expectedOrderTotal: String
    private lateinit var driver: ChromeDriver

    init {

        Before(HookNoArgsBody { startBrowser() })

        Given("I am on the Demoblaze Home page") {
            visitHomePage()
        }

        When("select the {string} product in the {string} category") { 
                productName: String, category: String ->
            run {
                selectCategory(category)
                selectProduct(productName)
            }
        }

        When("I go to the cart") {
            driver.findElement(By.id("cartur")).click()
        }

        When("I delete the {string} from the cart") { 
                productName: String ->
            run {
                deleteProductFromCart(productName)
            }
        }

        When("I complete the order, for the customer with name {string} and credit card {string}") {
                customerName: String, creditCardNumber: String ->
            run {
                saveExpectedTotal()
                placeTheOrder()
                completeForm(customerName, creditCardNumber)
            }
        }

        Then("the order is confirmed") {
            val successIcon = findSweetAlert().findElement(By.className("sa-success"))
            assertThat(successIcon.getCssValue("display")).isEqualTo("block")
        }

        Then("the expected total purchase total is the sum of the ordered products") {
            val leadText = findSweetAlert().findElement(By.className("lead")).text!!
            val orderDetails = makeOrderDetails(leadText)
            assertThat(orderDetails["amount"]!!.split(" ")[0]).isEqualTo(expectedOrderTotal)
            logger.info("Order id ${orderDetails["id"]}")
            logger.info("Order amount ${orderDetails["amount"]}")
        }

        After(HookNoArgsBody { killBrowser() })
    }


    private fun findSweetAlert() = WebDriverWait(driver, Duration.ofSeconds(5)).until(
        ExpectedConditions.visibilityOfElementLocated((By.className("sweet-alert")))
    )

    private fun startBrowser() {
        val options = ChromeOptions()
        options.setBinary("/usr/bin/brave")
        driver = ChromeDriver(options)
        driver.manage().window().maximize()
    }

    private fun visitHomePage() {
        driver.get("https://www.demoblaze.com/index.html")
    }

    private fun selectCategory(category: String) {
        val categories = driver.findElement(By.className("list-group"))
        categories.findElement(By.ByXPath("//a[text()='$category']")).click()
    }

    private fun selectProduct(productName: String) {
        val products = WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy((By.cssSelector("#tbodyid .card")))
        )

        val targetProduct = products.find {
            WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(it.findElement(By.className("card-title")))
            ).text == productName
        }

        targetProduct?.findElement(By.tagName("a"))?.click()
        WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.elementToBeClickable((By.className("btn-success")))
        ).click()
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept()
        visitHomePage()
    }

    private fun deleteProductFromCart(productName: String) {
        val products = WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector("#tbodyid .success")))
        )
        val targetProduct = products.find { it.text.contains(productName) }!!
        targetProduct.findElement(By.tagName("a")).click()
        WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.invisibilityOf(targetProduct)
        )
    }

    private fun saveExpectedTotal() {
        expectedOrderTotal = WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.visibilityOfElementLocated((By.id("totalp")))
        ).text
    }

    private fun placeTheOrder() {
        WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.elementToBeClickable((By.className("btn-success")))
        ).click()
    }

    private fun completeForm(customerName: String, creditCardNumber: String) {
        WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.elementToBeClickable((By.id("name")))
        ).sendKeys(customerName)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.elementToBeClickable((By.id("card")))
        ).sendKeys(creditCardNumber)
        driver.findElement(By.cssSelector("#orderModal .modal-footer .btn-primary")).click()
    }

    private fun makeOrderDetails(leadText: String): HashMap<String, String> {
        val orderDetails = HashMap<String, String>()
        leadText.split("\n").map { it.split(":") }.forEach { orderDetails[it[0].toLowerCase()] = it[1].trim() }
        return orderDetails
    }

    private fun killBrowser() {
        driver.quit()
    }

    companion object {
        private val logger = Logger.getLogger(this.javaClass.name)
    }
}