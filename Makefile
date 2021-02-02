run_tests_chrome:
	mvn clean verify -Dtest=CucumberTestsRunner -Dbrowser=chrome -DhomePageUrl=https://www.demoblaze.com/index.html

run_tests_firefox:
	mvn clean verify -Dtest=CucumberTestsRunner -Dbrowser=firefox -DhomePageUrl=https://www.demoblaze.com/index.html