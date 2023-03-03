package de.hdmstuttgart.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "drinkconsumed")
public class DrinkConsumed {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String name;
    private int amount;
    private double volume;
    private long time;

    private String image;

    //getters
    public String getName() {
        return name;
    } //returns the name of the drink

    public int getAmount() {
        return amount;
    } //returns the amount of the drink

    public double getVolume() {
        return volume;
    } //returns the volume of the drink

    public String getImage() {
        return image;
    } //returns the image of the drink

    //setters
    public void setName(String name) {
        this.name = name;
    } //sets the name of the drink

    public void setAmount(int amount) {
        this.amount = amount;
    } //sets the amount of the drink

    public void setVolume(double volume) {
        this.volume = volume;
    } //sets the volume of the drink

    public long getTime() {
        return time;
    } //returns the time of the drink

    public void setTime(long time) {
        this.time = time;
    } //sets the time of the drink

    public void setImage(String image) {
        this.image = image;
    } //sets the image of the drink
}

