#!/bin/bash

clear

$JAVA_HOME/bin/java -Xms512m -Xmx2048m -Xss1000k -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:"../Logs/GC/nc-erp-service-gc.log" -jar ../lib/nc-erp-service-0.0.1.jar --spring.config.location=../config/nc-erp-service/application.properties &
