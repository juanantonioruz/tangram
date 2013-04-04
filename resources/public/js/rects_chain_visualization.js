
  //alert(ex.log(months_tree_data));
  
  var g = d3.select("svg").selectAll("g").select("text")
    .data(ex.months)
    .enter()
    .append("g")
    .attr("display", "none")

    .attr("id",  domain_lib.val(["month", "year"],"-"))  
    .append("text")
    .text(domain_lib.val(["month", "year"],"-"))
    .attr("x", function(d){return ddd_lib.random(w)}
         )
    .attr("y",400);


  
  d3.select("svg")
    .selectAll("rect")
    .data(ex.months)
    .enter()
    .append("rect")
    .attr("id", function(d) {
      return domain_lib.format(d,["month", "year"],"_");

    })
    .attr("x", 
          function(d,i){ 
            return i*(w/ex.months.length);
          })
    .attr("y", 
          function(d, i){ 
            return 100;
          })
    .attr("width", (w/ex.months.length)-10)
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
      var id=domain_lib.format(d,["month", "year"],"-");

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
            return 100});
  
