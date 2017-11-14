@new @Ignore
Feature: Cucumber Spring feature test amazon

  Scenario Template:
    Scenarios: Don't what this does
    Given user launches "CHROME" browser
    Then user opens "amazon" app
    And user searches for "<item>"
    Then user takes screen shot of the results
    And closes the browser

  @ex1
    Examples:
      | item   |
      | iphone |

  @ex2
    Examples:
      | item    |
      | samsung |