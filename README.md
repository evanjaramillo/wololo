<p align="center">
    <img src="https://github.com/evanjaramillo/wololo/blob/master/assets/monk.jpeg"/>
</p>
<h1 align="center">Wololo!</h1>

[![Build Status](https://app.travis-ci.com/evanjaramillo/wololo.svg?branch=master)](https://app.travis-ci.com/evanjaramillo/wololo)

### AOE2 Definitive Edition Discord taunt bot.

### Configuration file
The configuration file is called `config.yml` and should be placed in the directory of the executable jar. If no such config file is found at runtime, the default configuration file will be generated for you to edit:
```yaml
---
# Options for the thread pool
threadPoolOptions:
  # Thread naming scheme. Useful for log output
  threadNomenclature: "wololo-bot-%d"
  daemon: true
discordOptions:
  # Discord API token generated from Discord's developer portal.
  token: "NO TOKEN!"
  # Discord API compression setting
  compressionEnabled: true
  # Auto reconnect on disconnect
  autoReconnect: true
# I have found that people tend to misuse the bot. This limits
# the amount of times (capacity) each user can use the bot per durationMinutes.
rateLimiterOptions:
  capacity: 15
  durationMinutes: 10
# The bot will only operate on messages that appear in the below server channels
listeningMessageChannels: []
# How the bot should be activated.
activationCharacter: "/"
```

### Running the jar
This app requires that you have a java 1.8 compatible JRE installed on your system.
You can run the bot using: `java -jar wololo-bot.jar`.