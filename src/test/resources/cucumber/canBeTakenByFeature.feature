Feature: can be taken by Feature

  Scenario: ap can be taken by math2 for ali
    Given student "ali" is existed
    When want to take "ap" and "math2"
    Then should be able to


  Scenario: math1 cannot be taken by math2 for ali
    Given student "ali" is existed
    When want to take "math1" and "math2"
    Then should not be able to
