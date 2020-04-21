set JAVADIR="C:\Program Files\Java\jdk-14"
set JARFILE="C:\Users\demon\IdeaProjects\MyFirstJavaFX\out\artifacts\MyFirstJavaFX\MyFirstJavaFX.jar"
set STARTDIR="C:\Users\demon\IdeaProjects\MyFirstJavaFX\out\artifacts\MyFirstJavaFX"

cd %STARTDIR%
%JAVADIR%\bin\java.exe --module-path %JAVADIR%\javafx-sdk-14\lib --add-modules=javafx.controls,javafx.fxml -jar %JARFILE%

PAUSE