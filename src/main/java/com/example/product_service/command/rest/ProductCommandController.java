package com.example.product_service.command.rest;

import com.example.product_service.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    private final CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel model){
        CreateProductCommand command = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(model.getTitle())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .build();
        String result;
        try {
            result = commandGateway.sendAndWait(command);
        } catch (Exception e){
            result = e.getLocalizedMessage();
        }
        return result;
    }
}
