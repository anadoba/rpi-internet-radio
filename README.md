# rpi-internet-radio

The plan is to have an internet radio implementation that is controlled by an infrared remote.
The whole thing will be running on Raspberry Pi.

## Dependencies
Application needs properly configured, up and running LIRC.  
In order to use it in Java we will use JLIRC library, which dates from 2001.  
Unfortunately it's not present in any kind of maven repository.  
This means we have an appropriate JAR file in the project and it needs to be installed in your local computer maven repo.  
Before running the project for the first time you have to type:  
`mvn clean`  
