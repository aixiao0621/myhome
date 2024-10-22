package az.autumn.myhome.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    var id: String,
    val title: String,
    val icon: ImageVector,
) {

    object Home : Screen("home", "喝水", Icons.Outlined.Home)
    object Setting : Screen("setting", "设置", Icons.Outlined.Settings)

    object Items {
        val list = listOf(
            Home, Setting
        )
    }

}