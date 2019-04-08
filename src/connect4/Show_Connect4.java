package connect4;

public class Show_Connect4 {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Connect4 connect4 = new Connect4();
			}
		});
	}
}
