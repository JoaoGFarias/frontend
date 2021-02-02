package browser

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.AbstractDriverOptions

class ChromeBrowser: BrowserTemplate {

    private lateinit var driver: ChromeDriver

    override fun getDriver(options: AbstractDriverOptions<*>): WebDriver {
        driver = ChromeDriver(options as ChromeOptions)
        return driver
    }

    override fun getOptions(): AbstractDriverOptions<*> {
        val options = ChromeOptions()
        options.addArguments("--headless")
        return options
    }
}

