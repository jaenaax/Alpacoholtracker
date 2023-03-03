package de.hdmstuttgart.myapplication.model;

public class Person {
    private final int weight;
    private final int gender; //male = 2131231247, female = 2131231246 (Ids of the radio buttons)

    public Person(int weight, int gender) {
        this.weight = weight;
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    } //returns the weight of the person

    public int getGender() {
        return gender;
    } //returns the gender of the person
}
