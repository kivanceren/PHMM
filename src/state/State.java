package state;

import java.util.ArrayList;

public class State {
	
	public int matchPay,matchPayda;
	public int insertPay,insertPayda;
	public int deletePay,deletePayda;
	public ArrayList<String> matchString,insertString,deleteString;
	public ArrayList<String> strings;
    public StringBuilder karakterler;
	public State() {
		// TODO Auto-generated constructor stub
		matchPay=matchPayda=insertPay=insertPayda=deletePay=deletePayda=0;
		matchString=new ArrayList<String>();
		insertString=new ArrayList<String>();
		deleteString=new ArrayList<String>();
		strings=new ArrayList<String>();
		karakterler=new StringBuilder("");
	}
}
