package com.example.mappingservice.consumer;

import com.example.mappingservice.ApplicationSystem;
import com.example.mappingservice.Mapper;
import com.example.mappingservice.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Component
public class MappingRequestConsumer {

    @Autowired
    RabbitTemplate template;

    @RabbitListener(queues = MessagingConfig.MAPPING_QUEUE)
    public void handleMappingRequest(ApplicationSystem applicationSystem) {
        if (applicationSystem.getServices().size() > 1) {
            System.out.println("Message received!");
            System.out.println("The following system has been sent to me:");
            System.out.println("All Services:");
            applicationSystem.getServices().forEach(service -> System.out.println(service.getIpAdresse()));
            System.out.println("All connections:");
            applicationSystem.getConnections().forEach(connection -> System.out.println(connection.getService1().getIpAdresse() + "," + connection.getService2().getIpAdresse() + "; Weight: " + connection.getBytesExchanged()));
            setAffinities(applicationSystem);

            Mapper mapper = new Mapper(applicationSystem, applicationSystem.getNumberOfNodes());

            System.out.println("Mapper has been successfully created.");

            if (mapper.getMigrationInstruction() != null) {
                System.out.println(mapper.getMigrationInstruction().getGroups());
            }

            template.convertAndSend(MessagingConfig.INTERNAL_EXCHANGE, MessagingConfig.EXECUTE_MIGRATION_ROUTING_KEY, mapper.getMigrationInstruction());
        }
    }

    public void setAffinities(ApplicationSystem appSystem) {
        appSystem.getConnections().forEach(connection -> {
            System.out.println("Computing affinity...");
            BigDecimal affinity = connection.getBytesExchanged().divide(appSystem.getBytesExchangedTotal(), 2, HALF_UP);
            connection.setAffinity(affinity);
        });
    }
}
