### Database scaling
- scaling out (horizontally) 
    - costs of distributed system maintain.
    - more resources than scaling up for same price.
    - costs may grow if each machine will require its own database usage license
- scaling up (vertically)
    - easy to maintain
    - one machine one license
    - single point of failure
    - less resources for same price compared to scaling out
    
### Database query execution
- parser - parse
- optimizer - create or use cached execution plan
- executor - execute
