@echo off
CLS

java -Xms512m -Xmx2048m -Xss1000k -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:"./dds-gc.log" -jar ../lib/nc-admin-service-0.0.1.jar --spring.config.location=../config/nc-admin-service/application.properties
