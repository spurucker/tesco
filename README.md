# Tesco - Challenge
This is an API for Tesco Challenge that serves to transform a common url in a tiny url

## Running the project
    To run this application locally you should
    - Compile the project: mvn clean install
    - Run spring boot: ./mvnw spring-boot:run

## Endpoints documentation
    You can have more technical information at 
    http://127.0.0.1:8080/swagger-ui/#/
    
## Assumptions and notes
- As no information was specified regarding performance, availability or scalability they were not taking into count 
- This shorten method can have ~57 billion possible values
- As I was told not to spend much time with the exercise I am not validating scenarios bigger than that. In next iterations should be done
- Being a simple application JPA might have been a better choise to access database. But as I feel more comfortable working with jdbc I choose this last one
- I use an in memory database for the challenge. In production this could be changed to a real database. If scalability is an issue in the future a key value storage and may be a cache could be more performant
- Regarding authentication: As I was told no to spend much time with the exercise and I'm not very familiar with this subject, I choose for the basic resolution of the problem
- Testing: I only develop UrlService unit test again to save time (as it is the most interesting class to test). In real life product controllers and Daos should be tested as well. Same this with ShortenUrlRequest and its regex. Some integration test might serve as well.
- If you would like to see the project to have size validation for urls, JPA instead of JDBC or/and more testing let me know and I will add them
    
