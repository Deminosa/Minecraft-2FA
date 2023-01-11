# 2FA for Minecraft
Minecraft connections can be authenticated using a simple web interface.

## How to Setup?
First, an account must be created via the console. Then the user has to enter the register token via the web interface together with his Minecraft name. The Minecraft name is used for UUID identification.

After the user has entered the register token, the user receives a QR code which he can scan using the Google Auth app.

After that, the user has to authenticate himself for every connection. After successful authentication, the user only has 30s to connect. He then has to verify himself again.