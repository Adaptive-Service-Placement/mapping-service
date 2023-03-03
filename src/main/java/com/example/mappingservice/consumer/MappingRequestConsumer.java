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
        setAffinities(applicationSystem);

        // k = number of VM Instances or physical Servers available, big TODO
        Mapper mapper = new Mapper(applicationSystem, 3);

        template.convertAndSend(MessagingConfig.INTERNAL_EXCHANGE, MessagingConfig.EXECUTE_MIGRATION_ROUTING_KEY, mapper.getMigrationInstruction());
    }

    private void setAffinities(ApplicationSystem appSystem) {
        appSystem.getConnections().forEach(connection -> {
            BigDecimal affinity = connection.getBytesExchanged().divide(appSystem.getBytesExchangedTotal(), 2, HALF_UP);
            connection.setAffinity(affinity);
        });
    }
}
