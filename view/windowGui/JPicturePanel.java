/**
 * 
 */
package kalaha.view.windowGui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * @author Thomas Heinz, Stefan Schultheiss
 * 
 */
@SuppressWarnings("serial")
public class JPicturePanel extends JPanel {

	public Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/kalaha/media/background.jpg"));

	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
