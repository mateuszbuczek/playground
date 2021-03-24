Below are the five client resiliency patterns:
- Timeout - we can specify the timeout length that it wishes to wait for a response, and if that timeout is reached then we can define a specific logic to handle such incidents.
- Retry
- Circuit breakers - The CircuitBreaker is implemented via a finite state machine with three normal states: CLOSED, OPEN and HALF_OPEN and two special states DISABLED and FORCED_OPEN.
- Fallbacks - With the fallback pattern, when a remote service call fails, rather than generating an exception,
- Bulkheads - limit concurrent calls