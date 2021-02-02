package browser

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.AbstractDriverOptions

class FirefoxBrowser: BrowserTemplate {

    private lateinit var driver: FirefoxDriver

    override fun getDriver(options: AbstractDriverOptions<*>): WebDriver {
        driver = FirefoxDriver(options as FirefoxOptions)
        return driver
    }

    override fun getOptions(): AbstractDriverOptions<*> {
        val options = FirefoxOptions()
        options.addArguments("--headless")
        return options
    }
}