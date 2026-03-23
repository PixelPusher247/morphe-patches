package app.morphe.patches.trakttv.vip.fingerprints

import app.morphe.patcher.Fingerprint

internal object VipBannerVariant2Fingerprint : Fingerprint(
    custom = { methodDef, classDef ->
        classDef.type == "La/a;" && methodDef.name == "N"
    }
)