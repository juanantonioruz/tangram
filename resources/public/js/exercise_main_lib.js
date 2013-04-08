var circles, svg;
var w=1500;
var h=800;
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

//  require([
//  "tree_dates_visualization","rects_chain_visualization","ddd_lib"],
//  function(tree_dates_visualization,rects_chain_visualization, _ddd_lib) {

  require([ "rects_chain_visualization","ddd_lib"], function(rects_chain_visualization, _ddd_lib) {
    ddd_lib=_ddd_lib;
    run_visualization();
  });
};

// the visualization have already started (or can do it) in the loading external files/resources [require.js]
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
