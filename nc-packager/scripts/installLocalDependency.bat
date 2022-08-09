@echo off

cls

call mvn install:install-file -Dfile=../lib/create-order.jar -DgroupId=com.numbercleanup.service -DartifactId=create-order -Dversion=0.0.1 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/create-subscriber.jar -DgroupId=com.numbercleanup.service -DartifactId=create-subscriber -Dversion=0.0.1 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/esm.jar -DgroupId=com.numbercleanup.service -DartifactId=esm -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/esm-subscription-management.jar -DgroupId=com.numbercleanup.service -DartifactId=esm-subscription-management -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/hlr.jar -DgroupId=com.numbercleanup.service -DartifactId=hlr -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/pcrf.jar -DgroupId=com.numbercleanup.service -DartifactId=pcrf -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/nms-number-reservation.jar -DgroupId=com.numbercleanup.service -DartifactId=nms-number-reservation -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/GetCustomerProfileProxy.jar -DgroupId=com.numbercleanup.service -DartifactId=customer-profile-proxy -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/HLRService.jar -DgroupId=com.numbercleanup.service -DartifactId=hlr-profile-proxy -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/DownstreamProfileCheckProxy.jar -DgroupId=com.numbercleanup.service -DartifactId=downstream-profile-check-proxy -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/AnaOnboardingProxy.jar -DgroupId=com.numbercleanup.service -DartifactId=ana-onboarding -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/oraappservice-nmtc.jar -DgroupId=com.numbercleanup.service -DartifactId=oraappservice-nmtc -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/number-unreserve-postpaid.jar -DgroupId=com.numbercleanup.service -DartifactId=number-unreserve-postpaid -Dversion=1.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/createb2bsubscriberProxy.jar -DgroupId=com.numbercleanup.service -DartifactId=create-b2bsubscriber -Dversion=3.0 -Dpackaging=jar

call mvn install:install-file -Dfile=../lib/b2bcustomercid.jar -DgroupId=com.numbercleanup.service -DartifactId=b2bcustomercid -Dversion=1.0 -Dpackaging=jar

pause