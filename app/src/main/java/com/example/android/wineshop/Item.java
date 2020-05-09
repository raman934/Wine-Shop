package com.example.android.wineshop;

public class Item {
        private String Amount;
        private String userId;
        private String id;

        public Item(String Amount, String userId, String id) {
            this.Amount = Amount;
            this.userId = userId;
            this.id = id;
        }

        public Item() {
            this.Amount = "";
            this.userId = "";
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String food) {
            this.Amount = Amount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return Amount + " " + userId;
        }
}
