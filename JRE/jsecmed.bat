@echo off
cd %USERPROFILE%\AppData\LocalLow\Sun\Java\Deployment
c:
SETLOCAL ENABLEDELAYEDEXPANSION
set _medium=deployment.security.level=MEDIUM
FOR /f "delims=" %%G IN (deployment.properties) DO (
	set var=%%G
	set v=!var!
	SET _left=!v:~0,25!
	if     "!_left!"=="deployment.security.level" (echo %_medium%>>new.properties)
	if not "!_left!"=="deployment.security.level" (echo %%G>>new.properties)
)
del saved.properties
ren deployment.properties saved.properties
ren new.properties deployment.properties
@echo Java security setup change done!




