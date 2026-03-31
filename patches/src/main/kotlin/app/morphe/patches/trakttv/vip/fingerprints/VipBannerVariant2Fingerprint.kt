package app.morphe.patches.trakttv.vip.fingerprints

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction

internal object VipBannerVariant2Fingerprint : Fingerprint(
    custom = { methodDef, _ ->
        methodDef.implementation?.instructions?.any {
            (it as? WideLiteralInstruction)?.wideLiteral == 0x7f120275L // text_vip_upsell_default_2
        } == true &&
        methodDef.implementation?.instructions?.any {
            (it as? WideLiteralInstruction)?.wideLiteral == 0x7f120026L // badge_text_get_vip
        } == true
    }
)
