import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StaticResourceProcessor implements Processor {

  private StockServiceJDBC stockServiceJDBC = new StockServiceJDBC();
  private static HashMap<String, String> tokensUsers = new HashMap();
  private static HashMap<String, String> loginsAndPasswords = new HashMap<>();

  public StaticResourceProcessor() throws SQLException, ClassNotFoundException {
    loginsAndPasswords.put("admin", Hash.md5("1234"));
  }

  private String tokenGen() {
    SecureRandom random = new SecureRandom();
    byte bytes[] = new byte[20];
    random.nextBytes(bytes);
    String token = bytes.toString().substring(0, 10);
    return token;
  }

  @Override
  public void process(Request request, Response response) throws IOException {
    try {

      if(request.getType().equals("GET") && request.getURI().equals("/login")) {
        response.sendText(login(request.getParameter("Login").substring(1, 6), request.getParameter("Password").substring(1, 33)));
        return;
      }
      if(!tokensUsers.containsValue(request.getParameter("Token").substring(1, 11))) {
        response.sendText("403");
      } else {
//        System.out.println(request.getBody().substring(0, request.getBody().indexOf("}")));
        if (request.getType().equals("POST") && request.getURI().equals("/api/good")) {
          response.sendText(post(request.getBody()));
        } else if (request.getType().equals("GET") && request.getURI().substring(0, request.getURI().lastIndexOf("/")).equals("/api/good")) {
          response.sendText(get(request.getURI().substring(request.getURI().lastIndexOf("/") + 1)));
        } else if (request.getType().equals("DELETE") && request.getURI().substring(0, request.getURI().lastIndexOf("/")).equals("/api/good")) {
          response.sendText(delete(request.getURI().substring(request.getURI().lastIndexOf("/") + 1)));
        } else if (request.getType().equals("PUT") && request.getURI().equals("/api/good")) {
          response.sendText(put(request.getBody()));
        }
      }

    } catch (ParseException | SQLException e) {
      e.printStackTrace();
    }
  }

  public String post(String jsonStr) throws ParseException, SQLException {
    JSONParser parser = new JSONParser();
    System.out.println(jsonStr);
    JSONObject jsonObject = null;
    try {
      jsonObject = (JSONObject) parser.parse(jsonStr.toString());
    } catch (Exception e) {
      jsonObject = (JSONObject) parser.parse(jsonStr.toString());
    }
    if(get(String.valueOf(jsonObject.get("id"))).equals("404 Not Found")) return "404 Not Found";
    try {
        //    public void updateItem(String tableName, int index, String column, String value){
      String coll = (String)jsonObject.get("field");
      stockServiceJDBC.updateItem((String) jsonObject.get("group"), (long) jsonObject.get("id"), coll, (String) jsonObject.get(coll));
    } catch (Exception e) {
        e.printStackTrace();
      return "409 Conflict";
    }
    return "204 No Content";
  }

  public String put(String jsonStr) throws ParseException {
    JSONParser parser = new JSONParser();
    System.out.println(jsonStr);
    JSONObject jsonObject = null;
    jsonObject = (JSONObject) parser.parse(jsonStr.toString());
    if((long)jsonObject.get("numberOfProduct") < 0) return "409 Conflict";
    try {
    stockServiceJDBC.insertProduct((String)jsonObject.get("group"), (String) jsonObject.get("naming"), (String) jsonObject.get("description"), (String) jsonObject.get("manufacturer"), (long) jsonObject.get("price"), (long) jsonObject.get("numberOfProduct"));
    }catch (Exception e) {
      try {
         stockServiceJDBC.createTable((String) jsonObject.get("group"));
         stockServiceJDBC.insertProduct((String)jsonObject.get("group"), (String) jsonObject.get("naming"), (String) jsonObject.get("description"), (String) jsonObject.get("manufacturer"), (long) jsonObject.get("price"), (long)jsonObject.get("numberOfProduct"));
      } catch (Exception e2) {
          return "409 Conflict";
      }
    }
    return "201 Created";
  }

  public String get(String id) throws SQLException {
    JSONObject jsonObject = new JSONObject();
    try{
      ResultSet resultSet = stockServiceJDBC.readAllDB();//(Integer.parseInt(id));
      resultSet.first();
      resultSet.next();
      jsonObject.put("naming", resultSet.getString("naming"));
      jsonObject.put("numberOfProduct",resultSet.getString("numberOfProduct"));
      jsonObject.put("description", resultSet.getString("description"));
      jsonObject.put("manufacturer",resultSet.getString("manufacturer"));
      jsonObject.put("id",resultSet.getString("id"));
    } catch (SQLException e) {
        e.printStackTrace();
      return "404 Not Found";
    }
     // return "200 Ok " + jsonObject.toString();
      return "200 Ok ";
  }

  public String login(String login, String password) {
    System.out.println(login);
    System.out.println(loginsAndPasswords.size() + " PASSWORD1: " + "admin" +
            " PASSWORD2: " + login + "***: " + loginsAndPasswords.containsKey(login));
    System.out.println(login.equalsIgnoreCase("admin"));
    if(loginsAndPasswords.containsKey(login) && loginsAndPasswords.get(login).equals(password)) {
      String token = tokenGen();
      tokensUsers.put(login, token);
      return token;
    }
    return "401 Unauthorized";
  }

  public String delete(String id) throws SQLException {
    try{
    stockServiceJDBC.delete(Integer.parseInt(id));
    } catch (NullPointerException e) {
    return "404 Not Found";
    }
    return "204 No Content";
  }
}
