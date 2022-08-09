@echo off
CLS

java -Xms512m -Xmx2048m -Xss1000k -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:"./discovery-gc.log" -jar ../lib/nc-discovery-0.0.1.jar --spring.config.location=../config/discovery/application.properties
