package inoxoft.simon.myduka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import inoxoft.simon.myduka.screens.CreditScreen
import inoxoft.simon.myduka.screens.DashboardScreen
import inoxoft.simon.myduka.screens.FinancesScreen
import inoxoft.simon.myduka.screens.StockScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Dashboard.route,
        modifier = modifier
    ) {
        composable(route = Screens.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(route = Screens.Stock.route) {
            StockScreen()
        }
        composable(route = Screens.Finances.route) {
            FinancesScreen()
        }
        composable(route = Screens.Credit.route) {
            CreditScreen()
        }
    }
} 