package app.morphe.patches.trakttv.vip.fingerprints

import app.morphe.patcher.Fingerprint

internal object VipBannerVariant1Fingerprint : Fingerprint(
    custom = { methodDef, classDef ->
        classDef.type == "Lz7/s;" && methodDef.name == "n"
    }
)