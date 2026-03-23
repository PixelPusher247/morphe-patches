# Trakt Patches

Morphe patches for [Trakt.tv](https://trakt.tv) (	v.trakt.trakt).

## Patches

| Name | Description |
|------|-------------|
| Hide VIP banner | Removes the Upgrade to VIP upsell banner from the home screen. |

## Usage

### Morphe CLI

```
java -jar cli.jar patch --patches trakt-patches.mpp input.apk
```

### Morphe Manager

Select the .mpp file from the [Releases](../../releases/latest) page.

## Building

```
./gradlew :patches:buildAndroid
```

The compiled patch file will be at patches/build/libs/.

## License

[GNU General Public License v3.0](LICENSE)
