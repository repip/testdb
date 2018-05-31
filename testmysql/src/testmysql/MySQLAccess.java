package testmysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class MySQLAccess {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Object[][] databaseResults;

	public DefaultTableModel TableModel;
	public Object[] columns;
	final private String host = "192.168.1.8";
	final private String user = "root";
	final private String passwd = "kaliffo";
	public String cerca;
	public String codice;

	// public String[] dbcodes; //deve essere un array

	public MySQLAccess() {
		columns = new Object[] { "Codice", "Descrizione", "cla1" };
		TableModel = new DefaultTableModel(databaseResults, columns);
		// This will load the MySQL driver, each DB has its own driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Setup the connection with the DB
		try {
			connect = DriverManager.getConnection(
					"jdbc:mysql://" + host + "/matrixintranet?" + "user=" + user + "&password=" + passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readDataBase(String cerca) throws Exception {
		try {
			preparedStatement = connect
					.prepareStatement("SELECT CDARMA, DSARMA, CLA1MA from matrixintranet.MGART00F where DSARMA like '"
							+ cerca + "%' OR CDARMA like '" + cerca + "%' limit 100");
			resultSet = preparedStatement.executeQuery();
			Object[] tempRow;
			TableModel.setRowCount(0);
			while (resultSet.next()) {
				tempRow = new Object[] { resultSet.getString("CDARMA"), resultSet.getString("DSARMA"),
						resultSet.getString("CLA1MA") };
				TableModel.addRow(tempRow);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// close();
		}

	}

	public List<String> readDB(String codice) throws Exception {
		List<String> componenti = new ArrayList<String>();
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					"SELECT concat(COM, DES) FROM matrixintranet.DBCOMDES where ASS='" + codice + "' order by SEQ");
			while (resultSet.next()) {
				componenti.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// close();
		}
		return componenti;

	}

	public void ReadDbComp(String Cod, String Desc, int Ci, DbDataNode node) throws SQLException {
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("SELECT COM, DES, CI FROM DBCOMDES where ASS='" + Cod + "' order by SEQ");
		List<DbDataNode> children = new ArrayList<DbDataNode>();
		while (resultSet.next()) {
			System.out.println(resultSet.getString("COM") + resultSet.getString("DES"));
			children.add(new DbDataNode(resultSet.getString("COM"), resultSet.getString("DES"),
					resultSet.getInt("CI"), null));
		}
		for (DbDataNode child : children) {
			ReadDbComp(child.getCod(), child.getDesc(), child.getCi(), child);
		}
		if (children != null && !children.isEmpty()) {
			node.setChildren(children);
		}
	}

	public List<DbDataNode> ReadComp(String codice) throws Exception {
		List<DbDataNode> componenti = new ArrayList<DbDataNode>();
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(
					"SELECT COM, DES, CI FROM matrixintranet.DBCOMDES where ASS='" + codice + "' order by SEQ");

			while (resultSet.next()) {
				// componenti.add(new DbDataNode(resultSet.getString("COM"),
				// resultSet.getString("DES"),resultSet.getInt("CI"), null));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// close();
		}
		return componenti;
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String codice = resultSet.getString("CDARMA");
			String descrizione = resultSet.getString("DSARMA");
			String cla1 = resultSet.getString("CLA1MA");

			System.out.println("Codice: " + codice);
			System.out.println("Descrizione: " + descrizione);
			System.out.println("Cla1: " + cla1);

		}
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
