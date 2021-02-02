package browser

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.picocontainer.Disposable

class Browser: WebDriver, Disposable {
    private var delegate: WebDriver? = null

    private fun getDelegate(): WebDriver {
        if (delegate == null) {
            delegate = BrowserFactory().makeBrowser()
        }
        return delegate!!
    }

    override fun findElements(by: By): MutableList<WebElement> = getDelegate().findElements(by)

    override fun findElement(by: By): WebElement = getDelegate().findElement(by)

    override fun get(url: String) = getDelegate().get(url)

    override fun getCurrentUrl(): String = getDelegate().currentUrl

    override fun getTitle(): String = getDelegate().title

    override fun getPageSource(): String = getDelegate().pageSource

    override fun close() {
        getDelegate().close()
    }

    override fun quit() {
        getDelegate().quit()
    }

    override fun getWindowHandles(): MutableSet<String> = getDelegate().windowHandles

    override fun getWindowHandle(): String = getDelegate().windowHandle

    override fun switchTo(): WebDriver.TargetLocator = getDelegate().switchTo()

    override fun navigate(): WebDriver.Navigation = getDelegate().navigate()

    override fun manage(): WebDriver.Options = getDelegate().manage()

    override fun dispose() {
        quit()
    }
}