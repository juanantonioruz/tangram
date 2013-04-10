define([ "model", "ddd_lib", "domain_lib", "helper_view"], function(_exp, d, _domain, _viewHelper) {
  ex=_exp;
  ddd_lib=d;
  domain_lib=_domain;
  viewHelper=_viewHelper;

  var rectHeight=100;


  

  function configureRects(selection, the_months){
    return selection.attr("id", function(d) {
      return domain_lib.format(d,["month", "year"],"_");
    })
      .attr("x",  0)
      .attr("y", 0)
      .attr("width", (w/the_months.length)-10)
      .attr("height", rectHeight)
      .style("fill", "white")
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
        //selectedNode is a svg:g component
        
      })
      .on("click", function(d) {
        
        //selectedNode is a svg:g component
        var selectedNode=d3.select(this.parentNode);

        //only listen click if is expanded
        if(selectedNode.datum().m.display_increment>0){

          selectedNode.datum().m.check(selectedNode);

          selectedNode.datum().m.monthListenClick(selectedNode);

          selectedNode
            .append("rect")
            .classed("menu", "true")
            .attr("x",  0)
            .attr("y", 0)
            .attr("width", 10)
            .attr("height", 10)
            .style("fill", "black" )
            .on("click", function(){
              selectedNode.datum().m.check(selectedNode);
              selectedNode.selectAll("rect.menu").remove();
              selectedNode.datum().m.monthListenClick(selectedNode);
            });          

          
          

//          console.dir(selectedNode.datum());

        }
      })
    
    ;


  }

  function run(the_months){
var svg=d3.select("svg");
   svg.selectAll("g").filter(
     function(d,i){
       return !d3.select(this).classed("user-list");

     }
                           ).remove();
    svg.selectAll("path").remove();
  var enter = svg.selectAll("g")
    .data(the_months)
    .enter();
  
  var allGs=enter.append("g");

  allGs
    .call(function (selection){

      return selection.attr("display", "visible");
    })
    .attr("transform", function(d,i){ 
      // extending svg:g.d3 object instead extending function class beacuse is now more agil 
      // but maybe can be shared this strategy because object must contain the mutable data and function class the inmutable (in this case)
      d.ayuda=function(){ console.log("you are using help mode in this selection"+this.date)};

      var m=new domain_lib.MonthDisplayable();
      d.monthListenClick=m.monthListenClick;
      d.m=m;
      var the_x=(i*(w/the_months.length));
      
      m.display_data.push({x:the_x, y:h-120, scale:1});
      m.display_data.push({x:the_x, y:0, scale:0.1});
      m.display_data.push({x:(w/2)-200, y:50, scale:6});

      return ddd_lib.translate(d.m);

    })
    .attr("id",  domain_lib.val(["month", "year"],"-"));  

  
  var rectsInsideGs=allGs
    .append("rect")
    .call(configureRects, the_months);

    var the_menu_rect=allGs
    .append("rect");
  the_menu_rect.classed("active", "true")
    .attr("x",  10)
    .attr("y", 10)
    .attr("width", 10)
    .attr("height", 10)
    .style("fill", function(d){
      return d.active? "green" :"red";
    }
          )
    .on("click", function(d){
      (d.active? d.active=false: d.active=true);
      var d3_element=d3.select(this);
      (!d.active? d3_element.style("fill", "red") :d3_element.style("fill", "green"));
    });
  var textsInsideGs=allGs.append("text")
    .text(domain_lib.val(["month", "year"],"-"))
    .attr("x", 0)
      .attr}("y",rectHeight);
  



  
return {run:run};

}
      );

// DOCUMENTATION

/*
  var id=domain_lib.format(d,["month", "year"],"-");
  var g_related=d3.select("#"+id);

  var datum=g_related.datum();
  // you can select through id component or with the parentChild relation between components
  */

// TWO WAYS FOR DOING THE SAME, CALL  A FUNCTION WITH THE NODE CONTEXT
//1-        selectedNode.call(functio(selectNode){});
//2-        monthListenClick(selectedNode);        
