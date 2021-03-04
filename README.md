# Teddyhack Anarchy Client

Teddyhack is an anarchy client for minecraft anarchy servers for 1.12.2. <br>
Some of the code is skidded because im new to java lol <br>
**PLEASE NOTE THAT THIS CLIENT HAS ABSOLUTELY NO ANTICHEAT BUILT IN, IT IS ONLY FOR ANARCHY** <br>

## How to use for development environments

I really only know how to set this up for intellij, soooo yeah <br>
- Download and open project in intelliJ (when opening, click the build.gradle and then open) <br>
- The project will auto build itself, so wait for that <br>
- After it builds, you want to go to terminal and run the command `gradlew genIntellijRuns` or `genEclipseRuns` for eclipse <br>
- You are almost done! <br>
- If you are on eclipse, do `gradlew eclipse` after that. <br>
- Refresh the project by closing and opening it, now go to the run configs section :) <br>

## Run configurations

 - Go to Run > Edit Configurations, then click on the plus sign and click application. <br>
 - Put the name to whatever you want, doesn't matter (i usually put it as runClient) <br>
 - First go to modify options, then select add VM options (VM arguements for eclipse). <br>
 - The main class should already be GradleStart, but if it isn't just type gradlestart in the box and it will show it. <br>
 - Now in VM options, you want to put this in: `-Dforge.logging.console.level=debug -Dforge.logging.markers=SCAN,REGISTRIES,REGISTRYDUMP -Dfml.coreMods.load=com.teddyhack.mixin.MixinLoader` <br>
 - Finally, set the working directory to `TeddyhackDirectory\run` (replace the teddyhackdirectory with wherever the main file is) <br>
 - You are done with everything, now just press run (MAKE SURE YOU ARE RUNNING MINECRAFT CLIENT, NOT MINECRAFT SERVER) <br>
 - also i do not know how to change anything in eclipse so diy ez <br>
 
 If it says module not specified, then put the java run version as java 8 and the box next to it as Teddyhack.main. 

## External Libraries & Resources
For the mixins (thanks spongeforge): https://www.spongepowered.org/downloads/spongeforge/stable/1.12.2 <br>
ClickGUI (might be changed in the future idk): https://github.com/lukflug/PanelStudio <br> 
IntelliJ IDEA (community edition): https://www.jetbrains.com/idea/download/#section=windows <br>
<br>
![alt text](https://cdn.discordapp.com/attachments/678127344774545409/808351881272229918/maybe.jpg)
