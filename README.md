RESTMVC
=======

The Concept behind this JEE6 Maven WAR project is to utilize the standards in a state-less light weight manner to produce rich Web applications that are viewable on any device.

The features of this RESTful MVC are as follows
-----------------------------------------------

<ul>
<li>JAX-RS 1.0 with Jersey is used as the Controller</li>
<li>JPA 2.0 Entity POJOs with JSON annotations are used as the Model</li>
<li>State-less JSF 2.1/Facelets templates are used as the View</li>
<li>The Jersey View API is used as the glue between the Controller and the View</li>
<li>JAX-RS extensions with the Jackson JSON API are used to consume and produce between JSON and the Model</li>
<li>JSON is utilize in the initial rendering and for AJAX partial page rendering</li>
<li>State-less Session EJB services with EJB 3.1 lite are integrated into the JAX-RS views and resources</li>
<li>Contexts and Dependency Injection (CDI 1.0) with managed beans are utilized throughout</li>
<li>Rendering semantic HTML5 mark-up</li>
<li>Responsive Web Design with CSS3 and SASS with a goal of being mobile first</li>
<li>Utilizes Modernizr and jQuery JavaScript libraries</li>
<li>Demonstrate single code maintenance Web pages</li>
<li>Incorporate search, edit, add, and delete Web pages for hierarchical data</li>
<li>Production mode features for browser performance
<ul>
<li>HTML compressing with in-line CSS and JavaScript minification</li>
<li>Asset or resource combining to minimize browser request</li>
</ul>
</li>
</ul>

Minimal Setup
-------------

<ul>
<li>NetBeans 7.3 ; or Eclipse JUNO with JBoss Tools or JBoss Developer Studio 6</li>
<li>GlassFish 3.1.2 with JavaDB (Apache Derby) sun-appserv-samples database</li>
<li>Tables: CUSTOMER, DISCOUNT_CODE, MANUFACTURER, MICRO_MARKET, PRODUCT_CODE, PRODUCT, and PURCHASE_ORDER</li>
<li>If you need to create and load these tables with data the scripts are in the folder: docs/sample-db
<ul>
<li>NetBeans Grab Structures are provided and can be used with the [Database Explorer UI's Recreate Table feature](https://db.netbeans.org/uispecs/DBModuleUISpec.html#2.4.2.2)</li>
<li>DDL SQL files are provided to be used with any database client like the Eclipse Database Development perspective</li>
<li>There are (del) delimited files produced with the [Derby ij utility](http://db.apache.org/derby/papers/DerbyTut/ij_intro.html) that can be used for importing
<ul>
<li>Use the instructions from [Example importing all data from a delimited file](http://db.apache.org/derby/docs/10.4/tools/rtoolsimport91458.html) with these files</li>
</ul>
</li>
</ul>
</li>
</ul>

TODO
----

* The implementation of CSS3 and SASS
* A code maintenance Web page
* Utilize JSON in rendering Web pages
* A search with results Web page
* A CRUD hierarchical data Web page
* Production mode asset combining
* Add more documentation
