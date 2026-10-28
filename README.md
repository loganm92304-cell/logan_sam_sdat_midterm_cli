# Airport & Flight Management REST API — Sprint Reflection
**Authors:** Logan Marsh & Sam  
**Course:** Software Development (Keyin College)

---

# Airport CLI Client — Four Core Questions Implementation

The CLI application connects to the Spring Boot REST API via HTTP (`HttpClient`) to retrieve, process, and display relational data answering the four required sprint questions.

---

## 1. What airports are in each city?
* **API Endpoint Queried:** `GET /cities`
* **Data Model Relationship:** `City` $\rightarrow$ `List<Airport>`
* **Implementation Details:**  
  The CLI fetches all `City` entities from the REST API. Each city object contains a nested list of associated `Airport` objects (`City.airports`). The service iterates through each city and maps its name to the airport names and IATA codes located within it.

**Example Output:**

--- Airports in Each City ---
City: St. John's -> Airports: St. John's International Airport (YYT)
City: Halifax -> Airports: Halifax Stanfield International Airport (YHZ)


# 2. What aircraft has each passenger flown on?

* API Endpoint Queried: GET /passengers
* Data Model Relationship: Passenger $\rightarrow$ List<Aircraft>
* Implementation Details:
The CLI sends a GET request to retrieve all Passenger records. Each passenger 
entity maintains a many-to-many relationship with Aircraft objects (Passenger.aircraft). 
The CLI service formats each passenger's full name (firstName + lastName) alongside the 
aircraft type and operating airline.

**Example Output**

--- Aircraft Flown by Each Passenger ---
Passenger: Logan Marsh -> Aircraft Flown: Boeing 737 (Air Canada), Airbus A320 (WestJet)
Passenger: Jane Doe -> Aircraft Flown: Dash 8 (Porter Airlines)

# 3. What airports does each aircraft take off from and land at?

* API Endpoint Queried: GET /aircraft
* Data Model Relationship: Aircraft $\rightarrow$ List<Airport>
* Implementation Details:
  The CLI queries the /aircraft endpoint to retrieve all aircraft registered in the system. 
Each Aircraft object holds a list of operating airports (Aircraft.airports). The CLI service 
iterates over the list to output which airports each aircraft model operates out of.

**Example Output:**
--- Airports per Aircraft ---
Aircraft: Boeing 737 [Air Canada] -> Associated Airports: St. John's International Airport (YYT), Toronto Pearson (YYZ)
Aircraft: Airbus A320 [WestJet] -> Associated Airports: Calgary International (YYC)

# 4. What airports have passengers used?

* API Endpoint Queried: GET /passengers
* Data Model Relationship: Passenger $\rightarrow$ List<Aircraft> $\rightarrow$ List<Airport>
* Implementation Details:
  To answer this question, the CLI traverses through relational entity layers: it fetches all
  Passenger objects, iterates through every Aircraft each passenger has flown on,
  and extracts all Airport entities linked to those aircraft. It uses a Set<String> to
  eliminate duplicates and display a distinct, consolidated list of all airports utilized by
  passengers.

**Example Output:**

--- Airports Used by Passengers ---
St. John's International Airport (YYT)
Toronto Pearson International Airport (YYZ)
Halifax Stanfield International Airport (YHZ)

---


## Sprint Challenge: JPA Pagination & Sorting Reflection

### Q3. Which two endpoints did you add pagination to, and why did those make sense to paginate?

@GetMapping("/paginated")
public Page<Passenger> getPassengersPaginated(Pageable pageable) {
return passengerRepository.findAll(pageable);
}

Explanation:
Spring MVC automatically inspects incoming query parameters (like page, size, and sort) 
from the HTTP request URL and binds them into a Spring Data Pageable object.
When passed into passengerRepository.findAll(pageable) (which inherits built-in pagination from
JpaRepository), Hibernate dynamically translates this parameter into SQL statements
containing LIMIT and OFFSET clauses. This ensures the database only fetches the specific
subset of records requested rather than loading the entire table.


### Q4. Show a Postman request that uses pagination and sorting on one of your endpoints. What URL did you use, and what did the response look like?

List<T>: Returns a plain Java collection containing only the matching entity items. It contains zero metadata 
regarding pagination, meaning the client application has no way of knowing how many total records or pages 
exist in the database without running a separate count query.

Page<T>: Wraps the list of items alongside comprehensive 
metadata, including totalElements, totalPages, number (current page), size, and isLast.

### Why choose one over the other?

Choose List when you need to fetch all records at once 
for internal batch processing or lightweight endpoints where pagination overhead isn't needed.

Choose Page (as implemented in our paginated endpoints) whenever building a user-facing client 
interface that requires pagination controls (such as "Next/Previous" buttons, page numbers, or 
"Page X of Y" displays).