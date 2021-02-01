package demoblaze.objects

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

class Wait {
    companion object {
        fun defaultWait(driver: WebDriver) =  WebDriverWait(driver, Duration.default)
    }
}