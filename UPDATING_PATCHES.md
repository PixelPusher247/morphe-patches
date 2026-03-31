# Updating Trakt.tv Patches for a New App Version

## Prerequisites
- jadx-gui installed
- The new `trakt_base.apk`
- This repo cloned and building successfully

---

## Step 1: Check If Patches Still Work

Before doing any reverse engineering, just try patching the new APK first.
If it succeeds and the banners are gone on device, you're done.

If you get a `PatchException: Failed to match fingerprint` error, continue below.

---

## Step 2: Open the New APK in jadx

1. Open jadx-gui
2. File → Open files → select `trakt_base.apk`
3. Wait for decompilation to finish

---

## Step 3: Check If Resource IDs Changed

In jadx, use **Ctrl+F** to search in the R.string class for:

- `text_vip_upsell_default`
- `text_vip_upsell_default_2`

Note the hex values. Compare them to what is currently in the fingerprints.
If the values are the same, the fingerprints should still match — the issue
is likely something else. If the values changed, update them in the relevant
fingerprint files (see Step 5).

---

## Step 4: Find the New Methods

For each fingerprint that failed, find the new method using **Find Usages**:

1. In jadx, navigate to the R.string class
2. Click on the string name (e.g. `text_vip_upsell_default`)
3. Press **X** (or right-click → Find Usages)
4. Click each result to find the composable method that renders the banner
5. Note the class type and method name (e.g. `Lc9/a;` and `r`)

The method you want will:
- For **Variant 1**: reference `text_vip_upsell_default` and `badge_text_get_vip`
- For **Variant 2**: reference `text_vip_upsell_default_2` and `badge_text_get_vip`

---

## Step 5: Update the Fingerprints

Open each fingerprint file and update the literal values if the R.string IDs
changed, or fall back to class/method matching if the literals can't be found.

### Literal-based (preferred, survives obfuscation):

`VipBannerVariant1Fingerprint.kt`
```kotlin
custom = { methodDef, _ ->
    methodDef.implementation?.instructions?.any {
        (it as? WideLiteralInstruction)?.wideLiteral == 0xNEW_VALUE_1L
    } == true &&
    methodDef.implementation?.instructions?.any {
        (it as? WideLiteralInstruction)?.wideLiteral == 0xNEW_VALUE_2L
    } == true
}
```

### Class/method name fallback (last resort, will break next update):
```kotlin
custom = { methodDef, classDef ->
    classDef.type == "Lnew/class;" && methodDef.name == "newMethod"
}
```

---

## Step 6: Rebuild and Test
```powershell
# Build
.\gradlew.bat build

# Patch the APK
java -jar C:\tools\morphe-cli.jar patch `
  -b patches\build\libs\patches-1.19.0.mpp `
  -p "Hide VIP banner" `
  trakt_base.apk

# Install
adb install -r trakt_base-patched.apk
```

Open the app and verify:
- [ ] Home screen VIP upsell banner is gone
- [ ] App is otherwise functional

---

## Fingerprint Reference

| Fingerprint | Key Literals | What It Hides |
|---|---|---|
| `VipBannerVariant1Fingerprint` | `text_vip_upsell_default` + `badge_text_get_vip` | Home screen banner (variant 1) |
| `VipBannerVariant2Fingerprint` | `text_vip_upsell_default_2` + `badge_text_get_vip` | Home screen banner (variant 2) |

---

## Notes

- Class and method names (e.g. `Lz7/s;`, `i`) change on **every** release due to
  R8 obfuscation. Never rely on them as the primary matching strategy.
- R.string integer IDs (e.g. `0x7f120265`) are stable across most releases but
  can shift if Trakt adds or removes resources. Always verify them first.
- If a banner reappears after an update, the string resource was likely renamed.
  Search jadx for the visible banner text as a fallback to locate the new method.
