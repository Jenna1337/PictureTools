import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
@SuppressWarnings("unused")
public class Picture extends SimplePicture 
{
	///////////////////// constructors //////////////////////////////////
	
	/**
	 * Constructor that takes no arguments 
	 */
	public Picture ()
	{
		/* not needed but use it to show students the implicit call to super()
		 * child constructors always call a parent constructor 
		 */
		super();  
	}
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
	 * Constructor that takes a file name and creates the picture 
	 * @param fileName the name of the file to create the picture from
	 */
	public Picture(String fileName)
	{
		// let the parent class handle this fileName
		super(fileName);
	}
	
	/**
	 * Constructor that takes the width and height
	 * @param height the height of the desired picture
	 * @param width the width of the desired picture
	 */
	public Picture(int height, int width)
	{
		// let the parent class handle this width and height
		super(width,height);
	}
	
	/**
	 * Constructor that takes a picture and creates a 
	 * copy of that picture
	 * @param copyPicture the picture to copy
	 */
	public Picture(Picture copyPicture)
	{
		// let the parent class do the copy
		super(copyPicture);
	}
	
	/**
	 * Constructor that takes a buffered image
	 * @param image the buffered image to use
	 */
	public Picture(BufferedImage image)
	{
		super(image);
	}
	
	////////////////////// methods ///////////////////////////////////////
	
	/**
	 * Method to return a string with information about this picture.
	 * @return a string with information about the picture such as fileName,
	 * height and width.
	 */
	public String toString()
	{
		String output = "Picture, filename " + getFileName() + 
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
		// TODO Auto-generated method stub
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int count = 0;
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
