
public class GUIinit {
	GUIinit(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginDialog().setVisible(true);
            }
        });
	}
}
