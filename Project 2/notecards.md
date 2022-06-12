# Technology Project Requirements

## Project Description

This project will implement a notecard service that allows for notecards to be created, browsed, stored in decks, and rated on a sliding scale that will influence the likelihood of seeing the card in future draws from the notecard deck. 

### Project Design Specifications and Documents

##### Relational Data Model
![Relational Model](https://raw.githubusercontent.com/220509-web-dev/FoundationProject-Josiah/main/Project%202/imgs/notecardERD.png)

### Technologies

**Persistence Tier**
- PostGreSQL

**Application Tier**
- Java 8
- Spring 5 & Spring Boot
- Apache Maven
- Hibernate & Spring Data
- JSON Web Tokens
- JUnit
- Mockito

**Client Tier**
- HTML
- CSS
- TypeScript
- React

**Deployment Platform Tools**
- Cloud Provider: Amazon Web Services (AWS)
- Database Deployment: AWS Relational Database Service (RDS)
- Server Deployment: AWS Elastic Beanstalk (EB) + AWS Elastic Compute Cloud (EC2)
- UI Deployment: AWS Simple Storage Service (S3)
- Build Automation: AWS Code Build
- Pipeline Management: AWS Code Pipeline

### Functional Requirements

- Functional: Users can register for an account with the system
- Functional: Users can create new decks
- Functional: Users can view all notecards in the system
- Functional: Users can create their own notecards
- Functional: Users can add any notecard to decks they own
- Functional: Users can view all decks alongside the deck owner
- Functional: Users can select a deck and get flashcard functionality
- Functional: Users can select an option to stop seeing a card 
- Functional: Users can rate cards on a sliding scale from 0 to 10 inclusive
- Functional: The probability of a user seeing a card they rated is a function of their ratings of it

### Non-Functional Requirements

- Basic validation is enforced to ensure that invalid/improper data is not persisted
- User passwords will be hashed by the system before persisting them to the data source
- Errors and exceptions are handled properly and their details are obfuscated from the user
- The system conforms to RESTful architecture constraints
- The system's is tested with at least 80% line coverage in all service and utility classes
- The system's data schema and component design is documented and diagrammed 
- The system keeps detailed logs on info, error, and fatal events that occur 
- UI and API builds and deployments are automated using a CI/CD pipeline
- API is deployed to AWS EC2 (via Elastic Beanstalk) as a Docker container
- UI is deployed to a AWS S3 bucket configured for static web hosting

### Suggested Bonus Features
- Responsive and intuitive UI allowing for optimal study of notecards

#### Project Presentation 
- 08Jul2022

## Minimal Viable Product Requirements (End-to-end completion)
- Functional: Users can register for an account with the system
- Functional: Users can create new decks
- Functional: Users can view all notecards in the system
- Functional: Users can create their own notecards
- Functional: Users can add any notecard to decks they own
- Functional: Users can view all decks alongside the deck owner
- Functional: Users can select a deck and get flashcard functionality
- Functional: Users can select an option to stop seeing a card 
- Functional: Users can rate cards on a sliding scale from 0 to 10 inclusive
- Functional: The probability of a user seeing a card they rated is a function of their ratings of it
- Non-Functional: Test coverage of the API's service layer is at least 80%
- Non-Functional: The API's endpoints are exposed in a RESTful manner
- Non-Functional: User passwords are hashed or encrypted before persisting to the database
