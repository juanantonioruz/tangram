define([ "model", "ddd_lib", "domain_lib", "helper_view"], function(_exp, d, _domain, _viewHelper) {
  ex=_exp;
  ddd_lib=d;
  domain_lib=_domain;
  viewHelper=_viewHelper;

  var rectHeight=100;


  

  function configureRects(selection){
    return selection.attr("id", function(d) {
      return domain_lib.format(d,["month", "year"],"_");
    })
      .attr("x",  0)
      .attr("y", 0)
      .attr("width", (w/ex.months.length)-10)
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
      })
      .on("click", function(d) {
        
        var id=domain_lib.format(d,["month", "year"],"-");
        var g_related=d3.select("#"+id);

        var datum=g_related.datum();
        //        g_related.attr("x", x);

       
          
        g_related
        //movin up
          .transition()
          .duration(200)
          .attr("transform", function(d){
            return ddd_lib.translate(d);
          }
               )
//        moving down and scaling
          .transition()
          .duration(500)
          .attr("transform", function(d){
            return ddd_lib.translate(d);
          })
;

        
        g_related
          .append("text").text("eyyyy")  .attr("x", 100)
          .attr("y", 100);
        ;
        
//        g_related.select("rect")

        // console.dir(g_related.select("rect").datum().date);

        // console.dir(g_related.datum());

      })
    
    ;

  }

  var svg=d3.select("svg");
  var enter = svg.selectAll("g")
    .data(ex.months)
    .enter();
  
  var allGs=enter.append("g");

  allGs
    .call(function (selection){
      return selection.attr("display", "visible");
    })
    .attr("transform", function(d,i){ 
      var the_x=(i*(w/ex.months.length));
      // d.display_data[0].x=the_x;
      // d.display_data[0].y=100;
      // d.display_data[0].scale=1;
      // var n={x:the_x, y:100, scale:1};
      d.display_data.push({x:the_x, y:0, scale:1});
      d.display_data.push({x:the_x, y:100, scale:1});
      d.display_data.push({x:(w/2)-200, y:300, scale:3});

      return ddd_lib.translate(d);

    })
    .attr("id",  domain_lib.val(["month", "year"],"-"));  

  
  var rectsInsideGs=allGs
    .append("rect")
    .call(configureRects);

  var textsInsideGs=allGs.append("text")
    .text(domain_lib.val(["month", "year"],"-"))
    .attr("x", 0)
    .attr("y",rectHeight);
  

  svg
    .selectAll("rect").select("text")
    .data(ex.months)
    .enter()
    .append("text")
    .text(domain_lib.val(["month", "year"], "/" ))
    .attr("font-family", "sans-serif")
    .attr("font-size", "15px")
    .attr("fill", "white")
    .attr("x", 
          function(d,i){ 
            return i*(w/ex.months.length);})
    .attr("y", 
          function(d){ 
            return 300});


}
      );
