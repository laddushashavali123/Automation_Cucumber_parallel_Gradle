Feature: Cucumber guice feature test for Snapdeal

  Scenario Template: Snapdeal test scenario
    Given user launches "FIREFOX" browser
    Then user opens "snapdeal" app
    And user searches for "<item>"
    Then user takes screen shot of the results
    And closes the browser
    Examples:
      | item   |
      | dell   |
      | lenovo |