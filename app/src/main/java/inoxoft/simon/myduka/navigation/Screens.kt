package inoxoft.simon.myduka.navigation

sealed class Screens(val route: String) {
    object Dashboard : Screens("dashboard_screen")
    object Stock : Screens("stock_screen")
    object Finances : Screens("finances_screen")
    object Credit : Screens("credit_screen")
} 