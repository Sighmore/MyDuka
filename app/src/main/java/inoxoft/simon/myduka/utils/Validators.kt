package inoxoft.simon.myduka.utils

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

object Validators {
    fun validateAmount(amount: String): ValidationResult {
        return when {
            amount.isEmpty() -> ValidationResult.Error("Amount cannot be empty")
            amount.toFloatOrNull() == null -> ValidationResult.Error("Invalid amount format")
            amount.toFloat() < 0 -> ValidationResult.Error("Amount cannot be negative")
            else -> ValidationResult.Success
        }
    }

    fun validateQuantity(quantity: String): ValidationResult {
        return when {
            quantity.isEmpty() -> ValidationResult.Error("Quantity cannot be empty")
            quantity.toIntOrNull() == null -> ValidationResult.Error("Invalid quantity format")
            quantity.toInt() < 0 -> ValidationResult.Error("Quantity cannot be negative")
            else -> ValidationResult.Success
        }
    }

    fun validateName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult.Error("Name cannot be empty")
            name.length < 3 -> ValidationResult.Error("Name must be at least 3 characters")
            else -> ValidationResult.Success
        }
    }

    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isEmpty() -> ValidationResult.Error("Phone number cannot be empty")
            !phone.matches(Regex("^[0-9]{10}$")) -> 
                ValidationResult.Error("Invalid phone number format (10 digits required)")
            else -> ValidationResult.Success
        }
    }

    fun validatePrice(price: String): ValidationResult {
        return when {
            price.isEmpty() -> ValidationResult.Error("Price cannot be empty")
            price.toFloatOrNull() == null -> ValidationResult.Error("Invalid price format")
            price.toFloat() <= 0 -> ValidationResult.Error("Price must be greater than 0")
            else -> ValidationResult.Success
        }
    }
} 