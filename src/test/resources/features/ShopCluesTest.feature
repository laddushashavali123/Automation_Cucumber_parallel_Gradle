@parallel-feature
Feature: Cucumber Picocontainer feature test shopclues
  This feature tests Cucumber Picocontainer on Shopclues website

  Scenario Template: ShopClues cuke test scenario
  In this scenario we will launch shopclues
  then search for some item
  and then take its screen shot
    Given user launches "<browser>" browser
    Then user opens "shopclues" app
    And user searches for "<item>"
    Then user takes screen shot of the results
    And closes the browser

  @cucumber-report @parallel-scenario
    Examples: Mobile
      | item   | browser   |
      | iphone | PHANTOMJS |

  @parallel-scenario
    Examples: Firm
      | item    | browser |
      | samsung | CHROME  |