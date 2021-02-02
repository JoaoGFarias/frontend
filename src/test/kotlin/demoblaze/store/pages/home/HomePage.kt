package demoblaze.store.pages.home

import demoblaze.objects.Wait
import demoblaze.store.pages.cart.CartPage
import demoblaze.store.pages.product.ProductPage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.StaleElementReferenceException

import org.openqa.selenium.support.ui.WebDriverWait
import java.util.function.Function


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
        clickOnProductByName(productName)
        return ProductPage(driver)
    }

    private fun clickOnProductByName(
        productName: String
    ) {
        Wait.defaultWait(driver)
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                findProductByName(productName).click()
                true
            }
    }

    private fun findProductByName(productName: String) = driver.findElement(makeLinkLocatorByInnerText(productName))

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
