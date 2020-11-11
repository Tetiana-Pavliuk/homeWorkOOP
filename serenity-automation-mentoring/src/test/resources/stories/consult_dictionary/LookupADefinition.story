Lookup a definition
Narrative:
In order to talk better
As an English student
I want to look up word definitions

Scenario: Looking up the definition of 'apple'
Meta:
@skip
Given the user is on the Wikionary home page
When the user looks up the definition of the word 'apple'
Then they should see the definition 'A common, round fruit produced by the tree Malus domestica, cultivated in temperate climates.'

Scenario: Looking up the definition of 'pear'
Given the user is on the Wikionary home page
When the user looks up the definition of the word 'pear'
Then they should see the definition 'An edible fruit produced by the pear tree, similar to an apple but elongated towards the stem.'

Scenario: Looking up the definition of 'transport'
Given the user is on the Wikionary home page
When the user looks up the definition of the word '<word>'
Then they should see the definition '<definition>'

Examples:
|word      |definition 																																			     |
|car       |A wheeled vehicle that moves independently, with at least three wheels, powered mechanically, steered by a driver and mostly for personal transportation.|
|motorcycle|An open-seated motor vehicle with handlebars instead of a steering wheel, and having two (or sometimes three) wheels.                                    |