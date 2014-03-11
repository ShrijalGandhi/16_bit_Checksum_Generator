/*
	CHECKSUM 16 BIT GENERATOR
*/

import java.io.*;

class Arrays
{
	char a[];

	Arrays()
	{
	a=new char[4];
	}
}

class DataOfChecksum
{
	static InputStreamReader r;
	static BufferedReader input;

	static String str;
	static int size;
	static int no_of_arrays_reqd;
	static char checksum[];
	static Arrays representation[];


	DataOfChecksum()throws IOException
	{
	r=new InputStreamReader(System.in);
	input=new BufferedReader(r);

		System.out.println("Enter the string for which checksum has to be generated");
		str=input.readLine();
		checksum=new char[4];
		size=str.length();

			if(size%2==0)   		//if size is even we need size/2 arrays to represent it in hex
			{				//eg forouzan size=8....arrays reqd=4
			no_of_arrays_reqd=size/2;			
			representation=new Arrays[no_of_arrays_reqd]; 
			}
			else
			{
			no_of_arrays_reqd=(size/2)+1;	
			representation=new Arrays[no_of_arrays_reqd];	//if size odd one more array reqd
			}




		initializeArrays();
	}

	void initializeArrays()throws IOException
	{
	char temp[]=new char[size];
	temp=str.toCharArray();

	String concatenator;
		for(int i=0;i<no_of_arrays_reqd;i++)
		{
		representation[i]=new Arrays();		//very imp statement..since we allocate size in constructor 
							//but we do not create the object	
		
		concatenator=getHexValue((i*2),(i*2)+1,temp);

		representation[i].a[0]=concatenator.charAt(0);
		representation[i].a[1]=concatenator.charAt(1);
		representation[i].a[2]=concatenator.charAt(2);
		representation[i].a[3]=concatenator.charAt(3);
		}

		calChecksum();


	
	}

	void calChecksum()throws IOException
	{
	int temp=0;
	int carry=0;
	String nonWrapped=new String();
	String t=new String();
	String tempo;
	String d;

		for(int i=no_of_arrays_reqd-1;i>=0;i--)
		{
		temp=0;
			for(int j=no_of_arrays_reqd-1;j>=0;j--)
			temp+=Integer.parseInt(Character.toString(representation[j].a[i]),16);
			temp+=carry;

			try
			{
			tempo=Integer.toHexString(temp);
			nonWrapped+=tempo.charAt(1);
			t+=tempo.charAt(0);
			carry=Integer.parseInt(t);
			t="";
			}
			catch(StringIndexOutOfBoundsException e)	//in case carry is zero and string is of size 1
			{						//ie addition is within 16
				tempo=Integer.toHexString(temp);
				nonWrapped+=tempo.charAt(0);
				carry=0;
			}
		
		/*
		System.out.println("carry " +carry);	
		System.out.println(tempo);			DEBUGGING	
		System.out.println("Press");	
		d=input.readLine();				
		*/

		
		}


		System.out.println(nonWrapped);

		checksum(nonWrapped);
		wrapper(nonWrapped,carry);
		complement();

	}

	String getHexValue(int i,int j,char temp[])throws IOException
	{
		String t1=new String();
		String t2=new String();
		String t;
		int hex;

		hex=(int)temp[i];
		t1=Integer.toHexString(hex);

		if(j<size)	//if size is odd then last element size is greater and is null
		hex=(int)temp[j];
		t2=Integer.toHexString(hex);

		t=t1+t2;
		t=t.toUpperCase();

	return t;
	}

	void checksum(String nonWrapped)
	{
	int j=0;
		for(int i=3;i>=0;i--)
		checksum[i]=nonWrapped.charAt(j++);
	}

	void wrapper(String nonWrapped,int carry)
	{
	int x;
	String temp;

	temp=Character.toString(nonWrapped.charAt(0));
	x=Integer.parseInt(temp,16);
	x+=carry;

	temp=Integer.toHexString(x);
	temp=temp.toUpperCase();

	checksum[3]=temp.charAt(0);

	}

	void complement()
	{
	String temp;
	int x;
		for(int i=3;i>=0;i--)
		{
		temp=Character.toString(checksum[i]);
		x=Integer.parseInt(temp,16);
		x=15-x;
		temp=Integer.toHexString(x);
		checksum[i]=temp.charAt(0);
		}

	System.out.println("---------------CHECKSUM----------------");

	display();
	}

	void display()
	{	
		for(int i=0;i<4;i++)
		System.out.print(checksum[i]);
		
	}
}

class Checksum
{
	public static void main(String args[])throws IOException
	{
	DataOfChecksum d=new DataOfChecksum();
	}
}