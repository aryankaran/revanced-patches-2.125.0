package app.revanced.patches.youtube.layout.amoled.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.impl.ResourceData
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patcher.patch.impl.ResourcePatch
import app.revanced.patches.youtube.layout.amoled.annotations.AmoledCompatibility
import app.revanced.patches.youtube.misc.manifest.patch.FixLocaleConfigErrorPatch
import org.w3c.dom.Element
import java.io.File

@Patch
@DependsOn([FixLocaleConfigErrorPatch::class])
@Name("amoled")
@Description("Enables pure black theme.")
@AmoledCompatibility
@Version("0.0.1")
class AmoledPatch : ResourcePatch() {
    override fun execute(data: ResourceData): PatchResult {
        data.xmlEditor["res${File.separator}values${File.separator}colors.xml"].use { editor ->
            val resourcesNode = editor.file.getElementsByTagName("resources").item(0) as Element

            for (i in 0 until resourcesNode.childNodes.length) {
                val node = resourcesNode.childNodes.item(i)
                if (node !is Element) continue

                val element = resourcesNode.childNodes.item(i) as Element
                element.textContent = when (element.getAttribute("name")) {
                    "yt_black2", "yt_black3", "yt_black4", "yt_status_bar_background_dark" -> "@android:color/system_accent1_800"
                    "yt_black1_opacity95", "yt_black1" -> "@android:color/system_neutral1_900"
                    "yt_youtube_red", "yt_brand_red", "video_progress_passed_indicator_color", "shorts_multi_segment_progress_bar_active_color", "text_color_blue", "yt_light_blue", "lc_button_style_primary_background", "yt_light_red" -> "@android:color/system_accent1_100"
                    "yt_medium_red" -> "@android:color/system_accent1_600"
                    "yt_selected_nav_label_dark" -> "#ffdf0000"
                    else -> continue
                }
            }
        }

        return PatchResultSuccess()
    }
}
