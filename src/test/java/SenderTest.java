import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SenderTest {
    @Test
    public void serverTest() throws Exception {
        String token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("group", "foo");
        jsonObject.put("naming", "1234");
        jsonObject.put("numberOfProduct", 1234);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("group", "foo");
        jsonObject2.put("id", 0);
        jsonObject2.put("numberOfProduct", 1234);
        System.out.println(jsonObject.toString());
        token = Sender.aut("http://localhost:8891/login", "admin", "1234");
        Sender.doPut("http://localhost:8891/api/good", jsonObject, token);
        Sender.doPost("http://localhost:8891/api/good", jsonObject2, token);
        Sender.doGet("http://localhost:8891/api/good/id0", token);
        Sender.doDelete("http://localhost:8891/api/good/id0", token);
        Sender.doGet("http://localhost:8891/api/good/id0", token);
        Sender.doPost("http://localhost:8891/api/good", jsonObject2, token);
    }

}