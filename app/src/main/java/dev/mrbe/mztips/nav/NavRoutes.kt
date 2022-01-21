package dev.mrbe.mztips.nav

sealed class NavRoutes(val route: String) {
    object HomePage : NavRoutes("home")
    object DailyOdds : NavRoutes("dailyOdds")
    object PreviousOdds : NavRoutes("previousOdds")
}
