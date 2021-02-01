package demoblaze.store.pages.home

import demoblaze.objects.Wait
import demoblaze.store.pages.cart.CartPage
import demoblaze.store.pages.product.ProductPage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions

class HomePage(private val driver: WebDriver) {

    fun openPage(): HomePage {
        driver.get("https://www.demoblaze.com/index.html")
        return this
    }

    fun selectCategory(category: String) {
        val categories = findCategories()
        selectCategory(categories, category)
    }

    private fun findCategories() = driver.findElement(categoriesLocator)

    private fun selectCategory(categories: WebElement, category: String) {
        categories.findElement(makeLinkLocatorByInnerText(category)).click()
    }

    fun selectProduct(productName: String): ProductPage {
        findProductByName(productName).click()
        return ProductPage(driver)
    }

    private fun findProductByName(
        productName: String
    ) = Wait.defaultWait(driver).until(
            ExpectedConditions.visibilityOfElementLocated(makeLinkLocatorByInnerText(productName))
        )

    fun goToCart(): CartPage {
        driver.findElement(cartPageLinkLocator).click()
        return CartPage(driver)
    }

    companion object {
        private val categoriesLocator = By.className("list-group")
        private val cartPageLinkLocator = By.id("cartur")

        fun makeLinkLocatorByInnerText(text: String) = By.ByXPath("//a[text()='$text']")
    }
}
