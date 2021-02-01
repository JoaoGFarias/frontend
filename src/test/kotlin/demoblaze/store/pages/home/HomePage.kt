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
        categories.findElement(By.ByXPath("//a[text()='$category']")).click()
    }

    fun selectProduct(productName: String): ProductPage {
        val products = findAllProducts()

        val targetProduct = findProductByName(products, productName)

        selectProduct(targetProduct)
        return ProductPage(driver)
    }

    private fun findAllProducts() = Wait.defaultWait(driver).until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(productLocator)
    )

    private fun selectProduct(targetProduct: WebElement?) {
        targetProduct!!.findElement(productLinkLocator)!!.click()
    }

    private fun findProductByName(
        products: MutableList<WebElement>,
        productName: String
    ) = products.find {
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable(it.findElement(productNameLocator))
        ).text == productName
    }

    fun goToCart(): CartPage {
        driver.findElement(cartPageLinkLocator).click()
        return CartPage(driver)
    }

    companion object {
        private val categoriesLocator = By.className("list-group")
        private val productLocator = By.cssSelector("#tbodyid .card")
        private val productLinkLocator = By.tagName("a")
        private val productNameLocator = By.className("card-title")
        private val cartPageLinkLocator = By.id("cartur")
    }
}
