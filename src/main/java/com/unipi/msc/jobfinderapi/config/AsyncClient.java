package com.unipi.msc.jobfinderapi.config;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("singleton")
public class AsyncClient {
    public static void sendEmail(String emailTo, String token){
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        try {
            JSONObject mainObject = new JSONObject();
            JSONArray personalizationsArray = new JSONArray();
//            Receiver
            JSONObject receiverObject = new JSONObject();
            JSONArray mailArray = new JSONArray();
            JSONObject mailObject = new JSONObject();
            mailObject.put("email",emailTo);
            mailArray.add(mailObject);
            receiverObject.put("to", mailArray);
            receiverObject.put("subject","Enable your account");
            personalizationsArray.add(receiverObject);
//             Sender
            JSONObject senderObject = new JSONObject();
            JSONArray bodyLineArray = new JSONArray();
            JSONObject bodyJson = new JSONObject();
            senderObject.put("email","info@jobfinder.com");
            mainObject.put("personalizations", personalizationsArray);
            mainObject.put("from",senderObject);
            bodyJson.put("type","text/html");
            bodyJson.put("value","<a class='btn' href='http://127.0.0.1:8080/auth/enable?token="+token+">Open it</a>");
            bodyLineArray.add(bodyJson);
            mainObject.put("content", bodyLineArray);
            client.prepare("POST", "https://rapidprod-sendgrid-v1.p.rapidapi.com/mail/send")
                    .setHeader("content-type", "application/json")
                    .setHeader("X-RapidAPI-Key", "d8455cfac5mshb2e12524fc60827p13bf2fjsna477236d177e")
                    .setHeader("X-RapidAPI-Host", "rapidprod-sendgrid-v1.p.rapidapi.com")
                    .setBody(mainObject.toJSONString())
                    .execute()
                    .toCompletableFuture()
                    .thenAccept(response -> {
                        if (response.getStatusCode() == 202){

                        }
                    })
                    .join();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
