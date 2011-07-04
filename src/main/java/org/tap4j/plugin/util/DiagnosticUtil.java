/* 
 * The MIT License
 * 
 * Copyright (c) 2010 Bruno P. Kinoshita <http://www.kinoshita.eti.br>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.tap4j.plugin.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 1.0
 */
public class DiagnosticUtil
{

	private static final String INNER_TABLE_HEADER = 
		"<tr>\n<td class=\"yaml\" colspan=\"4\">\n<table width=\"98%\" style=\"border-collapse: collapse; display: box; border: 0px solid; margin: 4px auto; padding: 0px;\">";
	
	private static final String INNER_TABLE_FOOTER = 
		"</table>\n</td>\n</tr>";
	
	private DiagnosticUtil()
	{
		super();
	}
	
	public static String createDiagnosticTable( Map<String, Object> diagnostic )
	{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append( INNER_TABLE_HEADER );
		
		createDiagnosticTableRecursively( diagnostic, sb );
		
		sb.append( INNER_TABLE_FOOTER );
		
		return sb.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createDiagnosticTableRecursively( Map<String, Object> diagnostic, StringBuilder sb )
	{
		for (Entry<String, Object> entry : diagnostic.entrySet() )
		{
			String key = entry.getKey();
			Object value = entry.getValue();
			
			sb.append( "<tr>" );
			sb.append( "<td>"+key+"</td>" );
			if ( value instanceof java.util.Map )
			{
				sb.append( "<td><table width=\"98%\" style=\"border-collapse: collapse; display: box; border: 0px solid; margin: 4px auto; padding: 0px;\">" );
				createDiagnosticTableRecursively ( (java.util.Map)value, sb );
				sb.append( "</table></td>" );
			}
			else
			{
				sb.append( "<td>"+value+"</td>" );
			}
			sb.append( "</tr>" );			
		}
	}
	
}
