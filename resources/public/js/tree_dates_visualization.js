  define([ "model", "ddd_lib", "domain_lib", "helper_view"], function(_exp, d, _domain, _viewHelper) {
    ex=_exp;
    ddd_lib=d;
    domain_lib=_domain;
    viewHelper=_viewHelper;



  /**
     BEGINNIG TREE
  **/

  var radius=10;

  
  

  var link = d3.svg.diagonal()
    .projection(function(d)
                {
                  return [d.y, d.x];
                });

  var layoutRoot = d3.select("svg")
    .append("g")
    .attr("class", "container")
    .attr("transform", "translate( 100,00)");
  

  var tree=d3.layout.tree()
    .sort(null)
    .size([800, 1100])
    .separation(function separation(a, b) {
      // console.log("A");
      // console.dir(a);
      // console.log("B");
      // console.dir(b);
      return 1;
})
    .children(function(d)
              {
                return (!d.contents || d.contents.length === 0) ? null : d.contents;
              });


  function updateNodesLinks(data_tree){
    var  nodes = tree.nodes(data_tree);
    
    var links = tree.links(nodes);

    d3.selectAll("g.container").selectAll("path").remove();
    d3.selectAll("g.container g.node").remove();



    var path_links=layoutRoot.selectAll("path.link")
      .data(links);
    path_links.enter()
      .append("path")
      .attr("class", function(d){

        return "link";
      }
           )
      .attr("d", link);
    //    path_links.exit().transition().style("stroke-opacity", 0.1).remove()


    var nodeGroup = layoutRoot.selectAll("g.node")
      .data(nodes)
      .enter().append("g");

    nodeGroup
      .attr("class", "node")
      .attr("transform", function(d)
            {
              return "translate(" + d.y + "," + d.x + ")";
            });

    nodeGroup.append("circle")
      .call(domain_lib.changeColorRectRelatedOnMouseEvents, "black", "yellow")
      .attr("class", "node-dot")
      .attr("r", radius)
      .on("click", function(d,i){
        //ex.log(d);
        if(i==0){
          if(months_tree_data.contents.length == 0){
            months_tree_data.contents=months_tree_data_all;
          }else{
            months_tree_data.contents=[];
            months_tree_data.children=[];
          }
        }else{
          if(d.contents.length>0){
            d.contents=[];
            d.children=[];
          }else{
            var days=ex.getDaysOfTheMonth(d.date);
            var n_data=days.map(
              function(o){
                return {id:2, day:o.getDate(), month:o.getMonth()+1, year:o.getFullYear(), date:o, contents:[]};
              });
            d.contents=n_data;

          };

          
        };
        updateNodesLinks(months_tree_data);

      });



    var ap=layoutRoot.selectAll("g.node")
    ap.append("text")
      .attr("dx", function(d)
            {
              var gap = (2 * radius);
              return d.children ? -(gap+30) : gap;
            })
      .call(domain_lib.changeColorRectRelatedOnMouseEvents, "green", "violet")
      .text(function(d,i)
            {
              if(d.day!=undefined)
                return d.day;
              else
                return domain_lib.format(d,["month", "year"],"_");
            })
    ;

    //    console.dir(ap);
  }

  var months_tree_data_all=viewHelper.adaptToTreeData(ex.months);
  var months_tree_data={
    id:0,
    month:"root",
    contents:[]
  };
  updateNodesLinks(months_tree_data);
 

  });
