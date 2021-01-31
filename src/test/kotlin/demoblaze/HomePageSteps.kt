package demoblaze

import io.cucumber.java8.En
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.cucumber.java8.HookNoArgsBody

class HomePageSteps: En {


    private lateinit var driver: ChromeDriver

    init {

        Before(HookNoArgsBody { startBrowser() })

        Given("I am on the Demoblaze Home page") {
            driver.get("https://www.demoblaze.com/index.html")
        }

        After(HookNoArgsBody { killBrowser() })
    }

    private fun startBrowser() {
        val options = ChromeOptions()
        options.setBinary("/usr/bin/brave")
        driver = ChromeDriver(options)
        driver.manage().window().maximize()
    }

    private fun killBrowser() {
        driver.quit()
    }
}