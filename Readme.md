## Clean-me-up

### Preconditions
* The application exposes a public REST end-point for sending emails
* The application acts like a facade towards a legacy email library located in module **clean-me-up-support**
* The current implementation is rather ugly
* The API contract has not yet been published so you don't need to be backwards compatible
* Application is located in module **clean-me-up-rest**
* Don't change the legacy module **clean-me-up-support**

### Assignment
* Refactor the code in module **clean-me-up-rest** as you wish
* When you are done you should be rather happy with the code when it comes to: maintenance, quality, structure, architecture, testing, validation etc
* If you left things behind - please note that down in a read-me file

Estimated time ~4 hours, but feel free to impress us. 

### Stretch goals
When implementing a service - there are lots of things to consider. Some of those are:
* DevOps
* Security
* Documentation
* Versioning
* Monitoring
* Logging
* CI / CD
* Configuration
* Data integrity
* Fault handling

## Comments after completion. 
That is what I think acceptable code is (not perfect, there is always a chance to improve something). 
I left some comments inside. This version is quite close to what I do now (except java version, I use 17th on prod).

### Out of scope 
* Authorization with spring security. 
* Tracing. I would use any of brave/opentracing for production. 
* Swagger and contract-first approach. 
* CI-pipeline and k8s deployment. 
* Prometheus + grafana dashboards. 
