package com.example.uffdcbank;

public class Transaction {
        private String description;
        private String date;
        private String amount;
        private String status;

        public Transaction(String description, String date, String amount, String status) {
            this.description = description;
            this.date = date;
            this.amount = amount;
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public String getAmount() {
            return amount;
        }

        public String getStatus() {
            return status;
        }
    }
