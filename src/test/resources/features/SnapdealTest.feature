@parallel-feature
Feature: Cucumber Picocontainer feature test snapdeal

  Scenario Template: Snapdeal cuke test scenario
    Given user launches "<browser>" browser
    Then user opens "snapdeal" app
    And user searches for "<item>"
    Then user takes screen shot of the results
    And closes the browser

  @cucumber-report @parallel-scenario
    Examples:
      | item | browser   |
      | dell | PHANTOMJS |

  @parallel-scenario
    Examples:
      | item   | browser |
      | lenovo | CHROME  |