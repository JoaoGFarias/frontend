run_tests_chrome:
	mvn clean verify -Dtest=CucumberTestsRunner -Dbrowser=chrome

run_tests_firefox:
	mvn clean verify -Dtest=CucumberTestsRunner -Dbrowser=firefox