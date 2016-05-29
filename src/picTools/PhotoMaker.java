package picTools;

import java.awt.Color;
import java.util.ArrayList;

public class PhotoMaker
{
	public static final String filepath="";
	public static final int transparent = 0x00000000;
	public static final int opaque      = 0xFF000000;
	public static boolean isTransparent(int color)
	{
		return get(color,'a')==get(transparent,'a');
	}
	public static boolean isSemiTransparent(int color)
	{
		return (!isTransparent(color))&&(!isOpaque(color));
	}
	public static boolean isOpaque(int color)
	{
		return get(color,'a')==get(opaque,'a');
	}
	public static final int BitOffset_RED  =0;
	public static final int BitOffset_GREEN=8;
	public static final int BitOffset_BLUE =16;
	public static final int BitOffset_ALPHA=24;
	public static int get(int color, char attr)
	{
		switch(attr)
		{
			case 'r':
			case 'R':
				return get(color,BitOffset_RED);
			case 'g':
			case 'G':
				return get(color,BitOffset_GREEN);
			case 'b':
			case 'B':
				return get(color,BitOffset_BLUE);
			case 'a':
			case 'A':
				return get(color,BitOffset_ALPHA);
			default:
				throw new IllegalArgumentException("Character \'"+attr+"\'.");
		}
	}
	public static int get(int color, int offset)
	{
		return ((color>>offset)&0xFF);
	}
	public static Picture mirrorHorizontal(Picture pic, boolean topToBot)
	{
		Picture cop = new Picture(pic);
		if(topToBot)
			cop.mirrorHorizontal();
		else
			cop.mirrorHorizontal1();
		return cop;
	}
	public static Picture mirrorVertical(Picture pic, boolean leftToRight)
	{
		Picture cop = new Picture(pic);
		if(leftToRight)
			cop.mirrorVertical();
		else
			cop.mirrorVerticalR2L();
		return cop;
	}
	public static Picture setRed(Picture pic, int value)
	{
		return modifyPicture(pic, "setRed", value);
	}
	public static Picture setGreen(Picture pic, int value)
	{
		return modifyPicture(pic, "setGreen", value);
	}
	public static Picture setBlue(Picture pic, int value)
	{
		return modifyPicture(pic, "setBlue", value);
	}
	public static Picture setAlpha(Picture pic, int value)
	{
		return modifyPicture(pic, "setAlpha", value);
	}
	public static Picture setColor(Picture pic, Color value)
	{
		return modifyPicture(pic, "setColor", value);
	}
	public static Pixel setRed(Pixel pic, int value)
	{
		return modifyPixel(pic, "setRed", value);
	}
	public static Pixel setGreen(Pixel pic, int value)
	{
		return modifyPixel(pic, "setGreen", value);
	}
	public static Pixel setBlue(Pixel pic, int value)
	{
		return modifyPixel(pic, "setBlue", value);
	}
	public static Pixel setAlpha(Pixel pic, int value)
	{
		return modifyPixel(pic, "setAlpha", value);
	}
	public static Pixel setColor(Pixel pic, Color value)
	{
		return modifyPixel(pic, "setColor", value);
	}
	public static Picture merge(boolean mask, String[] filenames)
	{
		Picture[] tomerge=new Picture[filenames.length];
		for(int i=0;i<tomerge.length;++i)
			tomerge[i]=new Picture(filenames[i]);
		return merge(mask, tomerge);
	}
	public static Picture merge(final boolean bitmask, Picture... pics)
	{
		//final boolean bitmask=false; 
		int mh=0,mw=0;
		for(int i=0;i<pics.length;++i)
		{
			mh=Math.max(mh,pics[i].getHeight());
			mw=Math.max(mw,pics[i].getWidth());
		}
		int[] offw=new int[pics.length], offh=new int[pics.length];
		for(int i=0; i<pics.length; ++i)
		{
			offw[i]=0; offh[i]=0;
			if(pics[i].getHeight()<mh || pics[i].getWidth()<mh)
			{
				Picture newpic = new Picture(mw, mh);
				newpic=PhotoMaker.setAlpha(newpic, 0);
				
				while(pics[i].getHeight()+offh[i]<mh)
				{
					offh[i]+=1;
				}
				while(pics[i].getWidth()+offw[i]<mw)
				{
					offw[i]+=1;
				}
			}
		}
		Picture cop = new Picture(mw, mh);
		cop=PhotoMaker.setAlpha(cop, 0);
		
		cop.getPixels2D();
		for(int row=0;row<cop.getHeight(); ++row)
		{
			for (int col=0;col<cop.getWidth(); ++col)
			{
				ArrayList<Integer> colorlist=new ArrayList<Integer>();
				for(int pi=0;pi<pics.length;++pi)
				{
					int c;
					try
					{
						c = pics[pi].getBasicPixel(col-offw[pi]/2, row-offh[pi]/2);
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						c = transparent;
					}
					colorlist.add(c);
				}
				
				//update the picture with the int value
				cop.setBasicPixel(col,row,blend(colorlist, bitmask));
			}
		}
		return cop;
	}
	public static int blend(ArrayList<Integer> colorlist, boolean bitmask)
	{
		long sum1=0,sum2=0;
		int alpha=0;
		int size=colorlist.size();
		for (int color : colorlist)
		{
			alpha+=get(color,'a');
			if(!isTransparent(color))
			{
				sum1+= color    &0x00FF00FF;
				sum2+=(color>>8)&0x00FF00FF;
			}
			if(isTransparent(color))
				size-=1;
		}
		alpha=bitmask ? ((alpha==0)?0:0xFF) : (alpha/colorlist.size());
		int value=0;
		try{
		value|=(((sum1&0xFFFF)/(size))&0xFF);}catch(ArithmeticException ae){}try{
		value|=(((sum2&0xFFFF)/(size))&0xFF)<<8;}catch(ArithmeticException ae){}try{
		value|=((((sum1>>=16)&0xFFFF)/(size))&0xFF)<<16;}catch(ArithmeticException ae){}
		return value|=alpha<<24;
	}
	public static Pixel[][] getPixels(Picture pic, int rowstart, int colstart, int rowend, int colend)
	{
		Pixel[][] pixs = new Pixel[rowend-rowstart+1][colend-colstart+1];
		for (int row = rowstart; row <= rowend && row < pic.getHeight(); row++)
			for (int col = colstart; col <= colend && col < pic.getWidth(); col++)
				pixs[rowend-row][colend-col] = pic.getPixels2D()[row][col];
		return pixs;
	}
	/**
	 * 
	 * @param pic
	 * @param row
	 * @return Pixels in row-column order
	 */
	public static Pixel[] getRow(Picture pic, int row)
	{
		return getPixels(pic, row, 0, row, pic.getWidth()-1)[0];
	}
	/**
	 * 
	 * @param pic
	 * @param rowfrom
	 * @param rowto
	 * @return Pixels in row-column order
	 */
	public static Pixel[][] getRows(Picture pic, int rowfrom, int rowto)
	{
		return getPixels(pic, rowfrom, 0, rowto, pic.getWidth()-1);
	}
	/**
	 * 
	 * @param pic
	 * @param col
	 * @return Pixels in column-row order
	 */
	public static Pixel[] getCol(Picture pic, int col)
	{
		Pixel[][] rowcol = getPixels(pic, 0, col, pic.getHeight()-1, col);
		Pixel[] pixcol=new Pixel[pic.getHeight()];
		for(int i=0;i<pixcol.length;++i)
			pixcol[i]=rowcol[i][0];
		return pixcol;
	}
	/**
	 * 
	 * @param pic
	 * @param colfrom
	 * @param colto
	 * @return Pixels in column-row order
	 */
	public static Pixel[][] getCols(Picture pic, int colfrom, int colto)
	{
		Pixel[][] rowcol = getPixels(pic, 0, colfrom, pic.getHeight()-1, colto);
		Pixel[][] colrow=new Pixel[rowcol[0].length][rowcol.length];
		for (int x = 0; x < rowcol[0].length; x++)
			for (int y = 0; y < rowcol.length; y++)
				colrow[x][y] = rowcol[y][x];
		return colrow;
	}
	
	private static Picture modifyPicture(Picture pic, String f, Object value)
	{
		try
		{
			for (Pixel[] rowArray : pic.getPixels2D())
			{
				for (Pixel pixelObj : rowArray)
				{
					modifyPixel(pixelObj, f, value);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return pic;
	}
	private static Pixel modifyPixel(Pixel pixelObj, String f, Object value)
	{
		switch(f)
		{
			case "setAlpha":
				pixelObj.setAlpha((int) value);
				break;
			case "setRed":
				pixelObj.setRed((int) value);
				break;
			case "setGreen":
				pixelObj.setGreen((int) value);
				break;
			case "setBlue":
				pixelObj.setBlue((int) value);
				break;
			case "setColor":
				if(value!=null && Color.class.equals(value.getClass()))
					pixelObj.setColor((Color) value);
				else
					pixelObj.setColor(new Color((int) value));
				break;
			case "grayscale":
				int avg = ((Double)pixelObj.getAverage()).intValue();
				pixelObj.setRed(avg);
				pixelObj.setBlue(avg);
				pixelObj.setGreen(avg);
				break;
		}
		return pixelObj;
	}
	private static int max(int... b)
	{
		int m=b[0];
		for(int n:b)
			m=Math.max(m, n);
		return m;
	}
}
