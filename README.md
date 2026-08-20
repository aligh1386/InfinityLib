# InfinityLib (Folia-compatible)

A shaded library for Slimefun addons which adds a bunch of useful classes and utilities.

**This version includes native Folia support** — no external FoliaLib dependency.  
It detects Folia at runtime and routes scheduler tasks through Paper's `GlobalRegionScheduler` / `AsyncScheduler`. On regular Paper/Spigot it behaves exactly like the original.

Original author: [Mooy1](https://github.com/Mooy1)  
Folia adaptation: native (direct Paper API)

---

## Packages & Features

### Core
**AbstractAddon**: An implementation of JavaPlugin which you will need to extend for many of the other features to work. It provides multiple utility methods and does some basic setup for you.

**AddonConfig**: is an implementation of YamlConfiguration which makes comments available in the user's config and provides utility methods such as getting a value from within a range and removing unused/old keys from the user's config.

### Common
**CoolDowns**: A utility object for keeping track of cool downs of players/uuids

**PersistentType**: Contains some PersistentDataTypes for ItemStack's, ItemStack Array's, Locations, and String Arrays. Also provides a constructor for PersistentDataType that uses lambda parameters.

**Events**: Contains static utility methods for registering listeners, creating handlers, and calling events

**Scheduler**: Contains static utility methods for running and repeating tasks  
→ **Folia-compatible** (uses GlobalRegionScheduler / AsyncScheduler on Folia)

### Commands
**AddonCommand**: allows you to add commands easily with a parent-child structure, so you could have a command with a sub command which has a sub command. It also adds some default commands such as an addon info, aliases, and help command.

### Groups
**MultiGroup**: An implementation of ItemGroup which lets you organize your groups into SubGroups

**SubGroup**: An ItemGroup that is hidden from the main page, for use in MultiGroup

### Machines
**MenuBlock**: A of SlimefunItem with a menu, providing overridable methods for setting up the menu

**TickingMenuBlock**: A MenuBlock with slimefun ticker

**AbstractMachineBlock**: A TickingMenuBlock which implements EnergyNetComponent and provides a process method

**MachineBlock**: An AbstractMachineBlock which makes it easy to create simple input-output machines

---

## How to use (via JitPack)

After you push this repository to **your GitHub**, others (and you) can depend on it like this:

### 1. Add the JitPack repository

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### 2. Add the dependency

Replace `YOUR_GITHUB_USERNAME` and the version/tag:

```xml
<dependency>
    <groupId>com.github.YOUR_GITHUB_USERNAME</groupId>
    <artifactId>InfinityLib</artifactId>   <!-- or the exact repo name you chose -->
    <version>1.3.10-folia</version>         <!-- or a tag / commit hash -->
    <scope>compile</scope>
</dependency>
```

> Tip: After pushing, create a **Release / Tag** named e.g. `1.3.10-folia` so the version is stable and JitPack caches it.

### 3. Shade + Relocate (required)

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.5.1</version>
    <configuration>
        <minimizeJar>true</minimizeJar>
        <relocations>
            <relocation>
                <pattern>io.github.mooy1.infinitylib</pattern>
                <shadedPattern>YOUR.MAIN.PACKAGE.HERE.infinitylib</shadedPattern>
            </relocation>
        </relocations>
        <filters>
            <filter>
                <artifact>*:*</artifact>
                <excludes>
                    <exclude>META-INF/*</exclude>
                </excludes>
            </filter>
        </filters>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 4. Main class

Change your main plugin class to extend `AbstractAddon` and implement the constructor.  
Use `enable()` / `disable()` instead of `onEnable()` / `onDisable()`.  
Do **not** call `super.onEnable()` / `super.onDisable()`.

---

## Building locally

```bash
mvn clean package
```

The shaded jar will be in `target/`.

---

## License

Same as the original InfinityLib (see LICENSE file).
