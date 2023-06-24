package com.example.watchup;

public class PersonForGraph {
    private String name;
    private int numberOfVisits;

    public PersonForGraph(String name, int numberOfVisits) {
        this.name = name;
        this.numberOfVisits = numberOfVisits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    @Override
    public String toString() {
        return "PersonForGraph{" +
                "name='" + name + '\'' +
                ", numberOfVisits=" + numberOfVisits +
                '}';
    }
}
