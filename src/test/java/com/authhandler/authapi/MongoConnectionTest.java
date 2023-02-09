package com.authhandler.authapi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isNotNull;

import com.mongodb.client.MongoClient;
import com.mongodb.client.ListDatabasesIterable;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoConnectionTest {
    @Autowired
    private MongoClient mongoClient;
 
    @Test
    public void mongoDatabaseShouldConnect() {
        ListDatabasesIterable<Document> databases = mongoClient.listDatabases();
        assertEquals(databases, isNotNull());
    }
}