RESTMVC
=======

The Concept behind this JEE6 Maven WAR project\prototype is to utilize the standards in a state-less light weight manner to produce rich Web applications that are viewable on any device. The purpose of this prototype is to be a pattern of behavior that can be copied for use in a production Web application. You can find more detail about this project in the [Wiki pages](https://github.com/ericrglass/RESTMVC/wiki).

The features of this RESTful MVC are as follows
-----------------------------------------------

* JAX-RS 1.0 with Jersey is used as the Controller
* JPA 2.0 Entity POJOs with JSON annotations are used as the Model
* State-less JSF 2.1/Facelets templates and composite components are used as the View
* Utilizes the JSF 2 utilities library [omnifaces](https://code.google.com/p/omnifaces/)
* The Jersey View API is used as the glue between the Controller and the View
* JAX-RS extensions with the Jackson JSON API are used to consume and produce between JSON and the Model
* JSON is utilize in the initial rendering and for AJAX partial page rendering
* State-less Session EJB services with EJB 3.1 lite are integrated into the JAX-RS views and resources
* Contexts and Dependency Injection (CDI 1.0) with managed beans are utilized throughout
* Rendering semantic HTML5 mark-up
* Responsive Web Design with CSS3 and SASS with a goal of being mobile first
* Utilizes [Modernizr](http://modernizr.com/) and [jQuery](http://jquery.com/) JavaScript libraries
* Demonstrate single code maintenance Web pages
* Incorporate search, edit, add, and delete Web pages for hierarchical data
* Production mode features for browser performance
    * HTML compressing with in-line CSS and JavaScript minification
    * Asset or resource combining to minimize browser request

Minimal Setup
-------------

* NetBeans 7.3 ; or Eclipse JUNO with JBoss Tools or JBoss Developer Studio 6
* GlassFish 3.1.2 with JavaDB (Apache Derby) sun-appserv-samples database
    * For Eclipse or JBoss Developer Studio you will need the [Oracle GlassFish Server Tools part of Oracle Enterprise Pack for Eclipse](http://www.oracle.com/technetwork/developer-tools/eclipse/downloads/index.html)
* Tables: CUSTOMER, DISCOUNT_CODE, MANUFACTURER, MICRO_MARKET, PRODUCT_CODE, PRODUCT, and PURCHASE_ORDER
* If you need to create and load these tables with data the scripts are in the folder: docs/sample-db
    * NetBeans Grab Structures are provided and can be used with the [Database Explorer UI's Recreate Table feature](https://db.netbeans.org/uispecs/DBModuleUISpec.html#2.4.2.2)
    * DDL SQL files are provided to be used with any database client like the Eclipse Database Development perspective
    * There are (del) delimited files produced with the [Derby ij utility](http://db.apache.org/derby/papers/DerbyTut/ij_intro.html) that can be used for importing
        * Use the instructions from [Example importing all data from a delimited file](http://db.apache.org/derby/docs/10.4/tools/rtoolsimport91458.html) with these files

TODO
----

* The implementation of CSS3 and SASS
* A code maintenance Web page
* Utilize JSON in rendering Web pages
* A search with results Web page
* A CRUD hierarchical data Web page
* Production mode asset combining
* Add more documentation
