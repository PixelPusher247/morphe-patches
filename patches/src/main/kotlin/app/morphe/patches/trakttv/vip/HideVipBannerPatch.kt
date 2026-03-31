package app.morphe.patches.trakttv.vip

import app.morphe.patches.trakttv.vip.fingerprints.VipBannerVariant1Fingerprint
import app.morphe.patches.trakttv.vip.fingerprints.VipBannerVariant2Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch

@Suppress("unused")
val hideVipBannerPatch = bytecodePatch(
    name = "Hide VIP banner",
    description = "Removes the Upgrade to VIP upsell banner from the home screen.",
) {
    compatibleWith("tv.trakt.trakt" to setOf("3.5.0"))

    execute {
        VipBannerVariant1Fingerprint.method.addInstructions(0, "return-void")
        VipBannerVariant2Fingerprint.method.addInstructions(0, "return-void")
    }
}
