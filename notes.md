Annotations
===========

- `@Path("/path/to/resource")`
- `@Produces({list, of, mediatypes})`
    * can use plain strings with MIME types or constants from `MediaType class`
    * actually it's the content of `Content-Type` HTTP header of response
    * E.g.
        + `"text/plain"` or `MediaType.TEXT_PLAIN`
        + `"text/xml"` or `MediaType.TEXT_XML`
        + `"application/x-www-form-urlencoded"` or `MediaType.APPLICATION_FORM_URLENCODED`
    * actual result depends on `Accept` HTTP header
- `@Consumes({list, of, mediatypes})`
    * `Content-Type` header of request
- HTTP methods
    * `@GET`
    * `@POST`
    * `@PUT`
    * `@DELETE`
    * `@HEAD`
- `@PathParam(name)` may be used on method parameter of a request method
    * can be used on method arguments and instance fields
- `@QueryParam(name)` used to extract query parameters from path (e.g. "q=foo&bar=baz")
    * can also set default value with @DefaultValue
    * can be
        + primitive type
        + has constructor with single argument of type String
        + has static factory method `valueOf` or `fromString`
        + be List<T>, Set<T> or SortedSet<T> where T satisfies 2 or 3 above
- also
    * `FormParam`
    * `HeaderParam`
    * `MatrixParam` (???)
        + actually not used. E.g. `example.com/foo;p1=spam;ps=1,2,3/bar/baz;p3=4;p5=6`
    * `CookieParam`

- `@Context` used on fields and method parameters to obtain all request context
    * `UriInfo`
        + `getQueryParameters()` -> `MultiValuedMap<String, String>`
        + `getPathParameters()` -> `MultiValuedMap<String, String>`
    * `HttpHeaders`
        + `getRequestHeaders()` -> `MultiValuedMap<String, String>`
        + `getCookies()` 
    * `Request`
        + `getMethod() -> String`
    * `SecurityContext`
    * Methods that process forms (`@Consumes("application/x-www-form-urlencoded")`) can accept `MultiValuedMap` of form parameter as single argument

- `@Singleton` - one instance per web application
- `@PerSession` - one instance of class per session

MISC
====

- if response method returns nothing HTTP code 204 is returned
- `WebApplicationException(errorCode)`
- `NotFoundException`

Built-in server
===============

- Variant 1

        import com.sun.jersey.api.container.httpserver.HttpServerFactory;
        import com.sun.net.httpserver.HttpServer;

        public class Main {
            public static void main(String[] args) throws Exception {
                HttpServer server = HttpServerFactory.create("http://localhost:8000/");
                server.start();
            }
        }

- Variant 2

        GrizzlyServerFactory.createHttpServer(BASE_URI, rc)

    
Resources mapping
=================

* web.xml in `WEB-INF`
* class extending PackageResourceConfig and annotated with ApplicationPath
*  

DICT
====

* JAXB - Java Architecture for XML Binding (JSR 222)
* Root resource class - class annotated with `@Path`
* Provider class - class annotated with `@Provider`. E.g. 
* Sub-resource locators - methods annotated with `@Path` but without HTTP methods specified
  They should return another resource classess for subresources

JAXB
====

Namespaces
----------

* xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" (see [here][JAXB-schema])
* xmlns:xsd="http://www.w3.org/2001/XMLSchema"

CUTOMIZTIONS
------------

see 

1. [Java EE tutorial chapter](http://docs.oracle.com/javaee/5/tutorial/doc/bnbbf.html#bnbbz)
2. [JAXB tutorial on java.net](http://jaxb.java.net/tutorial/section_5_1-Customizing.html#Customizing)
3. [Adding behavior example in Unofficial JAXB Guide](http://jaxb.java.net/guide/Adding_behaviors.html) 

Change class (complexType) name inside of schema definition

      <xsd:complexType>
         <xsd:annotation>
            <xsd:appinfo>
               <jaxb:class name="SumExpression"/>
            </xsd:appinfo>
         </xsd:annotation>
         ...
      </xsd:complexType>
      
Change property name (inline)

    <xsd:element name="class" type="ClassType">
      <xsd:annotation>
        <xsd:appinfo>
          <jxb:property name="klass"/>
        </xsd:appinfo>
      </xsd:annotation>
    </xsd:element>
      
Change package name (inline)

      <xsd:annotation>
        <xsd:appinfo>
          <jaxb:schemaBindings>
            <jaxb:package name="jess.ruleml"/>
          </jaxb:schemaBindings>
        </xsd:appinfo>
      </xsd:annotation>
      
Change package name (in external binding file)

      <jaxb:bindings xmlns:jaxb="http://java.sun.com/xml/ns/jaxb"
                     version="2.0">
        <jaxb:schemaBindings>
          <jaxb:package name="jess.ruleml"/>
        </jaxb:schemaBindings>
      </jaxb:bindings>
      
Binding file for any schema: `<jxb:bindings schemaLocation = "xs:anyURI">`
Binding for whole schema (e.g. set custom package name): 

      <jxb:bindings schemaLocation="po.xsd" node="/xs:schema">
         ...
      </xjb:bindings>
      
Custom converters for simple types (inline)

      <jaxb:globalBindings>
        <jaxb:javaType name="String"
                       xmlType="xsd:string"
                       parseMethod="faststring.StringInterner.parseStringToString"/>
      </jaxb:globalBindings>
      
   external...
      
      <?xml version="1.0" encoding="UTF-8"?>
      <jaxb:bindings xmlns:jaxb="http://java.sun.com/xml/ns/jaxb"
                     xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                     jaxb:version="1.0">
        <jaxb:bindings schemaLocation="roman.xsd" node="/xsd:schema">
          <jaxb:bindings node="//xsd:simpleType[@name='RomanNumberType']">
            <jaxb:javaType name="int"
              parseMethod="util.roman.RomanNumberConverter.parseStringToInt"
              printMethod="util.roman.RomanNumberConverter.printIntToString"/>
          </jaxb:bindings>
        </jaxb:bindings>
      </jaxb:bindings>
      
Disable typesafe enums generation for `enumeration` restriction if it has more than 0 elements

      


MISC
----

 * NOTE: in this case @XMLRootElemet annotation will not be added to the generated class

* Children elements (like the ones in xsd:sequence that translated to List<Object>) represented as actual types, not as 
JAXBElement<ActualType>

* JAXB2 Maven plugin

        <plugins>
            <plugin>
                <groupId>org.jvnet.jaxb2.maven2</groupId>
                <artifactId>maven-jaxb2-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>


WADL
====

Get WADL description of web-application

* POST on my.site.com/application.wadl
* OPTIONS on my.site.com/my/resource/

wadl2java
---------

* usage format: `wadl2java -o outputdir -p package application.wadl`
* 

[JAXB-schema]: https://www.google.ru/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&ved=0CDMQFjAA&url=http%3A%2F%2Fwww.oracle.com%2Fwebfolder%2Ftechnetwork%2Fjsc%2Fxml%2Fns%2Fjaxb%2Fbindingschema_2_0.xsd&ei=36o7UYH3JIil4ATohIDYDA&usg=AFQjCNEmiDaABtf128ccR7NoMyqRq2Q8nA&sig2=nvRLNatGMIKpJJaPK55JDw&bvm=bv.43287494,d.bGE "JAXB Schema"
