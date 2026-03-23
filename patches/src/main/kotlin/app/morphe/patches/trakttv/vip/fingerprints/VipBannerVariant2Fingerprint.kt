package app.morphe.patches.trakttv.vip.fingerprints

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.literal

internal object VipBannerVariant2Fingerprint : Fingerprint(
    filters = listOf(
        literal { 0x7f120266 } // text_vip_upsell_default_2
    )
)