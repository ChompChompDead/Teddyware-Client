# Teddyware Utility Mod
[![GitHub all releases](https://img.shields.io/github/downloads/ChompChompDead/Teddyware/total?color=32CD32&style=flat-square)](https://github.com/ChompChompDead/Teddyware/releases)
[![Lines of code](https://img.shields.io/tokei/lines/github/ChompChompDead/Teddyware?color=32CD32&style=flat-square)](https://github.com/ChompChompDead/Teddyhack/tree/master/src/main/java/com/teddyware) <br>

before i start this, yes the client is hugely skidded from postman (though it isnt actually forked from it) <br>
hopefully everyone knows im a terrible coder and im still learning sooo yeah <br>
in the future, there will probably be a rewrite or a new client. not sure. <br>

Teddyware is a utility mod for minecraft anarchy servers for 1.12.2. <br>
It is like **optifine**, and should only be used on servers that allow it. <br>
Also huge thanks to srgantmoomoo for making moo base :) some of the base is made by it (because im trash skid) <br>
Once the whole client is actually out of pre release I will work on unskidding. <br>

## How to use for development environments
I really only know how to set this up for intellij, soooo yeah <br>
- Download and open project in intelliJ (when opening, click the build.gradle and then open) <br>
- The project will auto build itself, so wait for that <br>
- After it builds, you want to go to terminal and run the command `gradlew genIntellijRuns` or `genEclipseRuns` for eclipse <br>
- You are almost done! <br>
- If you are on eclipse, do `gradlew eclipse` after that. <br>
- Refresh the project by closing and opening it, now go to the run configs section :) <br>
- 
## Run configurations
- Go to Run > Edit Configurations, then click on the plus sign and click application. <br>
- Put the name to whatever you want, doesn't matter (i usually put it as runClient) <br>
- First go to modify options, then select add VM options (VM arguments for eclipse). <br>
- The main class should already be GradleStart, but if it isn't just type gradlestart in the box and it will show it. <br>
- Now in VM options, you want to put this in: `-Dforge.logging.console.level=debug -Dforge.logging.markers=SCAN,REGISTRIES,REGISTRYDUMP -Dfml.coreMods.load=com.chompchompdead.teddyhack.api.mixin.MixinLoader` <br>
- Finally, set the working directory to `TeddywareDirectory\run` (replace the teddywaredirectory with wherever the main file is) <br>
- You are done with everything, now just press run <br>
- If setupdecompworkspace is showing an error, just remove it.
- also i do not know how to change anything in eclipse so diy ez <br>
- 
## External Libraries, Resources, and Contacts
Official Discord: https://discord.gg/X2BmAqW8ry <br>
SpongeForge Mixins: https://www.spongepowered.org/downloads/spongeforge/stable/1.12.2 <br>
IntelliJ IDEA (community edition): https://www.jetbrains.com/idea/download/#section=windows <br>
PanelStudio ClickGUI: https://github.com/lukflug/PanelStudio <br>
<br>
![alt text for photo](https://cdn.discordapp.com/attachments/807282463512592445/821552314824654848/maybe_1_2.jpg)
