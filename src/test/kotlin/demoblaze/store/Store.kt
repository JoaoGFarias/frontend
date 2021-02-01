package demoblaze.store

import demoblaze.store.pages.cart.CartPage
import demoblaze.store.pages.home.HomePage
import demoblaze.store.stateobjects.Cart
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
class Store(
    private val driver: WebDriver,
    val cart: Cart = Cart()
) {

    private lateinit var cartPage: CartPage
    private lateinit var homePage: HomePage

    fun visitHomePage() {
        homePage = HomePage(driver).openPage()
    }

    fun closeStore() {
        driver.quit()
    }

    fun addProductToCart(productName: String, category: String) {
        selectCategory(category)
        Thread.sleep(2500)
        selectProduct(productName)
    }

    private fun selectCategory(category: String) {
        homePage.selectCategory(category)
    }

    private fun selectProduct(productName: String) {
        val productPage = homePage.selectProduct(productName)
        productPage.addProductToCart()
        this.visitHomePage()
    }

    fun goToCart() {
        cartPage = homePage.goToCart()
    }


    fun deleteProductFromCart(productName: String) {
        cartPage.deleteProductFromCart(productName)
    }
}

