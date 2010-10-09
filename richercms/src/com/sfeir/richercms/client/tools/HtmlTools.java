package com.sfeir.richercms.client.tools;

public class HtmlTools {

	public static String changeUrl(String html) {
		if (html==null) {
			return null;
		}
		
		StringBuilder res = new StringBuilder();
		
		int longueurHref = "<a href=\"".length();
		int curPos = 0;
		int posHref = html.indexOf("<a href=\"", curPos);
		while (posHref>-1) {
			int posHrefHttp = html.indexOf("<a href=\"http", curPos);
			
			res.append(html.substring(curPos, posHref+longueurHref));
			if (posHref!=posHrefHttp) {
				res.append("/site/");
			}
			
			curPos = posHref + longueurHref;
			posHref = html.indexOf("<a href=\"", curPos);
		}
		
		return res.append(html.substring(curPos)).toString();
	}

}
