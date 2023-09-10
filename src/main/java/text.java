import java.util.ArrayList;

class text {
    public ArrayList<Integer> getLis() {
        return lis;
    }

    public void setLis(ArrayList<Integer> lis) {
        this.lis = lis;
    }

    private ArrayList<Integer> lis = new ArrayList<>();

    public text() {

    }

    void io() {
        lis.add(1);
    }

    public static void main(String[] args) {
        text t1 = new text();
        text t2 = new text();
        t1.io();
        t1.io();
        System.out.println(t1.getLis());
        t2.io();
        System.out.println(t2.getLis());
    }
}