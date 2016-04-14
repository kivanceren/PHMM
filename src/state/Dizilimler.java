package state;

import java.util.ArrayList;

public class Dizilimler {

	public String string;
	public int state;//State=0 Match State=1 Insert ve State=2 Delete State
	public int state_indis;
	
	public Dizilimler() {
		// TODO Auto-generated constructor stub
		string=new String();
		state=0;
		state_indis=0;
	}
	public Dizilimler(String line,int st,int st_indis)
	{
		string=line;
		state=st;
		state_indis=st_indis;
	}
}
