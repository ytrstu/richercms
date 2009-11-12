package com.esial.richercms.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Tree;


public class RichercmsTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.esial.richercms.Richercms";
	}
	
	public void testCreateTree(){
		Tree tree=new Tree();
		assertEquals(0, tree.getItemCount());
	}

}
