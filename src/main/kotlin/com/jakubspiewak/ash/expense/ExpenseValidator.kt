package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.http.*
import com.jakubspiewak.ash.expense.model.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

fun <K, V> Map<K, V>.deepEqualKeys(keys: Set<K>): Boolean {
    return this.keys == keys
}

fun String.isEmail(): Boolean {
    val pattern = Regex(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    return this.matches(pattern)
}

@Service
class ExpenseValidator(private val repository: ExpenseRepository) {
    private val keysForNumberAmountType = setOf("it")
    private val keysForNetGrossAmountType = setOf("net", "gross", "vat")

    fun create(request: ExpenseCreateRequest) = validateCreateRequest(request)

    fun update(id: UUID, request: ExpenseCreateRequest) {
        checkIfExists(id)
        validateCreateRequest(request)
    }

    fun delete(id: UUID) = checkIfExists(id)

    private fun validateCreateRequest(request: ExpenseCreateRequest) {
        val errors: MutableList<HttpParameterError> = mutableListOf()
        errors += validateName(request.name)
        errors += validateAmountValue(request.amount)
        errors += validateDateRange(request.date)
        errors += validateMailConfig(request.mailConfig)

        if (errors.isNotEmpty()) {
            throw HttpValidationException(HttpValidationExceptionBody(errors, Date()))
        }
    }

    private fun validateMailConfig(mailConfig: MailConfig?): List<HttpParameterError> {
        val errors: MutableList<HttpParameterError> = mutableListOf()
        if (mailConfig == null) return errors

        val address = mailConfig.address
        val attachmentPattern = mailConfig.attachmentPattern

        if (!address.isEmail()) {
            errors += HttpParameterError("mailConfig.address", "Has to be valid email")
        }
        if (attachmentPattern.isEmpty()) {
            errors += HttpParameterError("mailConfig.attachmentPattern", "Has to be not empty")
        }
        return errors
    }

    private fun validateDateRange(dateRange: DateRange): List<HttpParameterError> {
        val errors: MutableList<HttpParameterError> = mutableListOf()
        val start = dateRange.start
        val end = dateRange.end
        println(dateRange.end?.isAfter(dateRange.start ?: LocalDate.now()))
        if (end != null && start?.isAfter(end) == true) {
            errors += HttpParameterError("date.start", "Has to be after end date")
        }
        return errors
    }

    private fun validateName(name: String): List<HttpParameterError> {
        return if (name.length !in 4..32) {
            mutableListOf(
                HttpParameterError(
                    "name",
                    "Length should be in [6-32] range"
                )
            )
        } else emptyList()
    }

    private fun validateAmountValue(amount: ExpenseAmount): List<HttpParameterError> {
        return when (amount.type) {
            ExpenseAmountType.NUMBER -> validateNumberAmountValue(amount.value)
            ExpenseAmountType.NET_GROSS -> validateNetGrossAmountValue(amount.value)
        }
    }

    private fun validateNetGrossAmountValue(value: Map<String, Any>): List<HttpParameterError> {
        val errors: MutableList<HttpParameterError> = mutableListOf()
        if (!value.deepEqualKeys(keysForNetGrossAmountType)) {
            errors += HttpParameterError("amount.value", "Incorrect body")
        }
        val net = value["net"]
        val gross = value["gross"]
        val vat = value["vat"]

        if (net !is Number) {
            errors += HttpParameterError("amount.net", "Has to be number")
        }
        if (gross !is Number) {
            errors += HttpParameterError("amount.gross", "Has to be number")
        }
        if (vat !is Number) {
            errors += HttpParameterError("amount.vat", "Has to be number")
        }
        if (net is Number && gross is Number && (net.toString().toDouble()) < (gross.toString().toDouble())) {
            errors += HttpParameterError("amount.net", "Has to be less or equals than gross")
        }
        if (net is Number && (net.toString().toDouble()) < 0.0) {
            errors += HttpParameterError("amount.net", "Has to be a positive number")
        }
        if (gross is Number && (gross.toString().toDouble()) < 0.0) {
            errors += HttpParameterError("amount.gross", "Has to be a positive number")
        }
        return errors
    }

    private fun validateNumberAmountValue(value: Map<String, Any>): List<HttpParameterError> {
        val errors: MutableList<HttpParameterError> = mutableListOf()
        if (!value.deepEqualKeys(keysForNumberAmountType)) {
            errors += HttpParameterError("amount.value", "Incorrect body")
        }
        val it = value["it"]
        if (it !is Number) {
            errors += HttpParameterError("amount.value.it", "Has to be number")
        }
        if (it is Number && (it.toString().toDouble()) < 0.0) {
            errors += HttpParameterError("amount.value.it", "Has to be a positive")
        }
        return errors
    }

    private fun checkIfExists(id: UUID) {
        repository.findByIdOrNull(id) ?: throw HttpNotFoundException(
            HttpNotFoundExceptionBody(
                "Expense not found",
                Date()
            )
        )
    }
}