import io.cucumber.java8.En

class StepDefs: En {

    init {
        Given("I have {int} cukes in my belly") { int1: Int? ->
            // all good
        }
        When("I wait {int} hour") { int1: Int? ->
            // all good
        }

        Then("my belly should growl") {
            // all good
        }
    }
}