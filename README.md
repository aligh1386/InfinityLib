# InfinityLib


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
