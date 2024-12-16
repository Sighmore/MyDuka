package inoxoft.simon.myduka.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import inoxoft.simon.myduka.viewmodels.FinanceViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.text.KeyboardOptions
import inoxoft.simon.myduka.utils.Validators
import inoxoft.simon.myduka.components.ValidatedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen() {
    val context = LocalContext.current
    val viewModel: FinanceViewModel = viewModel(
        factory = FinanceViewModel.Factory(
            (context.applicationContext as MyDukaApplication).financeRepository
        )
    )
    
    var cash by remember { mutableStateOf("") }
    var float by remember { mutableStateOf("") }
    var workingAmount by remember { mutableStateOf("") }
    
    val records by viewModel.records.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Finances") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Daily Input Section
            item {
                DailyInputCard(
                    cash = cash,
                    float = float,
                    workingAmount = workingAmount,
                    onCashChange = { cash = it },
                    onFloatChange = { float = it },
                    onWorkingAmountChange = { workingAmount = it }
                )
            }

            // Total Card
            item {
                TotalCard(
                    cash = cash.toFloatOrNull() ?: 0f,
                    float = float.toFloatOrNull() ?: 0f,
                    workingAmount = workingAmount.toFloatOrNull() ?: 0f
                )
            }

            // Save Button
            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.addRecord(
                            cash = cash.toFloatOrNull() ?: 0f,
                            float = float.toFloatOrNull() ?: 0f,
                            workingAmount = workingAmount.toFloatOrNull() ?: 0f
                        )
                        // Reset inputs
                        cash = ""
                        float = ""
                        workingAmount = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        Icons.Default.Save,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Save Daily Record")
                }
            }

            // Records Table Header
            item {
                Text(
                    text = "Recent Records",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Records
            items(records.sortedByDescending { it.date }) { record ->
                var showDeleteDialog by remember { mutableStateOf(false) }
                
                RecordCard(
                    record = record,
                    onDelete = { showDeleteDialog = true }
                )
                
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Delete Record") },
                        text = { 
                            Text("Are you sure you want to delete this financial record from ${
                                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(record.date))
                            }?") 
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    viewModel.deleteRecord(record)
                                    showDeleteDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Delete")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyInputCard(
    cash: String,
    float: String,
    workingAmount: String,
    onCashChange: (String) -> Unit,
    onFloatChange: (String) -> Unit,
    onWorkingAmountChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ValidatedTextField(
                value = cash,
                onValueChange = onCashChange,
                label = "Cash (Ksh)",
                validator = Validators::validateAmount,
                keyboardType = KeyboardType.Decimal,
                prefix = { Text("Ksh ") }
            )

            ValidatedTextField(
                value = float,
                onValueChange = onFloatChange,
                label = "Float (Ksh)",
                validator = Validators::validateAmount,
                keyboardType = KeyboardType.Decimal,
                prefix = { Text("Ksh ") }
            )

            ValidatedTextField(
                value = workingAmount,
                onValueChange = onWorkingAmountChange,
                label = "Working Amount (Ksh)",
                validator = Validators::validateAmount,
                keyboardType = KeyboardType.Decimal,
                prefix = { Text("Ksh ") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoneyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("$label (Ksh)") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        prefix = { Text("Ksh ") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Composable
private fun TotalCard(
    cash: Float,
    float: Float,
    workingAmount: Float
) {
    val total = cash + float + workingAmount

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Amount",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Ksh ${"%.2f".format(total)}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun RecordCard(
    record: FinanceRecord,
    onDelete: () -> Unit = {}
) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = dateFormat.format(Date(record.date)),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            text = "Total: Ksh ${"%.2f".format(record.total)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    
                    // Delete Button
                    IconButton(
                        onClick = onDelete,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Record",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            
            Divider()
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AmountColumn("Cash", record.cash)
                AmountColumn("Float", record.float)
                AmountColumn("Working", record.workingAmount)
            }
        }
    }
}

@Composable
private fun AmountColumn(
    label: String,
    amount: Float
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Ksh ${"%.2f".format(amount)}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

data class FinanceRecord(
    val id: Long,
    val date: Long,
    val cash: Float,
    val float: Float,
    val workingAmount: Float
) {
    val total: Float get() = cash + float + workingAmount
}

private val sampleFinanceRecords = listOf(
    FinanceRecord(1, System.currentTimeMillis() - 86400000 * 2, 25000f, 15000f, 5000f),
    FinanceRecord(2, System.currentTimeMillis() - 86400000, 28000f, 12000f, 6000f),
    FinanceRecord(3, System.currentTimeMillis(), 30000f, 18000f, 4000f)
) 