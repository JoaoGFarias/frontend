package browser

import org.openqa.selenium.WebDriver

class BrowserFactory {
    fun makeBrowser(): WebDriver {
        val browserName = System.getProperty("browser")
        return when (browserName) {
            "chrome" -> ChromeBrowser().makeBrowser()
            else -> FirefoxBrowser().makeBrowser()
        }
    }
}