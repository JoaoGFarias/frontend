package demoblaze.store.pages.cart

import demoblaze.objects.Wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions

class CartPage(private val driver: WebDriver) {

    fun deleteProductFromCart(productName: String) {
        val targetProduct = findProductByName(productName)
        deleteProduct(targetProduct)
        waitProductDisappear(targetProduct)
    }

    private fun findProductByName(
        productName: String
    ): WebElement {
        val products = findAllProducts()
        return products.find { it.text.contains(productName) }!!
    }

    private fun findAllProducts() = Wait.defaultWait(driver).until(
        ExpectedConditions.visibilityOfAllElementsLocatedBy((allProductsLocator))
    )

    private fun deleteProduct(targetProduct: WebElement) {
        targetProduct.findElement(productDeleteButton).click()
    }

    private fun waitProductDisappear(targetProduct: WebElement) {
        Wait.defaultWait(driver).until(
            ExpectedConditions.invisibilityOf(targetProduct)
        )
    }

    companion object {
        private val allProductsLocator = By.cssSelector("#tbodyid .success")
        private val productDeleteButton = By.tagName("a")
    }
}

