Feature: course Feature

  Scenario: null name is not accepted
    Given title is null
    When ask for creating course
    Then should throw "Course must have a name."


  Scenario: graduate level is not valid
    Given one course with course number "1234567" and title "NLP" and credit "3" and graduate level "Master"
    When add new course
    Then should throw "Graduate level is not valid."
