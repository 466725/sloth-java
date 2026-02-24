@ui-web @amazon @cucumber
Feature: Amazon sign-in validation
  As a user
  I want to be prevented from signing in with invalid credentials
  So that account security is maintained

  Scenario: Invalid email and password are rejected
    Given I am on the Amazon sign-in page
    When I attempt to sign in with email "invalid-user@example.com" and password "invalid-password-123"
    Then I should see a sign-in error on Amazon

  Scenario: Signing in with invalid credentials gets prevented, user navigates back to home page
    Given I am on the Amazon sign-in page
    When I attempt to sign in with email "invalid-user@example.com" and password "invalid-password-123"
    And I navigate back to Amazon home page
    Then I should be on the Amazon home page
