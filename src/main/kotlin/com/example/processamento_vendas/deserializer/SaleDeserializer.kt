package com.example.processamento_vendas.deserializer

import com.example.processamento_vendas.entities.Sale
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer

internal class SaleDeserializer : Deserializer<Sale> {

    override fun deserialize(topic: String?, sale: ByteArray?): Sale? {
        try {
            return ObjectMapper().readValue(sale, Sale::class.java)
        } catch (e: Exception) {
            println("Error: $e")
        }

        return null
    }

}