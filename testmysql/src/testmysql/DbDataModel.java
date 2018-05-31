package testmysql;

public class DbDataModel extends MyAbstractTreeTableModel {
    // Spalten Name.
    static protected String[] columnNames = { "CODICE", "DESCRIZIONE", "CI" };
 
    // Spalten Typen.
    static protected Class<?>[] columnTypes = { MyTreeTableModel.class, String.class, Integer.class };
 
    public DbDataModel(DbDataNode rootNode) {
        super(rootNode);
        root = rootNode;    }
 
    public Object getChild(Object parent, int index) {
        return ((DbDataNode) parent).getChildren().get(index);
    }
 
 
    public int getChildCount(Object parent) {
        return ((DbDataNode) parent).getChildren().size();
    }
 
 
    public int getColumnCount() {
        return columnNames.length;
    }
 
 
    public String getColumnName(int column) {
        return columnNames[column];
    }
 
 
    public Class<?> getColumnClass(int column) {
        return columnTypes[column];
    }
 
    public Object getValueAt(Object node, int column) {
        switch (column) {
        case 0:
            return ((DbDataNode) node).getCod();
        case 1:
            return ((DbDataNode) node).getDesc();
        case 2:
            return ((DbDataNode) node).getCi();
        default:
            break;
        }
        return null;
    }
 
    public boolean isCellEditable(Object node, int column) {
        return true; // Important to activate TreeExpandListener
    }
 
    public void setValueAt(Object aValue, Object node, int column) {
    }
 
}