#!/bin/bash
# -*- ENCODING: UTF-8 -*-
echo "Compilando paquetes"
javac -cp .:dist/lib/mysql-connector-java-8.0.11.jar:dist/lib/zip4j-2.11.2.jar:dist/lib/commons-net-1.2.1.jar CargarClase.java graficos/*.java control/*.java modelo/*.java -d ./dist/
echo "Generando JAR"
cd dist/
jar -cmf META-INF/MANIFEST.MF java_clases.jar pkgdir/CargarClase.class pkgdir res/*.png
echo "Ejecutando aplicacion"
java -jar java_clases.jar
cd ..
exit
