# customer-rewards-service

## Summary
Provides a RESTful endpoint providing a "Rewards Summary" that summarizes the rewards a customer earned, given their transactions, in the past three-month period.
Organizes each customer's rewards into a summary, one that describes the customer, as well points earned each month, total spent each month, and total earned over the period.

## Endpoint
The REST endpoint is served at `[location-of-service]/rewards/summary`. By default, the service is served at `localhost:8081`.

To hit the endpoint you can use cURL:
```bash
curl 'http://localhost:8081/rewards/summary' | jq  # jq for pretty-printing the response
```

Example Summary (endpoint returns a list of summaries, this would be a single one for brevity’s sake):
```json
{
      "customer": {
        "firstName": "Margaret",
        "lastName": "King"
      },
      "rewardsEarnedByMonth": {
        "AUGUST": {
          "totalDollarsSpent": 0.0,
          "totalPointsEarned": 0
        },
        "SEPTEMBER": {
          "totalDollarsSpent": 0.0,
          "totalPointsEarned": 0
        },
        "OCTOBER": {
          "totalDollarsSpent": 92.0,
          "totalPointsEarned": 42 
        }
      },
      "totalPointsEarned": 42
    }
```

**Note**: `totalDollarsSpent` and `totalPointsEarned` within each month refer to the totals across _all_ transactions, so calculating points given the `totalDollarsSpent` will not work. You will need to investigate each transaction on an individual basis, calculate the rewards earned, then sum all rewards earned to verify.

## Possible Improvements
Given more time, and more answers to specific business-related questions of the problem, there's a few things I would consider to improve this project.

- The implementation I wrote treats the 3-month period as a business expectation
  - API Query Parameter could drive the period
  - A @Value property that could be modified in real time while the service was still running
- Another alternative would be to create a 'job' that runs on a schedule
  - Could run daily, hourly, every 5 minutes to calculate the user's current total rewards, then persist those to a table
  - For read-only operations, cached values might work.
  - Extended upon even further, if customers could use the rewards balance, create service-level and query-level transactions to deduct points appropriately
  
- More unit tests/code coverage
  - I wrote mine in Spock for a couple of reasons:
    - I am more familiar with Spock
    - Spock offers more extensible testing than JUnit does out of the box
- Integration testing
    - Full end-to-end tests with a PostgreSQL database spun up
    - spring-boot-starter-test dependency for this (setting up Spring IoC), as well as a testcontainers (PostgreSQL in a Docker container accessible by the test in question)
- Docker Container
  - Compose file for starting the Spring Boot Service and the backing PostgreSQL instance
- OpenAPI specification
  - Create a specification *first* (API-first design), then generate the necessary service bindings  from that specification using a tool like `openapi-generator`
  - Serve a swagger-ui.html webjar page up, displaying  the API specification created

## Stack
- Java (Primary language, JDK 21)
- PostgreSQL (Database in Use)
- Groovy (Unit testing)
## Libraries
- Spring Framework, Spring Boot (Server/Service Logic)
- Hibernate (Database Connection)
- JPA (ORM Specification)
- Spock Framework (Unit testing)
