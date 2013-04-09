var circles, svg;
var w=1500;
var h=800;
var internal_padding=5;


var ex, ddd_lib, domain_lib, viewHelper,modern;

/**
   setup function; user for loading external and modulated resources
   using require.js 
**/
function my_d3(months){
  
  require.config({
    urlArgs: "bust=" + (new Date()).getTime()
  });

  //  require([
  //  "tree_dates_visualization","rects_chain_visualization","ddd_lib"],
  //  function(tree_dates_visualization,rects_chain_visualization, _ddd_lib) {

  require([ "rects_chain_visualization","ddd_lib"], function(rects_chain_visualization, _ddd_lib) {
    ddd_lib=_ddd_lib;

    modern.d3_integration();

    run_visualization();
    rects_chain_visualization.run(months);
  });
};

// the visualization have already started (or can do it) in the loading external files/resources [require.js]
function run_visualization(){
  d3.select("svg").attr("width", w).attr("height", h).style("background", "gray");

}

// using requirejs plugin to call the init function
require(['domReady','modern', 'model'], function (domReady,  _modern, model ) {
  domReady(function () {
function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}
    var id=getURLParameter("id");

//     from = $("#datepicker").val().split("-");
// f = new Date(from[2], from[1] - 1, from[0]);

    modern=elavio;
    modern.d3_integration();

    modern.birds.get_user(id, function(x) {
      function getDate(s){

        var from=s.split("/");
        console.log(from);
        return new Date(from[2],from[0]-1, from[1]);
      }
      console.dir(x);
      var  started=getDate(x.started);
      var ended=getDate(x.ended);
      console.dir(ended);
      console.dir(started);
      
      if(ended==null || ended=="" || ended.getTime()>new Date().getTime() )
        ended=new Date();
      my_d3(model.getMonths(started, ended));

    });

    //This function is called once the DOM is ready.
    //It will be safe to query the DOM and manipulate
    //DOM nodes in this function.
    

  });
});
