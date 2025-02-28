set url_webapps=E:\apache-tomcat-10.1.7\webapps
set nom_projet=gestion_vol
set url_temp=E:\apache-tomcat-10.1.7\webapps\gestion_vol
set url_lib=lib
set url_xml=E:\Studies\S6\framework\gestion_vol\vol\test
set url_view=E:\Studies\S6\framework\gestion_vol\vol\test\view

rmdir "%url_temp%"
mkdir "%url_temp%"
mkdir "%url_temp%\WEB-INF"
mkdir "%url_temp%\WEB-INF\lib"
mkdir "%url_temp%\WEB-INF\classes"
mkdir "%url_temp%\views"

copy "%url_lib%\*.*" "%url_temp%\WEB-INF\lib"
copy "%url_xml%\*.xml" "%url_temp%\WEB-INF"
xcopy "%url_view%\assets" "%url_temp%\assets" /E /I
copy "%url_view%\pages\*.jsp" "%url_temp%\views"

dir /s /b *.java > sources.txt
javac -parameters -d "E:\apache-tomcat-10.1.7\webapps\gestion_vol\WEB-INF\classes" -cp "lib\*" @sources.txt
set namefilewar=%nom_projet%
jar -cvf "%url_webapps%\%namefilewar%.war" -C "%url_temp%" .

pause








