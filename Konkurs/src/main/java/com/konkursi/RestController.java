package com.konkursi;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;


public class RestController extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		//rest configuration
		restConfiguration().component("netty-http").host("localhost").port(8080).enableCORS(true).bindingMode(RestBindingMode.off);
		
		//rest api
		rest("/candidate").get("/getAll").to("direct:getAll");	
		rest("/candidate").get("find-rejected").to("direct:find-rejected");
		rest("/candidate").get("find-accepted").to("direct:find-accepted");
		rest("/candidate").get("save-rejected").to("direct:save-rejected");
		rest("/candidate").get("save-accepted").to("direct:save-accepted");
		rest("/candidate").get("save-all").to("direct:save-all");
				
	}
}
