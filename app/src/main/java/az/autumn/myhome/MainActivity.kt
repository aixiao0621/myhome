package az.autumn.myhome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import az.autumn.myhome.ui.navigation.Navigation
import az.autumn.myhome.ui.navigation.Screen
import az.autumn.myhome.ui.page.HomePage
import az.autumn.myhome.ui.page.SettingPage
import az.autumn.myhome.ui.theme.MyhomeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val currentScreen = mutableStateOf(Screen.Home.id)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyhomeTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        modifier = Modifier
                                            .padding(0.dp),
                                        text = "Az's home",
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            )

                        },
                        bottomBar = {
                            Navigation(navController)
                        }) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = Screen.Home.id,
                            Modifier.padding(innerPadding),
                        ) {
                            composable(Screen.Home.id) { HomePage() }
                            composable(Screen.Setting.id) { SettingPage() }
                        }
                    }

                }
            }
        }
    }
}