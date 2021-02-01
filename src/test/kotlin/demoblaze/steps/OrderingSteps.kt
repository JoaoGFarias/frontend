package demoblaze.steps

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.google.common.util.concurrent.AtomicDouble
import demoblaze.store.Store
import io.cucumber.java8.En
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.cucumber.java8.HookNoArgsBody
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.logging.Logger


class OrderingSteps: En {


    private lateinit var store: Store
    private lateinit var driver: ChromeDriver

    init {

        Before(HookNoArgsBody { startStore() })

        Given("I am on the Demoblaze Home page") {
            store.visitHomePage()
        }

        When("select the {string} product in the {string} category") { 
                productName: String, category: String ->
            run {
                store.addProductToCart(productName = productName, category = category)
            }
        }

        When("I go to the cart") {
            store.goToCart()
        }

        When("I delete the {string} from the cart") { 
                productName: String ->
            run {
                store.deleteProductFromCart(productName)
            }
        }

        When("I complete the order, for the customer with name {string} and credit card {string}") {
                customerName: String, creditCardNumber: String ->
            run {
                store.saveExpectedTotal()
                store.placeTheOrder()
                store.completeForm(customerName, creditCardNumber)
            }
        }

        Then("the order is confirmed") {
            assertThat(store.`is successful purchase icon visible`()).isTrue()
        }

        Then("the expected total purchase total is the sum of the ordered products") {
            val leadText = findSweetAlert().findElement(By.className("lead")).text!!
            val orderDetails = makeOrderDetails(leadText)
            assertThat(orderDetails["amount"]!!.split(" ")[0].toDouble()).isEqualTo(store.cart.getTotal())
            logger.info("Order id ${orderDetails["id"]}")
            logger.info("Order amount ${orderDetails["amount"]}")
        }

        After(HookNoArgsBody { closeStore() })
    }

    private fun startStore() {
        store = Store(startBrowser())
    }

    private fun startBrowser(): WebDriver {
        val options = ChromeOptions()
        options.setBinary("/usr/bin/brave")
        driver = ChromeDriver(options)
        driver.manage().window().maximize()
        return driver
    }

    private fun selectCategory(category: String) {
        val categories = driver.findElement(By.className("list-group"))
        categories.findElement(By.ByXPath("//a[text()='$category']")).click()
    }

    private fun selectProduct(productName: String) {
        val products = WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector("#tbodyid .card")))
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
        store.visitHomePage()
    }

    private fun findSweetAlert() = WebDriverWait(driver, Duration.ofSeconds(5)).until(
        ExpectedConditions.visibilityOfElementLocated((By.className("sweet-alert")))
    )

    private fun makeOrderDetails(leadText: String): HashMap<String, String> {
        val orderDetails = HashMap<String, String>()
        leadText.split("\n").map { it.split(":") }.forEach { orderDetails[it[0].toLowerCase()] = it[1].trim() }
        return orderDetails
    }

    private fun closeStore() {
        store.closeStore()
    }

    companion object {
        private val logger = Logger.getLogger(this.javaClass.name)
    }
}