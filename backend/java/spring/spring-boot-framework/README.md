1. main run()
    - SpringFactoriesLoader
        - loads first most important part of available spring.factories implementations 
        like application listeners

2. Application Starting Event
    - ConfigFileApplicationListener
        - loads application.yaml/yml/properties env vars cmd args
        - listens for ApplicationEnvironmentPreparedEvent ApplicationPrepared Event
        - from loaded properties create environment - profiles and properties
    - EnvironmentPostProcessor
        - loaded by SpringFactoriesLoader using spring.factories in this stage
        - post processors are used to post process loaded properties 
        
3. Application Environment Prepared Event
    - here at the beginning is created context based on classpath
     (for example if servlet found create ServletContainer
      if reactive one found create ReactiveContainer
      if none found create GenericApplicationContext)
    - ApplicationContextInitializer
        - loaded from spring.factories
        - possibility to register beans based on ConfigurableApplicationContext
        
4. Application Context Initialized Event
    - starting application
    - context holds environment, beans, beans factory, (webserver - if on classpath, initialized at later stage) 
    
5. Application Prepared Event
    - BeanDefinitionLoader
        - loads bean definitions
        - AnnotationBeanDefinitionReader/XmlBeanDefinitionReader/ClassPathBeanDefinitionScanner/
          ConfigurationClassPostProcessor/GroovyBasedDefinitionReader/PropertiesBeanDefinitionReader
    - application.class is the entrypoint bean 
    - application.class @EnableAutoConfiguration annotation indicates BeanDefinitionLoader to load
        autoconfiguration classes from spring.factories spring-boot-autoconfiguration
    - BeanFactoryPostProcessor 
        - configure already created beans
6. Context Refreshed Event
    - phase 3 of creating beans
7. Servlet Web Server Initialized Event - if applicable
    = web servlet initialized 
8. Application Started Event
    - application started
9. Application Ready Event
10. Context Closed Event


-- SIDE NOTES -- 
- spring publishes events during startup
- to profile startup application use CPU profiler with event=wall option 
- @PostConstruct runs after BeanPostProcessor.postProcessBeforeInitialization and before BeanPostProcessor.postProcessAfterInitialization 
