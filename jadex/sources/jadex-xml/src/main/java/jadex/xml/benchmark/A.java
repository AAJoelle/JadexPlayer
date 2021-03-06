package jadex.xml.benchmark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A
{
	protected int i;
	
	protected String s;
	
	protected B b;
	
	protected List bs;
	
	protected Integer[] ints;
	
	public A()
	{
	}
	
	public A(int i, String s, B b, B[] bs)
	{
		this.i = i;
		this.s = s;
		this.b = b;
		if(bs!=null)
		{
			this.bs = new ArrayList();
			for(int j=0; j<bs.length; j++)
			{
				this.bs.add(bs[j]);
			}
		}
	}

	public int getI()
	{
		return this.i;
	}

	public void setI(int i)
	{
		this.i = i;
	}

	public String getS()
	{
		return this.s;
	}

	public void setS(String s)
	{
		this.s = s;
	}

	public B getB()
	{
		return this.b;
	}

	public void setB(B b)
	{
		this.b = b;
	}

	public B[] getBs()
	{
		return (B[])(this.bs==null? null: this.bs.toArray(new B[0]));
	}

	public void setBs(B[] bs)
	{
		this.bs = new ArrayList();
		for(int j=0; j<bs.length; j++)
		{
			this.bs.add(bs[j]);
		}
	}
	
	public void addB(B b)
	{
		if(bs==null)
			bs = new ArrayList();
		bs.add(b);
	}
	
	/**
	 *  Get the ints.
	 *  @return the ints.
	 */
	public Integer[] getInts()
	{
		return ints;
	}

	/**
	 *  Set the ints.
	 *  @param ints The ints to set.
	 */
	public void setInts(Integer[] ints)
	{
		this.ints = ints;
	}

	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((bs == null) ? 0 : bs.hashCode());
		result = prime * result + i;
		result = prime * result + Arrays.hashCode(ints);
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		A other = (A)obj;
		if(b == null)
		{
			if(other.b != null)
				return false;
		}
		else if(!b.equals(other.b))
			return false;
		if(bs == null)
		{
			if(other.bs != null)
				return false;
		}
		else if(!bs.equals(other.bs))
			return false;
		if(i != other.i)
			return false;
		if(!Arrays.equals(ints, other.ints))
			return false;
		if(s == null)
		{
			if(other.s != null)
				return false;
		}
		else if(!s.equals(other.s))
			return false;
		return true;
	}

	public String toString()
	{
		return "A [i=" + i + ", s=" + s + ", b=" + b + ", bs=" + bs + ", ints="
				+ Arrays.toString(ints) + "]";
	}
	
}