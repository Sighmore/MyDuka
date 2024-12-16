package inoxoft.simon.myduka.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import inoxoft.simon.myduka.MyDukaApplication
import inoxoft.simon.myduka.components.ValidatedTextField
import inoxoft.simon.myduka.utils.Validators
import inoxoft.simon.myduka.utils.ValidationResult
import inoxoft.simon.myduka.viewmodels.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen() {
    val context = LocalContext.current
    val viewModel: StockViewModel = viewModel(
        factory = StockViewModel.Factory(
            (context.applicationContext as MyDukaApplication).stockRepository
        )
    )
    
    var showAddDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf<StockItem?>(null) }
    val stockItems by viewModel.stockItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Stock Management") },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Stock")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            StockList(
                stockItems = stockItems,
                onUpdateStock = { item -> viewModel.updateStock(item) },
                onDeleteStock = { item -> showDeleteConfirmation = item }
            )
        }

        if (showAddDialog) {
            AddStockDialog(
                onDismiss = { showAddDialog = false },
                onAddStock = { newItem ->
                    viewModel.addStock(newItem)
                    showAddDialog = false
                }
            )
        }

        // Delete Confirmation Dialog
        showDeleteConfirmation?.let { stockItem ->
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = null },
                title = { Text("Delete Stock Item") },
                text = { Text("Are you sure you want to delete ${stockItem.name}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteStock(stockItem)
                            showDeleteConfirmation = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = null }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun StockList(
    stockItems: List<StockItem>,
    onUpdateStock: (StockItem) -> Unit,
    onDeleteStock: (StockItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Current Stock",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        items(stockItems) { item ->
            StockItemCard(
                item = item,
                onUpdateQuantity = { newQuantity ->
                    onUpdateStock(item.copy(quantity = newQuantity))
                },
                onDelete = { onDeleteStock(item) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddStockDialog(
    onDismiss: () -> Unit,
    onAddStock: (StockItem) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var buyingPrice by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var reorderPoint by remember { mutableStateOf("") }
    
    var isValid by remember { mutableStateOf(false) }

    LaunchedEffect(name, quantity, buyingPrice, sellingPrice, reorderPoint) {
        isValid = listOf(
            Validators.validateName(name),
            Validators.validateQuantity(quantity),
            Validators.validatePrice(buyingPrice),
            Validators.validatePrice(sellingPrice),
            Validators.validateQuantity(reorderPoint)
        ).all { it is ValidationResult.Success }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Stock") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ValidatedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Product Name",
                    validator = Validators::validateName
                )
                
                ValidatedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = "Quantity",
                    validator = Validators::validateQuantity,
                    keyboardType = KeyboardType.Number
                )
                
                ValidatedTextField(
                    value = buyingPrice,
                    onValueChange = { buyingPrice = it },
                    label = "Buying Price (Ksh)",
                    validator = Validators::validatePrice,
                    keyboardType = KeyboardType.Decimal,
                    prefix = { Text("Ksh ") }
                )
                
                ValidatedTextField(
                    value = sellingPrice,
                    onValueChange = { sellingPrice = it },
                    label = "Selling Price (Ksh)",
                    validator = Validators::validatePrice,
                    keyboardType = KeyboardType.Decimal,
                    prefix = { Text("Ksh ") }
                )
                
                ValidatedTextField(
                    value = reorderPoint,
                    onValueChange = { reorderPoint = it },
                    label = "Reorder Point",
                    validator = Validators::validateQuantity,
                    keyboardType = KeyboardType.Number
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isValid) {
                        onAddStock(
                            StockItem(
                                id = System.currentTimeMillis(),
                                name = name,
                                quantity = quantity.toInt(),
                                buyingPrice = buyingPrice.toFloat(),
                                sellingPrice = sellingPrice.toFloat(),
                                reorderPoint = reorderPoint.toInt()
                            )
                        )
                    }
                },
                enabled = isValid
            ) {
                Text("Add Stock")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockItemCard(
    item: StockItem,
    onUpdateQuantity: (Int) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantityControls(
                            quantity = item.quantity,
                            onUpdateQuantity = onUpdateQuantity
                        )
                        IconButton(
                            onClick = onDelete,
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            
            Divider()
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceInfo(
                    label = "Buying Price",
                    value = "Ksh ${item.buyingPrice}"
                )
                PriceInfo(
                    label = "Selling Price",
                    value = "Ksh ${item.sellingPrice}"
                )
            }
            
            if (item.quantity <= item.reorderPoint) {
                Text(
                    text = "Low Stock Alert!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun QuantityControls(
    quantity: Int,
    onUpdateQuantity: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            onClick = { if (quantity > 0) onUpdateQuantity(quantity - 1) }
        ) {
            Icon(Icons.Default.Remove, contentDescription = "Decrease")
        }
        
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.titleMedium
        )
        
        IconButton(
            onClick = { onUpdateQuantity(quantity + 1) }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Increase")
        }
    }
}

@Composable
private fun PriceInfo(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

data class StockItem(
    val id: Long,
    val name: String,
    val quantity: Int,
    val buyingPrice: Float,
    val sellingPrice: Float,
    val reorderPoint: Int
)

private val sampleStockItems = listOf(
    StockItem(1, "Maize Flour", 5, 180f, 200f, 10),
    StockItem(2, "Sugar", 8, 150f, 170f, 15),
    StockItem(3, "Cooking Oil", 3, 350f, 380f, 8),
    StockItem(4, "Rice", 7, 140f, 160f, 12)
) 