import java.awt.*;
import java.awt.image.*;
import javax.swing.JComponent;
import javax.swing.JFrame;

// Manipulation for binary images
 
public class BinaryImage {

	private int width; // width of the image
	private int height; // height of the image
	private int[] raster; // raster for the image

	static final int BLACK = 0xFF000000;
	static final int WHITE = 0xFFFFFFFF;
	static final int GREY = 0xFFDCDCDC;

	
// Constructor that instantiates an image of a specified width and height (all pixels are black)
	public BinaryImage(int width, int height) {
		this.width = width;
		this.height = height;
		raster = new int[width * height];
		for (int i = 0; i < width * height; i++)
			raster[i] = WHITE;
	}

	
// Fill all pixel with given color
	public void fill(int height, int width, Cell[][] cells) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (cells[i][j].color == 0) {
					for (int k = 0; k < 20; k++) {
						for (int l = 0; l < 20; l++)
							setPixel(20 * i + k, 20 * j + l, WHITE);
					}
				} else if (cells[i][j].color == 1) {
					for (int k = 0; k < 20; k++) {
						for (int l = 0; l < 20; l++)
							setPixel(20 * i + k, 20 * j + l, GREY);
					}
				} else {
					for (int k = 0; k < 20; k++) {
						for (int l = 0; l < 20; l++)
							setPixel(20 * i + k, 20 * j + l, BLACK);
					}
				}
			}
		}
	}

	
// Produces an printable image from the raster 
	public java.awt.Image toImage() {
		ImageProducer ip = new MemoryImageSource(width, height, raster, 0, width);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}


// Return the width of the image
	public int getWidth() {
		return this.width;
	}


// Return the height of the image
	public int getHeight() {
		return this.height;
	}

// Set the pixel at position (x,y) to color c
	protected void setPixel(int x, int y, int c) {
		if (x < 0 || x >= height || y < 0 || y >= width) {
			throw new IllegalArgumentException("illegal position");
		}
		raster[x * width + y] = c;
	}
}


// Creation of an image

class ImageViewer extends JFrame {

	private static final long serialVersionUID = -7498525833438154949L;
	static int xLocation = 0;

	public ImageViewer(BinaryImage img, Cell[][] cells) {
		this.setLocation(xLocation, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageComponent ic = new ImageComponent(img, cells);
		add(ic);
		pack();
		setVisible(true);
		xLocation += img.getWidth();
	}
}


// Characteristics of an image

class ImageComponent extends JComponent {

	private static final long serialVersionUID = -7710437354239120390L;
	private BinaryImage img;
	private Cell[][] cells;

	public ImageComponent(BinaryImage img, Cell[][] cells) {
		this.img = img;
		this.cells = cells;
		setPreferredSize(new Dimension(img.getWidth(), img.getWidth()));
	}

	public void paint(Graphics g) {
		int h = img.getHeight();
		int w = img.getWidth();
		g.drawImage(img.toImage(), 0, 0, this);
		for (int i = 0; i < w + 1; i = i + 20)
			g.drawLine(i, 0, i, h);
		for (int j = 0; j < h + 1; j = j + 20)
			g.drawLine(0, j, w, j);
		for (int i = 0; i < w / 20; i++) {
			for (int j = 0; j < h / 20; j++) {
				if (cells[j][i].number != 0)
					g.drawString(cells[j][i].number + "", 20 * i + 5, 20 * j + 15);
			}
		}
	}
}
