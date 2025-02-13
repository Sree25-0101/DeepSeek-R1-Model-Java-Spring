package dev.sreenidhi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    private static final String API_KEY =  System.getenv("DEEPSEEK_API_KEY");
    private static final String BASE_URL = "https://api.deepseek.com/v1";

    public static void main(String[] args) throws IOException, InterruptedException {
        var body = """
                {
                    "model":"deepseek-reasoner",
                    "messages": [
                      {
                        "role":"user",
                        "content":"how many r's are in the word Strawberry "
                       }
                    ]
                }
                """;

        //Creating a request to send the above body
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL +"/chat/completions"))
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var client = HttpClient.newHttpClient();
        var response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        var responseBody = response.body();
        System.out.println(responseBody);

        if(!responseBody.isBlank())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            Object json = objectMapper.readValue(responseBody, Object.class);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println(prettyJson);
        }
    }
}