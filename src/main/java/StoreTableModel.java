import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<>();

    private List<Product> product;

    public StoreTableModel(List<Product> product) {
        this.product = product;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "id";
            case 1:
                return "product_name";
            case 2:
                return "description";
            case 3:
                return "producer";
            case 4:
                return "quantity";
            case 5:
                return "price";
        }
        return "";
    }

    public int getRowCount() {
        return product.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Product p =  product.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                return p.getProductName();
            case 2:
                return p.getDescription();
            case 3:
                return p.getProducer();
            case 4:
                return p.getQuantity();
            case 5:
                return p.getPrice();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

}