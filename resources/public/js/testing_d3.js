  var circles, svg;
var w=1000;
var h=500;

function my_d3(){
 
  // console.info(toType(bird.greeting));
  d3.select("svg").attr("width", w).attr("height", h);

 

  function xPosition(dataset){
    return function(d, i){
      return i * (w / dataset.length);
    };
  }
  
  function  dataValue(influence){
    if(influence === undefined) return function(d){return d.value;
                                           };
    
    return  function(d){return influence.call(this, d.value);
                };
  }
 
 //    var dataset = [ 5, 10, 15, 20, 25 ];
  var dataset = [];
  for (var i = 0; i < 25; i++) {
    var newNumber = Math.random() * 30;
    dataset.push({value:newNumber, id:i});
  }
  d3.select("body").select("div").selectAll("div").data(dataset).enter().append("div").style("height", function(d){return 10*d.value+"px";
                                                                                                           }
                                                                                            ).style("vertical-align","bottom").classed("bar", true).text(dataValue);


 
  var scale=d3.scale.linear()
    .domain([0, d3.max(dataset)])
    .range([2, w]);
  
  var xAxis = d3.svg.axis();
  xAxis.scale(scale);

  d3.select("svg").selectAll("circle").data(dataset).enter().append("circle").
    attr("cx", xPosition(dataset)).
    attr("fill-opacity", 0.5).
    attr("cy", 150).
    style("fill", "white").
    attr("r", dataValue(function(i){ return i*3;
                            })
        );
  var wScale = function(d){

    return (d3.scale.linear()
            .domain([0, d3.max(dataset, function(d) { return d.value; })])
            .range([2, (w/dataset.length)-4]))(d);

  };
  var hScale = function(d){

    var res= (d3.scale.linear()
              .domain([0, d3.max(dataset, function(d) { return d.value; })])
              .range([2, (h/2)]))(d);
    return res;
  };

  var yScale = function(d){

    var res= (d3.scale.linear()
              .domain([0, d3.max(dataset, function(d) { return d.value; })])
              .range([2, (h/2)]))(d);
    return h-100-res;
  };


  d3.select("svg").selectAll("rect").data(dataset).enter().append("rect")
    .attr("x", xPosition(dataset))

    .attr("y", dataValue(yScale))
    .attr("width", dataValue(wScale))
    .attr("height", dataValue(hScale))
    .attr("fill-opacity", 0.2)
    .style("fill", "cyan")
    .attr("stroke", "cyan")
    .attr("stroke-width", 1);
  


  



  /**
     var dataset = [ 5, 10, 15, 20, 25 ];

     d3.select("body").selectAll("p")
     .data(dataset)
     .enter()
     .append("p")
     .text("New paragraph!");

  **/


  svg =d3.select("svg");

  circles = svg.selectAll("circle");
  d3.select("body").style("background-color", "cyan");
  svg.style("background-color", "pink");


  d3.select("svg").append("g")
    .attr("class", "axis")
    .attr("transform", "translate(0," + (h - 40) + ")")
    .call(xAxis);

  d3.selectAll("rect")
    .on("click", function(ev) {
      alert("click"+JSON.stringify(ev, null, 4));
      console.dir(ev);
    });
};

function remove(){
  var circle = svg.selectAll("circle")
    .data([32, 57]);
  circle.exit().remove();
}
function random(){return Math.random()*h;
         };
function randomh(d){ return Math.random(d.value)*w};

function   listen(){

  d3.select("h1").style("color", "orange");

  //  console.log();
  

  circles.data([32, 57, 112]);


  circles.transition().duration(1000)
    .delay(function(d, i) {
      if(i<3)
        return i * 1000 ;
      return 0;
    })
    .style("fill", "steelblue")
    .attr("cy", function(d){return d.value;})
    .attr("cx", randomh)
    .attr("r", 30);

  

  var process=d3.select("svg").selectAll("circle").data([1,2,3,4]);
  var my_select=process.enter();
  var my_action=my_select.insert("circle");
  my_action.attr("cx", randomh).attr("cy", random).attr("r", 0).style("fill", "cyan").transition().attr("r", 30);

  process.exit().style("fill", "yellow").transition().attr("r", 15);;

}

