Feature: Login feature

  Scenario: Login success
    Given I open Login Page
    When I enter email "akansha.shukla@testpro.io"
    And I enter password"te$t$tudent"
    And I submit
    Then I should get logged in