package inoxoft.simon.myduka.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Payment
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem(
        route = Screens.Dashboard.route,
        title = "Dashboard",
        icon = Icons.Default.Dashboard
    )
    object Stock : BottomNavItem(
        route = Screens.Stock.route,
        title = "Stock",
        icon = Icons.Default.Inventory
    )
    object Finances : BottomNavItem(
        route = Screens.Finances.route,
        title = "Finances",
        icon = Icons.Default.AccountBalance
    )
    object Credit : BottomNavItem(
        route = Screens.Credit.route,
        title = "Credit",
        icon = Icons.Default.Payment
    )
} 