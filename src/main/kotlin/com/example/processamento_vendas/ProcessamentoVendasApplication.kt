package com.example.processamento_vendas

import com.example.processamento_vendas.deserializer.SaleDeserializer
import com.example.processamento_vendas.entities.Sale
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.Duration
import java.util.*

@SpringBootApplication
class ProcessamentoVendasApplication

fun main(args: Array<String>) {
    runApplication<ProcessamentoVendasApplication>(*args)

    val properties = Properties()

    properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
    properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = SaleDeserializer::class.qualifiedName
    properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.qualifiedName
    properties[ConsumerConfig.GROUP_ID_CONFIG] = "grupo-processamento-teste"
    properties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"

    val consumer = KafkaConsumer<String, Sale>(properties)

    try {
        consumer.subscribe(listOf("venda-ingressos-teste"))

        while(true) {
            val vendas = consumer.poll(Duration.ofMillis(200))

            for(record in vendas){
                var venda = record.value()

                if(Random().nextBoolean()){
                    venda.status = "APROVADA"
                } else {
                    venda.status = "REPROVADA"
                }

                Thread.sleep(500)
                println("Vendas: $venda")
            }
        }

    } catch(e: Exception){
        println("Erro main: $e")
    }

}
