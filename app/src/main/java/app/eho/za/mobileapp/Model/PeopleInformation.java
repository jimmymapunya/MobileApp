package app.eho.za.mobileapp.Model;

/**
 * Created by JimmyM on 2017/05/16.
 */

public class PeopleInformation {

    private String name;
    private String gender;
    private String species;
    private String height;
    private String mass;
    private String hairColour;
    private String skinColor;
    private String yearOfBirth;

    public PeopleInformation(String name, String gender, String species, String height,String mass, String hairColour, String skinColor, String yearOfBirth) {
        this.name = name;
        this.gender = gender;
        this.species = species;
        this.height = height;
        this.mass = mass;
        this.hairColour = hairColour;
        this.skinColor = skinColor;
        this.yearOfBirth = yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColour() {
        return hairColour;
    }

    public void setHairColour(String hairColour) {
        this.hairColour = hairColour;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
