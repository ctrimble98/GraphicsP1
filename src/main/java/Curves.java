import processing.core.PApplet;

public class Curves extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Curves");
    }

    @Override
    public void settings() {
        size(640, 480);
    }

    @Override
    public void setup() {
        fill(120,50,240);
    }

    @Override
    public void draw(){
        ellipse(width/2,height/2,second(),second());
    }
}