package demoblaze.store.pages.product

import demoblaze.store.pages.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ProductPage(driver: WebDriver): Page(driver) {
    fun addProductToCart() {
        clickWhenAvailable(addToCartButtonLocator)
        acceptAlert()
    }

    companion object {
        private val addToCartButtonLocator = By.className("btn-success")
    }

}
