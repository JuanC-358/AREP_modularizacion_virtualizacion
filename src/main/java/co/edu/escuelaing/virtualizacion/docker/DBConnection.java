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

    public ArrayList<String[]> get10(){
        MongoDatabase database = mongoClient.getDatabase("Lab");
        MongoCollection<Document> collection =database.getCollection("logs");
        FindIterable fit = collection.find();
        ArrayList<Document> docs = new ArrayList<Document>();
        ArrayList<String[]> results = new ArrayList<>();
        fit.into(docs);
        int count = docs.size();
        for (int i =0;i<10;i++) {
            if (docs.get(count).get("mensaje")!= null && docs.get(count).get("fecha")!=null){
                results.add(new String[]{docs.get(count).get("mensaje").toString(), docs.get(count).get("fecha").toString()});
            }
            count-=1;
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
