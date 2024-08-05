package com.example.processamento_vendas.entities

import java.math.BigDecimal

data class Sale(
    val operation: Long? = null,
    val client: String? = null,
    val numberOfTickets: Int? = null,
    val totalValue: BigDecimal? = null,
    var status: String? = null
)