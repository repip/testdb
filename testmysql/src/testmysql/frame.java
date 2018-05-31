package testmysql;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.sun.corba.se.impl.interceptors.CDREncapsCodec;
import com.sun.org.apache.xml.internal.security.utils.SignerOutputStream;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static MySQLAccess dao;
	private static JTable table;
	private JScrollPane scrollPane;
	private JTextField cerca;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dao = new MySQLAccess();
					frame frame = new frame();
					frame.setVisible(true);
					// dao = new MySQLAccess();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(101, 11, 453, 135);
		contentPane.add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(101, 159, 623, 121);
		contentPane.add(scrollPane_1);

		table = new JTable();
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					scrollPane_1.setViewportView(creatree(table.getValueAt(table.getSelectedRow(), 0).toString()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					scrollPane_1.setViewportView(creatree(table.getValueAt(table.getSelectedRow(), 0).toString()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "New column", "New column", "New column", "New column" }));

		cerca = new JTextField();
		cerca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					table.setModel(dao.TableModel);
					dao.readDataBase(cerca.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		cerca.setBounds(12, 12, 89, 19);
		contentPane.add(cerca);
		cerca.setColumns(10);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(101, 292, 623, 144);
		contentPane.add(scrollPane_2);
		MyAbstractTreeTableModel treeTableModel;
		try {
			List<DbDataNode> children = new ArrayList<DbDataNode>();
			children.add(new DbDataNode("N12", "C12", 1, null));
			DbDataNode root = new DbDataNode("root", "root", 1 , children);
			dao.ReadDbComp("C21.210M", "desc", 1, root);
			treeTableModel = new DbDataModel(root);
			MyTreeTable treetab = new MyTreeTable(treeTableModel);
			treetab.setBounds(10, 131, 398, 241);
			scrollPane_2.setViewportView(treetab);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		try {
			//ReadDbComp("H34.260U");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	
/*	
	private static DbDataNode createDataStructure() {
		List<DbDataNode> children1 = new ArrayList<DbDataNode>();
		children1.add(new DbDataNode("Codice1", "Descrizione1", Integer.valueOf(60), null));
		children1.add(new DbDataNode("Codice2", "Descrizione2", Integer.valueOf(60), null));
		children1.add(new DbDataNode("Codice3", "Descrizione3", Integer.valueOf(60), null));

		List<DbDataNode> children2 = new ArrayList<DbDataNode>();
		children1.add(new DbDataNode("Codice4", "Descrizione4", Integer.valueOf(60), null));
		children1.add(new DbDataNode("Codice5", "Descrizione5", Integer.valueOf(60), children1));
		children1.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), null));


		List<DbDataNode> rootNodes = new ArrayList<DbDataNode>();
		rootNodes.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), null));
		rootNodes.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), children2));
		rootNodes.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), null));
		rootNodes.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), children1));
		rootNodes.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), null));
		rootNodes.add(new DbDataNode("Codice6", "Descrizione6", Integer.valueOf(60), null));

		DbDataNode root = new DbDataNode("R1", "R1", Integer.valueOf(10), rootNodes);

		return root;
	}
*/

	private JTree creatree(String codice) throws Exception {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(codice);
		AddComp(codice, root);
		JTree tree = new JTree(root);
		expandAll(tree, tree.getPathForRow(0));
		// model.reload(root);
		return tree;

	}

	private void expandAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path);
			}
		}
		tree.expandPath(parent);
		// tree.collapsePath(parent);
	}

	private void AddComp(String cod, DefaultMutableTreeNode root) throws Exception {
		DefaultMutableTreeNode comp = null;
		List<String> componenti = dao.readDB(cod);
		for (String componente : componenti) {
			System.out.println(componente);
			comp = new DefaultMutableTreeNode(componente);
			root.add(comp);
			AddComp(componente.substring(0, 17), comp);
		}
	}

	private static void setColumnWidths(JTable table, Object[] columns, int... widths) {
		TableColumn column;
		for (int i = 0; i < columns.length; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(widths[i]);
		}
	}
}
