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
import java.util.Set;

/**
 * Used to create YAML view.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 1.0
 */
public class DiagnosticUtil
{

	private enum RENDER_TYPE 
	{
		TEXT, IMAGE
	};
	
	private static final String INNER_TABLE_HEADER = 
		"<tr>\n<td colspan='4' class='yaml'>\n<table width=\"100%\" class=\"yaml\">";
	
	private static final String INNER_TABLE_FOOTER = 
		"</table>\n</td>\n</tr>";
	
	private DiagnosticUtil()
	{
		super();
	}
	
	public static String createDiagnosticTable( Map<String, Object> diagnostic )
	{
		
		StringBuilder sb = new StringBuilder();
		
		createDiagnosticTableRecursively( diagnostic, sb, 1 ); // 1 is the first depth
		
		return sb.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createDiagnosticTableRecursively( Map<String, Object> diagnostic, StringBuilder sb, int depth )
	{
		
		sb.append( INNER_TABLE_HEADER );
		
		RENDER_TYPE renderType = getMapEntriesRenderType( diagnostic ); 
		
		for (Entry<String, Object> entry : diagnostic.entrySet() )
		{
			String key = entry.getKey();
			Object value = entry.getValue();
			sb.append("<tr>");
			
			for( int i =0 ; i < depth ; ++i )
			{
				sb.append( "<td width='5%' class='hidden'> </td>" );
			}
			sb.append( "<td style=\"width: auto;\">"+key+"</td>" );
			if ( value instanceof java.util.Map )
			{
				sb.append( "<td> </td>" );
				createDiagnosticTableRecursively ( (java.util.Map)value, sb, (depth+1));
			}
			else
			{
				sb.append( "<td>"+ getRenderedValue( key, value, renderType ) +"</td>" );
			}
			sb.append( "</tr>" );			
		}
		
		sb.append( INNER_TABLE_FOOTER );
	}

	/**
	 * @param diagnostic
	 * @return
	 */
	private static RENDER_TYPE getMapEntriesRenderType(
			Map<String, Object> diagnostic )
	{
		RENDER_TYPE renderType = RENDER_TYPE.TEXT;
		final Set<String> keys = diagnostic.keySet();
		if ( keys.contains("File-Type") && (keys.contains("File-Location") || keys.contains("File-Content") ))
		{
			renderType = RENDER_TYPE.IMAGE;
		}
		return renderType;
	}
	
	/**
	 * @param key
	 * @param value 
	 * @param renderType 
	 * @return
	 */
	private static String getRenderedValue( String key, Object value, RENDER_TYPE renderType )
	{
		switch( renderType )
		{
		case IMAGE:
			if( key.equals("File-Content") )
			{
				return "Base64 content suppressed!";
			}
			else
			{
				return value.toString();
			}
		default:
		case TEXT:
			return value.toString();
		}
	}
	
}
