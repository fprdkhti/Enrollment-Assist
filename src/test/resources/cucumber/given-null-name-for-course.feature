Feature: given null name for course

  Scenario: null name is not accepted
    Given title is null
    When ask for creating course
    Then should throw "Course must have a name."