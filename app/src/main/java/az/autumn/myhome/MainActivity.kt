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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import az.autumn.myhome.data.repository.DeviceRepository
import az.autumn.myhome.database.AppDatabase
import az.autumn.myhome.network.RetrofitClient
import az.autumn.myhome.ui.navigation.Navigation
import az.autumn.myhome.ui.navigation.Screen
import az.autumn.myhome.ui.page.HomePage
import az.autumn.myhome.ui.page.SettingPage
import az.autumn.myhome.ui.theme.MyhomeTheme
import az.autumn.myhome.viewmodel.HomeViewModel
import az.autumn.myhome.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // Remove or implement this if necessary
        WindowCompat.setDecorFitsSystemWindows(window, false)

//        // Initialize database and repository
        val database = AppDatabase.getDatabase(this)
        val deviceRepository = DeviceRepository(RetrofitClient.apiService)
        val viewModelFactory = HomeViewModelFactory(deviceRepository, database.temperatureDao())

        setContent {
            MyhomeTheme {
                val navController = rememberNavController()
                val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Az's home",
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    },
                    bottomBar = {
                        Navigation(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Home.id,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.id) { HomePage(viewModel) }
                        composable(Screen.Setting.id) { SettingPage() }
                    }
                }
            }
        }
    }
}