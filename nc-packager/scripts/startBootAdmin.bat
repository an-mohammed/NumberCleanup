@echo off
CLS

java -Xms512m -Xmx2048m -Xss1000k -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:"./boot-admin-gc.log" -jar ../lib/nc-boot-admin-0.0.1.jar --spring.config.location=../config/boot-admin/application.properties
