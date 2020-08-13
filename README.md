Table of Contents
=================

   * [Necessities](#necessities)
      * [Commands](#commands)
         * [/gamemode](#gamemode)
         * [/necessities](#necessities)
      * [Releases](#releases)
      * [Building](#building)
      
# Necessities
As a developer who runs a lot of servers for various clients, I like having certain features that
Essentials just can't provide. This results in me re-writing the same commands 100 times. So, I'm
compiling all the commands I use into Necessities. Necessities (Ness for short) also has the goal
of command syntax that just makes sense.

## Where does it work?
It's designed to work on Paper 1.16.1 and 1.15.2. Nothing else is supported.

## Commands
All commands also have aliases that are `/n` + the command name and its aliases.

### /gamemode
Permission: `ness.gamemode`  
Aliases: `/gm`, `/gm<identifier>` (will be used for examples)

Usage | Description | Example
--- | --- | ---
`/gm` | Toggle between Creative and Survival | `/gm`
`/gm <players...>` | Toggle players between Creative and Survival | `/gm violetwtf violetlol`
`/gm <identifier/gamemode>` | Set your game mode | `/gm creative`
`/gm <identifier/gamemode> <players...>` | Set players' game modes | `/gm adventure *`
`/gm<identifier>` | Set your game mode | `/gmc`
`/gm<identifier> <players...>` | Set players' game modes | `/gm0 violetwtf`

Where identifiers are:
* 0 or s, meaning survival mode
* 1 or c, meaning creative mode
* 2 or a, meaning adventure mode
* 3 or sp, meaning spectator mode

Where gamemodes are:
* survival
* creative
* adventure
* spectator

The character `*`, when used in place of players, refers to all players.

### /necessities
Permission: none  
Alias: `/ness`

Displays information about the plugin.

## Releases
Releases won't be built until 1.0.0, but you can build this yourself! See [Building](#building).

## Building
* Clone https://github.com/violetwtf/Common
* Install it to your local Maven repository (`mvn install`)
* Clone this repository
* Run `mvn package`
* Ta-da!