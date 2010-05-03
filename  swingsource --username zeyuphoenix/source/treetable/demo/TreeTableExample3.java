package demo;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

import study.JTreeTable;
import study.TreeTableModel;

public class TreeTableExample3 {

    /** Used to represent the model. */
    private JTreeTable treeTable;
    /** Frame containing everything. */
    private JFrame frame;
    /** Path created for. */
    private String path;

    /**
     * Creates a TreeTableExample3, loading the bookmarks from the file at
     * <code>path</code>.
     */
    public TreeTableExample3(String path) {
        this.path = path;

        frame = createFrame();

        Container cPane = frame.getContentPane();
        JMenuBar mb = createMenuBar();
        TreeTableModel model = createModel(path);

        treeTable = createTreeTable(model);
        JScrollPane sp = new JScrollPane(treeTable);
        sp.getViewport().setBackground(Color.white);
        cPane.add(sp);

        frame.setJMenuBar(mb);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates and returns the instanceof JTreeTable that will be used.
     */
    protected JTreeTable createTreeTable(TreeTableModel model) {
        JTreeTable treeTable = new JTreeTable(model);

        treeTable.setDefaultRenderer(Date.class, new BookmarksDateRenderer());
        treeTable.setDefaultRenderer(Object.class,
                new BookmarksStringRenderer());
        return treeTable;
    }

    /**
     * Creates the BookmarksModel for the file at <code>path</code>.
     */
    protected TreeTableModel createModel(String path) {
        Bookmarks bookmarks = new Bookmarks(path);
        return new BookmarksModel(bookmarks.getRoot());
    }

    /**
     * Creates the JFrame that will contain everything.
     */
    protected JFrame createFrame() {
        JFrame retFrame = new JFrame("TreeTable " + path);

        retFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                frame.dispose();
                System.exit(0);
            }
        });
        return retFrame;
    }

    /**
     * Creates a menu bar.
     */
    protected JMenuBar createMenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem menuItem;

        menuItem = new JMenuItem("Open");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser(path);
                int result = fc.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String newPath = fc.getSelectedFile().getPath();

                    new TreeTableExample3(newPath);
                }
            }
        });
        fileMenu.add(menuItem);
        fileMenu.addSeparator();

        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        fileMenu.add(menuItem);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(fileMenu);

        return menuBar;
    }

    /**
     * The renderer used for Dates in the TreeTable. The only thing it does, is
     * to format a null date as '---'.
     */
    private static class BookmarksDateRenderer extends DefaultTableCellRenderer {
        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;
        DateFormat formatter;

        public BookmarksDateRenderer() {
            super();
        }

        public void setValue(Object value) {
            if (formatter == null) {
                formatter = DateFormat.getDateInstance();
            }
            setText((value == null) ? "---" : formatter.format(value));
        }
    }

    /**
     * The renderer used for String in the TreeTable. The only thing it does, is
     * to format a null String as '---'.
     */
    static class BookmarksStringRenderer extends DefaultTableCellRenderer {
        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;

        public BookmarksStringRenderer() {
            super();
        }

        public void setValue(Object value) {
            setText((value == null) ? "---" : value.toString());
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            // User is specifying the bookmark file to show.
            for (int counter = args.length - 1; counter >= 0; counter--) {
                new TreeTableExample3(args[counter]);
            }
        } else {
            // No file specified, see if the user has one in their home
            // directory.
            String path;

            try {
                path = System.getProperty("user.home");
                if (path != null) {
                    path += File.separator + ".netscape" + File.separator
                            + "bookmarks.html";
                    File file = new File(path);
                    if (!file.exists()) {
                        path = URLDecoder.decode(TreeTableExample3.class
                                .getResource("bookmarks.html").getPath(),
                                "UTF-8");
                    }
                    System.out.println(path);
                }
            } catch (Throwable th) {
                path = null;
            }
            if (path == null) {
                // None available, use a default.
                path = "bookmarks.html";
            }
            new TreeTableExample3(path);
        }
    }
}
