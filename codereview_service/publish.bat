@echo off

rmdir /S /Q publish

dir .\ & timeout /t 2 & dir .\

mkdir publish
cd .\publish

set DOMAIN_JAR_NAME=codereview_domain.jar
set SERIVCE_PATH=D:\workspaces\codereview_service\build\classes

xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\common\pojo\* 			cn\finedo\codereview\common\pojo\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\entity\* 			    cn\finedo\codereview\entity\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\entity\mail\* 			cn\finedo\codereview\entity\mail\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncomment\domain\* 		cn\finedo\codereview\svncomment\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnlog\domain\* 			cn\finedo\codereview\svnlog\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnpathtype\domain\* 	cn\finedo\codereview\svnpathtype\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewuser\domain\* cn\finedo\codereview\svncodeviewuser\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\login\domain\* 			cn\finedo\codereview\login\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnmng\domain\* 			cn\finedo\codereview\svnmng\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\projectmember\domain\* cn\finedo\codereview\projectmember\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewgroup\domain\* cn\finedo\codereview\svncodeviewgroup\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewgroupuser\domain\* cn\finedo\codereview\svncodeviewgroupuser\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnuserright\domain\* cn\finedo\codereview\svnuserright\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnuserpermission\domain\* cn\finedo\codereview\svnuserpermission\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewproject\domain\* cn\finedo\codereview\svncodeviewproject\domain\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncommend\domain\* cn\finedo\codereview\svncommend\domain\

xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\login\*Proxy*.class 			cn\finedo\codereview\login\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnmng\*Proxy*.class 			cn\finedo\codereview\svnmng\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnuserright\*Proxy*.class 			cn\finedo\codereview\svnuserright\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\util\*.class 			cn\finedo\codereview\util\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewuser\*Proxy*.class 			cn\finedo\codereview\svncodeviewuser\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncomment\*Proxy*.class 			cn\finedo\codereview\svncomment\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnpathtype\*Proxy*.class 			cn\finedo\codereview\svnpathtype\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\projectmember\*Proxy*.class 			cn\finedo\codereview\projectmember\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewgroup\*Proxy*.class 			cn\finedo\codereview\svncodeviewgroup\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewgroupuser\*Proxy*.class 			cn\finedo\codereview\svncodeviewgroupuser\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnuserright\*Proxy*.class 			cn\finedo\codereview\svnuserright\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svnuserpermission\*Proxy*.class 			cn\finedo\codereview\svnuserpermission\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncodeviewproject\*Proxy*.class 			cn\finedo\codereview\svncodeviewproject\
xcopy /Y /S %SERIVCE_PATH%\cn\finedo\codereview\svncommend\*Proxy*.class 			cn\finedo\codereview\svncommend\

jar cvf %DOMAIN_JAR_NAME% cn

set TARGET_PATH=D:\workspaces\codereview_webapp\WebContent\WEB-INF\lib
xcopy /Y %DOMAIN_JAR_NAME% %TARGET_PATH%

cd ..
rmdir /S /Q publish


pause
