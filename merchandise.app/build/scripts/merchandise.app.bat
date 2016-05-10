@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  merchandise.app startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and MERCHANDISE_APP_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\gs-relational-data-access-0.1.0.jar;%APP_HOME%\lib\spring-boot-starter-web-1.1.10.RELEASE.jar;%APP_HOME%\lib\spring-jdbc-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-data-jpa-1.1.10.RELEASE.jar;%APP_HOME%\lib\postgresql-9.1-901-1.jdbc4.jar;%APP_HOME%\lib\spring-boot-starter-1.1.10.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-1.1.10.RELEASE.jar;%APP_HOME%\lib\jackson-databind-2.3.4.jar;%APP_HOME%\lib\hibernate-validator-5.0.3.Final.jar;%APP_HOME%\lib\spring-core-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-web-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-tx-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-aop-1.1.10.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-jdbc-1.1.10.RELEASE.jar;%APP_HOME%\lib\hibernate-entitymanager-4.3.7.Final.jar;%APP_HOME%\lib\spring-orm-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-data-jpa-1.6.4.RELEASE.jar;%APP_HOME%\lib\spring-aspects-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-boot-1.1.10.RELEASE.jar;%APP_HOME%\lib\spring-boot-autoconfigure-1.1.10.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-logging-1.1.10.RELEASE.jar;%APP_HOME%\lib\snakeyaml-1.13.jar;%APP_HOME%\lib\tomcat-embed-core-7.0.57.jar;%APP_HOME%\lib\tomcat-embed-el-7.0.57.jar;%APP_HOME%\lib\tomcat-embed-logging-juli-7.0.57.jar;%APP_HOME%\lib\tomcat-embed-websocket-7.0.57.jar;%APP_HOME%\lib\jackson-annotations-2.3.0.jar;%APP_HOME%\lib\jackson-core-2.3.4.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\classmate-1.0.0.jar;%APP_HOME%\lib\commons-logging-1.1.3.jar;%APP_HOME%\lib\spring-aop-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-context-4.0.8.RELEASE.jar;%APP_HOME%\lib\spring-expression-4.0.8.RELEASE.jar;%APP_HOME%\lib\aspectjrt-1.8.4.jar;%APP_HOME%\lib\aspectjweaver-1.8.4.jar;%APP_HOME%\lib\tomcat-jdbc-7.0.57.jar;%APP_HOME%\lib\jboss-logging-annotations-1.2.0.Beta1.jar;%APP_HOME%\lib\hibernate-core-4.3.7.Final.jar;%APP_HOME%\lib\dom4j-1.6.1.jar;%APP_HOME%\lib\hibernate-commons-annotations-4.0.5.Final.jar;%APP_HOME%\lib\hibernate-jpa-2.1-api-1.0.0.Final.jar;%APP_HOME%\lib\jboss-transaction-api_1.2_spec-1.0.0.Final.jar;%APP_HOME%\lib\javassist-3.18.1-GA.jar;%APP_HOME%\lib\spring-data-commons-1.8.4.RELEASE.jar;%APP_HOME%\lib\slf4j-api-1.7.7.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.7.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.7.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.7.jar;%APP_HOME%\lib\logback-classic-1.1.2.jar;%APP_HOME%\lib\tomcat-juli-7.0.57.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\jandex-1.1.0.Final.jar;%APP_HOME%\lib\xml-apis-1.0.b2.jar;%APP_HOME%\lib\logback-core-1.1.2.jar;%APP_HOME%\lib\jboss-logging-3.1.3.GA.jar;%APP_HOME%\lib\aopalliance-1.0.jar

@rem Execute merchandise.app
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %MERCHANDISE_APP_OPTS%  -classpath "%CLASSPATH%" merchandise.Application %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable MERCHANDISE_APP_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%MERCHANDISE_APP_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
