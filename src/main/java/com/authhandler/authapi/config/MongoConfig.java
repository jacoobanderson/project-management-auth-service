package com.authhandler.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() {
        // ConnectionString connectionString = new ConnectionString(mongoUri);
        // MongoClientSettings settings = MongoClientSettings.builder()
        //     .applyConnectionString(connectionString)
        //     .build();
        // MongoClient mongoClient = MongoClients.create(settings);
        // return mongoClient;
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build())
            .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        // ConnectionString connectionString = new ConnectionString(mongoUri);
        // return connectionString.getDatabase();
        // MongoDatabase database = mongoClient.getDatabase("project-management-auth");
        return "project-management-auth";
    }
}
