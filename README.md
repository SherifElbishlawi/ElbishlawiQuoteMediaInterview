# Instructions
1. Run QuoteApplicationt to start server
2. Use one of the requests below
3. Login with username: "user" and password "password" when prompted
# Requests getting latest quote:
- http://localhost:8080/symbols/FB/quotes/latest
- http://localhost:8080/symbols/MSFT/quotes/latest
- http://localhost:8080/symbols/GOOG/quotes/latest
# Request for getting highest ask on specific day:
http://localhost:8080/symbols/highest-ask/YYYY-MM-DD, example: http://localhost:8080/symbols/highest-ask/2020-01-05
# Features included
- Error handling for edge cases
- Caching
- Two endpoints
- Test cases
