package demoblaze.store.pages.product

import demoblaze.store.pages.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.util.logging.Logger

class ProductPage(driver: WebDriver): Page(driver) {
    fun addProductToCart(): Double {
        logger.info { "Product page - Adding product to cart" }
        clickWhenAvailable(addToCartButtonLocator)
        acceptAlert()
        return productPrice()
    }

    private fun productPrice(): Double {
        val priceText = getTextWhenAvailable(priceContainer)
        return priceText.removePrefix("$").removeSuffix(" *includes tax").toDouble()
    }

    companion object {
        private val logger = Logger.getLogger(this.javaClass.name)
        private val addToCartButtonLocator = By.className("btn-success")
        private val priceContainer = By.className("price-container")
    }

}
