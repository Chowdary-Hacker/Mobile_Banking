package com.example.uffdcbank;
import com.google.gson.Gson;
import java.util.ArrayList;
public class Blockchain {
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static void main(String from) {
        blockchain.clear();
        // Add the genesis block.
        blockchain.add(new Block("Genesis Block", from, "To", "100000", "Description", "0"));
        System.out.println("Trying to Mine Genesis Block... ");

     /*   // Add subsequent blocks.
        blockchain.add(new Block("Second Block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine Second Block... ");

        blockchain.add(new Block("Third Block", blockchain.get(blockchain.size() - 1).hash));
        System.out.println("Trying to Mine Third Block... ");  */
    }
    public static String getGenesis(){
        Gson gson = new Gson();
        String blockchainJson = gson.toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
        return blockchainJson;
    }
    public static String addBlock(ArrayList<Block> blockchain, String data, String from, String to, String balance, String Description){
        String previousHash = blockchain.get(blockchain.size() - 1).getHash();
        blockchain.add(new Block(data, from, to, balance, Description, previousHash));
        Gson gson = new Gson();
        String blockchainJson = gson.toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
        return blockchainJson;
    }

    // Check if the blockchain is valid.
    public static String isChainValid(ArrayList<Block> blockchain, String balance) {
        Block currentBlock;
        Block previousBlock;
String balancer = balance+".0";
        // Loop through blockchain to check hashes.
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            // Compare registered hash and calculated hash.
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return "Current Hashes not equal";
            }
            // Compare previous hash and registered previous hash.
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("Previous Hashes not equal");
                return "Previous Hashes not equal";
            }
            // whether balance is modified or not
            if(!blockchain.get(blockchain.size()-1).getBalance().equals(balancer)){
                System.out.println("balance has been modified");
                return "balance has been modified"+blockchain.get(blockchain.size()-1).getBalance()+" param - "+balancer;
            }
        }
        return "OK";
    }

}

