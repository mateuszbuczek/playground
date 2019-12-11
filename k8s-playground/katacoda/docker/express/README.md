docker caches a result of executing lines in dockerfile for optimization. By copying package.json file we ensure that versions will be validated.If we copied our code before running npm install then it would run every time as our code would have changed. By copying just package.json we can be sure that the cache is invalidated only when our package contents have changed

 Splitting the installation of the dependencies and copying out source code enables us to use the cache when required.

  - docker run -d --name my-production-running-app -e NODE_ENV=production -p 3000:3000 my-nodejs-app

