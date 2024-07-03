package com.example.uffdcbank;
import com.google.gson.Gson;
import java.util.Date;
import java.security.MessageDigest;
import java.util.Random;
public class Block {
    public String hash;
    public String previousHash;
    private String data;
    private String description;
    private String from;
    private String to;
    private String balance;
    private String timeStamp; // As number of milliseconds since 1/1/1970.
    private int nonce;

    // Block Constructor.
    public Block(String data, String from, String to, String balance, String description, String previousHash) {
        this.data = data;
        this.from = from;
        this.to = to;
        this.balance = balance;
        this.description = description;
        this.previousHash = previousHash;
        this.timeStamp = new Date().toString();
        Random random = new Random();
        this.nonce = random.nextInt(999);
        this.hash = calculateHash(); // Making sure we do this after we set the other values.
    }

    public String getData(){
        return data;
    }
    public String getDescription(){
        return description;
    }
    public String getBalance(){
        System.out.println("block "+balance); return balance;
    }
    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public String getHash(){
        return hash;
    }
    public String getPreviousHash(){
        return previousHash;
    }
    public String getTimeStamp(){return timeStamp;}
    // Calculate new hash based on block's contents.
    public String calculateHash() {
        String input = previousHash + timeStamp + Integer.toString(nonce) + data + description + balance + from + to;
        return applySha256(input);
    }


    // Applies Sha256 to a string and returns the result.
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Applies sha256 to our input.
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as a hexadecimal.
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
