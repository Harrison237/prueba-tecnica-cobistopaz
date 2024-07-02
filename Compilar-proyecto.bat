echo cd target > ejecutar_proyecto.bat
echo java -jar prueba-tecnica-0.0.1-SNAPSHOT.jar >> ejecutar_proyecto.bat
echo pause >> ejecutar_proyecto.bat

mvn clean package -f pom.xml
