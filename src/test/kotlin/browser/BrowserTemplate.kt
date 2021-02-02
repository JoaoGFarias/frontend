package browser

import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.AbstractDriverOptions

interface BrowserTemplate {
    fun makeBrowser(): WebDriver {
        val options = getOptions()
        val driver = getDriver(options)
        driver.manage().window().maximize()
        return driver
    }

    fun getDriver(options: AbstractDriverOptions<*>): WebDriver
    fun getOptions(): AbstractDriverOptions<*>
}