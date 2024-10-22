package az.autumn.myhome.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun Navigation(
    navController: NavController
) {
    val items = Screen.Items.list
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.id } == true,
                onClick = { navController.navigate(item.id) },
                label = { Text(item.id) },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                    )
                }
            )
        }
    }
}