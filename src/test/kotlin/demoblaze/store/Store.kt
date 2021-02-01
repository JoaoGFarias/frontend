package demoblaze.store

import demoblaze.store.pages.home.HomePage
import demoblaze.store.stateobjects.Cart
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class Store(
    private val driver: WebDriver,
    val cart: Cart = Cart()
) {

    private lateinit var homePage: HomePage

    fun visitHomePage() {
        homePage = HomePage(driver)
    }

    fun closeStore() {
        driver.quit()
    }

    fun addProductToCart(productName: String, category: String) {
        selectCategory(category)
        Thread.sleep(2000)
        selectProduct(productName)
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
        this.visitHomePage()
    }
}