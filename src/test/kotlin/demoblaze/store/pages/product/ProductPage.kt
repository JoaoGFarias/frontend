package demoblaze.store.pages.product

import demoblaze.store.pages.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ProductPage(driver: WebDriver): Page(driver) {
    fun addProductToCart(): Double {
        clickWhenAvailable(addToCartButtonLocator)
        acceptAlert()
        return productPrice()
    }

    private fun productPrice(): Double {
        val priceText = getTextWhenAvailable(priceContainer)
        return priceText.removePrefix("$").removeSuffix(" *includes tax").toDouble()
    }

    companion object {
        private val addToCartButtonLocator = By.className("btn-success")
        private val priceContainer = By.className("price-container")
    }

}
