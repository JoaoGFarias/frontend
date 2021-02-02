package demoblaze.store.pages.cart

import demoblaze.objects.Wait
import demoblaze.store.pages.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import java.util.logging.Logger

class CartPage(private val driver: WebDriver): Page(driver) {

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

    private fun findAllProducts() = findAllWhenAvailable(allProductsLocator)

    private fun deleteProduct(targetProduct: WebElement) {
        click(targetProduct, productDeleteButtonLocator)
    }

    private fun waitProductDisappear(targetProduct: WebElement) {
        waitToDisappear(targetProduct)
    }

    fun getTotalPrice() =
        getTextWhenAvailable(totalPriceLocator).toDouble()

    fun placeOrder() {
        clickWhenAvailable(placeOrderButtonLocator)
    }

    fun completeForm(customerName: String, creditCardNumber: String) {
        addCustomerName(customerName)
        addCreditCard(creditCardNumber)
        completePurchase()
    }

    private fun addCustomerName(customerName: String) {
        sendText(locator = customerNameLocator, text = customerName)
    }

    private fun addCreditCard(creditCardNumber: String) {
        sendText(locator = customerCardLocator, text = creditCardNumber)
    }

    private fun completePurchase() {
        click(purchaseButtonLocator)
    }

    fun `is successful purchase icon visible`() =
        findSuccessIcon().getCssValue("display") == "block"

    private fun findSuccessIcon() = find(findSweetAlert(), successIconLocator)

    private fun findSweetAlert() = findWhenAvailable(sweetAlertLocator)

    fun findTotalPurchasePrice(): Double {
        val leadText = find(findSweetAlert(), leadLocator).text!!
        val orderDetails = makeOrderDetails(leadText)
        logger.info("Order id ${orderDetails["id"]}")
        logger.info("Order amount ${orderDetails["amount"]}")
        return extractValueOfOrder(orderDetails)
    }

    private fun makeOrderDetails(leadText: String): HashMap<String, String> {
        val orderDetails = HashMap<String, String>()
        leadText.split("\n").map { it.split(":") }.forEach { orderDetails[it[0].toLowerCase()] = it[1].trim() }
        return orderDetails
    }

    private fun extractValueOfOrder(orderDetails: HashMap<String, String>) =
        orderDetails["amount"]!!.split(" ")[0].toDouble()

    companion object {
        private val logger = Logger.getLogger(this.javaClass.name)
        private val allProductsLocator = By.cssSelector("#tbodyid .success")
        private val productDeleteButtonLocator = By.tagName("a")
        private val totalPriceLocator = By.id("totalp")
        private val placeOrderButtonLocator = By.className("btn-success")
        private val customerNameLocator = By.id("name")
        private val customerCardLocator = By.id("card")
        private val leadLocator = By.className("lead")
        private val purchaseButtonLocator = By.cssSelector("#orderModal .modal-footer .btn-primary")
        private val sweetAlertLocator = By.className("sweet-alert")
        private val successIconLocator = By.className("sa-success")
    }
}

