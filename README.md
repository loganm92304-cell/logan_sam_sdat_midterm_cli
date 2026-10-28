# Airport & Flight Management REST API — Sprint Reflection
**Authors:** Logan Marsh & Sam  
**Course:** Software Development (Keyin College)

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