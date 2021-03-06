package demoblaze.store

import demoblaze.store.pages.cart.CartPage
import demoblaze.store.pages.home.HomePage
import demoblaze.store.stateobjects.Cart
import org.openqa.selenium.WebDriver

class Store(
    private val driver: WebDriver,
    val cart: Cart = Cart()
) {

    private lateinit var cartPage: CartPage
    private lateinit var homePage: HomePage

    fun visitHomePage() {
        homePage = HomePage(driver).openPage()
    }

    fun addProductToCart(productName: String, category: String) {
        selectCategory(category)
        selectProduct(productName)
    }

    private fun selectCategory(category: String) {
        homePage.selectCategory(category)
    }

    private fun selectProduct(productName: String) {
        val productPage = homePage.selectProduct(productName)
        val productPrice = productPage.addProductToCart()
        this.cart.addItem(productPrice)
        this.visitHomePage()
    }

    fun goToCart() {
        cartPage = homePage.goToCart()
    }


    fun deleteProductFromCart(productName: String) {
        val productPrice = cartPage.deleteProductFromCart(productName)
        this.cart.removeItem(productPrice)
    }

    fun placeTheOrder() {
        cartPage.placeOrder()
    }

    fun completeForm(customerName: String, creditCardNumber: String) {
        cartPage.completeForm(customerName, creditCardNumber)
    }

    fun `is successful purchase icon visible`() =
        cartPage.`is successful purchase icon visible`()

    fun findTotalPurchasePrice() =
        cartPage.findTotalPurchasePrice()
}

