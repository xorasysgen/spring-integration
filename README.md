# spring-integration

Graceful shutdown is supported with all four embedded web servers (Jetty, Reactor Netty, Tomcat, and Undertow) and with both reactive and Servlet-based web applications. When enabled, shutdown of the application will include a grace period of configurable duration. During this grace period, existing requests will be allowed to complete but no new requests will be permitted. The exact way in which new requests are not permitted varies depending on the web server that is being used. Jetty, Reactor Netty, and Tomcat will stop accepting requests at the network layer. Undertow will accept requests but respond immediately with a service unavailable (503) response.

Graceful shutdown occurs as one of the first steps during application close processing and before any beans have been destroyed. This ensures that the beans are available for use by any processing that occurs while in-flight requests are being allowed to complete. To 
