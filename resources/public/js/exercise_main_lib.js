var circles, svg;
var w=1500;
var h=800;
var internal_padding=5;

require.config({
  urlArgs: "bust=" + (new Date()).getTime()
});


require(['domReady','modern', 'model','rects_chain_visualization','ddd_lib'], function (domReady,  _modern, model,rects_chain_visualization, _ddd_lib ) {
  var ex, ddd_lib, domain_lib, viewHelper,modern;
  
  domReady(function () {

    d3.select("svg").attr("width", w).attr("height", h).style("background", "gray");

    function getURLParameter(name) {
      return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
    }

    var id=getURLParameter("id");

    modern=elavio;
    modern.d3_integration();
    
    modern.birds.get_users(
      function(x){
        function onEvent(_event, selection, actions){

//          console.dir(actions.map(function(o){return o}));
          selection.on(_event, function(){
            var that=this;
            var _s=d3.select(that);
            actions.reduce(
              function(b,o){
                return o.call(b);
              },_s
            );
          });
        };
        function onMouseOver(selection){
          return onEvent("mouseover", selection, Array.prototype.slice.call(arguments, 1));
        };
        function onMouseOut(selection){
          return onEvent("mouseout", selection, Array.prototype.slice.call(arguments, 1));
        };
        function onMouseClick(selection){
          return onEvent("click", selection, Array.prototype.slice.call(arguments, 1));
        };

        function changeColor(color){
          return function(){
            return this.transition().duration(500).style("fill", color);
          };
        }
        function changex(x){
          return function(){
            //console.log("ayyyy");
            var actualX=d3.select(this.node()).attr("x");
            return this.transition().attr("x", actualX-x);
          };
        }
        function logProperty(name){
          return function(){
            var value=d3.select(this.node()).datum()[name];
                         console.log("the months for: "+value);
          };
        }
        function callUserMonths(){
          return function(){
            var value=d3.select(this.node()).datum()["id"];

            console.log("the months for: "+value);
             userMonths(value);
          };
        }
        function configureBasicRect(selection, x,y, width, height){
          selection.attr("y",y)
          .attr("x", x)
          .attr("height", height)
          .attr("width", width);
        }

        var lay=new _ddd_lib.HorizontalLayout({margin:100, margin_inside:50, width:w, collection:x});


        d3.select("svg").append("g").classed("user-list", "true").selectAll("rect").data(x).enter()
         .append("rect")
          .call(onMouseOver,changex(10),changeColor("red"),changeColor("green"))
          .call(onMouseOut,changex(-10),changeColor("white"))
          .call(onMouseClick,  callUserMonths())
          .call(configureBasicRect, lay.getX, /*function(d,i){ return 100*i;}*/ 0   , lay.getElementWidth(), 100)
          .style("fill", "white")
        ;
       // d3.select("svg").append("g").attr("color", "green");
//        _ddd_lib.log(x);
      }
    );
    

    // paint months user
    function userMonths(id){
       modern.birds.get_user(id, function(x) {
        function getDate(s){

        var from=s.split("/");
        //  console.log(from);
        return new Date(from[2],from[0]-1, from[1]);
        }
        //console.dir(x);
        var  started=getDate(x.started);
        var ended=getDate(x.ended);
        // console.dir(ended);
        // console.dir(started);
        
        if(ended==null || ended=="" || ended.getTime()>new Date().getTime() )
        ended=new Date();

        rects_chain_visualization.run(model.getMonths(started, ended));
        });
    }
    /*  
       
    */

  }


          );

});


