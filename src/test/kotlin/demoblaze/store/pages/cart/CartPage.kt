package demoblaze.store.pages.cart

import demoblaze.objects.Wait
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import java.util.logging.Logger

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
        targetProduct.findElement(productDeleteButtonLocator).click()
    }

    private fun waitProductDisappear(targetProduct: WebElement) {
        Wait.defaultWait(driver).until(
            ExpectedConditions.invisibilityOf(targetProduct)
        )
    }

    fun getTotalPrice() =
        Wait.defaultWait(driver).until(
            ExpectedConditions.visibilityOfElementLocated((totalPriceLocator))
        ).text.toDouble()

    fun placeOrder() {
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable((placeOrderButtonLocator))
        ).click()
    }

    fun completeForm(customerName: String, creditCardNumber: String) {
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable((customerNameLocator))
        ).sendKeys(customerName)
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable((customerCardLocator))
        ).sendKeys(creditCardNumber)
        driver.findElement(purchaseButtonLocator).click()
    }

    fun `is successful purchase icon visible`() =
        findSuccessIcon().getCssValue("display") == "block"

    private fun findSuccessIcon() = findSweetAlert().findElement(successIconLocator)

    private fun findSweetAlert() = Wait.defaultWait(driver).until(
        ExpectedConditions.visibilityOfElementLocated((sweetAlertLocator))
    )

    fun findTotalPurchasePrice(): Double {
        val leadText = findSweetAlert().findElement(By.className("lead")).text!!
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
        private val purchaseButtonLocator = By.cssSelector("#orderModal .modal-footer .btn-primary")
        private val sweetAlertLocator = By.className("sweet-alert")
        private val successIconLocator = By.className("sa-success")
    }
}

