package demoblaze.store.pages.home

import org.openqa.selenium.WebDriver

class HomePage(private val driver: WebDriver) {

    init {
        openPage()
    }

    private fun openPage() {
        driver.get("https://www.demoblaze.com/index.html")
    }
}