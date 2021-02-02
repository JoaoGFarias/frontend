package demoblaze.steps

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import browser.Browser
import demoblaze.store.Store
import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import org.openqa.selenium.WebDriver

class OrderingSteps(private val browser: Browser): En {


    private lateinit var store: Store

    init {

        Before(HookNoArgsBody { startStore() })

        Given("I am on the Demoblaze Home page") {
            store.visitHomePage()
        }

        When("select the {string} product in the {string} category") {
                productName: String, category: String ->
            run {
                store.addProductToCart(productName = productName, category = category)
            }
        }

        When("I go to the cart") {
            store.goToCart()
        }

        When("I delete the {string} from the cart") { 
                productName: String ->
            run {
                store.deleteProductFromCart(productName)
            }
        }

        When("I complete the order, for the customer with name {string} and credit card {string}") {
                customerName: String, creditCardNumber: String ->
            run {
//                store.saveExpectedTotal()
                store.placeTheOrder()
                store.completeForm(customerName, creditCardNumber)
            }
        }

        Then("the order is confirmed") {
            assertThat(store.`is successful purchase icon visible`()).isTrue()
        }

        Then("the expected total purchase price is the sum of the ordered products") {
            val totalPrice = store.findTotalPurchasePrice()
            assertThat(totalPrice).isEqualTo(store.cart.getTotal())
        }

    }

    private fun startStore() {
        store = Store(browser)
    }
}