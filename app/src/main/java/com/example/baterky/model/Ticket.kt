package com.example.baterky.model

data class Ticket(
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val companyId: String,
    val email: String,
    val description: String,
    val serialNumber: String,
    val zipCode: String
)