package com.jakubspiewak.ash.expense.mongo

class DatabaseContextHolder {
    companion object {
        private val contextHolder: ThreadLocal<String?> = ThreadLocal()
        fun getCurrentUserId(): String? = contextHolder.get()
        fun setCurrentUserId(schema: String) = contextHolder.set(schema)
        fun clear(): Unit = contextHolder.remove()
    }
}