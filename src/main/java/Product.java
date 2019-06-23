import org.json.simple.JSONObject;

public class Product {
    private String productName;
    private String description;
    private String producer;
    private int quantity;
    private int price;
    private int id;



    public Product(String productName, String description, String producer, int quantity, int price ){
        this.productName = productName;
        this.description = description;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
        this.id = 0;
    }

    public Product(int id, String productName, String description, String producer, int quantity, int price ){
        this.id =id;
        this.productName = productName;
        this.description = description;
        this.producer = producer;
        this.quantity = quantity;
        this.price = price;
    }


    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public String getProducer() {
        return producer;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getJSON(){
        JSONObject obj = new JSONObject();
        obj.put("id", getId());
        obj.put("product_name",getProductName());
        obj.put("description",getDescription());
        obj.put("producer",getProducer());
        obj.put("quantity", getQuantity());
        obj.put("price", getPrice());

        return  obj;
    }

}
