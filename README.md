# Demoblaze Cart Shopping E2E

[![Build Status](https://travis-ci.com/JoaoGFarias/frontend.svg?branch=master)](https://travis-ci.com/github/JoaoGFarias/frontend)


## Run tests:

On Chrome:
```make run_tests_chrome```

On Firefox:
```make run_tests_firefox```

Note: Assume you have the necessary drivers on your Path.

## Things you will find:

* Browser creation using Dependency Injection
* Lazy browser creation
* User flows defined using Gherkin
* User flows simulated using Cucumber
* Continuous building using TravisCI (see badge link above)
* HTML report (on _target/cucumber-reports_ folder) when tests are executed locally. Current Travis configuration
does not provide the reports.

# Additional points

* The expected order price is calculated throughout a scenario, 
by adding and removing item prices when they are added or removed from
  the cart, instead of relying on app calculated sums, as displayed _Cart_ page or
  _Place Order_ modal.