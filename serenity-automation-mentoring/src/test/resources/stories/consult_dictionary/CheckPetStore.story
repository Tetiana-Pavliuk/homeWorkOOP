Check Pet Store Story
Narrative:
In order to launch the web store
As an customer
I want to find, create and delete pets

Scenario: Looking for the created pet
Given Barsic is created
When we get the response of creation on the endpoint
Then we verify that created pet is Barsic

Scenario: Updating the pet
Given the pet is created
When the user updates pet
Then the pet is updated

Scenario: Delete the pet
Given the user creates pet
When the user deletes pet
Then the search for pet returns 404