Feature: Cucumber Spring feature test flipkart

  Scenario Template: sample scenario
    Given user launches "CHROME" browser
    Then user opens "flipkart" app
    And user searches for "<item>"
    Then user takes screen shot of the results
    And closes the browser
    Examples:
      | item   |
      | iphone |
      | xiaomi |