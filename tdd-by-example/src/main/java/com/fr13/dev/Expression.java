package com.fr13.dev;

public interface Expression {
    Money reduce(Bank bank, String to);
}
