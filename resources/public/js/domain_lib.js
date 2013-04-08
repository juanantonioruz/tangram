define( ["./ddd_lib"],
        function(ddd_lib){


          function MonthDisplayable(){
            this.d3_g=null;
            this.display_data=[];
            this.display_data_pos=0;
            this.display_increment=1;
}
          

          MonthDisplayable.prototype.selectedMonth=null;
          
          MonthDisplayable.prototype.setM=function(v){
            MonthDisplayable.prototype.selectedMonth=v;
          };
          MonthDisplayable.prototype.getActualX=function(){
            var pos=this.display_data.length-1;
            if(this.display_increment==1)
              pos=0;
            return this.display_data[pos].x;
          };
          MonthDisplayable.prototype.getActualY=function(){
            var pos=this.display_data.length-1;
            if(this.display_increment==1)
              pos=0;
            return this.display_data[pos].y;


       
          };
          /**
           //check if is the same node clicked twice times // in this case hide element
           **/
          MonthDisplayable.prototype.check=function(selectedNode){

            if(this.selectedMonth==null){
                this.setM(selectedNode);
              
            }else{
              if( this.selectedMonth.attr("id")!=selectedNode.attr("id")){
               this.monthListenClick(this.selectedMonth);
                this.selectedMonth.selectAll("rect.menu").remove();
                this.setM(selectedNode);
              
              }else{
                this.setM(null);
              }
            }

          }

          MonthDisplayable.prototype.monthListenClick=function (selection){
           
            selection
            // moving up 
              .transition()
              .duration(350)
              .attr("transform", ddd_lib.translate(selection.datum().m))
            // moving down and scaling
              .each("end", function(){
                console.log("first!");
                 d3.select("svg").selectAll("path").remove();
            }
                  )
              .transition()
              .duration(250)
              .attr("transform", ddd_lib.translate(selection.datum().m))
              .each("end", function(){
                console.log("end second transition! ");
                

                var line = d3.svg.line()
                  .x(function(d) {return d[0]; })     
                  .y(function(d) { return d[1]; });


                
                var paths=[];
                //TODO is not saving the y value when display svg:g
                d3.selectAll("svg g")
                  .filter(function(d, i) { 

                    var isTrue=d.active && d.date!=selection.datum().date;
                    if(isTrue)
                      paths.push([d.m.getActualX(), d.m.getActualY()]);
                    return isTrue;
                  });
                
                console.dir(paths);
               if(selection.datum().active) 
                d3.select("svg").selectAll("path").data(paths).enter()
                  .append("path")
                  .attr("d", function(d) {
                    var sel_x=selection.datum().m.getActualX(), sel_y=selection.datum().m.getActualY();
                    var t_x=d[0], t_y=d[1];
                    // sel_y=100;
                    // sel_x=w/2;





                    var array = [[sel_x, sel_y],
                                 [t_x, t_y]];
                    return line(array) }) 
                  .attr("class", "line")
                  .style("stroke", "black" )
                  .attr('fill', 'none');
                console.dir(selection);
              }
                  );
              

          };

          function selectingRectById(d /**d= each row in d3.data(_data)**/){
            return  "svg #"+format(d,["month", "year"],"_");
          }

          function  changeColorRectRelatedOnMouseEvents(selection,  color, color2){
            return ddd_lib.mouseOverOutChangeColor.call(selection, selectingRectById, color,color2)
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
          }          

          return { MonthDisplayable:MonthDisplayable, changeColorRectRelatedOnMouseEvents:changeColorRectRelatedOnMouseEvents, format:format, val:val};
        });;







