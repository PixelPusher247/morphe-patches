package app.morphe.patches.trakttv.vip.fingerprints

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.literal

internal object VipBannerVariant1Fingerprint : Fingerprint(
    filters = listOf(
        literal { 0x7f120265 } // text_vip_upsell_default
    )
)