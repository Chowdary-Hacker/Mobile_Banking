package com.example.uffdcbank;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Object {
    private String b; private String bt;
    private String balance; Gson gson = new Gson();

        public ArrayList<Block> getBlockchain () {
            System.out.println("b - "+b);
            JsonArray jsonArray = JsonParser.parseString(b).getAsJsonArray();

            // Convert the JsonArray to an ArrayList
            Type type = new TypeToken<ArrayList<Block>>() {}.getType();
             ArrayList<Block> blockchain = gson.fromJson(jsonArray, type);
            System.out.println("object - "+gson.toJson(blockchain)); return blockchain;
        }
        public ArrayList<Block> getBlockchainto () {
            JsonArray jsonArrayt = JsonParser.parseString(bt).getAsJsonArray();

            // Convert the JsonArray to an ArrayList
            Type type = new TypeToken<ArrayList<Block>>() {}.getType();
            ArrayList<Block> blockchainto = gson.fromJson(jsonArrayt, type);
            System.out.println("objectto - "+gson.toJson(blockchainto)); return blockchainto;
        }

        public String getBalance () {
            System.out.println(balance); return balance;
        }

        public void setBalance (String balance){
            this.balance = balance;
        }

    public void setB (String b){this.b = b;}
    public void setBt (String bt){this.bt = bt;}

}
