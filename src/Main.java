import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import picTools.Iterator2D;
import picTools.PhotoMaker;
import picTools.Picture;
import picTools.Pixel;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		ArrayList<String> names1 = new ArrayList<String>();
		ArrayList<String> names2 = new ArrayList<String>();
		
		final String delim=prompt("delim? ", false);
		final String path =prompt("Path? ", true);
		final String newname =prompt("New name? ", true);
		boolean mask=true;
		
		BufferedReader f = new BufferedReader(new java.io.FileReader(path+"_Layouts.txt"));
		String line="";
		boolean mysec=true;
		while((line=f.readLine())!=null)
		{
			if(mysec)
			{
				if(line.equalsIgnoreCase("\\next"))
					mysec=false;
				else
					names1.add(path+line+".png");
			}
			else
				names2.add(line);
		}
		f.close();
		
		
		for(String part1:names1)
		{
			String[] filenames=new String[names2.size()];
			for(int i=0;i<names2.size();++i)
				filenames[i]=part1.replace(delim, names2.get(i));
			try
			{
				PhotoMaker.merge(mask, filenames).write(part1.replace(delim,newname).replace(".png", "2.png"));
				System.out.println("Wrote "+part1.replace(delim,newname));
			}
			catch(ArrayIndexOutOfBoundsException aioobe)
			{
				System.err.println(part1.replace(delim,newname));
				aioobe.printStackTrace();
			}
		}
	}
	public static String prompt(String outtext, boolean inputonnextline) throws IOException
	{
		System.out.print(outtext+(inputonnextline?System.lineSeparator():""));
		BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
		return reader.readLine();
	}
	public static void transparencyblendtest()
	{
		Picture redleft=new Picture(10,10);
		Picture bluetop=new Picture(10,10);
		redleft=PhotoMaker.setAlpha(redleft, 0);
		bluetop=PhotoMaker.setAlpha(bluetop, 0);
		Iterator2D<Pixel> cols = new Iterator2D<Pixel>(PhotoMaker.getCols(redleft, 0, redleft.getWidth()/2-1));
		for(Pixel p:cols)
			p.setColor(Color.RED);
		Iterator2D<Pixel> rows = new Iterator2D<Pixel>(PhotoMaker.getRows(bluetop, 0, bluetop.getHeight()/2-1));
		for(Pixel p:rows)
			p.setColor(Color.BLUE);
		redleft.write("redleft");
		bluetop.write("bluetop");
		
		Picture pic1=PhotoMaker.merge(false,redleft,bluetop);
		pic1.write("merge1");
		Picture pic2=PhotoMaker.merge(true,redleft,bluetop);
		pic2.write("merge2");
		System.out.println(Integer.toHexString(redleft.getBasicPixel(7, 7)));
		System.out.println(Integer.toHexString(bluetop.getBasicPixel(7, 7)));
		System.out.println(Integer.toHexString(pic1.getBasicPixel(7, 7)));
		System.out.println(Integer.toHexString(pic2.getBasicPixel(7, 7)));
	}
	@SuppressWarnings("unchecked")
	public static void doCombo(final int combotimes)
	{
		if(combotimes>3)
			throw new IllegalArgumentException("illegal call to doCombo() with value "+combotimes+".");
		combo(combotimes-1, new Picture[combotimes], new String[combotimes], combotimes, new long[]{0, (long) Math.pow(colors.length, combotimes)}, 
				new java.util.ArrayList[]{new java.util.ArrayList<String>()});
	}
	private static void combo(int combosleft, Picture[] colorpics, String[] names, final int totalcombos, long[] stats, java.util.ArrayList<String>[] cmbs)
	{
		if(combosleft<0)
			return;
		for(int ci=0;ci<colors.length;++ci)
		{
			names[combosleft] = Colors.values()[ci].name();
			colorpics[combosleft] = new Picture(10,10);
			PhotoMaker.setColor(colorpics[combosleft], colors[ci]);
			
			if(combosleft!=0)
				combo(combosleft-1, colorpics, names, totalcombos, stats, cmbs);
			
			Picture pic=PhotoMaker.merge(false,colorpics);
			String filename=names[0];
			for(int i=1;i<names.length;++i)
				filename+="+"+names[i];
			if(!cmbs[0].contains(filename))
			{
				cmbs[0].add(filename);
				String fn = "combos/"+totalcombos+"/"+filename;
				System.out.println("Writing file "+(stats[0]+=1)+"/"+stats[1]+"\""+fn+"\"");
				pic.write(fn);
			}
		}
	}
	public static void test()
	{
		for(int ci1=0;ci1<colors.length;++ci1)
		{
			String clr1nme = Colors.values()[ci1].name();
			Color  clr1clr = colors[ci1];
			
			Picture pic1 = new Picture(10,10);
			PhotoMaker.setColor(pic1, clr1clr);
			
			for(int ci2=0;ci2<colors.length;++ci2)
			{
				String clr2nme = Colors.values()[ci2].name();
				Color  clr2clr = colors[ci2];
				
				Picture pic2 = new Picture(10,10);
				PhotoMaker.setColor(pic2, clr2clr);
				
				Picture pic=PhotoMaker.merge(false,pic1,pic2);
				pic.write("combos/2/"+clr1nme+"+"+clr2nme);
			}
		}
	}
	static Color[] colors={Color.PINK, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, 
			Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.BLACK};
	static enum Colors{PINK, RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, MAGENTA, 
		WHITE, LIGHT_GRAY, GRAY, DARK_GRAY, BLACK;}
}
