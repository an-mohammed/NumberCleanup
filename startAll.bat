start java -jar nc-discovery\target\nc-discovery-0.0.1.jar

ping -n 10 127.0.0.1 > nul

start java -jar nc-erp-service\target\nc-erp-service-0.0.1.jar

ping -n 40 127.0.0.1 > nul

start java -jar nc-bscs-service\target\nc-bscs-service-0.0.1.jar

ping -n 40 127.0.0.1 > nul

start java -jar nc-ras-service\target\nc-ras-service-0.0.1.jar

ping -n 40 127.0.0.1 > nul

start java -jar nc-nms-service\target\nc-nms-service-0.0.1.jar

ping -n 40 127.0.0.1 > nul

start java -jar nc-admin-service\target\nc-admin-service-0.0.1.jar
