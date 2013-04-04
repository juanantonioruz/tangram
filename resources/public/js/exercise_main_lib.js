var circles, svg;
var w=1500;
var h=1000;
var internal_padding=5;


var ex, ddd_lib, domain_lib, viewHelper;

/**
   setup function; user for loading external and modulated resources
   using require.js 
**/
function my_d3(){
  require.config({
    urlArgs: "bust=" + (new Date()).getTime()
  });

  require([ "model", "ddd_lib", "domain_lib", "helper_view"], function(_exp, d, _domain, _viewHelper) {
    ex=_exp;
    ddd_lib=d;
    domain_lib=_domain;
    viewHelper=_viewHelper;
    run_visualization();
  });

};


function run_visualization(){
  
  
  d3.select("svg").attr("width", w).attr("height", h).style("background", "gray");


  
  


}

// using requirejs plugin to call the init function
require(['domReady'], function (domReady) {
  domReady(function () {

    //This function is called once the DOM is ready.
    //It will be safe to query the DOM and manipulate
    //DOM nodes in this function.
    my_d3();

  });
});
