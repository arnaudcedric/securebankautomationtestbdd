@ui @login
Feature: User authentication

  Background:
    Given the user is on the login page

  @smoke @positive
  Scenario: Successful login

    When the user logs in with:
      | username      | password   | role     |
      | standard_user | bank_sauce | CUSTOMER |

    Then the home page should be displayed


  @regression
  Scenario Outline: Login with different user accounts

    When the user logs in with username "<username>"
    And password "<password>"

    Then the login result should be <result> with message "<expectedMessage>"

    @positive
    Examples:
      | username       | password   | result  | expectedMessage                                    |
      | standard_user  | bank_sauce | SUCCESS | Welcome back, Alex                                 |
      | standard_user1 | bank_sauce | FAILURE | The username or password you entered is incorrect. |

    @negative
    Examples:
      | username   | password    | result  | expectedMessage     |
      | admin_user | admin_sauce | SUCCESS | Welcome back, Admin |