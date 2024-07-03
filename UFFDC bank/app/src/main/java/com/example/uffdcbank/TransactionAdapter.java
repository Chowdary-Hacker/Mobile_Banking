package com.example.uffdcbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import java.util.List;


    public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

        private List<Transaction> transactionList;

        public TransactionAdapter(List<Transaction> transactionList) {
            this.transactionList = transactionList;
        }

        @NonNull
        @Override
        public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
            return new TransactionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
            Transaction transaction = transactionList.get(position);
            holder.transactionDescription.setText(transaction.getDescription());
            holder.transactionDate.setText(transaction.getDate());
            holder.transactionAmount.setText("Rs" + transaction.getAmount());
            holder.transactionStatus.setText(transaction.getStatus());
        }

        @Override
        public int getItemCount() {
            return transactionList.size();
        }

        public static class TransactionViewHolder extends RecyclerView.ViewHolder {
            TextView transactionDescription, transactionDate, transactionAmount, transactionStatus;

            public TransactionViewHolder(@NonNull View itemView) {
                super(itemView);
                transactionDescription = itemView.findViewById(R.id.transaction_description);
                transactionDate = itemView.findViewById(R.id.transaction_date);
                transactionAmount = itemView.findViewById(R.id.transaction_amount);
                transactionStatus = itemView.findViewById(R.id.transaction_status);
            }
        }
    }











