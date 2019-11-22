import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class GalleryPanel extends JPanel {

	DisplayPanel displayPanel;

	// this panel is where the image will sit on
	public GalleryPanel(JLabel image, DisplayPanel panel) {
		// adds listener to gallerypanel
		MouseListener listen = new GalleryListener();
		addMouseListener(listen);
		// add image to gallerypanel
		this.add(image);
		this.revalidate();

		this.displayPanel = panel;
	}

	public void panelGone() {
		// removes panel from panel of images
		displayPanel.removeImage(this);

	}

	class GalleryListener implements MouseListener {

		public void actionPerformed(ActionEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// removes panel on mouse click
			// if delete on click selected
			panelGone();

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}