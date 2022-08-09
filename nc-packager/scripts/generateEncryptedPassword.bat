cls

@echo off

java -cp ../lib/jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=%1 password=D!rectDeb!tS3cr3t algorithm=PBEWITHMD5ANDDES