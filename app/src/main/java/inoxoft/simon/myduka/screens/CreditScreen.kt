package inoxoft.simon.myduka.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditScreen() {
    var showAddDialog by remember { mutableStateOf(false) }
    var customers by remember { mutableStateOf(sampleCustomers) }
    var selectedCustomer by remember { mutableStateOf<CreditCustomer?>(null) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Credit Management") },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Add Customer")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(customers) { customer ->
                CustomerCreditCard(
                    customer = customer,
                    onUpdateCredit = { 
                        selectedCustomer = customer
                        showUpdateDialog = true
                    },
                    onToggleBlacklist = { 
                        customers = customers.map { 
                            if (it.id == customer.id) it.copy(isBlacklisted = !it.isBlacklisted)
                            else it
                        }
                    },
                    onDelete = {
                        customers = customers.filter { it.id != customer.id }
                    }
                )
            }
        }

        // Add Customer Dialog
        if (showAddDialog) {
            AddCustomerDialog(
                onDismiss = { showAddDialog = false },
                onAddCustomer = { newCustomer ->
                    customers = customers + newCustomer
                    showAddDialog = false
                }
            )
        }

        // Update Credit Dialog
        if (showUpdateDialog && selectedCustomer != null) {
            UpdateCreditDialog(
                customer = selectedCustomer!!,
                onDismiss = { 
                    showUpdateDialog = false
                    selectedCustomer = null
                },
                onUpdate = { updatedCustomer ->
                    customers = customers.map { 
                        if (it.id == updatedCustomer.id) updatedCustomer
                        else it
                    }
                    showUpdateDialog = false
                    selectedCustomer = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomerCreditCard(
    customer: CreditCustomer,
    onUpdateCredit: () -> Unit,
    onToggleBlacklist: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (customer.isBlacklisted) 
                MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.surface
        )
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
                        text = customer.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = customer.phone,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = "Ksh ${"%.2f".format(customer.creditAmount)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (customer.creditAmount > 0) 
                        MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onUpdateCredit,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Update Credit")
                }
                
                IconButton(
                    onClick = onToggleBlacklist,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = if (customer.isBlacklisted)
                            MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (customer.isBlacklisted) 
                            Icons.Default.PersonOff
                        else Icons.Default.Person,
                        contentDescription = "Toggle Blacklist",
                        tint = Color.White
                    )
                }
                
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddCustomerDialog(
    onDismiss: () -> Unit,
    onAddCustomer: (CreditCustomer) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var initialCredit by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Customer") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Customer Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = initialCredit,
                    onValueChange = { initialCredit = it },
                    label = { Text("Initial Credit (Ksh)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onAddCustomer(
                            CreditCustomer(
                                id = System.currentTimeMillis(),
                                name = name,
                                phone = phone,
                                creditAmount = initialCredit.toFloatOrNull() ?: 0f,
                                isBlacklisted = false
                            )
                        )
                    }
                }
            ) {
                Text("Add Customer")
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
private fun UpdateCreditDialog(
    customer: CreditCustomer,
    onDismiss: () -> Unit,
    onUpdate: (CreditCustomer) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var isAdding by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Credit for ${customer.name}") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Current Credit: Ksh ${"%.2f".format(customer.creditAmount)}",
                    style = MaterialTheme.typography.titleMedium
                )
                
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount (Ksh)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterChip(
                        selected = isAdding,
                        onClick = { isAdding = true },
                        label = { Text("Add Credit") }
                    )
                    FilterChip(
                        selected = !isAdding,
                        onClick = { isAdding = false },
                        label = { Text("Subtract Credit") }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amountValue = amount.toFloatOrNull() ?: 0f
                    val newAmount = if (isAdding) 
                        customer.creditAmount + amountValue
                    else 
                        customer.creditAmount - amountValue
                    
                    onUpdate(customer.copy(creditAmount = newAmount))
                }
            ) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

data class CreditCustomer(
    val id: Long,
    val name: String,
    val phone: String,
    val creditAmount: Float,
    val isBlacklisted: Boolean
)

private val sampleCustomers = listOf(
    CreditCustomer(1, "John Doe", "0712345678", 1500f, false),
    CreditCustomer(2, "Jane Smith", "0723456789", 2500f, true),
    CreditCustomer(3, "Alice Johnson", "0734567890", 800f, false)
) 