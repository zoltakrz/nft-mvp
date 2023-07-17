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
- `mvn clean test -Dgroups=signup`
- `mvn clean test -Dgroups=forgot`
- `mvn clean test -Dgroups=note`
- `mvn clean test -Dgroups=task`
- `mvn clean test -Dgroups=areaoffocus`
- `mvn clean test -Dgroups=my_account`
- `mvn clean test -Dgroups=not_logged`

To run tests (fastest way) and display allure report after:
- `mvn clean test -Dheadless=true -Dslowmo=0 allure:serve`

Display report after some test run:
- `mvn allure:serve`
