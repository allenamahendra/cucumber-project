
Feature: Book flight tickets
  @RUN
  Scenario Outline: round way trip
    When I open united home page
    And I select Book menu
    And I select flight type option as "<flightType>"
    And I select from city as "<fromCity>"
    And I select to city as "<toCity>"
    And I select from date as "<FromDate>"
    And I select to date as "<toDate>"
    And I select travellers as "<travellers>" Adults
    And I click on find flights button
    Then list of available flights are displayed

    Examples:
      |flightType|fromCity|toCity|FromDate|toDate|travellers|
      |Roundtrip|Hyderabad|Chicago|Jul 09|   Jul 15    |5|

  Scenario Outline: One way trip
    When I open united home page
    And I select Book menu
    And I select flight type option as "<flightType>"
    And I select from city as "<fromCity>"
    And I select to city as "<toCity>"
    And I select from date as "<FromDate>"
    And I select travellers as "<travellers>" Adults
    And I click on find flights button
    Then list of available flights are displayed

    Examples:
      | flightType | fromCity  | toCity  | FromDate | travellers |
      | oneway  | Hyderabad | Chicago | Jul 09   | 5          |
