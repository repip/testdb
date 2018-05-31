package testmysql;

import java.util.Collections;
import java.util.List;

public class DbDataNode {
    private String Cod;
    private String Desc;
    private Integer Ci;
 
    private List<DbDataNode> children;
 
    public DbDataNode(String Cod, String Desc, Integer Ci, List<DbDataNode> children) {
        this.Cod = Cod;
        this.Desc = Desc;
        this.Ci = Ci;
        this.children = children;
 
        if (this.children == null) {
            this.children = Collections.emptyList();
        }
    }
 
    public String getCod() {
        return Cod;
    }
 
    public String getDesc() {
        return Desc;
    }

   
    public Integer getCi() {
        return Ci;
    }
 
    public List<DbDataNode> getChildren() {
        return children;
    }
 
    public void setChildren(List<DbDataNode> children) {
    	this.children = children;
}
    /**
     * Knotentext vom JTree.
     */
    public String toString() {
        return Cod;
    }
}