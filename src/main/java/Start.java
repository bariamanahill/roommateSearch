import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;

public class Start {
    public static void main(String[] args) {

        MongoClientURI uri = new MongoClientURI("mongodb://admin:pass@ds041566.mlab.com:41566/?authSource=roombae");

        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("roombae");
        MongoCollection<Document> collection = database.getCollection("names");

        Spark.get("/hello", (req, res) -> "Hello World");

        Spark.get("/hello2", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "Hello 2, World";
            }
        });

        Spark.get("/lname/:lname", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String lname = new String(request.params(":lname"));
                ArrayList<String> results = new ArrayList<String>();
                for (Document bd : collection.find(Filters.eq("lname", lname))) {
                    if (bd.containsKey("fname")) {
                        Student student = new Student(bd.get("fname").toString(), lname, 21);
                        return student;
//                        results.add(bd.get("fname").toString());
                    }
                    System.out.println(bd);
                }
                return "NO";
            }
        });

    }
}