
class WebApi {

  constructor (username, password, enpoint) {
	this.username = username ? username : "testelkron";
	this.password = password ? password : "elkrontest";
	this.endpoint = enpoint ? enpoint : "https://www.cloud.elkron.com/tool";
  }

  get remote () {
	var r = "";
	r += this.username ? this.username : "";
	r += this.password ? ":***" : "";
	r += "@";
	r += this.endpoint;
	return r;
  }

  login (success, error) {
        plugins.elkronApiPlugin.doRequest (
	    {
            	url: this.endpoint + "/index.php",
            	type: 'POST',
	    	contentType: 'application/x-www-form-urlencoded',
            	data: "httpd_username=" + this.username + "&httpd_password=" + this.password
	    },
            function(data){ 
		if (success) success();
	    },
	    function() {
		if (error) fail();
	    }
	);
  }     

}


