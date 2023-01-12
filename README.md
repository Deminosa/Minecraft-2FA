# 2FA for Minecraft (Bungeecord Only!)
Minecraft connections can be authenticated using a simple web interface.

![](https://img.shields.io/badge/Version-1.0--Snapshot-red) ![](https://img.shields.io/badge/Webinterface-v1.0-green) ![](https://img.shields.io/badge/Bungeecord-1.19--R0.1--SNAPSHOT-green)

## How to Setup?
First, an account must be created via the console. Then the user has to enter the register token via the web interface together with his Minecraft name. The Minecraft name is used for UUID identification.

After the user has entered the register token, the user receives a QR code which he can scan using the Google Auth app.

After that, the user has to authenticate himself for every connection. After successful authentication, the user only has 30s to connect. He then has to verify himself again.