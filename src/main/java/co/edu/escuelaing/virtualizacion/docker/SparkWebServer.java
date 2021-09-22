/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.virtualizacion.docker;

import static spark.Spark.get;
import static spark.Spark.port;
/**
 *
 * @author ADMIN
 */
public class SparkWebServer {
    
    public static void main(String... args){
          port(getPort());
          get("hello", (req,res) -> "Hello Docker!");
          DBConnection mongo = new DBConnection();
          get("database" ,(req,res) -> {
            mongo.post("test"); 
            return mongo.get10(); 
          }); 
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
    
}