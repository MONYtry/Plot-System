# Plot-System

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Paper](https://img.shields.io/badge/Paper-1.21.8-0B6E4F?style=for-the-badge)
![PlotSquared](https://img.shields.io/badge/PlotSquared-Required-4B8BBE?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-28A745?style=for-the-badge)
![Minecraft](https://img.shields.io/badge/Minecraft-Plugin-9B59B6?style=for-the-badge)
![Build](https://img.shields.io/badge/Build-Maven-C71D1D?style=for-the-badge&logo=apachemaven&logoColor=white)

</div>

A premium-style PlotSquared plugin for Minecraft servers that lets players customize and manage their plots with a polished configuration system, GUI-driven controls, rewards, permissions, and more.

## Overview

Plot-System is a Java plugin designed for PlotSquared-based servers. It adds plot customization tools that help players configure their plot experience with features such as border styling, biome selection, music disc support, hopper access control, rewards, and command-driven settings management.

This project is built as a Paper-compatible plugin and is intended for modern Minecraft server environments running PlotSquared.

## Features

- Plot customization options
- Border material permissions and controls
- Biome selection for plots
- Music disc support
- Reward and achievement integration
- GUI-related configuration files
- Command-based management via Plotsettings
- Permission-based access control for players and operators
- Java 21 / Paper 1.21.x compatibility

## Requirements

Before using this plugin, ensure your server meets the following:

- Java 21+
- Paper 1.21.x
- PlotSquared installed and enabled
- Vault API (if used by your server setup)
- WorldEdit compatibility for the relevant environment

## Installation

1. Build the plugin with Maven:

```bash
mvn clean package
```

2. Copy the generated JAR file from the target directory to your server's plugins folder.

3. Start or restart your Paper server.

4. Ensure PlotSquared is loaded before the plugin.

## Commands

The plugin registers the following command:

- `/Plotsettings` - Main plugin command entry point

## Permissions

The project includes permission entries such as:

- `plotsettings.use`
- `plotsettings.*`
- `plotsettings.hopper-admin`
- `plotsettings.border.*`
- `plotsettings.biome.*`
- `plotsettings.music.*`

Permissions are structured so operators can manage large parts of the plugin while standard users can be restricted appropriately.

## Configuration

The plugin includes a set of YAML configuration files under `src/main/resources`:

- `config.yml`
- `gui.yml`
- `messages.yml`
- `rewards.yml`
- `plugin.yml`
- `playerData.yml`
- `plot.yml`

These files handle plugin settings, display text, rewards, and plot data.

## Project Structure

```text
Plot-System/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── de/main/plotSettings/
│   │   │       ├── Achviment/
│   │   │       ├── Commands/
│   │   │       ├── GUI/
│   │   │       ├── Level/
│   │   │       ├── Listener/
│   │   │       ├── Manager/
│   │   │       ├── Rewards/
│   │   │       └── PlotSettings.java
│   │   └── resources/
│   │       ├── config.yml
│   │       ├── gui.yml
│   │       ├── messages.yml
│   │       ├── rewards.yml
│   │       ├── plugin.yml
│   │       ├── playerData.yml
│   │       └── plot.yml
├── pom.xml
├── .gitignore
└── README.md
```

## Building from Source

This project uses Maven.

```bash
git clone https://github.com/MONYtry/Plot-System.git
cd Plot-System
mvn clean package
```

The packaged JAR will be generated in the `target` directory.

## License

This repository does not currently declare a license in the project metadata. If you intend to distribute or reuse this plugin publicly, it is recommended to add an appropriate open-source license before release.

## Author

Created and maintained by MONYtry.

## Repository

- GitHub: https://github.com/MONYtry/Plot-System

## Notes

This plugin is best suited for servers that rely on PlotSquared for land and plot management, and it adds a layer of customization and player-facing control around that system.

---

If you want, I can also generate a more advanced version of this README with:
- screenshots section,
- feature cards,
- custom badges for release version,
- a full command/permission reference table, or
- a versioned release template for GitHub. 
