@new
Feature: Cucumber Picocontainer feature test shopclues

  Scenario Template: ShopClues cuke test scenario
    Given user launches "CHROME" browser
    Then user opens "shopclues" app
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