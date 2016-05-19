package picTools;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture
{
	
	/////////////////////// Fields /////////////////////////	
	/**
	 * buffered image to hold pixels for the simple picture
	 */
	private BufferedImage bufferedImage;
	
	/////////////////////// Constructors /////////////////////////
	
	/**
	 * A Constructor that takes no arguments.  It creates a picture with
	 * a width of 200 and a height of 100 that is all white.
	 * A no-argument constructor must be given in order for a class to
	 * be able to be subclassed.  By default all subclasses will implicitly
	 * call this in their parent's no-argument constructor unless a 
	 * different call to super() is explicitly made as the first line 
	 * of code in a constructor.
	 */
	public Picture() 
	{this(200,100);}
	
	/**
	 * A Constructor that takes a file name and uses the file to create
	 * a picture
	 * @param fileName the file name to use in creating the picture
	 */
	public Picture(String fileName)
	{
		
		// load the picture into the buffered image 
		load(fileName);
		
	}
	
	/**
	 * A constructor that takes the width and height desired for a picture and
	 * creates a buffered image of that size.  This constructor doesn't 
	 * show the picture.  The pixels will all be white.
	 * @param width the desired width
	 * @param height the desired height
	 */
	public  Picture(int width, int height)
	{
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		setAllPixelsToAColor(PhotoMaker.transparent);
	}
	
	/**
	 * A constructor that takes the width and height desired for a picture and
	 * creates a buffered image of that size.  It also takes the
	 * color to use for the background of the picture.
	 * @param width the desired width
	 * @param height the desired height
	 * @param theColor the background color for the picture
	 */
	public  Picture(int width, int height, Color theColor)
	{
		this(width,height);
		setAllPixelsToAColor(theColor);
	}
	
	/**
	 * A Constructor that takes a picture to copy information from
	 * @param copyPicture the picture to copy from
	 */
	public Picture(Picture copyPicture)
	{
		if (copyPicture.bufferedImage != null)
		{
			this.bufferedImage = new BufferedImage(copyPicture.getWidth(),
					copyPicture.getHeight(), BufferedImage.TYPE_INT_ARGB);
			this.copyPicture(copyPicture);
		}
	}
	
	/**
	 * A constructor that takes a buffered image
	 * @param image the buffered image
	 */
	public Picture(BufferedImage image)
	{
		this.bufferedImage = image;
	}
	
	////////////////////////// Methods //////////////////////////////////
	
	/**
	 * Method that will copy all of the passed source picture into
	 * the current picture object 
	 * @param sourcePicture  the picture object to copy
	 */
	public void copyPicture(Picture sourcePicture)
	{
		Pixel sourcePixel = null;
		Pixel targetPixel = null;
		
		// loop through the columns
		for (int sourceX = 0, targetX = 0; 
				sourceX < sourcePicture.getWidth() &&
				targetX < this.getWidth();
				sourceX++, targetX++)
		{
			// loop through the rows
			for (int sourceY = 0, targetY = 0; 
					sourceY < sourcePicture.getHeight() && 
					targetY < this.getHeight();
					sourceY++, targetY++)
			{
				sourcePixel = sourcePicture.getPixel(sourceX,sourceY);
				targetPixel = this.getPixel(targetX,targetY);
				targetPixel.setColor(sourcePixel.getColor());
			}
		}
		
	}
	
	/**
	 * Method to set the color in the picture to the passed color
	 * @param color the color to set to
	 */
	public void setAllPixelsToAColor(Color color)
	{
		// loop through all x
		for (int x = 0; x < this.getWidth(); x++)
		{
			// loop through all y
			for (int y = 0; y < this.getHeight(); y++)
			{
				getPixel(x,y).setColor(color);
			}
		}
	}
	
	/**
	 * Method to get the buffered image
	 * @return the buffered image 
	 */
	public BufferedImage getBufferedImage() 
	{
		return bufferedImage;
	}
	
	/**
	 * Method to get a graphics object for this picture to use to draw on
	 * @return a graphics object to use for drawing
	 */
	public Graphics getGraphics()
	{
		return bufferedImage.getGraphics();
	}
	
	/**
	 * Method to get a Graphics2D object for this picture which can
	 * be used to do 2D drawing on the picture
	 */
	public Graphics2D createGraphics()
	{
		return bufferedImage.createGraphics();
	}
	
	/**
	 * Method to get the width of the picture in pixels
	 * @return the width of the picture in pixels
	 */
	public int getWidth() { return bufferedImage.getWidth(); }
	
	/**
	 * Method to get the height of the picture in pixels
	 * @return  the height of the picture in pixels
	 */
	public int getHeight() { return bufferedImage.getHeight(); }
	
	/**
	 * Method to get an image from the picture
	 * @return  the buffered image since it is an image
	 */
	public Image getImage()
	{
		return bufferedImage;
	}
	
	/**
	 * Method to return the pixel value as an int for the given x and y location
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @return the pixel value as an integer (alpha, red, green, blue)
	 */
	public int getBasicPixel(int x, int y)
	{
		return bufferedImage.getRGB(x,y);
	}
	
	/** 
	 * Method to set the value of a pixel in the picture from an int
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @param rgb the new rgb value of the pixel (alpha, red, green, blue)
	 */     
	public void setBasicPixel(int x, int y, int rgb)
	{
		bufferedImage.setRGB(x,y,rgb);
	}
	
	/**
	 * Method to get a pixel object for the given x and y location
	 * @param x  the x location of the pixel in the picture
	 * @param y  the y location of the pixel in the picture
	 * @return a Pixel object for this location
	 */
	public Pixel getPixel(int x, int y)
	{
		// create the pixel object for this picture and the given x and y location
		Pixel pixel = new Pixel(this,x,y);
		return pixel;
	}
	
	/**
	 * Method to get a one-dimensional array of Pixels for this simple picture
	 * @return a one-dimensional array of Pixel objects starting with y=0
	 * to y=height-1 and x=0 to x=width-1.
	 */
	public Pixel[] getPixels()
	{
		int width = getWidth();
		int height = getHeight();
		Pixel[] pixelArray = new Pixel[width * height];
		
		// loop through height rows from top to bottom
		for (int row = 0; row < height; row++) 
			for (int col = 0; col < width; col++) 
				pixelArray[row * width + col] = new Pixel(this,col,row);
		
		return pixelArray;
	}
	
	/**
	 * Method to get a two-dimensional array of Pixels for this simple picture
	 * @return a two-dimensional array of Pixel objects in row-major order.
	 */
	public Pixel[][] getPixels2D()
	{
		int width = getWidth();
		int height = getHeight();
		Pixel[][] pixelArray = new Pixel[height][width];
		
		// loop through height rows from top to bottom
		for (int row = 0; row < height; row++) 
			for (int col = 0; col < width; col++) 
				pixelArray[row][col] = new Pixel(this,col,row);
		
		return pixelArray;
	}
	
	/**
	 * Method to load the buffered image with the passed image
	 * @param image  the image to use
	 */
	public void load(Image image)
	{
		// get a graphics context to use to draw on the buffered image
		Graphics2D graphics2d = bufferedImage.createGraphics();
		
		// draw the image on the buffered image starting at 0,0
		graphics2d.drawImage(image,0,0,null);
	}
	
	/**
	 * Method to load the picture from the passed file name
	 * @param fileName the file name to use to load the picture from
	 * @throws IOException if the picture isn't found
	 */
	public void loadOrFail(String fileName) throws IOException
	{
		File file = new File(fileName);
		
		if (!file.canRead()) 
		{
			// try adding the media path 
			file = new File(PhotoMaker.filepath+fileName);
		}
		
		bufferedImage = ImageIO.read(file);
	}
	
	
	/**
	 * Method to read the contents of the picture from a filename  
	 * without throwing errors
	 * @param fileName the name of the file to write the picture to
	 * @return true if success else false
	 */
	public boolean load(String fileName)
	{
		try {
			this.loadOrFail(fileName);
			return true;
			
		} catch (Exception ex) {
			System.out.println("There was an error trying to open " + fileName);
			bufferedImage = new BufferedImage(600,200,
					BufferedImage.TYPE_INT_ARGB);
			addMessage("Couldn't load " + fileName,5,100);
			return false;
		}
		
	}
	
	/**
	 * Method to load the picture from the passed file name
	 * this just calls load(fileName) and is for name compatibility
	 * @param fileName the file name to use to load the picture from
	 * @return true if success else false
	 */
	public boolean loadImage(String fileName)
	{
		return load(fileName);
	}
	
	/**
	 * Method to draw a message as a string on the buffered image 
	 * @param message the message to draw on the buffered image
	 * @param xPos  the x coordinate of the leftmost point of the string 
	 * @param yPos  the y coordinate of the bottom of the string  
	 */
	public void addMessage(String message, int xPos, int yPos)
	{
		// get a graphics context to use to draw on the buffered image
		Graphics2D graphics2d = bufferedImage.createGraphics();
		
		// set the color to white
		graphics2d.setPaint(Color.white);
		
		// set the font to Helvetica bold style and size 16
		graphics2d.setFont(new Font("Helvetica",Font.BOLD,16));
		
		// draw the message
		graphics2d.drawString(message,xPos,yPos);
		
	}
	
	/**
	 * Method to draw a string at the given location on the picture
	 * @param text the text to draw
	 * @param xPos the left x for the text 
	 * @param yPos the top y for the text
	 */
	public void drawString(String text, int xPos, int yPos)
	{
		addMessage(text,xPos,yPos);
	}
	
	/**
	 * Method to create a new picture by scaling the current
	 * picture by the given x and y factors
	 * @param xFactor the amount to scale in x
	 * @param yFactor the amount to scale in y
	 * @return the resulting picture
	 */
	public Picture scale(double xFactor, double yFactor)
	{
		// set up the scale transform
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.scale(xFactor,yFactor);
		
		// create a new picture object that is the right size
		Picture result = new Picture((int) (getWidth() * xFactor),
				(int) (getHeight() * yFactor));
		
		// get the graphics 2d object to draw on the result
		Graphics graphics = result.getGraphics();
		Graphics2D g2 = (Graphics2D) graphics;
		
		// draw the current image onto the result image scaled
		g2.drawImage(this.getImage(),scaleTransform,null);
		
		return result;
	}
	
	/**
	 * Method to create a new picture of the passed width. 
	 * The aspect ratio of the width and height will stay
	 * the same.
	 * @param width the desired width
	 * @return the resulting picture
	 */
	public Picture getPictureWithWidth(int width)
	{
		// set up the scale transform
		double xFactor = (double) width / this.getWidth();
		Picture result = scale(xFactor,xFactor);
		return result;
	}
	
	/**
	 * Method to create a new picture of the passed height. 
	 * The aspect ratio of the width and height will stay
	 * the same.
	 * @param height the desired height
	 * @return the resulting picture
	 */
	public Picture getPictureWithHeight(int height)
	{
		// set up the scale transform
		double yFactor = (double) height / this.getHeight();
		Picture result = scale(yFactor,yFactor);
		return result;
	}
	
	/**
	 * Method to load a picture from a file name and show it in a picture frame
	 * @param fileName the file name to load the picture from
	 * @return true if success else false
	 */
	public boolean loadPictureAndShowIt(String fileName)
	{
		boolean result = true;  // the default is that it worked
		
		// try to load the picture into the buffered image from the file name
		result = load(fileName);
		
		return result;
	}
	
	/**
	 * Method to write the contents of the picture to a file with 
	 * the passed name
	 * @param fileName the name of the file to write the picture to
	 */
	public void writeOrFail(String fileName) throws IOException
	{
		String extension="png"; // the default is current
		
		if(fileName.indexOf('.') < 0)
			fileName+="."+extension;
		// create the file object
		File file = new File(fileName);
		
		fileName = PhotoMaker.filepath+fileName;
		file = new File(fileName);
		
		// get the extension
		if(fileName.indexOf('.') >= 0)
			extension = fileName.substring(fileName.indexOf('.') + 1);
		
		if(!(new File(fileName).exists()))
			new File(fileName).mkdirs();
		
		// write the contents of the buffered image to the file
		ImageIO.write(bufferedImage, extension, file);
	}
	
	/**
	 * Method to write the contents of the picture to a file with 
	 * the passed name without throwing errors
	 * @param fileName the name of the file to write the picture to
	 * @return true if success else false
	 */
	public boolean write(String fileName)
	{
		try {
			this.writeOrFail(fileName);
			return true;
		} catch (Exception ex) {
			System.out.println("There was an error trying to write " + fileName);
			ex.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * Method to get the directory for the media
	 * @param fileName the base file name to use
	 * @return the full path name by appending
	 * the file name to the media directory
	 */
	public static String getMediaPath(String fileName) {
		return PhotoMaker.filepath;
	}
	
	/**
	 * Method to get the coordinates of the enclosing rectangle after this
	 * transformation is applied to the current picture
	 * @return the enclosing rectangle
	 */
	public Rectangle2D getTransformEnclosingRect(AffineTransform trans)
	{
		int width = getWidth();
		int height = getHeight();
		double maxX = width - 1;
		double maxY = height - 1;
		double minX, minY;
		Point2D.Double p1 = new Point2D.Double(0,0);
		Point2D.Double p2 = new Point2D.Double(maxX,0);
		Point2D.Double p3 = new Point2D.Double(maxX,maxY);
		Point2D.Double p4 = new Point2D.Double(0,maxY);
		Point2D.Double result = new Point2D.Double(0,0);
		Rectangle2D.Double rect = null;
		
		// get the new points and min x and y and max x and y
		trans.deltaTransform(p1,result);
		minX = result.getX();
		maxX = result.getX();
		minY = result.getY();
		maxY = result.getY();
		trans.deltaTransform(p2,result);
		minX = Math.min(minX,result.getX());
		maxX = Math.max(maxX,result.getX());
		minY = Math.min(minY,result.getY());
		maxY = Math.max(maxY,result.getY());
		trans.deltaTransform(p3,result);
		minX = Math.min(minX,result.getX());
		maxX = Math.max(maxX,result.getX());
		minY = Math.min(minY,result.getY());
		maxY = Math.max(maxY,result.getY());
		trans.deltaTransform(p4,result);
		minX = Math.min(minX,result.getX());
		maxX = Math.max(maxX,result.getX());
		minY = Math.min(minY,result.getY());
		maxY = Math.max(maxY,result.getY());
		
		// create the bounding rectangle to return
		rect = new Rectangle2D.Double(minX,minY,maxX - minX + 1, maxY - minY + 1);
		return rect;
	}
	
	/**
	 * Method to get the coordinates of the enclosing rectangle after this
	 * transformation is applied to the current picture
	 * @return the enclosing rectangle
	 */
	public Rectangle2D getTranslationEnclosingRect(AffineTransform trans)
	{
		return getTransformEnclosingRect(trans);
	}
	
	////////////////////// methods ///////////////////////////////////////
	
	public void negate()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setRed(255-pixelObj.getRed());
				pixelObj.setGreen(255-pixelObj.getGreen());
				pixelObj.setBlue(255-pixelObj.getBlue());
			}
		}
	}
	
	/**
	 * Method to return a string with information about this picture.
	 * @return a string with information about the picture such as fileName,
	 * height and width.
	 */
	public String toString()
	{
		String output = "Picture, " + 
				" height " + getHeight() 
				+ " width " + getWidth();
		return output;
		
	}
	
	/** Method to set the blue to 0 */
	public void zeroBlue()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setBlue(0);
			}
		}
	}
	/** Method to set the red to 0 */
	public void zeroRed()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setRed(0);
			}
		}
	}
	
	/** Method to set the green to 0 */
	public void zeroGreen()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				pixelObj.setGreen(0);
			}
		}
	}
	public void grayscale()
	{
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels)
		{
			for (Pixel pixelObj : rowArray)
			{
				int avg = ((Double)pixelObj.getAverage()).intValue();
				pixelObj.setRed(avg);
				pixelObj.setBlue(avg);
				pixelObj.setGreen(avg);
			}
		}
	}
	/** Method that mirrors the picture around a 
	 * vertical mirror in the center of the picture
	 * from left to right */
	public void mirrorVertical()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < width / 2; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				rightPixel.setColor(leftPixel.getColor());
			}
		} 
	}
	public void mirrorHorizontal()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int height = pixels.length;
		for (int col = 0; col < pixels[0].length; col++)
		{
			for (int row = 0; row < height; row++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[height - 1 - row][col];
				rightPixel.setColor(leftPixel.getColor());
			}
		} 
	}
	public void mirrorHorizontal1()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int height = pixels.length;
		for (int col = 0; col < pixels[0].length; col++)
		{
			for (int row = 0; row < height; row++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[height - 1 - row][col];
				leftPixel.setColor(rightPixel.getColor());
			}
		} 
	}
	public void mirrorVerticalR2L()
	{
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < width / 2; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				leftPixel.setColor(rightPixel.getColor());
			}
		} 
	}
	/** Mirror just part of a picture of a temple */
	public void mirrorTemple()
	{
		int mirrorPoint = 276;
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int count = 0;
		Pixel[][] pixels = this.getPixels2D();
		// loop through the rows
		for (int row = 27; row < 97; row++)
		{
			// loop from 13 to just before the mirror point
			for (int col = 13; col < mirrorPoint; col++)
			{
				
				leftPixel = pixels[row][col];      
				rightPixel = pixels[row]                       
						[mirrorPoint - col + mirrorPoint];
				rightPixel.setColor(leftPixel.getColor());
				count++;
			}
		}
		System.out.print(count);
	}
	
	/** copy from the passed fromPic to the
	 * specified startRow and startCol in the
	 * current picture
	 * @param fromPic the picture to copy from
	 * @param startRow the start row to copy to
	 * @param startCol the start col to copy to
	 */
	public void copy(Picture fromPic, 
			int startRow, int startCol)
	{
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = 0, toRow = startRow; 
				fromRow < fromPixels.length &&
				toRow < toPixels.length; 
				fromRow++, toRow++)
		{
			for (int fromCol = 0, toCol = startCol; 
					fromCol < fromPixels[0].length &&
					toCol < toPixels[0].length;  
					fromCol++, toCol++)
			{
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}   
	}
	public void copy(Picture fromPic, 
			int startRow, int startCol, int startfromRow, int startfromCol, int endfromRow, int endfromCol)
	{
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = startfromRow, toRow = startRow; fromRow < endfromRow && toRow < toPixels.length; fromRow++, toRow++)
		{
			for (int fromCol = startfromCol, toCol = startCol; fromCol < endfromCol && toCol < toPixels[0].length; fromCol++, toCol++)
			{
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}   
	}
	
	/** Method to create a collage of several pictures */
	public void createCollage()
	{
		Picture flower1 = new Picture("flower1.jpg");
		Picture flower2 = new Picture("flower2.jpg");
		Picture flowerNoBlue = new Picture(flower2); flowerNoBlue.zeroBlue();
		Picture grayflower = new Picture(flower1); grayflower.grayscale();
		Picture flowerblue = new Picture(flower1); flowerblue.zeroRed();
		Picture flowerred = new Picture(flower1); flowerred.zeroBlue();
		Picture flowergreen = new Picture(flower1); flowergreen.zeroGreen(); flowergreen.negate();
		
		this.copy(flower1,0,0);
		this.copy(flower2,100,0);
		this.copy(flower1,200,0);
		this.copy(flowerNoBlue,300,0);
		this.copy(flower1,400,0);
		this.copy(flower2,500,0);
		this.copy(grayflower,0,100,0,0,50,100);
		this.copy(flower1,50,100,50,0,100,100);
		this.copy(flowerblue,0,200,0,0,50,50);
		this.copy(flowerred,0,250,0,50,50,100);
		this.copy(flowergreen,50,200,50,0,100,100);
		this.mirror(100,0,200,100,false);
		this.mirrorVertical();
		this.write("collage.jpg");
	}
	
	
	/** Method to show large changes in color 
	 * @param edgeDist the distance for finding edges
	 */
	public void edgeDetection(int edgeDist)
	{
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		for (int row = 0; row < pixels.length-1; row++)
		{
			for (int col = 0; 
					col < pixels[0].length-1; col++)
			{
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][col+1];
				rightColor = rightPixel.getColor();
				if (leftPixel.colorDistance(rightColor) > edgeDist)
					leftPixel.setColor(Color.BLACK);
				else
				{
					leftPixel = pixels[row][col];
					rightPixel = pixels[row+1][col];
					rightColor = rightPixel.getColor();
					if (leftPixel.colorDistance(rightColor) > edgeDist)
						leftPixel.setColor(Color.BLACK);
					else
						leftPixel.setColor(Color.WHITE);
				}
			}
		}
	}
	
	public void chromakey(String bgs)
	{
		Picture bg = new Picture(bgs);
		Pixel[][] pixels = this.getPixels2D(), 
				bgpixels = bg.getPixels2D();
		Color key = this.getCommonColor();
		for (int row = 0; row < pixels.length; row++)
		{
			for (int col = 0; col < pixels[0].length; col++)
			{
				if (pixels[row][col].colorDistance(key) <= 20)
					pixels[row][col].setColor(bgpixels[row][col].getColor());
			}
		}
	}
	public Color getCommonColor()
	{
		Pixel[] pixs = this.getPixels();
		Color[] clrs = new Color[pixs.length];
		for(int i=0; i<pixs.length; ++i)
			clrs[i] = pixs[i].getColor();
		int count = 1, tempCount;
		Color popular = clrs[0];
		Color temp = null;
		for (int i = 0; i < (clrs.length - 1); i++)
		{
			temp = clrs[i];
			tempCount = 0;
			for (int j = 1; j < clrs.length; j++)
			{
				if (temp == clrs[j])
					tempCount++;
			}
			if (tempCount > count)
			{
				popular = temp;
				count = tempCount;
			}
		}
		return popular;
	}
	
	public void mirror(int c1, int r1, int c2, int r2, boolean h)
	{
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		
		// loop through the rows
		if(h)
			for (int row = r1; row < r2; row++)
			{
				for (int col = c1; col < c2; col++)
				{
					
					leftPixel = pixels[row][col];      
					rightPixel = pixels[row][c2 - col + c2];
					rightPixel.setColor(leftPixel.getColor());
				}
			}
		else if(!h)
			for (int row = r1; row < r2; row++)
			{
				for (int col = c1; col < c2; col++)
				{
					
					leftPixel = pixels[row][col];      
					rightPixel = pixels[r2 - row + r2][col];
					rightPixel.setColor(leftPixel.getColor());
				}
			}
	}
	
} // this } is the end of class Picture, put all new methods before this
