package net.e4net.demo.test;

public class Dollar {
    int amount;

    //생성자
    Dollar(int amount){
        this.amount = amount;
    }
    void times(int multiplier){
       amount = amount * multiplier;
    }
}
