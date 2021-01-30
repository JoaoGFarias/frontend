import io.cucumber.java8.En
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.cucumber.java8.HookNoArgsBody

class StepDefs: En {

    private lateinit var driver: ChromeDriver

    init {

        Before(HookNoArgsBody { startBrowser() })

        Given("I have {int} cukes in my belly") { int1: Int? ->
            // all good
        }
        When("I wait {int} hour") { int1: Int? ->
            // all good
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