import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.Format;
import java.util.ArrayList;
import java.util.Formatter;

import state.*;


public class PHMM {
	
	public static void main(String[] args) {
		
		ArrayList<Dizilimler> stringler=new ArrayList<Dizilimler>();
	    boolean match_state_matris[]=null;
	    State match[],insert[],delete[];
	    File file = new File("dosya.txt");
		FileReader fileReader = null;
		BufferedReader br;
		String line;
		int say=0,satir=1, son_State=1;
		try {
		 	  fileReader = new FileReader(file);
			  br = new BufferedReader(fileReader);
			  line=br.readLine();
			  say=Integer.parseInt(line);
			  while ((line = br.readLine()) != null) {
									if(satir!=(say+1))
									{
										stringler.add(new Dizilimler(line,0,0));														 
										satir++;
									}
								        else
									{
										match_state_matris=new boolean[line.length()];
										for(int i=0;i<line.length();i++)
										{
										    if(line.charAt(i)=='*')
										    {
											match_state_matris[i]=true;
											son_State++;
																	
										     }
										     else match_state_matris[i]=false;
										}
									}
												     
			  					  }//while end.
		    br.close();
     	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  //State'ler Yaratılıyor.
	  match=new State[son_State];
	  insert=new State[son_State];
	  delete=new State[son_State];
	 for(int i=0;i<son_State;i++)
	 {
		 match[i]=new State();
		 insert[i]=new State();
		 delete[i]=new State();
	 }
	  //Başlangıç koşulu için tüm stringler match[0] yani begin'e atanıyor.
	 for(int i=0;i<stringler.size();i++)
	 {
		 match[0].strings.add(stringler.get(i).string);
	 }
	  //for(int i=0;i<match[0].strings.size();i++) System.out.println(match[0].strings.get(i));
	 
	  int sutun_no=0;
	  int bir_sonraki_korunmus=0;
	  boolean ilk_insert[];
	  int yedek_sutun_no=0;
	  ilk_insert=new boolean[stringler.size()];
	  
	  
	  while(!kontrol(stringler,son_State))
	  {
		  for(int i=sutun_no;i<match_state_matris.length;i++){if(match_state_matris[i]){bir_sonraki_korunmus=i; break;}}
		  if(sutun_no>bir_sonraki_korunmus) 
		  {
			  sutun_no=bir_sonraki_korunmus;
			 
			  break;
		  }
		  
		  if(match_state_matris[sutun_no])//Eger korunmuş alansa o bölge
		  {
			  
			  for(int i=0 ; i<stringler.size();i++)//Tüm stringler için o sutunu dolaş
			  {  
				  ilk_insert[i]=true;
				  if(stringler.get(i).string.charAt(sutun_no)!='-') // Eger i.stringin korunmuş sutun daki karakteri - degilse
				  {
					  
					  if(stringler.get(i).state==0) //Eger string matchte ise matche geçer.
					  { 
						  match[stringler.get(i).state_indis+1].matchPay++;
						  match[stringler.get(i).state_indis+1].matchPayda=match[stringler.get(i).state_indis].strings.size();
						  match[stringler.get(i).state_indis+1].matchString.add(stringler.get(i).string);
						  match[stringler.get(i).state_indis+1].strings.add(stringler.get(i).string);
						 match[stringler.get(i).state_indis+1].karakterler.append(stringler.get(i).string.charAt(sutun_no));
					 }
					  else if(stringler.get(i).state==1) //Eger insertten matche geciyorsa
					  {
						  match[stringler.get(i).state_indis+1].insertPay++;
						  match[stringler.get(i).state_indis+1].insertPayda=insert[stringler.get(i).state_indis].insertPayda;
						  match[stringler.get(i).state_indis+1].insertString.add(stringler.get(i).string);
						  match[stringler.get(i).state_indis+1].strings.add(stringler.get(i).string);
						  match[stringler.get(i).state_indis+1].karakterler.append(stringler.get(i).string.charAt(sutun_no));
					  }
					  else if(stringler.get(i).state==2) //eger delete den matche geciyorsa
					  {
						  match[stringler.get(i).state_indis+1].deletePay++;
						  match[stringler.get(i).state_indis+1].deletePayda=delete[stringler.get(i).state_indis].strings.size();
						  match[stringler.get(i).state_indis+1].deleteString.add(stringler.get(i).string);
						  match[stringler.get(i).state_indis+1].strings.add(stringler.get(i).string);
						  match[stringler.get(i).state_indis+1].karakterler.append(stringler.get(i).string.charAt(sutun_no));
					  }
					  stringler.get(i).state=0;
					  stringler.get(i).state_indis++;
				  }
				  else //Alan korunmuş fakat '-' karakteri var bu sebeple delete state'ine geçecek
				  {
					  if(stringler.get(i).state==0) //Eger string matchte ise matche geçer.
					  {
						  delete[stringler.get(i).state_indis+1].matchPay++;
						  delete[stringler.get(i).state_indis+1].matchPayda=match[stringler.get(i).state_indis].strings.size();
						  delete[stringler.get(i).state_indis+1].strings.add(stringler.get(i).string);
						 // delete[stringler.get(i).state_indis+1].karakterler.add(stringler.get(i).string.charAt(sutun_no));
					 }
					  else if(stringler.get(i).state==1)
					  {
						  delete[stringler.get(i).state_indis+1].insertPay++;
						  delete[stringler.get(i).state_indis+1].insertPayda=insert[stringler.get(i).state_indis].strings.size();
						  delete[stringler.get(i).state_indis+1].strings.add(stringler.get(i).string);
						 // delete[stringler.get(i).state_indis+1].karakterler.add(stringler.get(i).string.charAt(sutun_no));
					  }
					  else if(stringler.get(i).state==2)
					  {
						  delete[stringler.get(i).state_indis+1].deletePay++;
						  delete[stringler.get(i).state_indis+1].deletePayda=delete[stringler.get(i).state_indis].strings.size();
						  delete[stringler.get(i).state_indis+1].strings.add(stringler.get(i).string);
						  //delete[stringler.get(i).state_indis+1].karakterler.add(stringler.get(i).string.charAt(sutun_no));
					  }
					  stringler.get(i).state=2;
					  stringler.get(i).state_indis++;
				  }
			  }
			 sutun_no++; 
		  }
		  else //O sütun korunmamışsa
		  {int sayac=0;
			   for(int i=0 ;i<stringler.size();i++)
			   {
				   
				   yedek_sutun_no=sutun_no;
				   while(yedek_sutun_no<=bir_sonraki_korunmus)
				   {  
					   if(yedek_sutun_no!=bir_sonraki_korunmus) 
					   {
						   if(stringler.get(i).string.charAt(yedek_sutun_no)=='-') //Eger ilgili satırda - varsa ve korunmamıştayiz devam ederiz.
						   {
							 
						   }
						   else //Eger korunmamısssa ve harf gelmişse oraya 
						   { 
							   sayac++;
							   if(ilk_insert[i]) //Daha onceki durum delete veya matchmis
							   {
								   if(stringler.get(i).state==0) //Durum match ise
								   {
									   insert[stringler.get(i).state_indis].matchPay++;
									   insert[stringler.get(i).state_indis].matchPayda=match[stringler.get(i).state_indis].strings.size();
									   insert[stringler.get(i).state_indis].matchString.add(stringler.get(i).string);
									   insert[stringler.get(i).state_indis].strings.add(stringler.get(i).string);
									   if(stringler.get(i).string.charAt(sutun_no)!='-')
									   //insert[stringler.get(i).state_indis].karakterler.app(stringler.get(i).string.charAt(sutun_no));
									   ilk_insert[i]=false;
								   }
								   else // Durum delete ise
								   {
									   insert[stringler.get(i).state_indis].deletePay++;
									   insert[stringler.get(i).state_indis].deletePayda=delete[stringler.get(i).state_indis].strings.size();
									   insert[stringler.get(i).state_indis].deleteString.add(stringler.get(i).string);
									   insert[stringler.get(i).state_indis].strings.add(stringler.get(i).string);
								   }
								   
							   }
							   else //ilk insert degilse
							   {
								 
								   insert[stringler.get(i).state_indis].insertPay++;
								   //insert[stringler.get(i).state_indis].insertPayda++;
								   
							   }
							   stringler.get(i).state=1;
							  
						   }
						   yedek_sutun_no++;
						   
					   }
					   else
					   {  
						   
						   yedek_sutun_no++;
					   }
					  
				   }
				   insert[stringler.get(i).state_indis].insertPayda=sayac;
			   }
			   sutun_no=bir_sonraki_korunmus;
			  
		  }
		
	  }
	//System.out.print(delete[4].strings+" "+match.length);	
	  int max_boyut=0;
	 for(int i=2;i<match.length;i++)
	 {
		 max_boyut=Math.max(match[i-1].karakterler.length(), match[i].karakterler.length());
	 }
	 int olasilik[][]=new int[match.length][26];
	 
	
	
	  for(int i=1;i<match.length;i++)
	  {
		  for(int j=65;j<=90;j++)
		  {
			  for(int k=0;k<match[i].karakterler.length();k++)
			  {
				  if((char)j==match[i].karakterler.charAt(k)) olasilik[i][j-65]++;
			  }
		  }
	  }

	  //for(int i=65;i<90;i++) System.out.print((char)i+" "); System.out.println();
	  for(int i=1;i<match.length;i++) {
		  System.out.print("Match("+i+"): ");
		  for(int j=0;j<25;j++) if(olasilik[i][j]!=0) System.out.print((char)(j+65)+"("+olasilik[i][j]+"/"+match[i].karakterler.length()+") ");
		  if(match[i].matchPay==0) System.out.print("  Matchten Gelme:0");
		  else System.out.print("  Matchten Gelme: "+match[i].matchPay+"/"+match[i].matchPayda);
		  
		  if(match[i].insertPay==0) System.out.print("  Insertten Gelme:0");
		  else System.out.print("  Insertten Gelme: "+match[i].insertPay+"/"+match[i].insertPayda);
		 
		  if(match[i].deletePay==0) System.out.print("  Deleteden Gelme:0");
		  else System.out.print("  Delete Gelme: "+match[i].deletePay+"/"+match[i].deletePayda);
		  System.out.println();
		  }
	 System.out.println("\n\n");
	 
	 for(int i=1;i<match.length;i++) {
		  System.out.print("Insert("+i+"): ");
		  System.out.print("Tüm Olasılıklar:1/25");
		  if(insert[i].matchPay==0) System.out.print("  Matchten Gelme:0");
		  else System.out.print("  Matchten Gelme: "+insert[i].matchPay+"/"+insert[i].matchPayda);
		  
		  if(insert[i].insertPay==0) System.out.print("  Insertten Gelme:0");
		  else System.out.print("  Insertten Gelme: "+insert[i].insertPay+"/"+insert[i].insertPayda);
		 
		  if(insert[i].deletePay==0) System.out.print("  Deleteden Gelme:0");
		  else System.out.print("  Delete Gelme: "+insert[i].deletePay+"/"+insert[i].deletePayda);
		  System.out.println();
		  }
	 
	 	System.out.println("\n\n");
	 
	 for(int i=1;i<match.length;i++) {
		  System.out.print("Delete("+i+"): ");
		
		  if(delete[i].matchPay==0) System.out.print("  Matchten Gelme:0");
		  else System.out.print("  Matchten Gelme: "+delete[i].matchPay+"/"+delete[i].matchPayda);
		  
		  if(delete[i].insertPay==0) System.out.print("  Insertten Gelme:0");
		  else System.out.print("  Insertten Gelme: "+delete[i].insertPay+"/"+delete[i].insertPayda);
		 
		  if(delete[i].deletePay==0) System.out.print("  Deleteden Gelme:0");
		  else System.out.print("  Delete Gelme: "+delete[i].deletePay+"/"+delete[i].deletePayda);
		  System.out.println();
		  }
	}
	
	
	
	
	public static boolean kontrol(ArrayList<Dizilimler> dizilim,int sonState)
	{
		 for(int i=0;i<dizilim.size();i++)
		 {
			 if(dizilim.get(i).state==0 && dizilim.get(i).state_indis==sonState)  ;
			 else return false;
		 }
		
		 return true;
	}

}
