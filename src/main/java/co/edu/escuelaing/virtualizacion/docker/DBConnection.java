/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.virtualizacion.docker;

/**
 *
 * @author ADMIN
 */
import com.mongodb.MongoClient;

import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import org.bson.Document;

import java.util.ArrayList;

public class DBConnection {
    MongoClientURI uri;
    MongoClient mongoClient;

    public DBConnection() {
        uri = new MongoClientURI("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
        mongoClient = new MongoClient(uri);
    }

    public ArrayList<String> get10(){
        MongoDatabase database = mongoClient.getDatabase("Lab");
        MongoCollection<Document> collection =database.getCollection("logs");
        FindIterable fit = collection.find();
        ArrayList<Document> docs = new ArrayList<Document>();
        ArrayList<String> results = new ArrayList<>();
        fit.into(docs);
        int count = docs.size()-10;
        if(docs.size() >= 10){ 
            for (int i = 0; i < 10; i++) {
                System.out.println("2");
                results.add(docs.get(count).toJson());
                System.out.println(docs.get(count).toJson());
                count += 1;
            }
        }else{
            for (int i = 0 ; i < docs.size() ; i++ ) {
                results.add(docs.get(i).toJson());
            }
        }
        
        return results;
    }

    public void post(String message){
        MongoDatabase database = mongoClient.getDatabase("Lab");
        MongoCollection<Document> collection =database.getCollection("logs");
        Document document=new Document();
        document.put("mensaje",message);
        document.put("fecha",LocalDateTime.now().toString());
        collection.insertOne(document);
    }

}
