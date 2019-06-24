import java.sql.*;
import java.util.ArrayList;

public class StockServiceJDBC {
    //static final String DATABASE_URL = "jdbc:mysql://localhost:3306/stock?autoReconnect=true&useSSL=false";\\
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/stock?autoReconnect=true&useSSL=false";
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    static final String USER = "root";
    //root
    static final String PASSWORD = "Mike2000";

    ArrayList<String> tablesList = new ArrayList<>();

    Connection connection;
    Statement statement;

    StockServiceJDBC() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        statement = connection.createStatement();
        DatabaseMetaData md = connection.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            tablesList.add(rs.getString(3));
        }
    }
/*
    public void createProductTable(String groupName) throws SQLException {
        String SQL = "CREATE TABLE " + groupName + " " + " (naming VARCHAR(50), " + " description VARCHAR(50), " + " manufacturer VARCHAR(50), " + " quantity INTEGER not NULL, " + " price INTEGER not NULL, " + " id INTEGER not NULL)";
        statement.executeUpdate(SQL);
        tablesList.add(groupName);
    }
*/

    public void createTable(String nameTable) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " +
                nameTable + " (" +
                " id MEDIUMINT NOT NULL AUTO_INCREMENT,"+
                " naming VARCHAR(26) NOT NULL," +
                " description VARCHAR(200) NOT NULL," +
                " manufacturer VARCHAR(26) NOT NULL," +
                " quantity INT NOT NULL," +
                " price INT NOT NULL," +
                " PRIMARY KEY (id)," +
                " UNIQUE KEY (naming))");
    }

    public ResultSet readGroup(String groupName) throws SQLException {
        String SQL = "SELECT * from " + groupName;
        return statement.executeQuery(SQL);
    }

    public ResultSet readTable(String table) throws SQLException {
        /*
        String tables = "";
        String selectQ = "";
        String whereQ = "";
        for(int i = 0; i < tablesList.size(); i++) {
            tables += tablesList.get(i);
            selectQ += tablesList.get(i) + ".naming, " + tablesList.get(i) + ".description, " + tablesList.get(i) + ".manufacturer, " + tablesList.get(i) + ".id, " + tablesList.get(i) + ".quantity";
            if(i != tablesList.size() - 1) {
                tables += ", ";
                selectQ += " AND ";
            }
        }
        String SQL = "SELECT " + selectQ + " FROM " + tables;
        System.out.println("************** " + SQL);
        return statement.executeQuery(SQL);*/
        String selectQ = "";
        String SQL = "SELECT * " + " FROM " + table;
        System.out.println("************** " + SQL);
        return statement.executeQuery(SQL);
    }

    public void updateProductNumber(String groupName, String product, long newNumber, String field) throws SQLException {
        String SQL = "UPDATE " + groupName + " SET "+ field +" = " + newNumber + " WHERE naming = '" + product + "'";
        statement.executeUpdate(SQL);
    }

    public void updateProductNumberId(String groupName, long product, long newNumber) throws SQLException {
        String SQL = "UPDATE " + groupName + " SET quantity = " + newNumber + " WHERE id = '" + product + "'";
        statement.executeUpdate(SQL);
    }

    public void updateItem(String tableName, long index, String column, String value){
        try{
            statement.executeUpdate("UPDATE "+tableName+" SET "+column+" = "+
                    "'"+value+"'"+" WHERE id = "+index);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    public void insertProduct(String groupName, String product, String description, String manufacturer, long priceProduct,  long newNumberOfProduct) throws SQLException {
        String SQL = "INSERT INTO " + groupName + " VALUES ('" + product + "', '" + description + "', '" + manufacturer + "', " + priceProduct + ", " + newNumberOfProduct + ", " + idGen +  ")";
        statement.executeUpdate(SQL);
        idGen++;
    }
*/

    public void insertProduct(String groupName, String naming, String description, String manufacturer, long price,  long quantity) throws SQLException {
        statement.executeUpdate("INSERT INTO " + groupName + " (naming, description, manufacturer, price, quantity) " + "VALUES " +
                "(" + "'" + naming + "'," + "'" + description + "'," +
                "'" + manufacturer + "'," + price + "," + quantity + ")");
    }

    public void deleteProduct(String groupName, String product) throws SQLException {
        String SQL = "DELETE FROM " + groupName + " WHERE naming = '" + product + "'";
        statement.executeUpdate(SQL);
    }

    public void dropTable(String groupName) throws SQLException {
        String SQL = "DROP TABLE IF EXISTS "+groupName;
        statement.executeUpdate(SQL);
        tablesList.remove(groupName);
    }

    public ResultSet listByСriteria(String tableName, String column, String criterion){
        String[] criterions;
        criterions = criterion.split(" ");
        StringBuilder select = new StringBuilder(" SELECT * FROM "+tableName+" WHERE "+column+" in (");
        for(int i = 0; i< criterions.length;i++){
            select.append("'"+criterions[i]+"', ");
        }
        String sel = select.substring(0,select.length()-2);
        sel+=")";
        ResultSet result = null;
        try{
            result = statement.executeQuery(sel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getProductId(long idPr) throws SQLException {
        String tables = "";
        String selectQ = "";
        String whereQ = "";
        for(int i = 0; i < tablesList.size(); i++) {
            tables += tablesList.get(i);
            selectQ += tablesList.get(i) + ".naming, " + tablesList.get(i) + ".description, " + tablesList.get(i) + ".manufacturer, " + tablesList.get(i) + ".id, " + tablesList.get(i) + ".price, " + tablesList.get(i) + ".quantity";
            if(i != tablesList.size() - 1) {
                tables += ", ";
                selectQ += " AND ";
            }
            if(i == 0) whereQ += tablesList.get(i) + "." + "id = " + idPr;
            else whereQ += " AND " + tablesList.get(i) + "." + "id = " + idPr;

        }
        String SQL = "SELECT " + selectQ + " FROM " + tables + " WHERE " + whereQ;
        System.out.println("************** " + SQL);
        return statement.executeQuery(SQL);
    }

    public void delete(long idPr) throws SQLException {
        String tables = "";
        String whereQ = "";
        for(int i = 0; i < tablesList.size(); i++) {
            tables += tablesList.get(i);
            if(i != tablesList.size() - 1) {
                tables += ", ";
            }
            if(i == 0) whereQ += tablesList.get(i) + "." + "id = " + idPr;
            else whereQ += " AND " + tablesList.get(i) + "." + "id = " + idPr;

        }
        String SQL = "DELETE FROM " + tables + " WHERE " + whereQ;
        statement.executeUpdate(SQL);
    }

    public long allPrice() throws SQLException {
        String tables = "";
        String selectQ = "";
        String whereQ = "";
        for(int i = 0; i < tablesList.size(); i++) {
            tables += tablesList.get(i);
            /*
            selectQ +=tablesList.get(i) + ".price";
            if(i != tablesList.size() - 1) {
                tables += ", ";
                selectQ += " AND ";
            }*/
            selectQ = "price";
        }
        System.out.println("************** " + tables + " *********************** " + selectQ + " ************************ " + whereQ);
        //String SQL = "SELECT " + "SUM(" + selectQ + ")" + " FROM " + tables;
        long sum = 0;
        ResultSet res = statement.executeQuery(statement.executeQuery("SELECT " + "SUM(" + selectQ + ")" + " FROM " + tables).getString(selectQ));
        while (res.next()) {
            int c = res.getInt(1);
            sum = sum + c;
        }
        return sum;
    }

    public void closeConnector() throws SQLException {
        statement.close();
        connection.close();
    }



}