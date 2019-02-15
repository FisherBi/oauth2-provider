
package com.fisherbi.server.models;

import java.util.Map;

public interface Request {


	public String getParameter(String name);


	public Map<String, String> getParameterMap();
	

	public String getHeader(String name);

}
