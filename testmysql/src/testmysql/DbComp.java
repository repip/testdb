package testmysql;

public class DbComp {
    String Cod;
    String Desc;
    int Ci;
    DbComp children;
    
	public DbComp(String Cod, String Desc, Integer Ci, DbComp children) {
        this.Cod = Cod;
        this.Desc = Desc;
        this.Ci = Ci;
        this.children = children;
    }

	public DbComp() {
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


	public DbComp getChildren() {
		return children;
	}

	/**
	 * Knotentext vom JTree.
	 */
	public String toString() {
		return Cod;
	}

}
