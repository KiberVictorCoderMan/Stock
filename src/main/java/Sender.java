import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CacheRequest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CacheRequest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Sender {

    static Crypto cr = new Crypto();
    //    public Product(String group, String productName, String description, String producer, int quantity, int price ){
    public static void main(String[] args) throws Exception {
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
        jsonObject2.put("id", 4);
        jsonObject2.put("field", "naming");
        jsonObject2.put("naming", "yyyy");

        /*
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("group", "fruits");
        jsonObject3.put("id", 1);
        jsonObject3.put("field", "price");
        jsonObject3.put("price", 350);
*/

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("naming", "trucks");
        token = aut("http://localhost:8891/login", "admin", "1234");
        doPut("http://localhost:8891/api/good", jsonObject, token);
        doPut("http://localhost:8891/api/good", jsonObject0, token);
        doPut("http://localhost:8891/api/good", jsonObject1, token);
       // doPut("http://localhost:8891/api/table", jsonObject3, token);

        doGet("http://localhost:8891/api/good/naming:777777", token);

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("namingOld", "trucks");
        jsonObject4.put("namingNew", "buses");
        doPost("http://localhost:8891/api/good", jsonObject2, token);
//        doGet("http://localhost:8891/api/all", token);
//        doGet("http://localhost:8891/api/tables", token);
       // doPost("http://localhost:8891/api/table", jsonObject4, token);
        doDelete("http://localhost:8891/api/good/fruits/naming:777777", token);
    }

    public static String doPost(String urlpath, JSONObject json, String token) {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Token", token);
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(cr.encrypt("345354345", json.toString()));
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                System.out.println(stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                System.out.println(connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            System.out.println(exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public static String doPut(String urlpath, JSONObject json, String token) {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Token", token);
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(cr.encrypt("345354345", json.toString()));
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                System.out.println(stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                System.out.println(connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            System.out.println(exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }


    public static String doGet(String urlpath, String token) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Token", token);
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                System.out.println(stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                System.out.println(connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            System.out.println(exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public static String doDelete(String urlpath, String token) {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Token", token);
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                System.out.println(stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                System.out.println(connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            exception.printStackTrace();
            System.out.println(exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public static String aut(String urlpath, String login, String password) {
        HttpURLConnection connection = null;
        try {
            URL url=new URL(urlpath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Login", login);
            connection.addRequestProperty("Password", Hash.md5(password));
            connection.setRequestProperty("Content-Type", "application/json" + "*1");
            connection.setRequestProperty("Accept", "application/json" + "*1");
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response);
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } else {
                return null;
            }
        } catch (Exception exception){
            System.out.println(exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

}