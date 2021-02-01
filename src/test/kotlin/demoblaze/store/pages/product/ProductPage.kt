package demoblaze.store.pages.product

import demoblaze.objects.Wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class ProductPage(private val driver: WebDriver) {
    fun addProductToCart() {
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable(addToCartButtonLocator)
        ).click()
        Wait.defaultWait(driver).until(ExpectedConditions.alertIsPresent())
        driver.switchTo().alert().accept()
    }

    companion object {
        private val addToCartButtonLocator = By.className("btn-success")
    }

}
