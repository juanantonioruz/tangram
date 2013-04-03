var circles, svg;
var w=1000;
var h=1000;
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

  var months_tree_data=months
    .map(
      function(o){
        var f=Object.create(o);
        f.contents=[];
        return f;
      }
    );


  var _0m=months_tree_data
    .map(
      function(o){
        return o.month;
      }
    );
  
  //alert(_0m.join(","));
  months_tree_data={
    id:0,
    month:"mio",
    contents:months_tree_data
  };
  
  //alert(ex.log(months_tree_data));
  










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
    .attr("id", function(d) {
      return format(d,["month", "year"],"_");

          })
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
  
  


/**
BEGINNIG TREE
**/

 var radius=10;

  var tree=d3.layout.tree()
    .sort(null)
    .size([400, 400])
    .children(function(d)
              {
                return (!d.contents || d.contents.length === 0) ? null : d.contents;
              });
  
  var nodes = tree.nodes(months_tree_data);
  var links = tree.links(nodes);
  

 var layoutRoot = d3.select("svg")
     .append("g")
     .attr("class", "container")
     .attr("transform", "translate( 100,400)");

  var link = d3.svg.diagonal()
    .projection(function(d)
                {
                  return [d.y, d.x];
                });

  layoutRoot.selectAll("path.link")
    .data(links)
    .enter()
    .append("path")
    .attr("class", "link")
    .attr("d", link);


 /*
     Nodes as
     <g class="node">
         <circle class="node-dot" />
         <text />
     </g>
  */
 var nodeGroup = layoutRoot.selectAll("g.node")
     .data(nodes)
     .enter()
     .append("g")
     .attr("class", "node")
     .attr("transform", function(d)
     {
         return "translate(" + d.y + "," + d.x + ")";
     });

 nodeGroup.append("circle")
     .attr("class", "node-dot")
     .attr("r", radius);


function changeColor(selection_fn, color){
  return function(d) {
    var selection_str=selection_fn(d);
    var e=d3.select(selection_str);    
    e.transition().style("fill", color);
//      alert(ev.date);
    }
};


var ap=nodeGroup.append("text");

function activate(){

}

ap.ey=function(event, color){
  var originalColor=this.style("fill");
  alert(originalColor);
  
  function selection_fn(d){
   return  "svg #"+format(d,["month", "year"],"_");
  }

  return this.on("mouseover", changeColor(selection_fn, color))
  .on("mouseout", changeColor(selection_fn, originalColor));
//  return this.on(event, changeColor(color));
};

 ap
     .attr("text-anchor", function(d)
     {
         return d.children ? "end" : "start";
     })
     .attr("dx", function(d)
     {
         var gap = 2 * radius;
         return d.children ? -gap : gap;
     })
     .attr("dy", 3)
    .style("fill", "white")
   // .on("mouseover", changeColor("blue"))
    .ey("", "red")
     .text(function(d)
     {
         return d.month;
     });
  
;


console.dir(ap);

// TRYING TO reuse components or callbacks

/**
   to reuse callbacks it must been the same data in the same order 

   to reuse components ??

   it would be the same as to clone ".on("event",callback)" but with
   eventType and callback differences


**/


}


require(['domReady'], function (domReady) {
  domReady(function () {
    my_d3();
    //This function is called once the DOM is ready.
    //It will be safe to query the DOM and manipulate
    //DOM nodes in this function.
  });
});
