# Team Project

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

By keeping this README up-to-date,
your team will find it easier to prepare for the final presentation
at the end of the term.


# Project Name
CocktailCraft
## Team Information
- team name: Team400
- TUT: 0401 BA2200 Group4
### Team Members
- Qinli Yang
- Enhao Tang
- Jiaqi Shen
- Wenqian Ling
---
## Project Description
CocktailCraft is an application for discovering, customizing, and managing cocktail recipes. It allows users to search and filter cocktails, personalize ingredients and quantities, save favourites, rate drinks, and receive recommendations based on the ingredients they have available.
### Domain
[Project description]
---
## User Stories
### User Story 1
As a user, I want to search for cocktails by name and filter them by base spirit, flavour profile, and carbonation so that I can easily find drinks that match my preferences.
### User Story 2
As a user, I want to customize existing cocktail recipes by adding, removing, or replacing ingredients and adjusting quantities so that I can make drinks according to my personal preferences.
### User Story 3
As a user, I want to save my favourite cocktails, rate them, and add personal notes so that I can keep track of drinks I enjoy and revisit their recipes later.
### User Story 4
As a user, I want to enter the ingredients I currently have and receive cocktail recommendations based on them so that I can discover drinks I like and can make with purchasing minimum additional ingredients.

---
## Use Cases
### Use Case 1 [Qinli]
Inputs
* Cocktail name: A text field where the user types a cocktail name.
* Base spirit: A dropdown with the options: Gin, Vodka, Rum, Tequila, Whiskey, Brandy, and Other.
Input requirement
The user must enter a cocktail name or select a specific base spirit, or both. The user can select multiple base spirits at a time, and the system would return (if exist) any cocktail that contains at least one of the base spirit.
Normal Flow
1. The user enters a cocktail name and/or selects the base spirit(s).
2. The user clicks Search.
3. The system searches for cocktails that match all provided criteria. Name matching is case-insensitive and allows partial matches.
4. The system displays the matching cocktail names in a results list.
Alternative Flows
* If the name is blank and there is no selected base spirit, the system displays: “Please enter a cocktail name or select a base spirit.”
* If no cocktails match, the system displays: “No cocktails found.”
* If the API request fails, the system displays an error message and allows the user to retry.
### Use Case 2

### Use Case 3

(Add additional use cases as needed.)
---
## Minimal Viable Product (MVP)
| Team Member | MVP Use Case |
|-------------|--------------|
| Qinli | The user enters a cocktail name and/or selects one or more base spirits, then clicks Search. The system displays matching cocktail names or a message indicating missing input, no matches, or an API failure. |
| Name | Use Case |
| Name | Use Case |
---
## Entities
### Entity 1
### Entity 2
(Add additional entities as needed.)
---
## API Usage
**API Name:**
**Documentation:** [Link]
**How it is used in the program:**
---
## Design Diagrams
### UML Class Diagram
### Sequence Diagrams
---
## How to Run
[Instructions for running the program.]
---
## Notes
[Any additional project information.]
