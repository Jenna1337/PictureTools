package picTools;

import java.awt.Color;
import java.util.ArrayList;

public class PhotoMaker
{
	public static final String filepath="";
	public static final Color transparent = new Color(0b11111111000000000000000000000000, true);
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
	public static Picture merge(final boolean bitmask, Picture... pics)
	{
		//final boolean bitmask=false; 
		int[] mha=new int[pics.length];
		int[] mwa=new int[pics.length];
		for(int i=0;i<pics.length;++i)
		{
			mha[i]=pics[i].getHeight();
			mwa[i]=pics[i].getWidth();
		}
		int mh=max(mha),mw=max(mwa);
		Picture cop = new Picture(mw, mh, transparent);
		
		cop.getPixels2D();
		for(int row=0;row<cop.getHeight(); ++row)
		{
			for (int col=0;col<cop.getWidth(); ++col)
			{
				ArrayList<Integer> colorlist=new ArrayList<Integer>();
				for(int pi=0;pi<pics.length;++pi)
					colorlist.add(pics[pi].getPixel(col, row).getColor().getRGB());
				
				//from http://stackoverflow.com/a/1165348
				long sum1=0,sum2=0;
				for (int color : colorlist)
				{
					sum1+= color    &0x00FF00FF;
					sum2+=(color>>8)&0x00FF00FF;
				}
				int value=0;
				final int size=colorlist.size();
				value|=bitmask?0xFF:(((sum1&0xFFFF)/size)&0xFF);
				value|=(((sum2&0xFFFF)/size)&0xFF)<<8;
				value|=((((sum1>>=16)&0xFFFF)/size)&0xFF)<<16;
				value|=((((sum2>>=16)&0xFFFF)/size)&0xFF)<<24;
				
				//update the picture with the int value
				cop.setBasicPixel(row,col,value);
			}
		}
		return cop;
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
