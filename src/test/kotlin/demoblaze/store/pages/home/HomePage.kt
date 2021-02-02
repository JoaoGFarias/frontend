package demoblaze.store.pages.home

import demoblaze.store.pages.Page
import demoblaze.store.pages.cart.CartPage
import demoblaze.store.pages.product.ProductPage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement


class HomePage(private val driver: WebDriver): Page(driver) {

    fun openPage(): HomePage {
        open(url)
        return this
    }

    fun selectCategory(category: String) {
        val categories = findCategories()
        selectCategory(categories, category)
    }

    private fun findCategories() = find(categoriesLocator)

    private fun selectCategory(categories: WebElement, category: String) {
        categories.findElement(makeLinkLocatorByInnerText(category)).click()
    }

    fun selectProduct(productName: String): ProductPage {
        clickOnProductByName(productName)
        return ProductPage(driver)
    }

    private fun clickOnProductByName(
        productName: String
    ) {
        clickWhenAvailable(finder = { findProductByName(productName) })
    }

    private fun findProductByName(productName: String) = find(makeLinkLocatorByInnerText(productName))

    fun goToCart(): CartPage {
        clickWhenAvailable(cartPageLinkLocator)
        return CartPage(driver)
    }

    companion object {
        private val categoriesLocator = By.className("list-group")
        private val cartPageLinkLocator = By.id("cartur")

        fun makeLinkLocatorByInnerText(text: String) = By.ByXPath("//a[text()='$text']")

        private val url = "https://www.demoblaze.com/index.html"
    }
}
