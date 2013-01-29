package org.jmanderson.subbing.dataobjects;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Extends ArrayList so that we can override the default
 * toString() action.
 */
public class SubbingArrayList extends ArrayList
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608340159057563951L;

	public SubbingArrayList(Collection c)
	{
		super(c);
	}

	public SubbingArrayList()
	{
		super();
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		int lastIndex = this.size() - 1;
		if (lastIndex >= 0)
		{
			for (int i = 0; i < lastIndex; i++)
				sb.append((String) this.get(i)).append(", ");
			sb.append((String) this.get(lastIndex));
		}
		return sb.toString();
	}
}
