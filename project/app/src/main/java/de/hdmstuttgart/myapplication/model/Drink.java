package de.hdmstuttgart.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drink")
public class Drink {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private final String name;
    private double volume;
    private int amount;
    private String image;

    public Drink(String name, double volume, int amount, String image) {
        this.name = name;
        this.volume = volume;
        this.amount = amount;
        this.image = image;
    }

    public DrinkConsumed convertToDrinkConsumed() { //converts a drink to a drinkDrunk
        DrinkConsumed drinkConsumed = new DrinkConsumed();
        drinkConsumed.setName(getName());
        drinkConsumed.setAmount(getAmount());
        drinkConsumed.setVolume(getVolume());
        drinkConsumed.setTime(System.currentTimeMillis());
        drinkConsumed.setImage(getImage());
        return drinkConsumed;
    }

    //getters
    public String getName() {
        return name;
    }  //returns the name of the drink

    public double getVolume() {
        return volume;
    } //returns the volume of the drink

    public int getAmount() {
        return amount;
    } //returns the amount of the drink

    public String getImage() {
        return image;
    } //returns the image of the drink

    //setters

    public void setVolume(double volume) {
        this.volume = volume;
    } //sets the volume of the drink

    public void setAmount(int amount) {
        this.amount = amount;
    } //sets the amount of the drink
}
