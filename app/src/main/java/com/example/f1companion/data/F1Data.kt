package com.example.f1companion.data

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

// Enums and Colors are not directly serializable in the same way. We handle the team color manually.
@Serializable
enum class Team(val teamName: String) {
    MERCEDES("Mercedes-AMG Petronas"),
    RED_BULL("Oracle Red Bull Racing"),
    FERRARI("Scuderia Ferrari"),
    MCLAREN("McLaren Formula 1 Team"),
    ASTON_MARTIN("Aston Martin Aramco Cognizant"),
    ALPINE("BWT Alpine F1 Team"),
    RB("Visa Cash App RB Formula One Team"),
    SAUBER("Stake F1 Team Kick Sauber"),
    HAAS("MoneyGram Haas F1 Team"),
    WILLIAMS("Williams Racing"),
    ALFA_ROMEO("Alfa Romeo F1 Team Stake"),
    ALPHATAURI("Scuderia AlphaTauri"),
    LOTUS("Lotus F1 Team"),
    RENAULT("Renault F1 Team"),
    BRAWN_GP("Brawn GP Formula One Team"),
    ANDRETTI("Andretti Global");

    val color: Color
        get() = when (this) {
            MERCEDES -> Color(0xFF6CD3BF)
            RED_BULL -> Color(0xFF3671C6)
            FERRARI -> Color(0xFFF91536)
            MCLAREN -> Color(0xFFFF8000)
            ASTON_MARTIN -> Color(0xFF358C75)
            ALPINE -> Color(0xFF2293D1)
            RB -> Color(0xFF6692FF)
            SAUBER -> Color(0xFF52E252)
            HAAS -> Color(0xFFB6BABD)
            WILLIAMS -> Color(0xFF64C4FF)
            ALFA_ROMEO -> Color(0xFFC92D4B)
            ALPHATAURI -> Color(0xFF5E8FAA)
            LOTUS -> Color(0xFFD4AF37)
            RENAULT -> Color(0xFFFFD700)
            BRAWN_GP -> Color(0xFFC4DD00)
            ANDRETTI -> Color(0xFF0066CC)
        }
}

@Serializable
data class Driver(
    val id: Int,
    val name: String,
    val number: Int,
    val team: Team,
    val nationality: String,
    val points: Int
)

@Serializable
data class Circuit(
    val id: String,
    val name: String,
    val location: String,
    val country: String,
)

@Serializable
data class Race(
    val round: Int,
    val name: String,
    val circuit: Circuit,
    val date: String // Using String for simplicity, should be a date type in a real app
)

@Serializable
data class Season(
    val year: Int,
    val drivers: List<Driver>,
    val schedule: List<Race>,
    val isCurrent: Boolean = false
)

object MockData {
    private val drivers2025 = listOf(
        Driver(1, "Lando Norris", 4, Team.MCLAREN, "United Kingdom", 250),
        Driver(2, "Charles Leclerc", 16, Team.FERRARI, "Monaco", 245),
        Driver(3, "Max Verstappen", 1, Team.RED_BULL, "Netherlands", 240),
        Driver(4, "Oscar Piastri", 81, Team.MCLAREN, "Australia", 220),
        Driver(5, "Carlos Sainz", 55, Team.WILLIAMS, "Spain", 190), // Team change example
        Driver(6, "George Russell", 63, Team.MERCEDES, "United Kingdom", 185),
        Driver(7, "Lewis Hamilton", 44, Team.FERRARI, "United Kingdom", 180), // Team change example
        Driver(8, "Sergio Pérez", 11, Team.HAAS, "Mexico", 150), // Team change example
        Driver(9, "Alex Albon", 23, Team.RED_BULL, "Thailand", 140), // Team change example
        Driver(10, "Colton Herta", 26, Team.ANDRETTI, "USA", 120) // New driver example
    )

    private val drivers2024 = listOf(
        Driver(1, "Lando Norris", 4, Team.MCLAREN, "United Kingdom", 175),
        Driver(2, "Max Verstappen", 1, Team.RED_BULL, "Netherlands", 169),
        Driver(3, "Charles Leclerc", 16, Team.FERRARI, "Monaco", 138),
        Driver(4, "Carlos Sainz", 55, Team.FERRARI, "Spain", 108),
        Driver(5, "Sergio Pérez", 11, Team.RED_BULL, "Mexico", 107),
        Driver(6, "Oscar Piastri", 81, Team.MCLAREN, "Australia", 71),
        Driver(7, "George Russell", 63, Team.MERCEDES, "United Kingdom", 54),
        Driver(8, "Lewis Hamilton", 44, Team.MERCEDES, "United Kingdom", 42),
        Driver(9, "Fernando Alonso", 14, Team.ASTON_MARTIN, "Spain", 33),
        Driver(10, "Yuki Tsunoda", 22, Team.RB, "Japan", 19)
    )

