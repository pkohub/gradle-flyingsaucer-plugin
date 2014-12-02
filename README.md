# Gradle Flyingsaucer plugin

## Usage

To use the plugin's functionality, you will need to add the its binary artifact to your build script's classpath and apply the plugin.

### Adding the plugin binary to the build

```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'at.pkohub.gradle-flyingsaucer-plugin:0.1'
    }
}
```
