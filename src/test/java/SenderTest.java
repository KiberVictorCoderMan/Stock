import org.json.simple.JSONObject;
import org.junit.Test;

public class SenderTest {
    @Test
    public void serverTest() throws Exception {
        String token;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("group", "fruits");
        jsonObject.put("description", "6");
        jsonObject.put("manufacturer", "4");
        jsonObject.put("naming", "9999");
        jsonObject.put("price", 5);
        jsonObject.put("quantity", 1234);

        JSONObject jsonObject0 = new JSONObject();
        jsonObject0.put("group", "fruits");
        jsonObject0.put("description", "6");
        jsonObject0.put("manufacturer", "4");
        jsonObject0.put("naming", "rrrr");
        jsonObject0.put("price", 5);
        jsonObject0.put("quantity", 1234);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("group", "vegetables");
        jsonObject1.put("description", "6");
        jsonObject1.put("manufacturer", "4");
        jsonObject1.put("naming", "777777");
        jsonObject1.put("price", 5);
        jsonObject1.put("quantity", 1234);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("group", "fruits");
        jsonObject2.put("id", 1);
        jsonObject2.put("field", "naming");
        jsonObject2.put("naming", "yyyy");

        JSONObject jsonObject7 = new JSONObject();
        jsonObject2.put("group", "fruits");
        jsonObject2.put("naming", "9999");
        jsonObject2.put("field", "naming");
        jsonObject2.put("naming", "yyyy");

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("_naming", "9999");
        jsonObject5.put("field", "naming");
        jsonObject5.put("naming", "mikhail loh");


        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("naming", "trucks");

        Sender s = new Sender();

        token = s.aut("http://localhost:8891/login", "admin", "1234");

        s.doPut("http://localhost:8891/api/good", jsonObject, token);
        s.doPut("http://localhost:8891/api/good", jsonObject0, token);
        s.doPut("http://localhost:8891/api/good", jsonObject1, token);
        //s.doPut("http://localhost:8891/api/table", jsonObject3, token);
        //s.doPut("http://localhost:8891/api/table", jsonObject3, token);
        s.doGet("http://localhost:8891/api/fruits/1", token);
        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("namingOld", "vegetables");
        jsonObject4.put("namingNew", "dish");
        //s.doPost("http://localhost:8891/api/good", jsonObject5, token);
        s.doGet("http://localhost:8891/api/all", token);
        s.doGet("http://localhost:8891/api/tables", token);
        s.doPost("http://localhost:8891/api/table", jsonObject4, token);
        s.doPost("http://localhost:8891/api/good", jsonObject2, token);
        s.doPost("http://localhost:8891/api/good", jsonObject5, token);
       // s.doDelete("http://localhost:8891/api/good/naming:rrrr", token);
       // s.doDelete("http://localhost:8891/api/good/fruits/1", token);
      //  s.doDelete("http://localhost:8891/api/table/fruits", token);
    }

}