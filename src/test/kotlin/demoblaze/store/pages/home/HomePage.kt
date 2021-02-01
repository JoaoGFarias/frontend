package demoblaze.store.pages.home

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class HomePage(private val driver: WebDriver) {

    init {
        openPage()
    }

    private fun openPage() {
        driver.get("https://www.demoblaze.com/index.html")
    }

    fun selectCategory(category: String) {
        val categories = driver.findElement(By.className("list-group"))
        categories.findElement(By.ByXPath("//a[text()='$category']")).click()
    }

    fun selectProduct(productName: String) {
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
    }
}