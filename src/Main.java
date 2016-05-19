import java.awt.Color;
import java.util.ArrayList;

import picTools.PhotoMaker;
import picTools.Picture;

public class Main
{
	public static void main(String[] args)
	{
		short combonum=3;
		doCombo(combonum);
	}
	@SuppressWarnings("unchecked")
	public static void doCombo(final int combotimes)
	{
		combo(combotimes-1, new Picture[combotimes], new String[combotimes], combotimes, new long[]{0, (long) Math.pow(colors.length, combotimes)}, 
				new ArrayList[]{new ArrayList<String>()});
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
