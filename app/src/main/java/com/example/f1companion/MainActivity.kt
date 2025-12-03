package com.example.f1companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.f1companion.data.Circuit
import com.example.f1companion.data.Driver
import com.example.f1companion.data.F1ApiService
import com.example.f1companion.data.MockF1ApiService
import com.example.f1companion.data.Race
import com.example.f1companion.data.Season
import com.example.f1companion.data.Team
import com.example.f1companion.ui.CircuitsScreen
import com.example.f1companion.ui.DriversScreen
import com.example.f1companion.ui.Screen
import com.example.f1companion.ui.theme.F1CompanionTheme
import com.example.f1companion.ui.theme.LightGray
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: F1ApiService) : ViewModel() {
    private val _seasons = MutableStateFlow<List<Season>>(emptyList())
    val seasons = _seasons.asStateFlow()

    private val _selectedSeason = MutableStateFlow<Season?>(null)
    val selectedSeason = _selectedSeason.asStateFlow()

    init {
        viewModelScope.launch {
            val allSeasons = apiService.getSeasons()
            _seasons.value = allSeasons
            _selectedSeason.value = allSeasons.firstOrNull { it.isCurrent }
        }
    }

    fun selectSeason(season: Season) {
        _selectedSeason.value = season
    }

    companion object {
        fun provideFactory(apiService: F1ApiService): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(apiService) as T
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.provideFactory(MockF1ApiService()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            F1CompanionTheme {
                F1CompanionApp(viewModel)
            }
        }
    }
}

@Composable
fun F1CompanionApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val screens = listOf(Screen.Dashboard, Screen.Drivers, Screen.Circuits)
    val selectedSeason by viewModel.selectedSeason.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, screens = screens)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) { 
                selectedSeason?.let { season ->
                    F1Dashboard(season, onPastSeasonsClick = { navController.navigate("past_seasons") })
                }
            }
            composable(Screen.Drivers.route) { 
                selectedSeason?.let { DriversScreen(it) } 
            }
            composable(Screen.Circuits.route) { CircuitsScreen() }
            composable("past_seasons") { PastSeasonsScreen(onSeasonSelected = { viewModel.selectSeason(it); navController.popBackStack() }) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, screens: List<Screen>) {
    NavigationBar(
        containerColor = LightGray
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = LightGray
                )
            )
        }
    }
}

@Composable
fun F1Dashboard(selectedSeason: Season, onPastSeasonsClick: () -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        item {
            Header(text = "Current Season: ${selectedSeason.year}")
            DriverStandings(drivers = selectedSeason.drivers)
        }
        item {
            Header(text = "Schedule")
        }
        items(selectedSeason.schedule) { race ->
            RaceCard(race = race)
        }
        item {
            Button(onClick = onPastSeasonsClick, modifier = Modifier.padding(16.dp)) {
                Text("View Past Seasons")
            }
        }
    }
}

@Composable
fun PastSeasonsScreen(onSeasonSelected: (Season) -> Unit) {
    val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = MainViewModel.provideFactory(MockF1ApiService()))
    val seasons by viewModel.seasons.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(seasons.filter { !it.isCurrent }) { season ->
            Card(
                modifier = Modifier.clickable { onSeasonSelected(season) },
                colors = CardDefaults.cardColors(containerColor = LightGray)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
                    Text(season.year.toString(), fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }
            }
        }
    }
}


@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun DriverStandings(drivers: List<Driver>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(drivers) { driver ->
            DriverCard(driver = driver)
        }
    }
}

@Composable
fun DriverCard(driver: Driver, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.width(200.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(driver.team.color)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(driver.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(driver.team.teamName, fontSize = 12.sp, color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Points: ${driver.points}", fontWeight = FontWeight.SemiBold)
                    Text("#${driver.number}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun RaceCard(race: Race, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(race.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(race.circuit.name, fontSize = 14.sp, color = Color.Gray)
            }
            Text(race.date, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    F1CompanionTheme {
        val previewSeason = Season(
            year = 2025,
            drivers = listOf(
                Driver(1, "Lando Norris", 4, Team.MCLAREN, "United Kingdom", 250),
                Driver(2, "Charles Leclerc", 16, Team.FERRARI, "Monaco", 245),
            ),
            schedule = listOf(
                Race(1, "Australian Grand Prix", Circuit("melbourne", "Albert Park Circuit", "Melbourne", "Australia"), "Mar 16")
            ),
            isCurrent = true
        )
        F1Dashboard(selectedSeason = previewSeason, onPastSeasonsClick = {})
    }
}
