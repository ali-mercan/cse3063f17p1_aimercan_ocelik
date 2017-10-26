public class City extends Square{
    private String name;
    private Square estate;

    @Override
    public String getName() {
        return name;
    }

    public Square getEstate() {
        return estate;
    }

}