    private val drivers2023 = listOf(
        Driver(1, "Max Verstappen", 1, Team.RED_BULL, "Netherlands", 575),
        Driver(2, "Sergio Pérez", 11, Team.RED_BULL, "Mexico", 285),
        Driver(3, "Lewis Hamilton", 44, Team.MERCEDES, "United Kingdom", 234),
        Driver(4, "Fernando Alonso", 14, Team.ASTON_MARTIN, "Spain", 206),
        Driver(5, "Charles Leclerc", 16, Team.FERRARI, "Monaco", 206),
        Driver(6, "Lando Norris", 4, Team.MCLAREN, "United Kingdom", 205),
        Driver(7, "Carlos Sainz", 55, Team.FERRARI, "Spain", 200),
        Driver(8, "George Russell", 63, Team.MERCEDES, "United Kingdom", 175),
        Driver(9, "Oscar Piastri", 81, Team.MCLAREN, "Australia", 97),
        Driver(10, "Lance Stroll", 18, Team.ASTON_MARTIN, "Canada", 74)
    )

    private val drivers2021 = listOf(
        Driver(1, "Max Verstappen", 33, Team.RED_BULL, "Netherlands", 395),
        Driver(2, "Lewis Hamilton", 44, Team.MERCEDES, "United Kingdom", 387),
        Driver(3, "Valtteri Bottas", 77, Team.MERCEDES, "Finland", 226)
    )

    private val drivers2012 = listOf(
        Driver(1, "Sebastian Vettel", 1, Team.RED_BULL, "Germany", 281),
        Driver(2, "Fernando Alonso", 5, Team.FERRARI, "Spain", 278),
        Driver(3, "Kimi Räikkönen", 9, Team.LOTUS, "Finland", 207)
    )

    val circuits = mapOf(
        "bahrain" to Circuit("bahrain", "Bahrain International Circuit", "Sakhir", "Bahrain"),
        "jeddah" to Circuit("jeddah", "Jeddah Corniche Circuit", "Jeddah", "Saudi Arabia"),
        "melbourne" to Circuit("melbourne", "Albert Park Circuit", "Melbourne", "Australia"),
        "suzuka" to Circuit("suzuka", "Suzuka International Racing Course", "Suzuka", "Japan"),
        "shanghai" to Circuit("shanghai", "Shanghai International Circuit", "Shanghai", "China"),
        "miami" to Circuit("miami", "Miami International Autodrome", "Miami", "USA"),
        "monaco" to Circuit("monaco", "Circuit de Monaco", "Monte Carlo", "Monaco"),
        "baku" to Circuit("baku", "Baku City Circuit", "Baku", "Azerbaijan"),
    )

    private val schedule2025 = listOf(
        Race(1, "Australian Grand Prix", circuits["melbourne"]!!, "Mar 16"),
        Race(2, "Chinese Grand Prix", circuits["shanghai"]!!, "Mar 23"),
        Race(3, "Japanese Grand Prix", circuits["suzuka"]!!, "Apr 06"),
        Race(4, "Bahrain Grand Prix", circuits["bahrain"]!!, "Apr 13"),
        Race(5, "Saudi Arabian Grand Prix", circuits["jeddah"]!!, "Apr 20"),
    )

    private val schedule2024 = listOf(
        Race(1, "Bahrain Grand Prix", circuits["bahrain"]!!, "Mar 02"),
        Race(2, "Saudi Arabian Grand Prix", circuits["jeddah"]!!, "Mar 09"),
        Race(3, "Australian Grand Prix", circuits["melbourne"]!!, "Mar 24"),
        Race(4, "Japanese Grand Prix", circuits["suzuka"]!!, "Apr 07"),
        Race(5, "Chinese Grand Prix", circuits["shanghai"]!!, "Apr 21"),
        Race(6, "Miami Grand Prix", circuits["miami"]!!, "May 05"),
        Race(7, "Monaco Grand Prix", circuits["monaco"]!!, "May 26"),
    )

    private val schedule2023 = listOf(
        Race(1, "Bahrain Grand Prix", circuits["bahrain"]!!, "Mar 05"),
        Race(2, "Saudi Arabian Grand Prix", circuits["jeddah"]!!, "Mar 19"),
        Race(3, "Australian Grand Prix", circuits["melbourne"]!!, "Apr 02"),
        Race(4, "Azerbaijan Grand Prix", circuits["baku"]!!, "Apr 30"),
        Race(5, "Miami Grand Prix", circuits["miami"]!!, "May 07"),
    )

    private val schedule2021 = emptyList<Race>()
    private val schedule2012 = emptyList<Race>()


    val seasons = (
            listOf(
                Season(2025, drivers2025, schedule2025, isCurrent = true),
                Season(2024, drivers2024, schedule2024),
                Season(2023, drivers2023, schedule2023),
                Season(2021, drivers2021, schedule2021),
                Season(2012, drivers2012, schedule2012)
            ) + (2022 downTo 2000).map { year ->
                if (year != 2021 && year != 2012) {
                    Season(year, emptyList(), emptyList())
                } else {
                    null
                }
            }.filterNotNull()
            ).sortedByDescending { it.year }
}
