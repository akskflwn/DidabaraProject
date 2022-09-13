@echo off

echo Installing GroupDocs.Conversion v16.10.1 ...

call mvn install:install-file -Dpackaging=jar -Dversion=16.10.1 -DgroupId=com.groupdocs -DartifactId=groupdocs-conversion -Dfile=GroupDocs.Conversion.jar

pause