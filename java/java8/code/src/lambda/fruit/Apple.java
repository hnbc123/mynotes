package lambda.fruit;

public class Apple extends Fruit{
    private String color;
    private int weight;

    public Apple(){}

    public Apple (int weight) {
        this.weight = weight;
    }
    
    public Apple(String color, int weight) {
    	this.color = color;
    	this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    

    @Override
    public String toString() {
        return "<apple color: " + color + "; weight: " + weight + "g>\n";
    }
}