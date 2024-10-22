package az.autumn.myhome.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val id: String,
    val title: String,
    val icon: ImageVector,
) {
    object Home : Screen("home", "Home", Icons.Outlined.Home)
    object Setting : Screen("setting", "Settings", Icons.Outlined.Settings)

    object Items {
        val list = listOf(
            Home, Setting
        )
    }
}