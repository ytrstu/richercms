#Broken project after updating appengine

# Introduction #

If you have the problem :

_Unable to start embedded HTTP server
java.lang.RuntimeException: Unable to locate the App Engine agent..._


There is an easy solution.


# Details #

Here's the fix:

1) Under your project properties, select "Run/Debug Settings"

2) Highlight the debug configuration you use and click the "edit" button

3) Select the "arguments" tab, and add the following to the "VM Arguments" window:

-javaagent:C:\opt\eclipse-ganymede\plugins
\com.google.appengine.eclipse.sdkbundle\_1.2.6.v200910130758\appengine-
java-sdk-1.2.6\lib\agent\appengine-agent.jar

NOTE: Of course modify the above path to fit your directory structure
and OS