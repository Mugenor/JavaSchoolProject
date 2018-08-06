@echo off
call ng build
call echo Built
call cp -r dist\resource ..\RailwayTravels\target\rwt
call echo Copied
