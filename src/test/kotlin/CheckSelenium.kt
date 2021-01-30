import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class CheckSelenium {

    private lateinit var driver: ChromeDriver

    @BeforeEach
    fun setupBrowser() {
        val options = ChromeOptions()
        options.setBinary("/usr/bin/brave")
        driver = ChromeDriver(options)
        driver.manage().window().maximize()
    }

    @AfterEach
    fun killBrowser() {
        driver.quit()
    }

    @Test
    fun performLoginTest() {
        driver.get("https://the-internet.herokuapp.com/login")

        driver.findElement(By.id("username")).sendKeys("tomsmith")
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!")
        driver.findElement(By.tagName("button")).click()

        Thread.sleep(2000)

        assertThat(driver.findElement(By.tagName("h2")).text).isEqualTo("Secure Area")
        assertThat(driver.findElement(By.tagName("h4")).text).isEqualTo("Welcome to the Secure Area. When you are done click logout below.")
    }
}