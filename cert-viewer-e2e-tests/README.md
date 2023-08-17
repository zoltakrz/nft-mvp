## Test runners

To run basic maven tests:
- `mvn clean test`

To change default (chromium) browser:
- `mvn clean test -Dbrowser=firefox`
- `mvn clean test -Dbrowser=safari`

To change slow motion speed:
- `mvn clean test -Dslowmo=10000`
- `mvn clean test -Dslowmo=5000`
- `mvn clean test -Dslowmo=3000`
- `mvn clean test -Dslowmo=500`
- `mvn clean test -Dslowmo=0`

To run tests by group:
- `mvn clean test -Dgroups=signin`

To run tests (fastest way) and display allure report after:
- `mvn clean test -Dheadless=true -Dslowmo=0 allure:serve`

Display report after some test run:
- `mvn allure:serve`
