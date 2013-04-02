var circles, svg;
var w=1000;
var h=500;
var internal_padding=5;


function random(v){
  return Math.random()*v;
};

function format(d, keyArray, join){
    var m=keyArray.map(function(o){return d[o]});

    var h=m.join(join);
  return "_"+h;
}

function val(keyArray, join){
  return function(d){
    return format(d,keyArray,join);
  };
  
};



var ex;

function my_d3(){

  require(["experiments"], function(exp) {
    ex=this;
    ex.the_data=this.months;

    //    ex.the_log=log;
    draw();
  });

  


};

function draw(){
  d3.select("svg").attr("width", w).attr("height", h).style("background", "gray");
  //  alert(log(months));


  var g = d3.select("svg").selectAll("g").select("text")
    .data(ex.the_data)
    .enter()
    .append("g")
    .attr("display", "none")

    .attr("id",  val(["month", "year"],"-"))  
    .append("text")
    .text(val(["month", "year"],"-"))
  .attr("x", function(d){return random(w)}
         )
    .attr("y",400);


  
  d3.select("svg")
    .selectAll("rect")
    .data(ex.the_data)
    .enter()
    .append("rect")
    .attr("x", 
          function(d,i){ 
            return i*(w/ex.the_data.length);
          })
    .attr("y", 
          function(d, i){ 
            return 100;
          })
    .attr("width", (w/ex.the_data.length)-10)
    .attr("height", 100)
    .attr("fill-opacity", function(d,i){
      if(ex.isTodayMonthYear(d.date))
        return 1;
      else if(ex.isTodayBeforeMonthYear(d.date))
        return 0.6;
      else
        return 0.2;
    }
         )
    .on("mouseover", function(ev) {
      
      d3.select(this).transition().style("fill", "red");
    })
    .on("mouseout", function(ev) {
      var sel= d3.select("svg rect");
      d3.select(this).transition().style("fill", "white");
    })
    .on("click", function(d) {
      var id=format(d,["month", "year"],"-");

      var text_related=d3.select("svg #"+id+" text");
      var x=d3.select(this).attr("x");


   

      var g_related=d3.select("svg #"+id);
      g_related.attr("x", x);

      g_related.transition()
        .duration(2000)
        .attr("display", "visible")

        .attr("transform", "translate("+(x-text_related.attr("x"))+",-200)");

    })
    .style("fill", "white");
  

  d3.select("svg")
    .selectAll("rect").select("text")
    .data(ex.the_data)
    .enter()
    .append("text")
    .text(val(["month", "year"], "/" ))
    .attr("font-family", "sans-serif")
    .attr("font-size", "15px")
    .attr("fill", "white")
    .attr("x", 
          function(d,i){ 
            return i*(w/ex.the_data.length);})
    .attr("y", 
          function(d){ 
            return 100});
  
  





}


require(['domReady'], function (domReady) {
  domReady(function () {
    my_d3();
    //This function is called once the DOM is ready.
    //It will be safe to query the DOM and manipulate
    //DOM nodes in this function.
  });
});
