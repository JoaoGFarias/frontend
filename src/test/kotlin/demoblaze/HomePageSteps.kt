package demoblaze

import io.cucumber.java8.En
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.cucumber.java8.HookNoArgsBody
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class HomePageSteps: En {


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

        After(HookNoArgsBody { killBrowser() })
    }

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
            ExpectedConditions.presenceOfAllElementsLocatedBy((By.cssSelector("#tbodyid .card")))
        )
        val targetProduct = products.find { it.findElement(By.className("card-title")).text == productName }

        targetProduct?.findElement(By.tagName("a"))?.click()
        WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.elementToBeClickable((By.className("btn-success")))
        ).click()
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept()
        visitHomePage()
    }

    private fun killBrowser() {
        driver.quit()
    }
}