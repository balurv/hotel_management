package com.example.hotelmanagement.entity;

@SuppressWarnings("ALL")
public enum Gender {
    FEMALE {
        @Override
        public String toString() {
            return null;
        }
    },
    MALE {
        @Override
        public String toString() {
            return "MALE";
        }
    },
    OTHER {
        @Override
        public String toString() {
            return null;
        }
    };

    public abstract String toString();
}
