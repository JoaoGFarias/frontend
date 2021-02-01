package demoblaze.store

import demoblaze.store.pages.home.HomePage
import demoblaze.store.stateobjects.Cart
import org.openqa.selenium.WebDriver

class Store(
    private val driver: WebDriver,
    val cart: Cart = Cart()
) {

    private lateinit var homePage: HomePage

    fun visitHomePage() {
        homePage = HomePage(driver)
    }

    fun closeStore() {
        driver.quit()
    }
}