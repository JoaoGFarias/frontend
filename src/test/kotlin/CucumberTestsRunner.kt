import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith // TODO - can we migrate to Junit5?

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/cucumber/features"],
)
class CucumberTestsRunner {
}