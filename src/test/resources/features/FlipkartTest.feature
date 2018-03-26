@parallel-feature
Feature: Cucumber Picocontainer feature test flipkart

  Scenario Template: Flipkart cuke test scenario
    Given user launches "<browser>" browser
    Then user opens "flipkart" app
    And user searches for "<item>"
    Then user takes screen shot of the results
    And closes the browser

  @cucumber-report @parallel-scenario
    Examples:
      | item   | browser   |
      | iphone | PHANTOMJS |

  @parallel-scenario
    Examples:
      | item   | browser |
      | xiaomi | FIREFOX |