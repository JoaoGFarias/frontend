package demoblaze.store.pages

import demoblaze.objects.Wait
import demoblaze.store.pages.cart.CartPage
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.WebElement

abstract class Page(private val driver: WebDriver) {

    fun clickWhenAvailable(locator: By) {
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable(locator)
        ).click()
    }

    fun acceptAlert() {
        Wait.defaultWait(driver).until(ExpectedConditions.alertIsPresent())
        driver.switchTo().alert().accept()
    }

    fun open(url: String) {
        driver.get(url)
    }

    fun find(locator: By): WebElement = driver.findElement(locator)

    fun find(target: WebElement, locator: By): WebElement =
        target.findElement(locator)

    fun clickWhenAvailable(finder: () -> WebElement) {
        Wait.defaultWait(driver)
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                finder().click()
                true
            }
    }

    fun findWhenAvailable(locator: By): WebElement =
        Wait.defaultWait(driver).until(
            ExpectedConditions.visibilityOfElementLocated(locator)
        )

    fun findAllWhenAvailable(locator: By): MutableList<WebElement> =
        Wait.defaultWait(driver).until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)
        )

    fun click(locator: By) {
        find(locator).click()
    }

    fun click(target: WebElement, locator: By) {
        find(target, locator).click()
    }

    fun waitToDisappear(target: WebElement) {
        Wait.defaultWait(driver).until(
            ExpectedConditions.invisibilityOf(target)
        )
    }

    fun getTextWhenAvailable(locator: By): String =
        findWhenAvailable(locator).text

    fun sendText(locator: By, text: String) {
        Wait.defaultWait(driver).until(
            ExpectedConditions.elementToBeClickable(locator)
        ).sendKeys(text)
    }

}