Set ws = CreateObject("Wscript.Shell") 
ws.run "cmd /c java -jar D:\code\sygdSoft\release\target\release-1.0-SNAPSHOT.jar  > runlog.log",vbhide 